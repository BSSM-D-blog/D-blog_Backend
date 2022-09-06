package com.example.Dblog.repository;

import com.example.Dblog.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileRepository extends CrudRepository<FileEntity, Long> {
}
