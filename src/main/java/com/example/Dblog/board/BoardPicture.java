package com.example.Dblog.board;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="files")
@NoArgsConstructor
public class BoardPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String saveFileName;

    @Column
    private String filePath;

    @Column
    private Long fileSize;

    @Column
    private Long board;


    @Builder
    public BoardPicture(Long id, String originalFileName, String saveFileName, String filePath, Long board, Long fileSize){
        this.id = id;
        this.originalFileName = originalFileName;
        this.saveFileName = saveFileName;
        this.filePath = filePath;
        this.board = board;
        this.fileSize = fileSize;
    }
}
