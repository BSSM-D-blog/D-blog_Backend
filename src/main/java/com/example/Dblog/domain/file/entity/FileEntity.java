package com.example.Dblog.domain.file.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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

    @Column
    private String serverPath;

    @Builder
    public FileEntity(Long id, String originalname, String filename, String filepath, String serverPath) {
        this.id = id;
        this.originalname = originalname;
        this.filename = filename;
        this.filepath = filepath;
        this.serverPath = serverPath;
    }
}
