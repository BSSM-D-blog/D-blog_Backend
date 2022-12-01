package com.example.Dblog.domain.board.entity.repository;

import com.example.Dblog.domain.board.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Page<BoardEntity> findAll(Pageable pageable);

    @Query("SELECT COUNT(b) from BoardEntity b")
    Long findCountWithJPQL();
}
