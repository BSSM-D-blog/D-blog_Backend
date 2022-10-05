package com.example.Dblog.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class FileService {
    private FileRepository fileRepository;

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
    public Long saveFile(MultipartFile file) {
        try {
            String originFileName = file.getOriginalFilename();
            assert originFileName != null;
            String extension = originFileName.substring(originFileName.lastIndexOf("."));
            String uuid = UUID.randomUUID().toString();
            String savePath = System.getProperty("user.dir") + "\\src\\main\\resources\\images";
            String filePath = savePath + "\\" + uuid + extension;
            file.transferTo(new File(filePath));
            String saveServerPath = "localhost:8080/images/" + uuid + extension;
            FileDto fileDto = new FileDto();
            fileDto.setOriginalname(originFileName);
            fileDto.setFilename(uuid);
            fileDto.setFilepath(saveServerPath);

            return fileRepository.save(fileDto.toEntity()).getId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
