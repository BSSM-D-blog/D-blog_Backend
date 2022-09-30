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

    @Column
    private Long userid;
}
