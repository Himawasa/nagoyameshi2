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

import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.form.ShopEditForm;
import com.example.nagoyameshi.form.ShopRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.service.CategoryService;
import com.example.nagoyameshi.service.ShopService;

import lombok.RequiredArgsConstructor;

/**
 * 管理者向けの店舗コントローラクラス。
 * 新規登録、編集、削除などの機能を提供します。
 */
@Controller
@RequestMapping("/admin/shops")
@RequiredArgsConstructor
public class AdminShopController {
	private final CategoryRepository categoryRepository;
	private final CategoryService categoryService;
	private final ShopService shopService;

	/**
	 * 店舗一覧ページ（ページネーション対応）
	 */
	@GetMapping
	public String index(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "keyword", required = false) String keyword,
			Model model) {
		Page<Shop> shopPage = shopService.findShops(PageRequest.of(page, 10), keyword);
		model.addAttribute("shopPage", shopPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("categories", categoryRepository.findAll()); // カテゴリ
		return "admin/shops/index";
	}

	/**
	 * 新規店舗登録ページを表示
	 */
	@GetMapping("/register")
	public String newShop(Model model) {
		model.addAttribute("shopRegisterForm", new ShopRegisterForm());
		model.addAttribute("categories", categoryRepository.findAll());
		return "admin/shops/register";
	}

	/**
	 * 新規店舗を登録
	 */
	@PostMapping("/register")
	public String createShop(@Valid @ModelAttribute ShopRegisterForm shopRegisterForm, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryRepository.findAll());
			return "admin/shops/register";
		}

		shopService.create(shopRegisterForm);
		return "redirect:/admin/shops?register";
	}

	/**
	 * 店舗詳細ページを表示
	 */
	@GetMapping("/{id}")
	public String showShop(@PathVariable Integer id, Model model) {
		Shop shop = shopService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found: " + id));

		model.addAttribute("shop", shop);
		model.addAttribute("categoryName", categoryService.getCategoryName(shop.getCategoryId()));
		return "admin/shops/show";
	}

	/**
	 * 店舗編集画面を表示
	 */
	@GetMapping("/{id}/edit")
	public String editShop(@PathVariable Integer id, Model model) {
		Shop shop = shopService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found: " + id));

		ShopEditForm shopEditForm = new ShopEditForm();
		shopEditForm.setId(shop.getId());
		shopEditForm.setName(shop.getName());
		shopEditForm.setCategoryId(shop.getCategoryId());
		shopEditForm.setDescription(shop.getDescription());
		shopEditForm.setBusinessHours(shop.getBusinessHours());
		shopEditForm.setPrice(shop.getPrice());
		shopEditForm.setPostalCode(shop.getPostalCode());
		shopEditForm.setAddress(shop.getAddress());
		shopEditForm.setPhoneNumber(shop.getPhoneNumber());
		shopEditForm.setRegularHoliday(shop.getRegularHoliday());

		model.addAttribute("shopEditForm", shopEditForm);
		model.addAttribute("categories", categoryRepository.findAll());
		return "admin/shops/edit";
	}

	/**
	 * 店舗情報を更新
	 */
	@PostMapping("/{id}")
	public String updateShop(@PathVariable Integer id, @Valid @ModelAttribute ShopEditForm shopEditForm,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryRepository.findAll());
			return "admin/shops/edit";
		}

		shopService.update(shopEditForm);
		return "redirect:/admin/shops?edit";
	}

	/**
	 * 店舗を削除
	 */
	@PostMapping("/{id}/delete")
	public String deleteShop(@PathVariable Integer id) {
		shopService.deleteById(id);
		return "redirect:/admin/shops?delete";
	}
}
