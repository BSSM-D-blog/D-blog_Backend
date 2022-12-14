package com.example.Dblog.domain.board.service;

import com.example.Dblog.domain.board.presentation.dto.BoardRequestDto;
import com.example.Dblog.domain.board.presentation.dto.BoardResponseDto;
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
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
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
    public List<BoardResponseDto> getBoardList(Pageable pageable){
        Page<BoardEntity> boards = boardRepository.findAll(pageable);
        List<BoardResponseDto> getBoard = new ArrayList<>();
        List<BoardEntity> boardList = boards.getContent();
        getBoardList(boardList, getBoard);
        return getBoard;
    }

    public void getBoardList(List<BoardEntity> boardList, List<BoardResponseDto> getBoard){
        for(BoardEntity board : boardList){
            Optional<FileEntity> file = Optional.empty();
            Optional<CategoryEntity> category = Optional.empty();
            Optional<UserEntity> user = userRepository.findByUsername(board.getUser());;
            if(board.getFileid() != null) file = fileRepository.findById(board.getFileid());
            if(board.getCategory() != null) category = categoryRepository.findByCategory(board.getCategory());

            assert file.isPresent();
            assert category.isPresent();
            BoardResponseDto getBoardListDto = BoardResponseDto.builder()
                    .id(board.getId())
                    .profile(user.isPresent() && user.get().getProfile() != null ? user.get().getProfile() : "")
                    .title(board.getTitle())
                    .filePath(board.getFileid() == null ? "" : file.get().getServerPath())
                    .category(board.getCategory() == null ? "전체" : category.get().getName())
                    .content(board.getContent())
                    .created(board.getCreated())
                    .user(user.get().getNickname())
                    .username(board.getUser())
                    .build();
            getBoard.add(getBoardListDto);
        }
    }

    @Transactional
    public BoardResponseDto getBoard(Long boardId){
        Optional<BoardEntity> board = boardRepository.findById(boardId);
        BoardResponseDto boardDto = null;
        if(board.isPresent()){
            Optional<FileEntity> file = Optional.empty();
            Optional<CategoryEntity> category = Optional.empty();
            if(board.get().getFileid() != null) file = fileRepository.findById(board.get().getFileid());
            if(board.get().getCategory() != null) category = categoryRepository.findByCategory(board.get().getCategory());
            Optional<UserEntity> user = userRepository.findByUsername(board.get().getUser());
            boardDto = BoardResponseDto.builder()
                    .id(board.get().getId())
                    .profile(user.get().getProfile() != null ? user.get().getProfile() : "")
                    .title(board.get().getTitle())
                    .filePath(board.get().getFileid() == null ? "" : file.get().getServerPath())
                    .category(board.get().getCategory() == null ? "전체" : category.get().getName())
                    .content(board.get().getContent())
                    .created(board.get().getCreated())
                    .user(user.get().getNickname())
                    .username(board.get().getUser())
                    .build();
        }
        return boardDto;
    }
    @Transactional
    public int getPage(Pageable pageable){
        return boardRepository.findAll(pageable).getTotalPages();
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
    public void deleteBoard(String token, Long id){
        BoardEntity board = boardRepository.findById(id).orElseThrow(()-> new IllegalIdentifierException("해당 게시글이 없습니다."));
        if(board.getFileid() != null) {
            FileEntity file = fileRepository.findById(board.getFileid()).orElseThrow(()-> new IllegalArgumentException("해당 파일이 없습니다."));
            fileService.deleteFile(file);
        }
        boardRepository.delete(board);
    }
}
