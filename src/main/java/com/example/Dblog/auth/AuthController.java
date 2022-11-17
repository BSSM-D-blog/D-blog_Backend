package com.example.Dblog.auth;

import com.example.Dblog.jwt.JwtService;
import com.example.Dblog.jwt.JwtTokenProvider;
import com.example.Dblog.jwt.Token;
import com.example.Dblog.user.UserCreateForm;
import com.example.Dblog.user.UserEntity;
import com.example.Dblog.user.UserRepository;
import com.example.Dblog.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

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
}
