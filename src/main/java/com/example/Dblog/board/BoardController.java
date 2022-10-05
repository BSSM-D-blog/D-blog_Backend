package com.example.Dblog.board;

import com.example.Dblog.file.FileEntity;
import com.example.Dblog.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;
    
    @PostMapping("/api/board")
    public void save(@RequestParam("file") Optional<MultipartFile> files, BoardCreateForm params){
        if(files.isPresent()){
           FileEntity file = fileService.saveFile(files.get());
           boardService.cretePost(params, file.getId());
        }else{
            boardService.cretePost(params);
        }
    }

    @GetMapping("/api/board")
    public List<GetBoardDto> getBoardList(){
        return boardService.getBoardList();
    }

    @GetMapping("/api/board/{id}")
    public GetBoardDto getBoard(@PathVariable Long id){
        return boardService.getBoard(id);
    }

    @PutMapping("/api/board/{id}")
    public void updateBoard(@PathVariable Long id, @RequestParam("file") Optional<MultipartFile> file, BoardCreateForm boardCreateForm){
        boardService.updateBoard(id, file, boardCreateForm);
    }

    @DeleteMapping("/api/board/{id}")
    public void deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
    }
}
