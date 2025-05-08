package com.example.product_catalog_api.user.service;

import com.example.product_catalog_api.security.JwtTokenProvider;
import com.example.product_catalog_api.user.entity.Role;
import com.example.product_catalog_api.user.entity.RoleEnum;
import com.example.product_catalog_api.user.entity.User;
import com.example.product_catalog_api.user.repository.RoleRepository;
import com.example.product_catalog_api.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public String loginUser(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return jwtTokenProvider.generateToken(authentication);
    }

    public void registerUser(String username, String password) {
        if (userRepository.existsByUsername(username))
            throw new IllegalArgumentException("Username already exists!");

        String encodedPassword = passwordEncoder.encode(password);
        User u = new User(username, encodedPassword);
        Role r = roleRepository.findByRole(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new IllegalArgumentException("Can't find role!"));
        u.addRole(r);

        userRepository.save(u);
    }

}