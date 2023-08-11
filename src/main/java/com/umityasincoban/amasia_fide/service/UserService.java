package com.umityasincoban.amasia_fide.service;

import com.umityasincoban.amasia_fide.dto.*;
import com.umityasincoban.amasia_fide.entity.User;

import java.util.List;

public interface UserService {
    TokenDTO login(LoginDTO request);

    TokenDTO register(RegisterDTO request);

    UserDTO getUserById(long id);

    UserDTO getUserByEmail(String email);

    boolean activateUserById(UserActivateDTO userActivateDTO);

    UserDTO createNewUser(RegisterDTO registerDTO);

    UserDTO getUserAndUpdate(UserUpdateDTO userUpdateDTO);

    List<UserDTO> getAllUsers();

}
