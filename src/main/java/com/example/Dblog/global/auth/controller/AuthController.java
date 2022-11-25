package com.example.Dblog.global.auth.controller;

import com.example.Dblog.domain.user.dto.UserRequestDto;
import com.example.Dblog.global.auth.service.AuthService;
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
    public boolean Signup(@Valid @RequestBody UserRequestDto userCreateForm, BindingResult bindingResult){
        return authService.create(userCreateForm, bindingResult);
    }

    @PostMapping("/login")
    public Map<String, String> Login(@RequestBody Map<String, String> user) {
        return authService.login(user);
    }

    @DeleteMapping("/logout")
    public void Logout(@RequestHeader("Authorization") String token){
        authService.logout(token);
    }
}
