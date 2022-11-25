package com.example.Dblog.global.jwt.entity.repository;

import com.example.Dblog.global.jwt.entity.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    Optional<JwtToken> findByRefreshToken(String refreshToken);
    boolean existsByKeyUsername(String username);
    void deleteByKeyUsername(String username);
    Optional<JwtToken> findByAccessToken(String accessToken);
}
