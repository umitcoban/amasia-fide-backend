package com.umityasincoban.amasia_fide.controller;


import com.umityasincoban.amasia_fide.dto.LoginDTO;
import com.umityasincoban.amasia_fide.dto.RegisterDTO;
import com.umityasincoban.amasia_fide.dto.TokenDTO;
import com.umityasincoban.amasia_fide.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class.getName());
    private final UserService userService;
    @Autowired
    public AuthController(UserService userService){
        this.userService = userService;
    }

    @Operation(description = "Create new user. User default inactive")
    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TokenDTO registerUser(@RequestBody @Valid RegisterDTO registerDTO){
        logger.info("request '/register' : {}", registerDTO.toString());
        return userService.register(registerDTO);
    }
    @Operation(description = "To login and get tokens")
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TokenDTO login(@RequestBody @Valid LoginDTO loginDTO){
        logger.info("request '/login': {}", loginDTO);
        return userService.login(loginDTO);
    }

}
