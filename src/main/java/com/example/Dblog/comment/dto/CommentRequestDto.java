package com.example.Dblog.comment.dto;

import com.example.Dblog.comment.CommentEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentRequestDto {
    private Long id;
    private String content;
    private Long user;
    private Long board;
    private Long parents;

    public CommentEntity toEntity(){
        return CommentEntity.builder()
                .id(id)
                .content(content)
                .created(null)
                .modified(null)
                .user(user)
                .board(board)
                .parents(parents)
                .deleted(0)
                .build();
    }
}
