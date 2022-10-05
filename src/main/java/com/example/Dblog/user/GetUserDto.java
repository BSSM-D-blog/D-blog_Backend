package com.example.Dblog.user;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
public class GetUserDto {
    private Long id;
    private String nickname;
    private LocalDateTime created;
    private Role role;
    private String profile;

    @Builder
    public GetUserDto(Optional<UserEntity> user){
        if(user.isPresent()){
            this.id = user.get().getId();
            this.nickname = user.get().getNickname();
            this.created = user.get().getCreated();
            this.role = user.get().getRole();
            this.profile = user.get().getProfile();
        }
    }
}
