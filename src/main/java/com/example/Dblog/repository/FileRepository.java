package com.example.Dblog.repository;

import com.example.Dblog.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {


}
