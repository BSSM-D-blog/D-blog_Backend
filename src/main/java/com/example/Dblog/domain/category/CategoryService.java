package com.example.Dblog.domain.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void createCategory(String name, Long user){
        CategoryDto categoryDto = CategoryDto.builder()
                .category(null)
                .name(name)
                .user(user)
                .build();
        categoryRepository.save(categoryDto.toEntity());
    }

    @Transactional
    public void updateCategory(String name, Long id){
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("카테고리가 없습니다."));
        category.setName(name);
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id){
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("카테고리가 없습니다."));
        categoryRepository.delete(category);
    }

    @Transactional
    public List<CategoryDto> getCategory(Long user){
        List<CategoryEntity> categoryEntityList = categoryRepository.findByUser(user);
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        setCategoryList(categoryEntityList, categoryDtoList);
        return categoryDtoList;
    }

    @Transactional
    public List<CategoryDto> getOneOfCategories(Long user, String name){
        List<CategoryEntity> categoryEntityList = categoryRepository.findByUserAndName(user, name);
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        setCategoryList(categoryEntityList, categoryDtoList);
        return categoryDtoList;
    }

    public boolean validCategory(Long id){
        Optional<CategoryEntity> category = categoryRepository.findByCategory(id);
        return category.isPresent();
    }

    public void setCategoryList(List<CategoryEntity> categoryEntityList, List<CategoryDto> categoryDtoList){
        for(CategoryEntity category : categoryEntityList){
            CategoryDto categoryDto = CategoryDto.builder()
                    .category(category.getCategory())
                    .name(category.getName())
                    .user(category.getUser())
                    .build();
            categoryDtoList.add(categoryDto);
        }
    }
}
