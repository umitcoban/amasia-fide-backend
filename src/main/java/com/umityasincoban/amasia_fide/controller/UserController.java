package com.umityasincoban.amasia_fide.controller;

import com.umityasincoban.amasia_fide.dto.RegisterDTO;
import com.umityasincoban.amasia_fide.dto.UserActivateDTO;
import com.umityasincoban.amasia_fide.dto.UserDTO;
import com.umityasincoban.amasia_fide.entity.ApiResponse;
import com.umityasincoban.amasia_fide.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ApiResponse<UserDTO> getUserById(@PathVariable long id){
        return new ApiResponse<>(HttpStatus.OK.value(), userService.getUserDtoById(id), System.currentTimeMillis());
    }

    @GetMapping
    public ApiResponse<List<UserDTO>> getAllUsers(){
        return new ApiResponse<>(HttpStatus.OK.value(), userService.getAllUsersDto(), System.currentTimeMillis());
    }

    @PostMapping
    public ApiResponse<UserDTO> createAndReturnNewUser(@RequestBody @Valid RegisterDTO registerDTO){
        return new ApiResponse<>(HttpStatus.OK.value(), userService.createAndGetNewUserDTO(registerDTO), System.currentTimeMillis());
    }

    @PutMapping
    public ApiResponse<UserDTO> updateAndReturnNewUser(@RequestBody @Valid RegisterDTO registerDTO){
        return new ApiResponse<>(HttpStatus.OK.value(), userService.updateAndGetNewUserDTO(registerDTO), System.currentTimeMillis());
    }

    @PutMapping("/activate")
    public ApiResponse<Boolean> activateUserById(@RequestBody UserActivateDTO activateDTO){
        return new ApiResponse<>(HttpStatus.OK.value(), userService.activateUserById(activateDTO), System.currentTimeMillis());
    }

}
