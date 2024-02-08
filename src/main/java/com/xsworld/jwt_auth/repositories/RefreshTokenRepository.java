package com.xsworld.jwt_auth.repositories;

import com.xsworld.jwt_auth.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    // custom methods

    Optional<RefreshToken>findByRefreshToken(String token);
}
