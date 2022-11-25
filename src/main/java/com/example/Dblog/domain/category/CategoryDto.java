package com.example.Dblog.domain.category;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CategoryDto {
    private Long category;
    private String name;
    private Long user;

    @Builder
    public CategoryDto(Long category, String name, Long user){
        this.category = category;
        this.name = name;
        this.user = user;
    }

    public CategoryEntity toEntity(){
        return new CategoryEntity(category, name, user);
    }
}
