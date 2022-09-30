package com.example.Dblog.user;

import com.example.Dblog.jwt.JwtService;
import com.example.Dblog.jwt.JwtTokenProvider;
import com.example.Dblog.jwt.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

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
    public Token Login(@RequestBody Map<String, String> user) {
        UserEntity member = userRepository.findByUsername(user.get("username")).
            orElseThrow(()-> new IllegalArgumentException("가입되지 않은 아이디 입니다."));
        Token tokenDto = jwtTokenProvider.createAccessToken(member.getUsername(), member.getRoles());
        jwtService.login(tokenDto);
        return tokenDto;
    }
}
