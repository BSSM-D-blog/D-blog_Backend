package com.example.Dblog.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="files")
@NoArgsConstructor
@Getter
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    @Column
    private String originalFileName;

    @Column
    private String saveFileName;

    @Column
    private String filePath;

    @Builder
    public FileEntity(Long id, BoardEntity board, String originalFileName, String saveFileName, String filePath){
        this.id = id;
        this.board = board;
        this.originalFileName = originalFileName;
        this.saveFileName = saveFileName;
        this.filePath = filePath;
    }
}
