package com.example.Dblog.repository;

import com.example.Dblog.entity.BoardEntity;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<BoardEntity, Long> {
}
