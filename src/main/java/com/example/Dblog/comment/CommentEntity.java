package com.example.Dblog.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment")
@NoArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime created;

    @Column
    @LastModifiedDate
    private LocalDateTime modified;

    @Column
    private Long user;

    @Column
    private Long board;

    @Column
    private Long parents;

    @Column
    private boolean delete;

    @Builder
    public CommentEntity(Long id, String content, LocalDateTime created, LocalDateTime modified, Long user, Long board, Long parents, boolean delete) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.modified = modified;
        this.user = user;
        this.board = board;
        this.parents = parents;
        this.delete = delete;
    }
}
