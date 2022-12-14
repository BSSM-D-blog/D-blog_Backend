package com.example.Dblog.domain.user.presentation.controller;

import com.example.Dblog.domain.file.entity.FileEntity;
import com.example.Dblog.domain.file.service.FileService;
import com.example.Dblog.domain.user.presentation.dto.UserResponseDto;
import com.example.Dblog.domain.user.service.UserService;
import com.example.Dblog.domain.user.entity.UserEntity;
import com.example.Dblog.domain.user.entity.repository.UserRepository;
import com.example.Dblog.global.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final FileService fileService;

    @GetMapping("/userinfo")
    public UserResponseDto findUser(@RequestHeader("Authorization") String token){
        return userService.findUser(token);
    }

    @PutMapping("/userinfo")
    public void updateProfile(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String token){
        userService.updateProfile(token, file);
    }

    @GetMapping("/user/{userId}")
    public Map<String, Object> getPersonalInfo(@PathVariable Long userId) {
        return userService.getPersonalInfo(userId);
    }
}

