package com.example.Dblog.auth;

import com.example.Dblog.jwt.JwtService;
import com.example.Dblog.jwt.JwtTokenProvider;
import com.example.Dblog.jwt.Token;
import com.example.Dblog.user.UserCreateForm;
import com.example.Dblog.user.UserEntity;
import com.example.Dblog.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @PostMapping("/signup")
    public boolean Signup(@Valid @RequestBody UserCreateForm userCreateForm, BindingResult bindingResult){
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
