package com.example.Dblog.category;

import com.example.Dblog.jwt.JwtService;
import com.example.Dblog.user.UserEntity;
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
    public void createCategory(@RequestBody String name, @RequestHeader("Authorization") String token){
        Optional<UserEntity> user = jwtService.getUserInfo(token);
        user.ifPresent(userEntity -> categoryService.createCategory(name, userEntity.getId()));
    }

    @PutMapping("/api/category/{id}")
    public void updateCategory(@RequestBody String name, @PathVariable Long id){
        categoryService.updateCategory(name, id);
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
