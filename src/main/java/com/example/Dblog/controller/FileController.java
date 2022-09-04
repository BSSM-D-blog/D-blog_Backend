package com.example.Dblog.controller;

import com.example.Dblog.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {
    @Autowired
    FileRepository fileRepository;
//
//    @PostMapping("/api/post/file")
//    public
}
