package com.example.Dblog.domain.comment.service;

import com.example.Dblog.domain.comment.dto.CommentRequestDto;
import com.example.Dblog.domain.comment.dto.CommentResponseDto;
import com.example.Dblog.domain.comment.entity.CommentEntity;
import com.example.Dblog.domain.comment.entity.repository.CommentRepository;
import com.example.Dblog.domain.user.entity.UserEntity;
import com.example.Dblog.domain.user.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createComment(CommentRequestDto dto){
        CommentEntity comment = dto.toEntity();
        commentRepository.save(comment);
    }

    @Transactional
    public boolean deleteComment(Long id){
        Optional<CommentEntity> comment = commentRepository.findById(id);
        if(comment.isPresent()){
            comment.get().setDeleted(1);
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
        setResponse(response, comment);
        return response;
    }

    @Transactional
    public List<CommentResponseDto> getReplyComment(Long id) {
        List<CommentEntity> comment = commentRepository.findByParents(id);
        List<CommentResponseDto> response = new ArrayList<>();
        setResponse(response, comment);
        return response;
    }

    public void setResponse(List<CommentResponseDto> response, List<CommentEntity> comment) {
        for(CommentEntity co : comment){
            Optional<UserEntity> getUser = userRepository.findById(co.getUser());
            CommentResponseDto res = null;
            if(getUser.isPresent()) {
                res = new CommentResponseDto(co, getUser.get().getProfile(), getUser.get().getNickname());
            }
            response.add(res);
        }
    }
}
