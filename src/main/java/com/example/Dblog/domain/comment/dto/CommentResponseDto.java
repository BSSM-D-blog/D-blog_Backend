package com.example.Dblog.domain.comment.dto;

import com.example.Dblog.domain.comment.CommentEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String profile;
    private String nickname;

    public CommentResponseDto(CommentEntity comment, String profile, String nickname){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.created = comment.getCreated();
        this.modified = comment.getModified();
        this.profile = profile;
        this.nickname = nickname;
    }
}
