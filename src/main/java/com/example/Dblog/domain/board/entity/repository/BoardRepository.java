package com.example.Dblog.domain.board.entity.repository;

import com.example.Dblog.domain.board.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Page<BoardEntity> findAll(Pageable pageable);
    Optional<BoardEntity> findById(Long aLong);
    List<BoardEntity> findByUser(String user);

    List<BoardEntity> findByUserAndCategory(String username, Long category);
}
