package com.example.Dblog.category;

import com.example.Dblog.jwt.JwtService;
import com.example.Dblog.jwt.JwtTokenProvider;
import com.example.Dblog.user.UserEntity;
import com.example.Dblog.user.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
