package com.example.Dblog.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByBoard(Long board);
    List<CommentEntity> findByParents(Long parent);
}
