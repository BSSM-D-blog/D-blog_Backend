package com.example.Dblog.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "category")
@NoArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category;

    @ManyToMany
    @JoinColumn(name = "user_userKey")
    private UserEntity userKey;

    @Column(length = 200)
    private String name;

    @Builder
    public CategoryEntity(Long category, UserEntity user, String name){
        this.category = category;
        this.userKey = user;
        this.name = name;
    }

    @Override
    public String toString(){
        return "category { category = " + category + " userKey = " + userKey + " name = " + name;
    }
}
