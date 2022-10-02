package com.example.Dblog.user;

import com.example.Dblog.jwt.JwtService;
import com.example.Dblog.jwt.JwtTokenProvider;
import com.example.Dblog.jwt.Token;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public boolean Signup(@Valid @RequestBody UserCreateForm userCreateForm, BindingResult bindingResult){
        return userService.create(userCreateForm, bindingResult);
    }

    @PostMapping("/login")
    public Map<String, String> Login(@RequestBody Map<String, String> user) {
        UserEntity member = userRepository.findByUsername(user.get("username")).
            orElseThrow(()-> new IllegalArgumentException("가입되지 않은 아이디 입니다."));
        Token tokenDto = jwtTokenProvider.createAccessToken(member.getUsername(), member.getRoles());
        jwtService.login(tokenDto);
        Map<String, String> token = new HashMap<>();
        token.put("accessToken", tokenDto.getAccessToken());
        token.put("refreshToken", tokenDto.getRefreshToken());
        return token;
    }

    @GetMapping("/api/user")
    public GetUserDto findUser(@RequestHeader("Authorization") String token){
        Claims parseToken = jwtTokenProvider.parseJwtToken(token);
        Optional<UserEntity> user = userRepository.findByUsername(parseToken.getSubject());
        return new GetUserDto(user);
    }
}

