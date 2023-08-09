package com.umityasincoban.amasia_fide.service;

import com.umityasincoban.amasia_fide.dto.*;
import com.umityasincoban.amasia_fide.entity.User;
import com.umityasincoban.amasia_fide.exception.UserAlreadyExistException;
import com.umityasincoban.amasia_fide.exception.UserNotFoundException;
import com.umityasincoban.amasia_fide.mapper.UserMapper;
import com.umityasincoban.amasia_fide.repository.UserRepository;
import com.umityasincoban.amasia_fide.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public TokenDTO login(LoginDTO request) {
        try {
            var user = userRepository.findByEmailAndPassword(request.username(), passwordEncoder.encode(request.password())).orElseThrow(UserNotFoundException::new);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
            var jwt = jwtService.generateToken(user);
            return new TokenDTO(jwt, System.currentTimeMillis(), 200);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw e;
        }
    }

    public TokenDTO register(RegisterDTO request) {
        userRepository.findByEmailOrCitizenNumberOrPhone(request.email(), request.citizenNumber(), request.phone()).ifPresent(user -> {
            throw new UserAlreadyExistException(String.format("%s email addresses or %s phone or %s citizen number already used", user.getEmail(),user.getPhone(), user.getCitizenNumber()));
        });
        var user = userMapper.registerDtoToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        emailService.sendWelcomeEmail(request.email());
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
