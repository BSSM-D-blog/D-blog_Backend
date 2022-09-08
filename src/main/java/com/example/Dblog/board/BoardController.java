package com.example.Dblog.board;

import com.example.Dblog.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class BoardController {

    private BoardService boardService;
    @PostMapping("/api/board/post")
    public ResponseEntity<?> Post(
            @Valid @RequestParam("user") UserEntity user,
            @Valid @RequestParam("title") String title,
            @Valid @RequestParam("content") String content,
            MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        BoardEntity board = boardService.addBoard(BoardEntity.builder()
                .postKey(null)
                .title(title)
                .content(content)
                .viewCnt(0)
                .commentCnt(0)
                .created(null)
                .build(), (List<MultipartFile>) multipartHttpServletRequest);

        URI uriLocation = new URI("/board/" + board.getPostKey());

        return ResponseEntity.created(uriLocation).body("{}");
    }
}
