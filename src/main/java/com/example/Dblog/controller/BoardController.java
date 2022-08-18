package com.example.Dblog.controller;

import com.example.Dblog.dto.BoardDto;
import com.example.Dblog.entity.BoardEntity;
import com.example.Dblog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {

    @Autowired
    BoardRepository boardRepository;

    @PostMapping("/api/board/post")
    public void Post(BoardDto dto){
        System.out.println(dto.toString());

        BoardEntity boardEntity = dto.toEntity();
        System.out.println(boardEntity.toString());

        BoardEntity save = boardRepository.save(boardEntity);
        System.out.println(save.toString());
    }
}
