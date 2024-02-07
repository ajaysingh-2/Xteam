package com.xsworld.userservice.service.serviceimpl;
import com.xsworld.userservice.exception.UserException;
import com.xsworld.userservice.helper.JwtHelper;
import com.xsworld.userservice.model.RefreshToken;
import com.xsworld.userservice.model.User;
import com.xsworld.userservice.request.LoginRequest;
import com.xsworld.userservice.response.AuthResponse;
import com.xsworld.userservice.service.AuthService;
import com.xsworld.userservice.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {


    @Autowired
    private  JwtHelper jwtHelper;

    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private CustomUserServiceImpl customUserServiceImpl;

    @Autowired
    private RefreshTokenService refreshTokenService;


    @Override
    public AuthResponse signup(User user) throws UserException {
        String email = user.getUsername();
        String password = user.getPassword();

        User createdUser = userServiceImpl.createUser(user);

        // Authenticate the new user and generate a JWT token
        Authentication authentication = authenticate(email, password);
        String token = jwtHelper.generateToken(authentication);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(email);

        // Create an AuthResponse object with the JWT token and a success message
        AuthResponse authResponse = AuthResponse.builder()
                .jwt(token)
                .message("Sign-up Success")
                .refreshToken(refreshToken.getRefreshToken())
                .build();

        return authResponse;
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Authenticate the user and generate a JWT token
        Authentication authentication = authenticate(email, password);
        String token = jwtHelper.generateToken(authentication);

        // Retrieve or generate a refresh token
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(email);

        // Create an AuthResponse object with the JWT token, refresh token, and a success message
        AuthResponse authResponse = AuthResponse.builder()
                .jwt(token)
                .refreshToken(refreshToken.getRefreshToken())
                .message("Sign-in Success")
                .build();

        return authResponse;
    }



    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customUserServiceImpl.loadUserByUsername(userName);

        if (userDetails == null) {
            log.error("Invalid Username: {}", userName);
            throw new BadCredentialsException("Invalid Username");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            log.error("Invalid Password for user: {}", userName);
            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
