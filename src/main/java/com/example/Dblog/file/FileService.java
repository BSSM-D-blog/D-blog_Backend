package com.example.Dblog.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class FileService {
    private FileRepository fileRepository;
    private String savePath = System.getProperty("user.dir") + "\\src\\main\\resources\\images";

    @Autowired
    ServletContext servletContext;

    public FileService(FileRepository file){
        this.fileRepository = file;
    }

    @Transactional
    public FileDto getFile(Long id) {
        FileEntity file = fileRepository.findById(id).get();
        return FileDto.builder()
                .id(id)
                .originalname(file.getOriginalname())
                .filename(file.getFilename())
                .filepath(file.getFilepath())
                .build();
    }

    @Transactional
    public FileEntity saveFile(MultipartFile file) {
        try {
            String originFileName = file.getOriginalFilename();
            assert originFileName != null;
            String extension = originFileName.substring(originFileName.lastIndexOf("."));
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + extension;
            String filePath = savePath + "\\" + fileName;
            file.transferTo(new File(filePath));
            String saveServerPath = "10.150.149.114:8080/images/" + fileName;
            FileDto fileDto = new FileDto();
            fileDto.setOriginalname(originFileName);
            fileDto.setFilename(fileName);
            fileDto.setFilepath(filePath);
            fileDto.setServerPath(saveServerPath);

            return fileRepository.save(fileDto.toEntity());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void updateFile(FileEntity entity, Optional<MultipartFile> file) {
        try {
            if(file.isPresent()){
                String originFileName = file.get().getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                assert originFileName != null;
                String extension = originFileName.substring(originFileName.lastIndexOf("."));
                String fileName = uuid + extension;
                String filePath = savePath + "\\" + fileName;
                file.get().transferTo(new File(filePath));
                String saveServerPath = "10.150.149.114:8080/images/" + fileName;
                entity.setOriginalname(originFileName);
                entity.setFilename(fileName);
                entity.setFilepath(filePath);
                entity.setServerPath(saveServerPath);
                fileRepository.save(entity);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Transactional
    public boolean deleteFile(FileEntity file){
        File file1 = new File(savePath + "\\" + file.getFilename());
        boolean result = file1.delete();
        fileRepository.delete(file);
        return result;
    }
}
