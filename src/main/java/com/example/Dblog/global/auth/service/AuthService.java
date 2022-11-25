package com.example.Dblog.global.auth.service;

import com.example.Dblog.global.jwt.*;
import com.example.Dblog.domain.user.entity.Role;
import com.example.Dblog.domain.user.dto.UserRequestDto;
import com.example.Dblog.domain.user.entity.UserEntity;
import com.example.Dblog.domain.user.entity.repository.UserRepository;
import com.example.Dblog.global.jwt.entity.JwtToken;
import com.example.Dblog.global.jwt.dto.Token;
import com.example.Dblog.global.jwt.entity.repository.JwtTokenRepository;
import com.example.Dblog.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenRepository jwtTokenRepository;
    private final JwtService jwtService;

    @Transactional
    public boolean create(UserRequestDto userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return false;
        }
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect", "2개의 패스워드가 일치하지 않습니다.");
            return false;
        }

        UserEntity user = new UserEntity();
        user.setUsername(userCreateForm.getUsername());
        user.setNickname(userCreateForm.getNickname());
        user.setPassword(passwordEncoder.encode(userCreateForm.getPassword1()));
        user.setRole(Role.USER);
        user.setRoles(Collections.singletonList("ROLE_USER"));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public void logout(String token){
        Optional<JwtToken> jwt = jwtTokenRepository.findByAccessToken(token);
        if(jwt.isPresent()){
            jwt.get().setAccessToken(null);
            jwt.get().setRefreshToken(null);
        }
    }

    @Transactional
    public Map<String, String> login(Map<String, String> user){
        UserEntity member = userRepository.findByUsername(user.get("username")).
                orElseThrow(()-> new IllegalArgumentException("가입되지 않은 아이디 입니다."));
        Token tokenDto = jwtTokenProvider.createAccessToken(member.getUsername(), member.getRoles());
        jwtService.login(tokenDto);
        Map<String, String> token = new HashMap<>();
        token.put("accessToken", tokenDto.getAccessToken());
        token.put("refreshToken", tokenDto.getRefreshToken());
        return token;
    }
}
