package com.example.Dblog.domain.board.service;

import com.example.Dblog.domain.board.dto.BoardRequestDto;
import com.example.Dblog.domain.board.dto.BoardResponseDto;
import com.example.Dblog.domain.board.entity.BoardEntity;
import com.example.Dblog.domain.board.entity.repository.BoardRepository;
import com.example.Dblog.domain.category.entity.CategoryEntity;
import com.example.Dblog.domain.category.entity.repository.CategoryRepository;
import com.example.Dblog.domain.file.entity.FileEntity;
import com.example.Dblog.domain.file.entity.repository.FileRepository;
import com.example.Dblog.domain.file.service.FileService;
import com.example.Dblog.domain.user.entity.UserEntity;
import com.example.Dblog.domain.user.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public void cretePost(BoardRequestDto form, Long fileId){
        BoardEntity board = new BoardEntity();
        board.setUser(form.getUser());
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        board.setFileid(fileId);
        board.setCategory(form.getCategory());
        this.boardRepository.save(board);
    }
    @Transactional
    public void cretePost(BoardRequestDto form){
        BoardEntity board = new BoardEntity();
        board.setUser(form.getUser());
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        board.setCategory(form.getCategory());
        this.boardRepository.save(board);
    }

    @Transactional
    public List<BoardResponseDto> getBoardList(){
        List<BoardEntity> boards = boardRepository.findAll();
        List<BoardResponseDto> getBoard = new ArrayList<>();

        for(BoardEntity board : boards){
            Optional<FileEntity> file = Optional.empty();
            Optional<CategoryEntity> category = Optional.empty();
            if(board.getFileid() != null) file = fileRepository.findById(board.getFileid());
            if(board.getCategory() != null) category = categoryRepository.findByCategory(board.getCategory());
            Optional<UserEntity> user = userRepository.findByUsername(board.getUser());
            BoardResponseDto boardDto = null;
            if(file.isPresent() && category.isPresent() && user.isPresent()){
                boardDto = BoardResponseDto.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .created(board.getCreated())
                        .user(board.getUser())
                        .filePath(file.get().getServerPath())
                        .category(category.get().getName())
                        .profile(user.get().getProfile())
                        .build();
            }else if(user.isPresent()){
                boardDto = BoardResponseDto.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .created(board.getCreated())
                        .user(board.getUser())
                        .filePath("")
                        .category("전체")
                        .profile(user.get().getProfile())
                        .build();
            }
            getBoard.add(boardDto);
        }
        return getBoard;
    }

    @Transactional
    public BoardResponseDto getBoard(Long boardId){
        Optional<BoardEntity> board = boardRepository.findById(boardId);
        Optional<FileEntity> file = Optional.empty();
        Optional<UserEntity> user = Optional.empty();
        Optional<CategoryEntity> category = Optional.empty();
        BoardResponseDto boardDto = null;
        if(board.isPresent()){
            if(board.get().getFileid() != null) file = fileRepository.findById(board.get().getFileid());
            if (board.get().getUser() != null) user = userRepository.findByUsername(board.get().getUser());
            if(board.get().getCategory() != null) category = categoryRepository.findByCategory(board.get().getCategory());
            if(file.isPresent() && category.isPresent() && user.isPresent()){
                boardDto = BoardResponseDto.builder()
                        .id(board.get().getId())
                        .title(board.get().getTitle())
                        .content(board.get().getContent())
                        .created(board.get().getCreated())
                        .user(board.get().getUser())
                        .filePath(file.get().getServerPath())
                        .category(category.get().getName())
                        .profile(user.get().getProfile())
                        .build();
            }
        }
        return boardDto;
    }

    @Transactional
    public void updateBoard(Long id, Optional<MultipartFile> file, BoardRequestDto boardCreateForm){
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
