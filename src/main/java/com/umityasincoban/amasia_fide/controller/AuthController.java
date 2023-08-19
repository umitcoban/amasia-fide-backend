package com.umityasincoban.amasia_fide.controller;


import com.umityasincoban.amasia_fide.dto.LoginDTO;
import com.umityasincoban.amasia_fide.dto.RegisterDTO;
import com.umityasincoban.amasia_fide.dto.TokenDTO;
import com.umityasincoban.amasia_fide.dto.UserActivateDTO;
import com.umityasincoban.amasia_fide.entity.ApiResponse;
import com.umityasincoban.amasia_fide.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


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
    public ApiResponse<Boolean> registerUser(@RequestBody @Valid RegisterDTO registerDTO, HttpServletRequest request){
        logger.info("request '/register' : {}", registerDTO.toString());
        var apiResponse = userService.register(registerDTO, request.getHeader("Accept-Language"));
        logger.info("token: {}",apiResponse);
        return apiResponse;
    }
    @Operation(description = "To login and get tokens")
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TokenDTO login(@RequestBody @Valid LoginDTO loginDTO){
        logger.info("request '/login': {}", loginDTO);
        return userService.login(loginDTO);
    }
    @Operation(description = "Activate user by id and activation code")
    @PostMapping(path = "/activate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Boolean> activateUser (@RequestBody @Valid UserActivateDTO userActivateDTO){
        logger.info("/activateUser {}", userActivateDTO);
        return new ApiResponse<>(HttpStatus.OK.value(), userService.activateUserByEmailAndRegistrationCode(userActivateDTO), System.currentTimeMillis());
    }
    @PutMapping("/resend/{email}")
    public ApiResponse<Boolean> resendRegistrationCode(@PathVariable String email){
        logger.info("/resend/{email} {}",email);
        return new ApiResponse<>(HttpStatus.OK.value(), userService.resendRegistrationCode(email), System.currentTimeMillis());
    }

}
