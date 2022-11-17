package com.example.Dblog.user;

import com.example.Dblog.file.FileEntity;
import com.example.Dblog.file.FileService;
import com.example.Dblog.jwt.JwtTokenProvider;
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
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final FileService fileService;

    @GetMapping("/api/user")
    public GetUserDto findUser(@RequestHeader("Authorization") String token){
        Claims parseToken = jwtTokenProvider.parseJwtToken(token);
        Optional<UserEntity> user = userRepository.findByUsername(parseToken.getSubject());
        return new GetUserDto(user);
    }

    @PutMapping("/api/user")
    public void updateProfile(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String token){
        Claims parseToken = jwtTokenProvider.parseJwtToken(token);
        Optional<UserEntity> user = userRepository.findByUsername(parseToken.getSubject());
        FileEntity saveFile = fileService.saveFile(file);
        user.ifPresent(userEntity -> userService.updateProfile(saveFile.getServerPath() , userEntity));
    }
}

