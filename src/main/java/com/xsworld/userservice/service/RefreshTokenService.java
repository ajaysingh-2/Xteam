package com.xsworld.userservice.service;

import com.xsworld.userservice.model.RefreshToken;
import com.xsworld.userservice.model.User;
import com.xsworld.userservice.repository.RefreshTokenRepository;
import com.xsworld.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    public long refreshTokenValidity=5*60*60*1000;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("User not found"));

        RefreshToken refreshToken1 = user.getRefreshToken();

        if (refreshToken1 == null) {
            refreshToken1 = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(refreshTokenValidity))
                    .user(user)
                    .build();
        } else {
            refreshToken1.setExpiry(Instant.now().plusMillis(refreshTokenValidity));
        }

        user.setRefreshToken(refreshToken1);

        userRepository.save(user);

        return refreshToken1;
    }


    public RefreshToken verifyRefreshToken(String refreshToken){

      RefreshToken refreshTokenOb =  refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("Given Token does not exists in db"));

      if (refreshTokenOb.getExpiry().compareTo(Instant.now())<0){
          refreshTokenRepository.delete(refreshTokenOb);
          throw new RuntimeException("Refresh Token Expired !!");
      }
      return refreshTokenOb;
    }

}
