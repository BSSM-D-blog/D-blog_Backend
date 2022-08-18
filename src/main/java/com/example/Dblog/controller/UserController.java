package com.example.Dblog.controller;

import com.example.Dblog.dto.UserDto;
import com.example.Dblog.entity.UserEntity;
import com.example.Dblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/user")
    public void InsertUser(UserDto dto){
        System.out.println(dto.toString());

        UserEntity userEntity = dto.toEntity();
        System.out.println(userEntity.toString());

        UserEntity save = userRepository.save(userEntity);
        System.out.println(save.toString());
    }

}
