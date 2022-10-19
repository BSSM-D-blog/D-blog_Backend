package com.example.Dblog.board;

import com.example.Dblog.category.CategoryService;
import com.example.Dblog.file.FileEntity;
import com.example.Dblog.file.FileRepository;
import com.example.Dblog.file.FileService;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;
    private final CategoryService categoryService;

    @Transactional
    public void cretePost(BoardCreateForm form, Long fileId){
        BoardEntity board = new BoardEntity();
        board.setUser(form.getUser());
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        board.setFileid(fileId);
        board.setCategory(form.getCategory());
        this.boardRepository.save(board);
    }
    @Transactional
    public void cretePost(BoardCreateForm form){
        BoardEntity board = new BoardEntity();
        board.setUser(form.getUser());
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        board.setCategory(form.getCategory());
        this.boardRepository.save(board);
    }

    @Transactional
    public List<GetBoardDto> getBoardList(){
        List<BoardEntity> boards = boardRepository.findAll();
        List<GetBoardDto> getBoard = new ArrayList<>();

        for(BoardEntity board : boards){
            Optional<FileEntity> file = Optional.empty();
            if(board.getFileid() != null) file = fileRepository.findById(board.getFileid());
            GetBoardDto boardDto;
            if(Objects.requireNonNull(file).isPresent()){
                boardDto = GetBoardDto.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .created(board.getCreated())
                        .user(board.getUser())
                        .filePath(file.get().getServerPath())
                        .build();
            }else{
                boardDto = GetBoardDto.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .created(board.getCreated())
                        .user(board.getUser())
                        .filePath("")
                        .build();
            }
            getBoard.add(boardDto);
        }
        return getBoard;
    }

    @Transactional
    public GetBoardDto getBoard(Long boardId){
        Optional<BoardEntity> board = Optional.ofNullable(boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("찾는 게시물이 없습니다.")));
        Optional<FileEntity> file = Optional.empty();
        GetBoardDto boardDto = null;
        if(board.isPresent()){
            if(board.get().getFileid() != null) file = Optional.ofNullable(fileRepository.findById(board.get().getFileid())
                    .orElseThrow(() -> new IllegalArgumentException("찾는 파일이 없습니다.")));
            if(file.isPresent()){
                boardDto = GetBoardDto.builder()
                        .id(board.get().getId())
                        .title(board.get().getTitle())
                        .content(board.get().getContent())
                        .created(board.get().getCreated())
                        .user(board.get().getUser())
                        .filePath(file.get().getServerPath())
                        .build();
            }
        }
        return boardDto;
    }

    @Transactional
    public void updateBoard(Long id, Optional<MultipartFile> file, BoardCreateForm boardCreateForm){
        BoardEntity board = boardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));
        if(board.getFileid() != null) {
            FileEntity getFile = fileRepository.findById(board.getFileid()).orElseThrow(()-> new IllegalArgumentException("해당 파일이 없습니다."));
            fileService.updateFile(getFile, file);
        }
        if(file.isPresent()){
            FileEntity saveFile = fileService.saveFile(file.get());
            board.setFileid(saveFile.getId());
        }
        board.setTitle(boardCreateForm.getTitle());
        board.setContent(boardCreateForm.getContent());
        board.setModifyDate(LocalDateTime.now());
        boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Long id){
        BoardEntity board = boardRepository.findById(id).orElseThrow(()-> new IllegalIdentifierException("해당 게시글이 없습니다."));
        if(board.getFileid() != null) {
            FileEntity file = fileRepository.findById(board.getFileid()).orElseThrow(()-> new IllegalArgumentException("해당 파일이 없습니다."));
            fileService.deleteFile(file);
        }
        boardRepository.delete(board);
    }
}
