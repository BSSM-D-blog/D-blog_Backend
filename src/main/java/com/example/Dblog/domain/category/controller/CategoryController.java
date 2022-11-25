package com.example.Dblog.domain.category.controller;

import com.example.Dblog.domain.category.dto.CategoryDto;
import com.example.Dblog.domain.category.service.CategoryService;
import com.example.Dblog.global.jwt.service.JwtService;
import com.example.Dblog.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final JwtService jwtService;
    private final CategoryService categoryService;

    @PostMapping("/api/category")
    public void createCategory(@RequestBody Map<String, String> name, @RequestHeader("Authorization") String token){
        Optional<UserEntity> user = jwtService.getUserInfo(token);
        user.ifPresent(userEntity -> categoryService.createCategory(name.get("name"), userEntity.getId()));
    }

    @PutMapping("/api/category/{id}")
    public void updateCategory(@RequestBody Map<String, String> name, @PathVariable Long id){
        categoryService.updateCategory(name.get("name"), id);
    }

    @DeleteMapping("/api/category/{id}")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id );
    }

    @GetMapping("/api/category")
    public List<CategoryDto> getCategory(@RequestHeader("Authorization") String token){
        Optional<UserEntity> user = jwtService.getUserInfo(token);
        List<CategoryDto> category = null;
        if(user.isPresent()) {
             category = categoryService.getCategory(user.get().getId());
        }
        return category;
    }

    @GetMapping("/api/category/{name}")
    public List<CategoryDto> getOneOfCategories(@RequestHeader("Authorization") String token, @PathVariable String name){
        Optional<UserEntity> user = jwtService.getUserInfo(token);
        List<CategoryDto> category = null;
        if(user.isPresent()){
            category = categoryService.getOneOfCategories(user.get().getId(), name);
        }
        return category;
    }
}
