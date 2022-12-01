package com.example.Dblog.domain.auth.controller;

import com.example.Dblog.domain.user.dto.UserRequestDto;
import com.example.Dblog.domain.auth.service.AuthService;
import com.example.Dblog.global.jwt.dto.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public boolean Signup(@Valid @RequestBody UserRequestDto userRequestDto, BindingResult bindingResult){
        return authService.create(userRequestDto, bindingResult);
    }

    @PostMapping("/login")
    public Token Login(@RequestBody Map<String, String> user) {
        return authService.login(user);
    }

    @DeleteMapping("/logout")
    public void Logout(@RequestHeader("Authorization") String token){
        authService.logout(token);
    }
}
