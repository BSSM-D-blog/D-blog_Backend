package com.example.Dblog.domain.comment.entity.repository;

import com.example.Dblog.domain.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByBoard(Long board);
    List<CommentEntity> findByParents(Long parent);
}
