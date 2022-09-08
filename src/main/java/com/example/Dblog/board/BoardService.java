package com.example.Dblog.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    private BoardRepository boardRepository;
    private BoardPictureRepository boardPictureRepository;
    private FileHandler fileHandler;

    @Autowired
    public BoardService(BoardRepository boardRepository, BoardPictureRepository boardPictureRepository, FileHandler fileHandler) {
        this.boardRepository = boardRepository;
        this.boardPictureRepository = boardPictureRepository;
        this.fileHandler = fileHandler;
    }

    public BoardEntity addBoard(BoardEntity board, List<MultipartFile> files) throws Exception {
        List<BoardPicture> list = fileHandler.parseFileInfo(board.getPostKey(), files);
        if(list.isEmpty()) {

        }
        else{
            List<BoardPicture> pictureBeans = new ArrayList<>();
            for(BoardPicture boardPicture : list) {
                pictureBeans.add(boardPictureRepository.save(boardPicture));
            }
            board.setPictures(pictureBeans);
        }
        return boardRepository.save(board);
    }
}
