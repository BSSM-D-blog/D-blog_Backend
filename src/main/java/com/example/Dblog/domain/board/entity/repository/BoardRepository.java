package com.example.Dblog.domain.board.entity.repository;

import com.example.Dblog.domain.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}