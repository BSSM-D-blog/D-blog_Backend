package com.example.Dblog.domain.file;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDto {
    private Long id;
    private String originalname;
    private String filename;
    private String filepath;
    private String serverPath;

    public FileEntity toEntity(){
        return FileEntity.builder()
                .id(id)
                .originalname(originalname)
                .filename(filename)
                .filepath(filepath)
                .serverPath(serverPath)
                .build();
    }

    @Builder
    public FileDto(Long id, String originalname, String filename, String filepath, String serverPath) {
        this.id = id;
        this.originalname = originalname;
        this.filename = filename;
        this.filepath = filepath;
        this.serverPath = serverPath;
    }
}
