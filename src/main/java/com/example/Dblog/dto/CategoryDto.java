package com.example.Dblog.dto;

import com.example.Dblog.entity.CategoryEntity;
import com.example.Dblog.entity.UserEntity;

public class CategoryDto {
    private UserEntity user;
    private String name;

    public CategoryEntity toEntity(){
        return CategoryEntity.builder()
                .category(null)
                .user(user)
                .name(name)
                .build();
    }

    @Override
    public String toString(){
        return "category { user = " + user + " name = " + name + " }";
    }
}
