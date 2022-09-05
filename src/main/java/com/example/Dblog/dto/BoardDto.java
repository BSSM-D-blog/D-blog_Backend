package com.example.Dblog.dto;

import com.example.Dblog.entity.BoardEntity;
import com.example.Dblog.entity.CategoryEntity;
import com.example.Dblog.entity.UserEntity;

public class BoardDto {
    private UserEntity userKey;
    private String content;
    private String title;
    private CategoryEntity category;

    public BoardDto(UserEntity userKey,String content, String title, CategoryEntity category){
        this.userKey = userKey;
        this.content = content;
        this.title = title;
        this.category = category;
    }

    @Override
    public String toString(){
        return "BoardEntity { content = " + content + " title = " + title;
    }

    public BoardEntity toEntity(){
        return BoardEntity.builder()
                .postKey(null)
                .userKey(userKey)
                .content(content)
                .title(title)
                .category(category)
                .created(null)
                .commentCnt(0)
                .viewCnt(0)
                .build();
    }
}