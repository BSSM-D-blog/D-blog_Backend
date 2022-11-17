package com.example.Dblog.comment;

import com.example.Dblog.comment.dto.CommentRequestDto;
import com.example.Dblog.comment.dto.CommentResponseDto;
import com.example.Dblog.error.ReponseStatus;
import com.example.Dblog.error.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<ResponseMessage> createComment(@RequestBody CommentRequestDto requestDto){
        ResponseMessage message = new ResponseMessage();
        HttpHeaders headers= new HttpHeaders();
        commentService.createComment(requestDto);
        message.setStatus(ReponseStatus.OK);
        message.setMessage("성공");
        message.setData(null);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/api/comment/{board}")
    public ResponseEntity<ResponseMessage> getComment(@PathVariable Long board){
        List<CommentResponseDto> res = commentService.getCommentList(board);
        ResponseMessage message = new ResponseMessage();
        HttpHeaders headers= new HttpHeaders();
        message.setStatus(ReponseStatus.OK);
        message.setMessage("성공");
        message.setData(res);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
