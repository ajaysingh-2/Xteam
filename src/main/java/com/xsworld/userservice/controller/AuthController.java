package com.xsworld.userservice.controller;
import com.xsworld.userservice.exception.UserException;
import com.xsworld.userservice.helper.JwtHelper;
import com.xsworld.userservice.model.RefreshToken;
import com.xsworld.userservice.model.User;
import com.xsworld.userservice.request.LoginRequest;
import com.xsworld.userservice.request.RefreshTokenRequest;
import com.xsworld.userservice.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xsworld.userservice.service.AuthService;
import com.xsworld.userservice.response.AuthResponse;
import static com.xsworld.userservice.constants.LoggerConstants.*;
import static com.xsworld.userservice.constants.MethodsNameConstants.CREATE_USER_HANDLER;
import static com.xsworld.userservice.constants.MethodsNameConstants.LOGIN_USER_HANDLER;


@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private  AuthService authService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private RefreshTokenService refreshTokenService;


    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshJwtToken(@RequestBody RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());

        User user = refreshToken.getUser();

        // Use user's authorities directly from UserDetails
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        String newJwt = jwtHelper.generateToken(authentication);

        AuthResponse authResponse = AuthResponse.builder()
                .jwt(newJwt)
                .refreshToken(refreshToken.getRefreshToken())
                .message("Token Refreshed Successfully")
                .build();

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody User user) throws UserException {
        log.info(String.format(ATTEMPT_TO_SIGN_UP_WITH_MAIL, CREATE_USER_HANDLER, user.getUsername()));
        AuthResponse response = authService.signup(user);
        log.info(String.format(SIGN_UP_SUCCESSFUL, CREATE_USER_HANDLER, user.getUsername()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUserHandler(@Valid @RequestBody LoginRequest loginRequest) {
        log.info(String.format(ATTEMPT_TO_LOGIN_WITH_MAIL, LOGIN_USER_HANDLER, loginRequest.getUsername()));
        AuthResponse response = authService.login(loginRequest);
        log.info(String.format(SIGN_IN_SUCCESSFUL, LOGIN_USER_HANDLER, loginRequest.getUsername()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}