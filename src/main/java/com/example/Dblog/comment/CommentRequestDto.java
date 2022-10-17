package com.example.Dblog.comment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentRequestDto {
    private Long id;
    private String content;
    private LocalDateTime created;
    private LocalDateTime modified;
    private Long user;
    private Long board;
    private Long parents;

    public CommentEntity toEntity(){
        return CommentEntity.builder()
                .id(id)
                .content(content)
                .created(created)
                .modified(modified)
                .user(user)
                .board(board)
                .parents(parents)
                .delete(false)
                .build();
    }
}
