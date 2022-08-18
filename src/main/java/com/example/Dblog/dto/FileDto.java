package com.example.Dblog.dto;

import com.example.Dblog.entity.BoardEntity;
import com.example.Dblog.entity.FileEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDto {
    private BoardEntity board;
    private String originalFileName;
    private String saveFileName;
    private String filePath;

    public FileEntity toEntity(){
        FileEntity build = FileEntity.builder()
                .board(board)
                .originalFileName(originalFileName)
                .saveFileName(saveFileName)
                .filePath(filePath)
                .build();
        return build;
    }

    @Builder
    public FileDto(BoardEntity board, String originalFileName, String saveFileName, String filePath){
        this.board = board;
        this.originalFileName = originalFileName;
        this.saveFileName = saveFileName;
        this.filePath = filePath;
    }
}
