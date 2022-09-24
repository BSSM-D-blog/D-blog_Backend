package com.example.Dblog.board;

import com.example.Dblog.board.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
