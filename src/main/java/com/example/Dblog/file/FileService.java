package com.example.Dblog.file;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FileService {
    private FileRepository fileRepository;

    public FileService(FileRepository file){
        this.fileRepository = file;
    }

    @Transactional
    public FileDto getFile(Long id) {
        FileEntity file = fileRepository.findById(id).get();
        FileDto fileDto = FileDto.builder()
                .id(id)
                .originalname(file.getOriginalname())
                .filename(file.getFilename())
                .filepath(file.getFilepath())
                .build();
        return fileDto;
    }

    @Transactional
    public Long saveFile(FileDto fileDto) {
        return fileRepository.save(fileDto.toEntity()).getId();
    }
}
