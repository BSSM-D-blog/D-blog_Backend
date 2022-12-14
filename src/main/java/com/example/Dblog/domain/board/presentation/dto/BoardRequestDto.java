package com.example.Dblog.domain.board.presentation.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BoardRequestDto {
    @NotEmpty(message = "작성자는 필수항목입니다.")
    private String user;

    @NotEmpty(message = "글 제목은 필수항목입니다.")
    private String title;

    @NotEmpty(message = "글 내용은 필수항목입니다.")
    private String content;
    private Long category;
}
