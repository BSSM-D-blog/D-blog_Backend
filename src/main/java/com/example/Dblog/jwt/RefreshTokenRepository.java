package com.example.Dblog.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<JwtToken, Long> {
    Optional<JwtToken> findByRefreshToken(String refreshToken);
    boolean existsByKeyUsername(String username);
    void deleteByKeyUsername(String username);
}
