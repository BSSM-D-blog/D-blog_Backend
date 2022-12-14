package com.example.Dblog.domain.user.service;

import com.example.Dblog.domain.board.entity.BoardEntity;
import com.example.Dblog.domain.board.entity.repository.BoardRepository;
import com.example.Dblog.domain.board.presentation.dto.BoardResponseDto;
import com.example.Dblog.domain.board.service.BoardService;
import com.example.Dblog.domain.category.dto.CategoryDto;
import com.example.Dblog.domain.category.entity.CategoryEntity;
import com.example.Dblog.domain.category.entity.repository.CategoryRepository;
import com.example.Dblog.domain.user.presentation.dto.UserResponseDto;
import com.example.Dblog.domain.user.entity.UserEntity;
import com.example.Dblog.domain.user.entity.repository.UserRepository;
import com.example.Dblog.global.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @Transactional
    public void updateProfile(String path, UserEntity user){
        user.setProfile(path);
        userRepository.save(user);
    }

    @Transactional
    public UserResponseDto findUser(String token){
        Claims parseToken = jwtTokenProvider.parseJwtToken(token);
        Optional<UserEntity> user = userRepository.findByUsername(parseToken.getSubject());
        return new UserResponseDto(user);
    }

    @Transactional
    public Map<String, Object> getPersonalInfo(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        List<CategoryEntity> category = categoryRepository.findByUser(id);
        List<BoardEntity> board = boardRepository.findByUser(user.get().getUsername());

        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for(CategoryEntity categoryEntity : category){
            CategoryDto cate = CategoryDto.builder()
                    .name(categoryEntity.getName())
                    .user(categoryEntity.getUser())
                    .category(categoryEntity.getCategory())
                    .build();
            categoryDtoList.add(cate);
        }

        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        boardService.getBoardList(board, boardResponseDtoList);

        Map<String, Object> getPersonalInfoMap = new HashMap<>();
        getPersonalInfoMap.put("profile", user.get().getProfile());
        getPersonalInfoMap.put("nickname", user.get().getNickname());
        getPersonalInfoMap.put("category", categoryDtoList);
        getPersonalInfoMap.put("board", boardResponseDtoList);

        return getPersonalInfoMap;
    }

}
