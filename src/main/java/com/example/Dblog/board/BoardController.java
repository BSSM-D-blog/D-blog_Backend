package com.example.Dblog.board;

import com.example.Dblog.file.FileDto;
import com.example.Dblog.file.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.wink.common.internal.model.admin.Resources;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;
    
    @PostMapping("/api/board")
    public void save(@RequestParam("file") Optional<MultipartFile> files, BoardCreateForm params){
        if(files.isPresent()){
           Long fileId = fileService.saveFile(files.get());
           boardService.cretePost(params, fileId);
        }else{
            boardService.cretePost(params);
        }
    }

    @GetMapping("/api/board")
    public List<GetBoardDto> getBoardList(){
        return boardService.getBoardList();
    }
}
