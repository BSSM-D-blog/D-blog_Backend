package com.example.Dblog.file;

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

    public FileEntity toEntity(){
        FileEntity build = FileEntity.builder()
                .id(id)
                .originalname(originalname)
                .filename(filename)
                .filepath(filepath)
                .build();
        return build;
    }

    @Builder
    public FileDto(Long id, String originalname, String filename, String filepath) {
        this.id = id;
        this.originalname = originalname;
        this.filename = filename;
        this.filepath = filepath;
    }
}
