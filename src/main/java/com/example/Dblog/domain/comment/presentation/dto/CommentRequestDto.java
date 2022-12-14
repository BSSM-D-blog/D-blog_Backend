package com.example.Dblog.domain.comment.presentation.dto;

import com.example.Dblog.domain.comment.entity.CommentEntity;
import lombok.Getter;

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
