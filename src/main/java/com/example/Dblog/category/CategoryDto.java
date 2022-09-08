package com.example.Dblog.category;

import com.example.Dblog.user.UserEntity;

public class CategoryDto {
    private UserEntity user;
    private String name;

    public CategoryEntity toEntity(){
        return CategoryEntity.builder()
                .category(null)
                .name(name)
                .build();
    }

    @Override
    public String toString(){
        return "category { user = " + user + " name = " + name + " }";
    }
}
