package com.example.nagoyameshi.controller;

// 必要なインポート
import org.springframework.data.domain.Page; // ページネーション用
import org.springframework.data.domain.Pageable; // ページネーション用
import org.springframework.data.web.PageableDefault; // デフォルトのページ設定
import org.springframework.security.core.annotation.AuthenticationPrincipal; // 現在認証されたユーザー情報を取得
import org.springframework.stereotype.Controller; // コントローラークラスを示すアノテーション
import org.springframework.ui.Model; // テンプレートにデータを渡すためのクラス
import org.springframework.validation.BindingResult; // バリデーション結果を格納するクラス
import org.springframework.validation.annotation.Validated; // バリデーション用アノテーション
// 各種リクエストマッピング用
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // リダイレクト時にデータを渡すクラス

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReviewEditForm;
import com.example.nagoyameshi.form.ReviewRegisterForm;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.ReviewService;

import lombok.RequiredArgsConstructor;

/**
 * レビュー関連のリクエストを処理するコントローラー
 */
@Controller
@RequestMapping("/shops/{shopId}/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewRepository reviewRepository; // レビューデータベース操作用リポジトリ
    private final ShopRepository ShopRepository; // 店舗データベース操作用リポジトリ
    private final ReviewService reviewService; // レビュー操作用サービス

    /**
     * 指定された店舗のレビュー一覧を表示
     */
    @GetMapping
    public String index(@PathVariable(name = "shopId") Integer shopId,
                        @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable, Model model) {
        // 店舗データを取得
        Shop shop = ShopRepository.getReferenceById(shopId);

        // 指定された店舗のレビューを取得
        Page<Review> reviewPage = reviewRepository.findByShopOrderByCreatedAtDesc(shop, pageable);

        // テンプレートに渡すデータを設定
        model.addAttribute("shop", shop);
        model.addAttribute("reviewPage", reviewPage);

        return "reviews/index"; // レビュー一覧ページを返却
    }

    /**
     * 新しいレビューを登録するフォームを表示
     */
    @GetMapping("/register")
    public String register(@PathVariable(name = "shopId") Integer shopId, Model model) {
        // 店舗データを取得
        Shop shop = ShopRepository.getReferenceById(shopId);

        // テンプレートに渡すデータを設定
        model.addAttribute("shop", shop);
        model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());

        return "reviews/register"; // レビュー登録ページを返却
    }

    /**
     * 新しいレビューを作成
     */
    @PostMapping("/create")
    public String create(@PathVariable(name = "shopId") Integer shopId,
                         @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                         @ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        // 店舗データを取得
        Shop shop = ShopRepository.getReferenceById(shopId);

        // 現在ログインしているユーザー情報を取得
        User user = userDetailsImpl.getUser();

        // バリデーションエラーがある場合
        if (bindingResult.hasErrors()) {
            model.addAttribute("shop", shop);
            return "reviews/register"; // エラーがある場合、登録ページを再表示
        }

        // レビューを作成
        reviewService.create(shop, user, reviewRegisterForm);

        // 成功メッセージを設定
        redirectAttributes.addFlashAttribute("successMessage", "レビューを投稿しました。");

        return "redirect:/shops/{shopId}"; // 店舗詳細ページにリダイレクト
    }

    /**
     * レビューを編集するフォームを表示
     */
    @GetMapping("/{reviewId}/edit")
    public String edit(@PathVariable(name = "shopId") Integer shopId,
                       @PathVariable(name = "reviewId") Integer reviewId, Model model) {
        // 店舗データを取得
        Shop shop = ShopRepository.getReferenceById(shopId);

        // 編集対象のレビューを取得
        Review review = reviewRepository.getReferenceById(reviewId);

        // フォームデータを設定
        ReviewEditForm reviewEditForm = new ReviewEditForm(review.getId(), review.getComment());

        // テンプレートに渡すデータを設定
        model.addAttribute("shop", shop);
        model.addAttribute("review", review);
        model.addAttribute("reviewEditForm", reviewEditForm);

        return "reviews/edit"; // レビュー編集ページを返却
    }

    /**
     * レビューを更新
     */
    @PostMapping("/{reviewId}/update")
    public String update(@PathVariable(name = "shopId") Integer shopId,
                         @PathVariable(name = "reviewId") Integer reviewId,
                         @ModelAttribute @Validated ReviewEditForm reviewEditForm,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        // 店舗データを取得
        Shop shop = ShopRepository.getReferenceById(shopId);

        // 編集対象のレビューを取得
        Review review = reviewRepository.getReferenceById(reviewId);

        // バリデーションエラーがある場合
        if (bindingResult.hasErrors()) {
            model.addAttribute("shop", shop);
            model.addAttribute("review", review);
            return "reviews/edit"; // エラーがある場合、編集ページを再表示
        }

        // レビューを更新
        reviewService.update(reviewEditForm);

        // 成功メッセージを設定
        redirectAttributes.addFlashAttribute("successMessage", "レビューを編集しました。");

        return "redirect:/shops/{shopId}"; // 店舗詳細ページにリダイレクト
    }

    /**
     * レビューを削除
     */
    @PostMapping("/{reviewId}/delete")
    public String delete(@PathVariable(name = "reviewId") Integer reviewId, RedirectAttributes redirectAttributes) {
        // レビューを削除
        reviewRepository.deleteById(reviewId);

        // 成功メッセージを設定
        redirectAttributes.addFlashAttribute("successMessage", "レビューを削除しました。");

        return "redirect:/shops/{shopId}"; // 店舗詳細ページにリダイレクト
    }
}
