package com.example.Dblog.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserRequestDto {
    @Size(min = 6, max = 25)
    @NotEmpty(message = "사용자 ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인는 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "닉네임은 필수입니다.")
    private String nickname;
}
