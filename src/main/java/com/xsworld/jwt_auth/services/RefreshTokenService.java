package com.xsworld.jwt_auth.services;


import com.xsworld.jwt_auth.entities.RefreshToken;
import com.xsworld.jwt_auth.entities.User;
import com.xsworld.jwt_auth.repositories.RefreshTokenRepository;
import com.xsworld.jwt_auth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    public  long refreshTokenValidity=5*60*60*10000;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(String username) {

        User user = userRepository.findByEmail(username).get();
        RefreshToken refreshToken1 = user.getRefreshToken();

        if (refreshToken1 == null) {


           refreshToken1 = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(refreshTokenValidity))
                    .user(userRepository.findByEmail(username).get())
                    .build();
        }else{
            refreshToken1.setExpiry(Instant.now().plusMillis(refreshTokenValidity));
        }
        user.setRefreshToken(refreshToken1);

            // save to database
            refreshTokenRepository.save(refreshToken1);

            return refreshToken1;
        }

        public RefreshToken verifyRefreshToken (String refreshToken){

            RefreshToken refreshTokenob = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("Given Token not valid"));

            if (refreshTokenob.getExpiry().compareTo(Instant.now()) < 0) {
                refreshTokenRepository.delete(refreshTokenob);
                throw new RuntimeException("Refresh Token Expired");
            } else {
                return refreshTokenob;

            }

        }

    }
