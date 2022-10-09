package com.example.Dblog.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByUser(Long id);
    Optional<CategoryEntity> findByName(String name);
}
