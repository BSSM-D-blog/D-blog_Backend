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
        List<BoardEntity> boardList = boards.getContent();
        return getBoardList(boardList);
    }

    public List<BoardResponseDto> getBoardList(List<BoardEntity> boardList){
        List<BoardResponseDto> getBoard = new ArrayList<>();
        for(BoardEntity board : boardList){
            BoardResponseDto boardResponseDto = getBoard(board);
            getBoard.add(boardResponseDto);
        }
        return getBoard;
    }
    @Transactional
    public BoardResponseDto getBoard(BoardEntity board){
        Optional<FileEntity> file = Optional.empty();
        Optional<CategoryEntity> category = Optional.empty();
        if(board.getFileid() != null) file = fileRepository.findById(board.getFileid());
        if(board.getCategory() != null) category = categoryRepository.findByCategory(board.getCategory());
        UserEntity user = userRepository.findByUsername(board.getUser()).orElseThrow(() -> new IllegalArgumentException("사용자가 없음"));

        return BoardResponseDto.builder()
                .id(board.getId())
                .profile(user.getProfile() != null ? user.getProfile() : "")
                .title(board.getTitle())
                .filePath(board.getFileid() == null ? "" : file.get().getServerPath())
                .category(board.getCategory() == null ? "전체" : category.get().getName())
                .content(board.getContent())
                .created(board.getCreated())
                .user(user.getNickname())
                .username(board.getUser())
                .userid(user.getId())
                .build();
    }

    @Transactional
    public BoardResponseDto getBoard(Long boardId){
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글 없음"));
        return getBoard(board);
    }
    @Transactional
    public int getPage(Pageable pageable){
        return boardRepository.findAll(pageable).getTotalPages();
    }

    @Transactional
    public List<BoardResponseDto> getBoardListWithUsernameAndCategoryId(String username, Long categoryId){
        List<BoardEntity> board = boardRepository.findByUserAndCategory(username, categoryId);
        return getBoardList(board);
    }

    @Transactional
    public List<BoardResponseDto> getBoardListWithUsername(Long userId){
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        List<BoardEntity> board = boardRepository.findByUser(user.getUsername());
        return getBoardList(board);
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
