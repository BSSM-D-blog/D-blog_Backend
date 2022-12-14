package com.example.Dblog.global.jwt.service;

import com.example.Dblog.domain.user.entity.UserEntity;
import com.example.Dblog.domain.user.entity.repository.UserRepository;
import com.example.Dblog.global.jwt.JwtTokenProvider;
import com.example.Dblog.global.jwt.entity.repository.JwtTokenRepository;
import com.example.Dblog.global.jwt.entity.JwtToken;
import com.example.Dblog.global.jwt.dto.Token;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenRepository jwtTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public void login(Token tokenDto){
        JwtToken token = JwtToken.builder()
                .keyUsername(tokenDto.getKey())
                .accessToken(tokenDto.getAccessToken())
                .refreshToken(tokenDto.getRefreshToken())
                .build();
        String loginUsername = token.getKeyUsername();
        if(jwtTokenRepository.existsByKeyUsername(loginUsername)){
            jwtTokenRepository.deleteByKeyUsername(loginUsername);
        }
        jwtTokenRepository.save(token);
    }

    public Optional<JwtToken> getRefreshToken(String refreshToken){
        return jwtTokenRepository.findByRefreshToken(refreshToken);
    }

    public Map<String, String> validateRefreshToken(String refreshToken){
        JwtToken refreshToken1 = getRefreshToken(refreshToken).get();
        String createdAccessToken = jwtTokenProvider.validateRefreshToken(refreshToken1);

        return createRefreshJson(createdAccessToken);
    }

    public Map<String, String> createRefreshJson(String createdAccessToken){
        Map<String, String> map = new HashMap<>();
        if(createdAccessToken == null){
            map.put("errortype", "Forbidden");
            map.put("status", "403");
            map.put("message", "Refresh ????????? ?????????????????????. ???????????? ???????????????.");
            return map;
        }
        map.put("status", "200");
        map.put("message", "Refresh ????????? ?????? Access Token ????????? ?????????????????????.");
        map.put("accessToken", createdAccessToken);
        return map;
    }

    public Optional<UserEntity> getUserInfo(String token){
        Claims parseToken = jwtTokenProvider.parseJwtToken(token);
        return userRepository.findByUsername(parseToken.getSubject());
    }
}
