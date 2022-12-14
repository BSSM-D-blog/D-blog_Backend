package com.example.Dblog.domain.auth.controller;

import com.example.Dblog.domain.user.presentation.dto.UserRequestDto;
import com.example.Dblog.domain.auth.service.AuthService;
import com.example.Dblog.global.jwt.RefreshApiResponseMessage;
import com.example.Dblog.global.jwt.dto.Token;
import com.example.Dblog.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/auth/signup")
    public boolean Signup(@Valid @RequestBody UserRequestDto userRequestDto, BindingResult bindingResult){
        return authService.create(userRequestDto, bindingResult);
    }

    @PostMapping("/auth/login")
    public Token Login(@RequestBody Map<String, String> user) {
        return authService.login(user);
    }

    @DeleteMapping("/auth/logout")
    public void Logout(@RequestHeader("Authorization") String token){
        authService.logout(token);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<RefreshApiResponseMessage> validateRefreshToken(@RequestBody Map<String, String> bodyJson){
        log.info("refresh controller 실행");
        Map<String, String> map = jwtService.validateRefreshToken(bodyJson.get("refreshToken"));


        if(map.get("status").equals("402")){
            log.info("RefreshController - Refresh Token이 만료.");
            RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
            return new ResponseEntity<RefreshApiResponseMessage>(refreshApiResponseMessage, HttpStatus.UNAUTHORIZED);
        }

        log.info("RefreshController - Refresh Token이 유효.");
        RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
        return new ResponseEntity<RefreshApiResponseMessage>(refreshApiResponseMessage, HttpStatus.OK);
    }
}
