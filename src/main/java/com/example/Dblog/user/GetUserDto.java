package com.example.Dblog.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetUserDto {
    private Long id;
    private String nickname;
    private LocalDateTime created;

    @Builder
    public GetUserDto(UserEntity user){
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.created = user.getCreated();
    }
}
