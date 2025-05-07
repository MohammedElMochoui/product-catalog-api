package com.example.product_catalog_api.user.controller;

import com.example.product_catalog_api.user.dto.request.LoginRequestDTO;
import com.example.product_catalog_api.user.dto.request.RegisterRequestDTO;
import com.example.product_catalog_api.user.dto.response.LoginResponseDTO;
import com.example.product_catalog_api.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> getToken(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = userService.loginUser(loginRequestDTO.username(), loginRequestDTO.password());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        userService.registerUser(registerRequestDTO.username(), registerRequestDTO.password());
        return ResponseEntity.ok("User with username " + registerRequestDTO.username() + " registered successfully!");
    }

}