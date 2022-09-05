package com.example.Dblog.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="files")
@NoArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String saveFileName;

    @Column
    private String filePath;

    @Builder
    public FileEntity(Long id, String originalFileName, String saveFileName, String filePath){
        this.id = id;
        this.originalFileName = originalFileName;
        this.saveFileName = saveFileName;
        this.filePath = filePath;
    }
}
