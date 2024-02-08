package com.xsworld.userservice.service;

import com.xsworld.userservice.exception.UserException;
import com.xsworld.userservice.model.User;
import com.xsworld.userservice.response.UserResponse;

public interface UserService {

    public User findByUserId(String userId) throws UserException;

    public UserResponse findUserProfileByJwt(String jwt) throws UserException;

    public User createUser(User user) throws UserException;

    public User saveUser(User user);

}
