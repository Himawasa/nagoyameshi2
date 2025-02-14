package com.example.nagoyameshi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	/**
	 * 名前が指定したキーワードに部分一致するCategoryエンティティを検索します。
	 *
	 * @param keyword 検索キーワード
	 * @param pageable ページング情報（ページサイズ、ソート順など）
	 * @return ページングされた検索結果
	 */
	Page<Category> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    /**
     * 指定したIDのCategoryエンティティを取得します。
     *
     * @param categoryId カテゴリID
     * @return カテゴリエンティティ
     */
    Optional<Category> findById(Integer categoryId);
}
