package com.example.Dblog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public List<GetBoardDto> getBoardList(){
        List<BoardEntity> boards = boardRepository.findAll();
        List<GetBoardDto> getBoard = new ArrayList<>();

        for(BoardEntity board : boards){
            GetBoardDto boardDto = GetBoardDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .created(board.getCreated())
                    .user(board.getUser())
                    .build();
            getBoard.add(boardDto);
        }
        return getBoard;
    }
}
