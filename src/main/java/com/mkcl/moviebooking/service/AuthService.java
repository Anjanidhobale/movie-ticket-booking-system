package com.mkcl.moviebooking.service;

import com.mkcl.moviebooking.dto.LoginRequest;
import com.mkcl.moviebooking.dto.LoginResponse;
import com.mkcl.moviebooking.dto.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}