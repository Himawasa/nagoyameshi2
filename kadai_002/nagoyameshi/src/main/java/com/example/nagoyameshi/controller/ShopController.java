package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.CategoryService;
import com.example.nagoyameshi.service.FavoriteService;
import com.example.nagoyameshi.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {
	private final ShopRepository shopRepository;
	private final ReviewService reviewService;
	private final FavoriteService favoriteService;
	private final FavoriteRepository favoriteRepository;
	private final ReviewRepository reviewRepository;
	private final CategoryRepository categoryRepository;
	private final CategoryService categoryService;

	/**
	 * 店舗一覧を表示するメソッド。
	 * 
	 * @param keyword 検索キーワード
	 * @param categoryId カテゴリ
	 * @param pageable ページング情報
	 * @param model モデル
	 * @return 店舗一覧のテンプレート名
	 */
	@GetMapping
	public String index(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "categoryId", required = false) Integer categoryId,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {
		Page<Shop> shopPage;

		if (keyword != null && !keyword.isEmpty()) {
			shopPage = shopRepository.findByNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%",
					"%" + keyword + "%", pageable);
		} else if (categoryId != null && categoryId != 0) {
			shopPage = shopRepository.findByCategoryIdOrderByCreatedAtDesc(categoryId, pageable);
		} else {
			shopPage = shopRepository.findAll(pageable);
		}

		model.addAttribute("shopPage", shopPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("categories", categoryRepository.findAll());
		return "shops/index";
	}

	/**
	 * 店舗詳細を表示するメソッド。
	 * 
	 * @param id 店舗ID
	 * @param model モデル
	 * @return 店舗詳細のテンプレート名
	 */
	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		Shop shop = shopRepository.getReferenceById(id);

		// レビュー済みフラグとお気に入りフラグを初期化
		boolean reviewFlag = false;
		boolean favoriteFlag = false;
		Favorite favorite = null;

		// ユーザーがログインしている場合の処理
		if (userDetailsImpl != null) {
			User user = userDetailsImpl.getUser();
			reviewFlag = reviewService.hasUserAlreadyReviewed(shop, user);
			favoriteFlag = favoriteService.isFavorite(shop, user);

			if (favoriteFlag) {
				favorite = favoriteRepository.findByShopAndUser(shop, user);
			}
		}

		// モデルにフラグとお気に入り情報を追加
		model.addAttribute("reviewFlag", reviewFlag);
		model.addAttribute("favoriteFlag", favoriteFlag);
		model.addAttribute("favorite", favorite);

		// レビュー情報を取得してモデルに追加
		List<Review> reviewList = reviewRepository.findTop6ByShopOrderByCreatedAtDesc(shop);
		model.addAttribute("reviewList", reviewList);

		int totalCount = (int) reviewRepository.countByShop(shop);
		model.addAttribute("totalCount", totalCount);

		// 店舗情報をモデルに追加
		model.addAttribute("shop", shop);
		model.addAttribute("categoryName", categoryService.getCategoryName(shop.getCategoryId()));
		model.addAttribute("reservationInputForm", new ReservationInputForm());
		return "shops/show";
	}
}
