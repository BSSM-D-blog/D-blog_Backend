package com.example.Dblog.user;

import com.example.Dblog.jwt.JwtTokenProvider;
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

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public boolean Signup(@Valid @RequestBody UserCreateForm userCreateForm, BindingResult bindingResult){
        boolean result = userService.create(userCreateForm, bindingResult);
        if(result) return true;
        return false;
    }

    @PostMapping("/login")
    public String Login(@RequestBody Map<String, String> user) {
        UserEntity member = userRepository.findByUsername(user.get("username")).
            orElseThrow(()-> new IllegalArgumentException("가입되지 않은 아이디 입니다."));
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }
}
