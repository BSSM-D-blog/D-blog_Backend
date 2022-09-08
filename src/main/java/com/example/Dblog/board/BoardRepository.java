package com.example.Dblog.board;

import com.example.Dblog.board.BoardEntity;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<BoardEntity, Long> {
}
