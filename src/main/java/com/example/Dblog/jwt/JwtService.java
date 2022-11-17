package com.example.Dblog.jwt;

import com.example.Dblog.user.UserEntity;
import com.example.Dblog.user.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public void login(Token tokenDto){
        JwtToken refreshToken = JwtToken.builder()
                .keyUsername(tokenDto.getKey())
                .refreshToken(tokenDto.getRefreshToken())
                .build();
        String loginUsername = refreshToken.getKeyUsername();
        if(refreshTokenRepository.existsByKeyUsername(loginUsername)){
            refreshTokenRepository.deleteByKeyUsername(loginUsername);
        }
        refreshTokenRepository.save(refreshToken);
    }

    public Optional<JwtToken> getRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken);
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
            map.put("message", "Refresh 토큰이 만료되었습니다. 로그인이 필요합니다.");
            return map;
        }
        map.put("status", "200");
        map.put("message", "Refresh 토큰을 통한 Access Token 생성이 완료되었습니다.");
        map.put("accessToken", createdAccessToken);
        return map;
    }

    public Optional<UserEntity> getUserInfo(String token){
        Claims parseToken = jwtTokenProvider.parseJwtToken(token);
        return userRepository.findByUsername(parseToken.getSubject());
    }
}
