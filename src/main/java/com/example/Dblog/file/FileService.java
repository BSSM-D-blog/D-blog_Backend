package com.example.Dblog.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.util.UUID;

@Service
public class FileService {
    private FileRepository fileRepository;

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
            String savePath = System.getProperty("user.dir") + "\\files";
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String filePath = savePath + "\\" + uuid + extension;
            file.transferTo(new File(filePath));

            FileDto fileDto = new FileDto();
            fileDto.setOriginalname(originFileName);
            fileDto.setFilename(uuid);
            fileDto.setFilepath(filePath);

            return fileRepository.save(fileDto.toEntity()).getId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
