package com.example.Dblog.domain.board.presentation.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime created;
    private String user;
    private String filePath;
    private String category;
    private String profile;
    private String username;

    @Builder
    public BoardResponseDto(Long id, String title, String content, LocalDateTime created, String user, String filePath, String category, String profile, String username){
        this.id = id;
        this.title = title;
        this.content = content;
        this.created = created;
        this.user = user;
        this.filePath = filePath;
        this.category = category;
        this.profile = profile;
        this.username = username;
    }
}
