package com.example.Dblog.domain.comment.presentation.dto;

import com.example.Dblog.domain.comment.entity.CommentEntity;
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
    private Long userid;

    public CommentResponseDto(CommentEntity comment, String profile, String nickname, Long userid){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.created = comment.getCreated();
        this.modified = comment.getModified();
        this.profile = profile;
        this.nickname = nickname;
        this.userid = userid;
    }
}
