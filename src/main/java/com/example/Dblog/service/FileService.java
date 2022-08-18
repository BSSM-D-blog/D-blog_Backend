package com.example.Dblog.service;

import com.example.Dblog.dto.FileDto;
import com.example.Dblog.entity.FileEntity;
import com.example.Dblog.repository.FileRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FileService {
    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    @Transactional
    public Long saveFile(FileDto dto){
        return fileRepository.save(dto.toEntity()).getId();
    }

    @Transactional
    public FileDto getFile(Long id){
        FileEntity file = fileRepository.findById().get();

        FileDto fileDto = FileDto.builder()
                .originalFileName(file.getOriginalFileName())
                .saveFileName(file.getSaveFileName())
                .filePath(file.getFilePath())
                .build();
        return fileDto;
    }
}
