package com.xsworld.userservice.service;
import com.xsworld.userservice.exception.UserException;
import com.xsworld.userservice.model.User;
import com.xsworld.userservice.request.LoginRequest;
import com.xsworld.userservice.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(User user) throws UserException;
    AuthResponse login(LoginRequest loginRequest);
}
