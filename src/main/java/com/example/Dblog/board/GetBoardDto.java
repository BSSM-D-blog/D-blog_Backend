package com.example.Dblog.board;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetBoardDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime created;
    private String user;
    private String filePath;

    @Builder
    public GetBoardDto(Long id, String title, String content, LocalDateTime created, String user, String filePath){
        this.id = id;
        this.title = title;
        this.content = content;
        this.created = created;
        this.user = user;
        this.filePath = filePath;
    }
}
