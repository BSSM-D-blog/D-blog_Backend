package com.example.Dblog.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="board")
@EntityListeners(AuditingEntityListener.class)
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_usercode")
    private UserEntity user;

    @Column(length = 5000)
    private String content;

    @Column
    private String title;

    @Column
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime created;

    @Column
    private Long commentCnt;

    @Column
    private Long hit;

    @ManyToOne
    @JoinColumn(name = "files_filecode")
    private String file;

    public BoardEntity() {
    }

    public BoardEntity(Long id, UserEntity user ,String content, String title, LocalDateTime created, Long commentCnt, Long hit){
        this.id = id;
        this.user = user;
        this.content = content;
        this.title = title;
        this.created = created;
        this.commentCnt = commentCnt;
        this.hit = hit;
    }

    @Override
    public String toString(){
        return "BoardEntity { id = " + id + " content = " + content + " title = " + title + " created" + created + " commentCnt = " + commentCnt + " hit = " + hit + " user = " + user;
    }
}
