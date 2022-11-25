package com.example.Dblog.domain.board.controller;

import com.example.Dblog.domain.board.dto.BoardRequestDto;
import com.example.Dblog.domain.board.dto.BoardResponseDto;
import com.example.Dblog.domain.board.service.BoardService;
import com.example.Dblog.domain.category.service.CategoryService;
import com.example.Dblog.global.error.ResponseMessage;
import com.example.Dblog.global.error.ReponseStatus;
import com.example.Dblog.domain.file.entity.FileEntity;
import com.example.Dblog.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;
    private final CategoryService categoryService;
    
    @PostMapping("/api/board")
    public ResponseEntity<ResponseMessage> save(@RequestParam("file") Optional<MultipartFile> files, BoardRequestDto params){
        ResponseMessage message = new ResponseMessage();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        if(params.getCategory() != null && !categoryService.validCategory(params.getCategory())){
            message.setStatus(ReponseStatus.BAD_REQUEST);
            message.setMessage("카테고리가 존재하지 않습니다.");
            message.setData(null);
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }
        if(files.isPresent()){
            FileEntity file = fileService.saveFile(files.get());
            boardService.cretePost(params, file.getId());
        }else{
            boardService.cretePost(params);
        }
        message.setStatus(ReponseStatus.OK);
        message.setMessage("성공");
        message.setData(null);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/api/board")
    public List<BoardResponseDto> getBoardList(){
        return boardService.getBoardList();
    }

    @GetMapping("/api/board/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id){
        return boardService.getBoard(id);
    }

    @PutMapping("/api/board/{id}")
    public void updateBoard(@PathVariable Long id, @RequestParam("file") Optional<MultipartFile> file, BoardRequestDto boardCreateForm){
        boardService.updateBoard(id, file, boardCreateForm);
    }

    @DeleteMapping("/api/board/{id}")
    public void deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
    }
}
