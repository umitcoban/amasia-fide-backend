package com.umityasincoban.amasia_fide.controller;

import com.umityasincoban.amasia_fide.dto.LoginDTO;
import com.umityasincoban.amasia_fide.dto.RegisterDTO;
import com.umityasincoban.amasia_fide.dto.TokenDTO;
import com.umityasincoban.amasia_fide.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public TokenDTO registerUser(@RequestBody @Valid RegisterDTO registerDTO) throws Exception{
        return userService.register(registerDTO);
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody @Valid LoginDTO loginDTO) throws Exception{
        return userService.login(loginDTO);
    }

}
