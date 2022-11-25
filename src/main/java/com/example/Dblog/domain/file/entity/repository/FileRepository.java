package com.example.Dblog.domain.file.entity.repository;

import com.example.Dblog.domain.file.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
