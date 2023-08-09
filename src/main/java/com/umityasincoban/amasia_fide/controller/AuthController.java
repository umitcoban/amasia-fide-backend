package com.umityasincoban.amasia_fide.controller;

import com.umityasincoban.amasia_fide.dto.LoginDTO;
import com.umityasincoban.amasia_fide.dto.RegisterDTO;
import com.umityasincoban.amasia_fide.dto.TokenDTO;
import com.umityasincoban.amasia_fide.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());
    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(description = "test")
    public TokenDTO registerUser(@RequestBody @Valid RegisterDTO registerDTO) throws Exception{
        logger.info("request '/register' : "+ registerDTO.toString());
        return userService.register(registerDTO);
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody @Valid LoginDTO loginDTO) throws Exception{
        return userService.login(loginDTO);
    }

}
