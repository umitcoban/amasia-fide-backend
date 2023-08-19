package com.umityasincoban.amasia_fide.service;

import com.umityasincoban.amasia_fide.dto.*;
import com.umityasincoban.amasia_fide.entity.ApiResponse;
import com.umityasincoban.amasia_fide.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    TokenDTO login(LoginDTO request);

    ApiResponse<Boolean> register(RegisterDTO registerDTO, String locale);

    UserDTO getUserById(long id);

    UserDTO getUserByEmail(String email);

    boolean activateUserByEmailAndRegistrationCode(UserActivateDTO userActivateDTO);

    UserDTO createNewUser(RegisterDTO registerDTO);

    UserDTO getUserAndUpdate(UserUpdateDTO userUpdateDTO);

    List<UserDTO> getAllUsers();

    boolean resendRegistrationCode(String email);

}
