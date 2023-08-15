package com.umityasincoban.amasia_fide.service;

import com.umityasincoban.amasia_fide.dto.*;
import com.umityasincoban.amasia_fide.entity.EmailTemplate;
import com.umityasincoban.amasia_fide.entity.Language;
import com.umityasincoban.amasia_fide.exception.UserAlreadyExistException;
import com.umityasincoban.amasia_fide.exception.UserNotFoundException;
import com.umityasincoban.amasia_fide.mapper.UserMapper;
import com.umityasincoban.amasia_fide.repository.UserRepository;
import com.umityasincoban.amasia_fide.util.JwtUtil;
import com.umityasincoban.amasia_fide.util.TemplateUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


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

    public TokenDTO login(LoginDTO request) {
        try {
            var user = userRepository.findByEmailAndPassword(request.username(), passwordEncoder.encode(request.password())).orElseThrow(UserNotFoundException::new);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
            var jwt = jwtService.generateToken(user);
            return new TokenDTO(jwt, System.currentTimeMillis(), 200);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public TokenDTO register(RegisterDTO registerDTO, String locale) {
        userRepository.findByEmailOrCitizenNumberOrPhone(registerDTO.email(), registerDTO.citizenNumber(), registerDTO.phone()).ifPresent(user -> {
            throw new UserAlreadyExistException(String.format("%s email addresses or %s phone or %s citizen number already used", user.getEmail(),user.getPhone(), user.getCitizenNumber()));
        });
        logger.info("locale {}", locale);
        var user = userMapper.registerDtoToUser(registerDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistrationCode(random.nextInt(100000, 999999));
        user.setRegistrationCodeLastUsedTime(ZonedDateTime.now());
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        Language localeLanguage = languageService.getLanguageByKey("tr-TR");
        EmailTemplate registrationCodeTemplate = emailTemplateService.getEmailTemplateByNameAndLanguage("registration_code", localeLanguage);
        var parameters = new HashMap<String, String>();
        parameters.put("NAME", registerDTO.firstName());
        parameters.put("REGISTRATION_CODE", String.valueOf(user.getRegistrationCode()));
        String body = TemplateUtil.convertTemplate(registrationCodeTemplate.getBody(), parameters);
        emailService.sendEmail(registerDTO.email(),registrationCodeTemplate.getSubject().toUpperCase(), body);
        return new TokenDTO(jwt, System.currentTimeMillis(), 200);
    }

    @Override
    public UserDTO getUserById(long id) {
        return userMapper.userToUserDTO(userRepository.findByUserId(id).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userMapper.userToUserDTO(userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public boolean activateUserById(UserActivateDTO userActivateDTO) {
        var existUser = userRepository.findByUserId(userActivateDTO.id()).orElseThrow(UserNotFoundException::new);
        userRepository.activateUserById(existUser.getUserId(), userActivateDTO.status());
        existUser.setActive(true);
        return existUser.isActive();
    }

    @Override
    public UserDTO createNewUser(RegisterDTO registerDTO) {
        userRepository.findByEmailOrCitizenNumberOrPhone(registerDTO.email(), registerDTO.citizenNumber(), registerDTO.phone()).ifPresent((user) -> {
            throw new UserAlreadyExistException(String.format("%s email addresses or %s phone or %s citizen number already used", user.getEmail(),user.getPhone(), user.getCitizenNumber()));
        });
        var user = userMapper.registerDtoToUser(registerDTO);
        user.setPassword(passwordEncoder.encode(registerDTO.password()));
        return userMapper.userToUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUserAndUpdate(UserUpdateDTO userUpdateDTO) {
        var existUser = userRepository.findByUserId(userUpdateDTO.id()).orElseThrow(UserNotFoundException::new);
        if(userUpdateDTO.firstName() != null)
            existUser.setFirstName(userUpdateDTO.firstName());

        return userMapper.userToUserDTO(userRepository.save(userMapper.userUpdateDTOToUser(userUpdateDTO)));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userMapper.userToUsersDTO(userRepository.findAll());
    }


}
