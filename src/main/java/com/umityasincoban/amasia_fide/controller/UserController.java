package com.umityasincoban.amasia_fide.controller;

import com.umityasincoban.amasia_fide.dto.*;
import com.umityasincoban.amasia_fide.entity.ApiResponse;
import com.umityasincoban.amasia_fide.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @Operation(summary = "Get User By Id", description = "returns a single user by id")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found by id", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @GetMapping(path = "/{id}")
    public ApiResponse<UserDTO> getUserById(@PathVariable @Schema(description = "user id value", example = "1") long id){
        return new ApiResponse<>(HttpStatus.OK.value(), userService.getUserById(id), System.currentTimeMillis());
    }

    @GetMapping
    public ApiResponse<List<UserDTO>> getAllUsers(){
        return new ApiResponse<>(HttpStatus.OK.value(), userService.getAllUsers(), System.currentTimeMillis());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<UserDTO> createAndReturnNewUser(@RequestBody @Valid RegisterDTO registerDTO){
        return new ApiResponse<>(HttpStatus.OK.value(), userService.createNewUser(registerDTO), System.currentTimeMillis());
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<UserDTO> updateAndReturnNewUser(@RequestBody @Valid UserUpdateDTO userUpdateDTO){
        return new ApiResponse<>(HttpStatus.OK.value(), userService.getUserAndUpdate(userUpdateDTO), System.currentTimeMillis());
    }

    @PutMapping(path = "/activate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Boolean> activateUserById(@RequestBody UserActivateDTO activateDTO){
        return new ApiResponse<>(HttpStatus.OK.value(), userService.activateUserById(activateDTO), System.currentTimeMillis());
    }

}
