package com.example.Dblog.entity;

import com.example.Dblog.user.UserEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="board")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postKey;

//    @ManyToMany
//    @JoinColumn(name = "user_userKey")
//    private UserEntity userKey;

//    @OneToMany
//    @JoinColumn(name = "category_category")
//    private CategoryEntity category;

    @Column(length = 5000)
    private String content;

    @Column
    private String title;

    @Column
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime created;

    @Column
    private int commentCnt;

    @Column
    private int viewCnt;

    @Builder
    public BoardEntity(Long postKey /*UserEntity userKey*/ ,String content, String title, LocalDateTime created, int commentCnt, int viewCnt /*CategoryEntity category*/){
        this.postKey = postKey;
        //this.userKey = userKey;
        this.content = content;
        this.title = title;
        this.created = created;
        this.commentCnt = commentCnt;
        this.viewCnt = viewCnt;
        //this.category = category;
    }

    @Override
    public String toString(){
        return "BoardEntity { postKey = " + postKey + " content = " + content +
                " title = " + title + " created" + created + " commentCnt = " + commentCnt +
                " viewCnt = " + viewCnt/* " userKey = " + userKey " category = " + category*/;
    }
}
