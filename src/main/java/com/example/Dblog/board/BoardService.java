package com.example.Dblog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void cretePost(BoardCreateForm form, Long fileId){
        BoardEntity board = new BoardEntity();
        board.setUser(form.getUser());
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        board.setFileid(fileId);
        this.boardRepository.save(board);
    }
    @Transactional
    public void cretePost(BoardCreateForm form){
        BoardEntity board = new BoardEntity();
        board.setUser(form.getUser());
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        this.boardRepository.save(board);
    }
}
