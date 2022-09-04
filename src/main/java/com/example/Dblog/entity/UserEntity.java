package com.example.Dblog.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userKey;

    @Column
    private String id;

    @Column
    private String pw;

    @Column
    private String name;

    @Column
    private String profile;

    @Column
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime created;

    public UserEntity() {
    }

    public UserEntity(Long userKey, String id, String pw, String name, String profile, LocalDateTime created){
        this.userKey = userKey;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.profile = profile;
        this.created = created;
    }

    @Override
    public String toString(){
        return "UserDto = { userKey = " + userKey + " id = " + id + " pw = " + pw + " name = " + name + " profile = " + profile  + " created = " + created;
    }
}