package com.umityasincoban.amasia_fide.service;

import com.umityasincoban.amasia_fide.annotation.RateLimit;
import com.umityasincoban.amasia_fide.dto.*;
import com.umityasincoban.amasia_fide.entity.*;
import com.umityasincoban.amasia_fide.exception.*;
import com.umityasincoban.amasia_fide.mapper.UserMapper;
import com.umityasincoban.amasia_fide.repository.UserRepository;
import com.umityasincoban.amasia_fide.util.JwtUtil;
import com.umityasincoban.amasia_fide.util.TemplateUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final LanguageService languageService;
    private final EmailTemplateService emailTemplateService;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private static final Random random = new Random();
    private static final int MAX_RANDOM_CODE = 999999;
    private static final int MIN_RANDOM_CODE = 100000;

    public TokenDTO login(LoginDTO request) {
        try {
            var user = userRepository.findUserByEmail(request.username()).orElseThrow(UserNotFoundException::new);
            if(!passwordEncoder.matches(request.password(), user.getPassword()))
                throw new BadCredentialsException("");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
            var jwt = jwtService.generateToken(user);
            return new TokenDTO(jwt, System.currentTimeMillis(), 200);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(e.getClass().getName());
            throw e;
        }
    }

    public ApiResponse<Boolean> register(RegisterDTO registerDTO, String locale) {
        userRepository.findByEmailOrCitizenNumberOrPhone(registerDTO.email(), registerDTO.citizenNumber(), registerDTO.phone()).ifPresent(user -> {
            throw new UserAlreadyExistException(String.format("%s email addresses or %s phone or %s citizen number already used", user.getEmail(), user.getPhone(), user.getCitizenNumber()));
        });
        logger.info("locale {}", locale);
        var user = userMapper.registerDtoToUser(registerDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistrationCode(random.nextInt(MIN_RANDOM_CODE, MAX_RANDOM_CODE));
        user.setRegistrationCreatedTime(ZonedDateTime.now());
        var role = new Role();
        role.setRole(ERole.ROLE_USER);
        var roles = new HashSet<Role>();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        Language localeLanguage = languageService.getLanguageByKey("tr-TR");
        EmailTemplate registrationCodeTemplate = emailTemplateService.getEmailTemplateByNameAndLanguage("registration_code", localeLanguage);
        var parameters = new HashMap<String, String>();
        parameters.put("NAME", registerDTO.firstName());
        parameters.put("REGISTRATION_CODE", String.valueOf(user.getRegistrationCode()));
        String body = TemplateUtil.convertTemplate(registrationCodeTemplate.getBody(), parameters);
        emailService.sendEmail(registerDTO.email(), registrationCodeTemplate.getSubject().toUpperCase(), body);
        return new ApiResponse<>(HttpStatus.OK.value(), true, System.currentTimeMillis());
    }

    @Override
    public UserDTO getUserById(long id) {
        return userMapper.userToUserDTO(userRepository.findByUserId(id).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userMapper.userToUserDTO(userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public boolean activateUserByEmailAndRegistrationCode(UserActivateDTO userActivateDTO) {
        var existUser = userRepository.findUserByEmail(userActivateDTO.email()).orElseThrow(UserNotFoundException::new);
        if(existUser.getRegistrationCodeCount() >= 3)
            throw new RateLimitExceededException();
        if(existUser.isActive())
            throw new AlreadyActivatedException();
        if (existUser.getRegistrationCode() != userActivateDTO.activationCode()) {
            incrementRegistrationCodeCount(existUser);
            throw new InvalidRegistrationCodeException();
        }
        var now = ZonedDateTime.now();
        var differenceInMinutes = ChronoUnit.MINUTES.between(existUser.getRegistrationCreatedTime(), now);
        if (differenceInMinutes >= 5)
            throw new RequestTimeOutException();
        userRepository.activateUserById(existUser.getUserId(), true);
        existUser.setActive(true);
        return existUser.isActive();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = InvalidRegistrationCodeException.class)
    public void incrementRegistrationCodeCount(User existUser) {
        logger.info("getRegistrationCodeCount: {}", existUser.getRegistrationCodeCount());
        existUser.setRegistrationCodeCount(existUser.getRegistrationCodeCount() + 1);
        existUser.setUpdatedAt(ZonedDateTime.now());
        userRepository.saveAndFlush(existUser);
    }

    @CacheEvict(value = "users")
    @Override
    public UserDTO createNewUser(RegisterDTO registerDTO) {
        userRepository.findByEmailOrCitizenNumberOrPhone(registerDTO.email(), registerDTO.citizenNumber(), registerDTO.phone()).ifPresent((user) -> {
            throw new UserAlreadyExistException(String.format("%s email addresses or %s phone or %s citizen number already used", user.getEmail(), user.getPhone(), user.getCitizenNumber()));
        });
        var user = userMapper.registerDtoToUser(registerDTO);
        user.setPassword(passwordEncoder.encode(registerDTO.password()));
        return userMapper.userToUserDTO(userRepository.save(user));
    }

    @CacheEvict(value = "users")
    @Override
    public UserDTO getUserAndUpdate(UserUpdateDTO userUpdateDTO) {
        var existUser = userRepository.findByUserId(userUpdateDTO.id()).orElseThrow(UserNotFoundException::new);
        if (userUpdateDTO.firstName() != null)
            existUser.setFirstName(userUpdateDTO.firstName());

        return userMapper.userToUserDTO(userRepository.save(userMapper.userUpdateDTOToUser(userUpdateDTO)));
    }

    @Cacheable(value = "users")
    @Override
    public List<UserDTO> getAllUsers() {
        return userMapper.userToUsersDTO(userRepository.findAll());
    }

    @Override
    @RateLimit(key = "resendRegistrationCode:#email")
    public boolean resendRegistrationCode(String email) {
        var existUser = userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
        var language = languageService.getLanguageByKey("tr-TR");
        var emailTemplate = emailTemplateService.getEmailTemplateByNameAndLanguage("registration_code", language);
        var parameters = new HashMap<String, String>();
        var newCode = random.nextInt(MIN_RANDOM_CODE, MAX_RANDOM_CODE);
        parameters.put("NAME", existUser.getFirstName());
        parameters.put("REGISTRATION_CODE", String.valueOf(newCode));
        var emailBody = TemplateUtil.convertTemplate(emailTemplate.getBody(), parameters);
        emailService.sendEmail(existUser.getEmail(), emailTemplate.getSubject(), emailBody);
        existUser.setRegistrationCode(newCode);
        existUser.setRegistrationCreatedTime(ZonedDateTime.now());
        userRepository.save(existUser);
        return true;
    }


}
