package com.example.Dblog.user;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class GetUserDto {
    private Long id;
    private String nickname;
    private LocalDateTime created;
    private Role role;

    @Builder
    public GetUserDto(Optional<UserEntity> user){
        this.id = user.get().getId();
        this.nickname = user.get().getNickname();
        this.created = user.get().getCreated();
        this.role = user.get().getRole();
    }
}
