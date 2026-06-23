package com.mkcl.moviebooking.service.impl;

import org.springframework.security.crypto.password.
        PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mkcl.moviebooking.dto.LoginRequest;
import com.mkcl.moviebooking.dto.LoginResponse;
import com.mkcl.moviebooking.dto.RegisterRequest;
import com.mkcl.moviebooking.entity.Role;
import com.mkcl.moviebooking.entity.User;
import com.mkcl.moviebooking.exception.InvalidCredentialsException;
import com.mkcl.moviebooking.repository.UserRepository;
import com.mkcl.moviebooking.security.JwtUtil;
import com.mkcl.moviebooking.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl
        implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    public String register(
            RegisterRequest request
    ) {

    	if(userRepository.findByEmail(request.getEmail()).isPresent()) {
    	    throw new RuntimeException("Email already registered");
    	}
    	
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(Role.CUSTOMER)
                .build();

        userRepository.save(user);

        return "User Registered Successfully";
    }

    @Override
    public LoginResponse login(
            LoginRequest request
    ) {

        User user =
                userRepository.findByEmail(
                        request.getEmail()
                ).orElseThrow(
                        () -> new InvalidCredentialsException(
                                "Invalid Credentials"
                        		));

        boolean matched =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!matched) {
        	throw new InvalidCredentialsException(
        	        "Invalid Credentials"
        	);
        }

        String token =
                jwtUtil.generateToken(
                        user.getEmail()
                );

        return LoginResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}