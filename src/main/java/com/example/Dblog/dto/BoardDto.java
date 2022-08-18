package com.example.Dblog.dto;

import com.example.Dblog.entity.BoardEntity;
import com.example.Dblog.entity.UserEntity;

public class BoardDto {
    private UserEntity user;
    private String content;
    private String title;

    public BoardDto(UserEntity user,String content, String title){
        this.user = user;
        this.content = content;
        this.title = title;
    }

    @Override
    public String toString(){
        return "BoardEntity { content = " + content + " title = " + title;
    }

    public BoardEntity toEntity(){
        return new BoardEntity(null, user, content, title, null, null, null);
    }
}
