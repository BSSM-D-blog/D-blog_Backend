package com.example.Dblog.domain.user.service;

import com.example.Dblog.domain.user.dto.UserResponseDto;
import com.example.Dblog.domain.user.entity.UserEntity;
import com.example.Dblog.domain.user.entity.repository.UserRepository;
import com.example.Dblog.global.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void updateProfile(String path, UserEntity user){
        user.setProfile(path);
        userRepository.save(user);
    }

    @Transactional
    public UserResponseDto findUser(String token){
        Claims parseToken = jwtTokenProvider.parseJwtToken(token);
        Optional<UserEntity> user = userRepository.findByUsername(parseToken.getSubject());
        return new UserResponseDto(user);
    }

}
