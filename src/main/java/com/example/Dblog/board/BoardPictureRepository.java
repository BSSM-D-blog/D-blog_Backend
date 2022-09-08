package com.example.Dblog.board;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BoardPictureRepository extends CrudRepository<BoardPicture, Long> {
    BoardPicture save(BoardPicture fileEntity);

    List<BoardPicture> findAllByBoard(Long board);

}
