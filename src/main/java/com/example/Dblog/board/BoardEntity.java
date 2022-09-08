package com.example.Dblog.board;

import com.example.Dblog.user.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="board")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postKey;

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

    @ManyToOne
    @JoinColumn(name = "userKey")
    private UserEntity userKey;

    @Column
    private List pictures;


    @Builder
    public BoardEntity(Long postKey, String content, String title, LocalDateTime created, int commentCnt, int viewCnt, UserEntity user){
        this.postKey = postKey;
        this.content = content;
        this.title = title;
        this.created = created;
        this.commentCnt = commentCnt;
        this.viewCnt = viewCnt;
        this.userKey = user;
    }
}
