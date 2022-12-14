package com.example.Dblog.domain.user.presentation.dto;
import com.example.Dblog.domain.user.entity.Role;
import com.example.Dblog.domain.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
public class UserResponseDto {
    private Long id;
    private String nickname;
    private LocalDateTime created;
    private Role role;
    private String profile;
    private String username;

    @Builder
    public UserResponseDto(Optional<UserEntity> user){
        if(user.isPresent()){
            this.username = user.get().getUsername();
            this.id = user.get().getId();
            this.nickname = user.get().getNickname();
            this.created = user.get().getCreated();
            this.role = user.get().getRole();
            this.profile = user.get().getProfile();
        }
    }
}
