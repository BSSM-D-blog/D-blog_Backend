package com.example.Dblog.user;

import com.example.Dblog.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.*;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public boolean create(UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return false;
        }
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect", "2개의 패스워드가 일치하지 않습니다.");
            return false;
        }

        UserEntity user = new UserEntity();
        user.setUsername(userCreateForm.getUsername());
        user.setNickname(userCreateForm.getNickname());
        user.setPassword(passwordEncoder.encode(userCreateForm.getPassword1()));
        user.setRole(Role.USER);
        user.setRoles(Collections.singletonList("ROLE_USER"));
        this.userRepository.save(user);
        return true;
    }

    @Transactional
    public void updateProfile(Long id, UserEntity user){
        user.setFileid(id);
        userRepository.save(user);
    }
}
