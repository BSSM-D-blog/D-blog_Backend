package com.example.Dblog.dto;

import com.example.Dblog.entity.UserEntity;

public class UserDto {
    private String id;
    private String pw;
    private String name;
    private String profile;

    public UserDto(String id, String pw, String name, String profile){
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "UserDto = { id = " + id + " pw = " + pw + " name = " + name + " profile = " + profile;
    }
    public UserEntity toEntity(){
        return UserEntity.builder()
                .userKey(null)
                .id(id)
                .pw(pw)
                .name(name)
                .created(null)
                .profile(profile)
                .build();
    }
}
