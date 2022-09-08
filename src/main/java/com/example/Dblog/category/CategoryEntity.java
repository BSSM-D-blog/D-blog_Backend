package com.example.Dblog.category;

import com.example.Dblog.user.UserEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@NoArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category;

    @Column(length = 200)
    private String name;

    @Builder
    public CategoryEntity(Long category,  String name){
        this.category = category;
        this.name = name;
    }

    @Override
    public String toString(){
        return "category { category = " + category + " name = " + name;
    }
}
