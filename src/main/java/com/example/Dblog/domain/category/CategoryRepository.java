package com.example.Dblog.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByUser(Long user);
    Optional<CategoryEntity> findByCategory(Long category);
    List<CategoryEntity> findByUserAndName(Long user, String name);
}
