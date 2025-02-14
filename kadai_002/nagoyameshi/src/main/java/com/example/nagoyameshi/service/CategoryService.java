package com.example.nagoyameshi.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.form.CategoryEditForm;
import com.example.nagoyameshi.form.CategoryRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

/**
 * カテゴリに関する処理を行うサービスクラス。
 * このクラスは、データベース操作やビジネスロジックを担当します。
 */
@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;

	/**
	 * ページネーションと検索に対応したカテゴリデータを取得するメソッド。
	 *
	 * @param pageRequest ページング情報
	 * @param keyword     検索キーワード
	 * @return ページングされたカテゴリデータ
	 */
	public Page<Category> findCategory(PageRequest pageRequest, String keyword) {
		if (keyword == null || keyword.isBlank()) {
			return categoryRepository.findAll(pageRequest);
		} else {
			return categoryRepository.findByNameContainingIgnoreCase(keyword, pageRequest);
		}
	}

	/**
	 * 指定したIDのカテゴリを取得するメソッド。
	 *
	 * @param id カテゴリのID
	 * @return Optionalでラップされたカテゴリデータ
	 */
	public Optional<Category> findById(Integer id) {
		return categoryRepository.findById(id);
	}

	/**
	 * 新しいカテゴリを登録するメソッド。
	 * 
	 * @param CategoryRegisterForm フォームから送信されたカテゴリ登録データ
	 */
	@Transactional
	public void create(CategoryRegisterForm CategoryRegisterForm) {
		categoryRepository.save(new Category(CategoryRegisterForm.getName()));
	}

	/**
	 * 既存のカテゴリデータを更新するメソッド。
	 * 
	 * @param CategoryEditForm フォームから送信された編集用データ
	 */
	@Transactional
	public void update(CategoryEditForm CategoryEditForm) {
		Category Category = categoryRepository.findById(CategoryEditForm.getId())
				.orElseThrow(() -> new IllegalArgumentException("カテゴリが見つかりません。ID: " + CategoryEditForm.getId()));
		Category.setName(CategoryEditForm.getName());
		categoryRepository.save(Category);
	}

	/**
	 * カテゴリデータをIDで削除するメソッド。
	 * 
	 * @param id 削除対象のカテゴリのID
	 */
	@Transactional
	public void deleteById(Integer id) {
		if (!categoryRepository.existsById(id)) {
			throw new IllegalArgumentException("削除しようとしたカテゴリが見つかりません。ID: " + id);
		}
		categoryRepository.deleteById(id);
	}

	/**
	 * カテゴリーIDからカテゴリー名称を取得する。
	 * @param categoryId
	 * @return
	 */
	public String getCategoryName(Integer categoryId) {
		return categoryId != null ? categoryRepository.findById(categoryId)
				.map(Category::getName)
				.orElse(null)
				: null; // 存在しない場合はnullを返す
	}

}
