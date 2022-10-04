package com.example.Dblog.board;

import com.example.Dblog.file.FileDto;
import com.example.Dblog.file.FileService;
import lombok.RequiredArgsConstructor;
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
    private final BoardRepository boardRepository;

    @PostMapping("/api/board")
    public String save(@RequestParam("file") Optional<MultipartFile> files, BoardCreateForm params){
        try{
            if(files.isPresent()) {
                String origFilename = files.get().getOriginalFilename();
                assert origFilename != null;
                String extension = origFilename.substring(origFilename.lastIndexOf("."));
                String uuid = UUID.randomUUID().toString();
                String savePath = System.getProperty("user.dir") + "\\files";
                if(!new File(savePath).exists()){
                    try{
                        new File(savePath).mkdir();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                String filePath = savePath + "\\" + uuid+extension;
                files.get().transferTo(new File(filePath));

                FileDto fileDto = new FileDto();
                fileDto.setOriginalname(origFilename);
                fileDto.setFilename(uuid);
                fileDto.setFilepath(filePath);

                Long fileId = fileService.saveFile(fileDto);
                boardService.cretePost(params, fileId);
            }else{
                boardService.cretePost(params);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    @GetMapping("/api/board")
    public List<GetBoardDto> getBoardList(){
        return boardService.getBoardList();
    }
}
