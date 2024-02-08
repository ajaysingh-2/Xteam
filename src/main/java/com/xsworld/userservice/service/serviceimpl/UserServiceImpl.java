package com.xsworld.userservice.service.serviceimpl;

import com.xsworld.userservice.exception.UserException;
import com.xsworld.userservice.helper.JwtHelper;
import com.xsworld.userservice.model.RefreshToken;
import com.xsworld.userservice.model.User;
import com.xsworld.userservice.repository.UserRepository;
import com.xsworld.userservice.response.UserResponse;
import com.xsworld.userservice.service.RefreshTokenService;
import com.xsworld.userservice.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import static com.xsworld.userservice.constants.ExceptionConstants.*;
import static com.xsworld.userservice.constants.LoggerConstants.*;
import static com.xsworld.userservice.constants.MethodsNameConstants.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User findByUserId(String userId) throws UserException {
        log.info(String.format(FINDING_USER_BY_ID,FIND_BY_USER_ID,userId));
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return user.get();
        }
        throw new UserException(String.format(USER_NOT_FOUND_EXCEPTION,FIND_BY_USER_ID,userId));
    }

    @Override
    public UserResponse findUserProfileByJwt(String jwt) throws UserException {
        log.info(String.format(FINDING_USER_BY_JWT, FIND_USER_PROFILE_BY_JWT, ""));
        String email = jwtHelper.getEmailFromJwtToken(jwt);
        Optional<User> user = userRepository.findByUsername(email);
        if (user.isPresent()) {
            return mapUserToUserResponse(user.get());
        }
        throw new UserException(String.format(USERNAME_NOT_FOUND_WITH_MAIL, FIND_USER_PROFILE_BY_JWT, email));
    }

    private UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getMobile())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .build();
    }

    @Transactional
    @Override
    public User createUser(User user) throws UserException {
        String email = user.getUsername();

        Optional<User> isEmailExist = userRepository.findByUsername(email);

        // Check if the email is already used
        if (isEmailExist.isPresent()) {
            throw new UserException(String.format(EMAIL_IS_ALREADY_USED_WITH_ANOTHER_ACCOUNT,CREATE_USER,""));
        }

        // Create a new user
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        // Save the user to the database
        User savedUser = userRepository.save(user);

        // Create and associate the RefreshToken
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(savedUser.getUsername());
        savedUser.setRefreshToken(refreshToken);

        // Save the updated user entity to ensure the association with the RefreshToken
        userRepository.save(savedUser);

        return savedUser;

    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

}