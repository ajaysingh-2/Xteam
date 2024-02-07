package com.xsworld.userservice.service.serviceimpl;
import com.xsworld.userservice.repository.UserRepository;
import  com.xsworld.userservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import static com.xsworld.userservice.constants.ExceptionConstants.USERNAME_NOT_FOUND_WITH_MAIL;
import static com.xsworld.userservice.constants.LoggerConstants.LOADED_USER;
import static com.xsworld.userservice.constants.LoggerConstants.LOADING_USER_BY_USERNAME;
import static com.xsworld.userservice.constants.MethodsNameConstants.LOAD_USER_BY_USERNAME;

@Service
@Slf4j
public class CustomUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info(String.format(LOADING_USER_BY_USERNAME,LOAD_USER_BY_USERNAME,username));

        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_WITH_MAIL,LOAD_USER_BY_USERNAME,username))
        );

        List<GrantedAuthority> authorityList = new ArrayList<>();

        log.info(String.format(LOADED_USER,LOAD_USER_BY_USERNAME,username));


        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorityList);
    }
}