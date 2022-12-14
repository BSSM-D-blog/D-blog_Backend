package com.example.Dblog.domain.user.service;

import com.example.Dblog.domain.category.dto.CategoryDto;
import com.example.Dblog.domain.category.entity.CategoryEntity;
import com.example.Dblog.domain.category.entity.repository.CategoryRepository;
import com.example.Dblog.domain.file.entity.FileEntity;
import com.example.Dblog.domain.file.service.FileService;
import com.example.Dblog.domain.user.presentation.dto.UserResponseDto;
import com.example.Dblog.domain.user.entity.UserEntity;
import com.example.Dblog.domain.user.entity.repository.UserRepository;
import com.example.Dblog.global.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    @Transactional
    public void updateProfile(String token, MultipartFile file){
        Claims parseToken = jwtTokenProvider.parseJwtToken(token);
        UserEntity user = userRepository.findByUsername(parseToken.getSubject()).orElseThrow(() -> new IllegalArgumentException("없는 사용자"));
        FileEntity saveFile = fileService.saveFile(file);
        user.setProfile(saveFile.getServerPath());
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

        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for(CategoryEntity categoryEntity : category){
            CategoryDto cate = CategoryDto.builder()
                    .name(categoryEntity.getName())
                    .user(categoryEntity.getUser())
                    .category(categoryEntity.getCategory())
                    .build();
            categoryDtoList.add(cate);
        }

        Map<String, Object> getPersonalInfoMap = new HashMap<>();
        getPersonalInfoMap.put("profile", user.get().getProfile());
        getPersonalInfoMap.put("nickname", user.get().getNickname());
        getPersonalInfoMap.put("category", categoryDtoList);

        return getPersonalInfoMap;
    }

}
