package com.example.nagoyameshi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // 必要に応じてカスタムメソッドを追加できます
}
