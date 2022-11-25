package com.example.Dblog.domain.user.controller;

import com.example.Dblog.domain.file.entity.FileEntity;
import com.example.Dblog.domain.file.service.FileService;
import com.example.Dblog.domain.user.service.UserService;
import com.example.Dblog.domain.user.dto.UserResponseDto;
import com.example.Dblog.domain.user.entity.UserEntity;
import com.example.Dblog.domain.user.entity.repository.UserRepository;
import com.example.Dblog.global.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final FileService fileService;

    @GetMapping("/user")
    public UserResponseDto findUser(@CookieValue("accessToken") String token){
        log.info("accessToken: " + token);
        return userService.findUser(token);
    }

    @PutMapping("/user")
    public void updateProfile(@RequestParam("file") MultipartFile file, @CookieValue("accessToken") String token){
        Claims parseToken = jwtTokenProvider.parseJwtToken(token);
        Optional<UserEntity> user = userRepository.findByUsername(parseToken.getSubject());
        FileEntity saveFile = fileService.saveFile(file);
        user.ifPresent(userEntity -> userService.updateProfile(saveFile.getServerPath() , userEntity));
    }
}

