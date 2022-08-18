package com.example.Dblog.dto;

import com.example.Dblog.entity.UserEntity;

public class UserDto {
    private String username;
    private String userid;
    private String userpass;
    private String userprofile;

    public UserDto(String username, String userid, String userpass, String userprofile){
        this.username = username;
        this.userid = userid;
        this.userpass = userpass;
        this.userprofile = userprofile;
    }

    @Override
    public String toString() {
        return "UserDto = { username = " + username + " userid = " + userid + " userpass = " + userpass + " userprofile = " + userprofile;
    }
    public UserEntity toEntity(){
        return new UserEntity(null, username, userid, userpass, userprofile, null);
    }
}
