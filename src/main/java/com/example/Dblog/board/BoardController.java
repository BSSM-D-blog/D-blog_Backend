package com.example.Dblog.board;

import com.example.Dblog.file.FileDto;
import com.example.Dblog.file.FileService;
import com.example.Dblog.user.UserEntity;
import com.example.Dblog.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;

    @PostMapping("/api/board")
    public String save(@RequestParam("file") Optional<MultipartFile> files, BoardCreateForm params){
        try{
            if(files.isPresent()) {
                String origFilename = files.get().getOriginalFilename();
                String filename = new MD5Generator(origFilename).toString();
                String savePath = System.getProperty("user.dir") + "\\files";
                if(!new File(savePath).exists()){
                    try{
                        new File(savePath).mkdir();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                String filePath = savePath + "\\" + filename;
                files.get().transferTo(new File(filePath));

                FileDto fileDto = new FileDto();
                fileDto.setOriginalname(origFilename);
                fileDto.setFilename(filename);
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
}
