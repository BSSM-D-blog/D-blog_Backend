package com.example.Dblog.board;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FileHandler {
    public List<BoardPicture> parseFileInfo(
            Long board,
            List<MultipartFile> multipartFiles
    ) throws Exception {
        List<BoardPicture> fileList = new ArrayList<>();
        if(multipartFiles.isEmpty()){
            return fileList;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());
        String absolutePath = new File("").getAbsolutePath() + "\\";

        String path = "images/" + current_date;
        File file = new File(path);

        if(!file.exists()){
            file.mkdirs();
        }

        for(MultipartFile multipartFile : multipartFiles){
            if(!multipartFile.isEmpty()){
                String contentType = multipartFile.getContentType();
                String originalFileExtension;

                if(ObjectUtils.isEmpty(contentType)){
                    break;
                }
                else{
                    if(contentType.contains("image/jpg")){
                        originalFileExtension = ".jpg";
                    }
                    else if(contentType.contains("image/png")){
                        originalFileExtension = ".png";
                    }
                    else if(contentType.contains("image/gif")){
                        originalFileExtension = ".gif";
                    }
                    else{
                        break;
                    }
                }
                String newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
                BoardPicture boardFicture = BoardPicture.builder()
                        .board(board)
                        .originalFileName(multipartFile.getOriginalFilename())
                        .saveFileName(path + "/" + newFileName)
                        .fileSize(multipartFile.getSize())
                        .build();
                fileList.add(boardFicture);

                file = new File(absolutePath + path + "/" + newFileName);
                multipartFile.transferTo(file);
            }
        }
        return fileList;
    }
}
