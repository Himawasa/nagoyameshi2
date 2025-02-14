package com.example.nagoyameshi.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.form.CategoryEditForm;
import com.example.nagoyameshi.form.CategoryRegisterForm;
import com.example.nagoyameshi.service.CategoryService;

import lombok.RequiredArgsConstructor;

/**
 * 管理者向けのカテゴリコントローラクラス。
 * 新規登録、編集、削除などの機能を提供します。
 */
@Controller
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {
	private final CategoryService categoryService;

	/**
	 * カテゴリー一覧ページ（ページネーション対応）
	 */
	@GetMapping
	public String index(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "keyword", required = false) String keyword,
			Model model) {
		Page<Category> categoryPage = categoryService.findCategory(PageRequest.of(page, 10), keyword);
		model.addAttribute("categoryPage", categoryPage);
		model.addAttribute("keyword", keyword);
		return "admin/category/index";
	}

	/**
	 * 新規カテゴリー登録ページを表示
	 */
	@GetMapping("/register")
	public String newCategory(Model model) {
		model.addAttribute("categoryRegisterForm", new CategoryRegisterForm());
		return "admin/category/register";
	}

	/**
	 * 新規カテゴリーを登録
	 */
	@PostMapping("/register")
	public String createCategory(@Valid @ModelAttribute CategoryRegisterForm categoryRegisterForm,
			BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "admin/category/register";
		}

		categoryService.create(categoryRegisterForm);
		return "redirect:/admin/category?register";
	}

	/**
	 * カテゴリー詳細ページを表示
	 */
	@GetMapping("/{id}")
	public String showCategory(@PathVariable Integer id, Model model) {
		Category category = categoryService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + id));

		model.addAttribute("category", category);
		model.addAttribute("categoryName", categoryService.getCategoryName(id));
		return "admin/category/show";
	}

	/**
	 * カテゴリー編集画面を表示
	 */
	@GetMapping("/{id}/edit")
	public String editCategory(@PathVariable Integer id, Model model) {
		Category category = categoryService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + id));
		CategoryEditForm categoryEditForm = new CategoryEditForm();
		categoryEditForm.setId(category.getId());
		categoryEditForm.setName(category.getName());
		model.addAttribute("categoryEditForm", categoryEditForm);
		return "admin/category/edit";
	}

	/**
	 * カテゴリー情報を更新
	 */
	@PostMapping("/{id}")
	public String updateCategory(@PathVariable Integer id, @Valid @ModelAttribute CategoryEditForm categoryEditForm,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "admin/category/edit";
		}

		categoryService.update(categoryEditForm);
		return "redirect:/admin/category?edit";
	}

	/**
	 * カテゴリーを削除
	 */
	@PostMapping("/{id}/delete")
	public String deleteCategory(@PathVariable Integer id) {
		categoryService.deleteById(id);
		return "redirect:/admin/category?delete";
	}
}
