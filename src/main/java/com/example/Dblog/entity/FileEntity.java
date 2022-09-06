package com.example.Dblog.entity;

import com.example.Dblog.user.UserEntity;
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

//    @ManyToMany
//    @JoinColumn(name = "user_userKey")
//    private UserEntity user;

    @Builder
    public FileEntity(Long id, String originalFileName, String saveFileName, String filePath /*UserEntity user*/){
        this.id = id;
        this.originalFileName = originalFileName;
        this.saveFileName = saveFileName;
        this.filePath = filePath;
        //this.user = user;
    }
}
