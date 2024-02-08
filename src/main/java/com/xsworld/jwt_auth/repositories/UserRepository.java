package com.xsworld.jwt_auth.repositories;
import com.xsworld.jwt_auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    public Optional<User> findByEmail(String email);
}
