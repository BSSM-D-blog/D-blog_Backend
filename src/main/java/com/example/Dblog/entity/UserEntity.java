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
    private Long usercode;

    @Column
    private String username;

    @Column
    private String userid;

    @Column
    private String userpass;

    @Column
    private String userprofile;

    @Column
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime created;

    public UserEntity() {
    }

    public UserEntity(Long usercode, String username, String userid, String userpass, String userprofile, LocalDateTime created){
        this.usercode = usercode;
        this.username = username;
        this.userid = userid;
        this.userpass = userpass;
        this.userprofile = userprofile;
        this.created = created;
    }

    @Override
    public String toString(){
        return "UserDto = { usercode = " + usercode + " username = " + username + " userid = " + userid + " userpass = " + userpass + " userprofile = " + userprofile + " created = " + created;
    }
}