package com.example.Dblog.category;

import com.example.Dblog.user.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "category")
@NoArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category;

    @Column(length = 200)
    private String name;

    @Column
    private Long user;

    public CategoryEntity(Long category, String name, Long user){
        this.category = category;
        this.name = name;
        this.user = user;
    }
}
