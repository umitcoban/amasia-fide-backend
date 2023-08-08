package com.umityasincoban.amasia_fide.service;

import com.mchange.util.AlreadyExistsException;
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
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public TokenDTO login(LoginDTO request) {
        try {
            Optional<User> existUser = userRepository.findByEmail(request.username());
            if(existUser.isEmpty())
                throw new UserNotFoundException("Email is not exist!");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
            var user = userRepository.findByEmail(request.username()).orElseThrow();
            var jwt = jwtService.generateToken(user);
            emailService.sendWelcomeEmail(request.username());
            return new TokenDTO(jwt, System.currentTimeMillis(), 200);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw e;
        }
    }

    public TokenDTO register(RegisterDTO request) throws AlreadyExistsException {
        Optional<User> existUserByEmail = userRepository.findByEmailOrCitizenNumberOrPhone(request.email(), request.citizenNumber(), request.phone());
        if (existUserByEmail.isPresent()) throw new UserAlreadyExistException("Email is already exist");
        var user = userMapper.registerDtoToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        emailService.sendWelcomeEmail(request.email());
        return new TokenDTO(jwt, System.currentTimeMillis(), 200);
    }

    public User getUserById(long id) {
        return userRepository.findByUserId(id).orElseThrow();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(RuntimeException::new);
    }

    public User getUserAndUpdate(User user) {
        User foundedUser = userRepository.findByEmail(user.getEmail()).orElseThrow(RuntimeException::new);
        user.setUserId(foundedUser.getUserId());
        return userRepository.saveAndFlush(user);
    }

    @CacheEvict(value = "usersDTO", allEntries = true)
    public boolean activateUserById(UserActivateDTO activateDTO) {
        try {
            userRepository.activateUserById(activateDTO.id(), activateDTO.status());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @CacheEvict(value = "usersDTO", allEntries = true)
    public User createNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.saveAndFlush(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Cacheable(value = "usersDTO")
    public List<UserDTO> getAllUsersDto() {
        return userMapper.userToUsersDTO(getAllUsers());
    }

    public UserDTO getUserDtoById(long id) {
        return userMapper.userToUserDTO(getUserById(id));
    }

    @CacheEvict(value = "usersDTO", allEntries = true)
    public UserDTO createAndGetNewUserDTO(RegisterDTO registerDTO) {
        return userMapper.userToUserDTO(createNewUser(userMapper.registerDtoToUser(registerDTO)));
    }

    @CacheEvict(value = "usersDTO", allEntries = true)
    public UserDTO updateAndGetNewUserDTO(RegisterDTO registerDTO) {
        return userMapper.userToUserDTO(getUserAndUpdate(userMapper.registerDtoToUser(registerDTO)));
    }
}
