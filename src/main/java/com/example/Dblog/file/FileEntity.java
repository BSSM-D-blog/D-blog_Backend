package com.example.Dblog.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name="files")
@NoArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String originalname;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String filepath;

    @Builder
    public FileEntity(Long id, String originalname, String filename, String filepath) {
        this.id = id;
        this.originalname = originalname;
        this.filename = filename;
        this.filepath = filepath;
    }
}
