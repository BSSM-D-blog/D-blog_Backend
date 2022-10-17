package com.example.Dblog.comment;

import com.example.Dblog.file.FileEntity;
import com.example.Dblog.file.FileRepository;
import com.example.Dblog.user.UserEntity;
import com.example.Dblog.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void createComment(CommentRequestDto dto){
        CommentEntity comment = dto.toEntity();
        commentRepository.save(comment);
    }

    @Transactional
    public boolean deleteComment(Long id){
        Optional<CommentEntity> comment = commentRepository.findById(id);
        if(comment.isPresent()){
            comment.get().setDelete(true);
            commentRepository.save(comment.get());
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateComment(String content, Long id){
        Optional<CommentEntity> comment = commentRepository.findById(id);
        if(comment.isPresent()){
            comment.get().setContent(content);
            LocalDateTime now = LocalDateTime.now();
            String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss"));
            comment.get().setModified(LocalDateTime.parse(format));
            commentRepository.save(comment.get());
            return true;
        }
        return false;
    }

    @Transactional
    public List<CommentResponseDto> getCommentList(Long board) {
        List<CommentEntity> comment = commentRepository.findByBoard(board);
        List<CommentResponseDto> response = new ArrayList<>();

        for(CommentEntity co : comment){
            Optional<UserEntity> getUser = userRepository.findById(co.getUser());
            Optional<FileEntity> file;
            CommentResponseDto res = null;
            if(getUser.isPresent()) {
                file = fileRepository.findById(getUser.get().getProfile());
                res = new CommentResponseDto(co, file.get().getServerPath(), getUser.get().getNickname());
            }
            response.add(res);
        }
        return response;
    }
}
