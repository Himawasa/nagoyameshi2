package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.FavoriteService;

import lombok.RequiredArgsConstructor;

/**
 * お気に入り機能を管理するコントローラークラス
 */
@Controller
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;
    private final ShopRepository shopRepository;
    private final FavoriteService favoriteService;


    /**
     * お気に入り一覧を表示
     *
     * @param userDetailsImpl ログイン中のユーザー情報
     * @param pageable        ページング情報
     * @param model           モデルオブジェクト
     * @return お気に入り一覧ページのテンプレートパス
     */
    @GetMapping("/favorites")
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                        @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable, Model model) {
        // ログイン中のユーザーを取得
        User user = userDetailsImpl.getUser();
        // ユーザーのお気に入り一覧を取得
        Page<Favorite> favoritePage = favoriteRepository.findByUserOrderByCreatedAtDesc(user, pageable);
        // モデルにお気に入り一覧を追加
        model.addAttribute("favoritePage", favoritePage);
        // お気に入り一覧ページを返却
        return "favorites/index";
    }

    /**
     * お気に入りを作成
     *
     * @param houseId          対象の店舗ID
     * @param userDetailsImpl  ログイン中のユーザー情報
     * @param redirectAttributes リダイレクト時のフラッシュメッセージ
     * @param model            モデルオブジェクト
     * @return 店舗詳細ページへのリダイレクトパス
     */
    @PostMapping("/shops/{shopId}/favorites/create")
    public String create(@PathVariable(name = "shopId") Integer shopId,
                         @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                         RedirectAttributes redirectAttributes, Model model) {
        // 店舗情報を取得
        Shop shop = shopRepository.getReferenceById(shopId);
        // ログイン中のユーザーを取得
        User user = userDetailsImpl.getUser();
        // お気に入りを作成
        favoriteService.create(shop, user);
        // 成功メッセージを設定
        redirectAttributes.addFlashAttribute("successMessage", "お気に入りに追加しました。");
        // 店舗詳細ページへリダイレクト
        return "redirect:/shops/" + shopId;
    }

    /**
     * お気に入りを削除
     *
     * @param shopId          対象の店舗ID
     * @param favoriteId       対象のお気に入りID
     * @param redirectAttributes リダイレクト時のフラッシュメッセージ
     * @return 店舗詳細ページへのリダイレクトパス
     */
    @PostMapping("/shops/{shopId}/favorites/{favoriteId}/delete")
    public String delete(@PathVariable(name = "shopId") Integer shopId,
                         @PathVariable(name = "favoriteId") Integer favoriteId,
                         RedirectAttributes redirectAttributes) {
        // お気に入りを削除
        favoriteRepository.deleteById(favoriteId);
        // 成功メッセージを設定
        redirectAttributes.addFlashAttribute("successMessage", "お気に入りを解除しました。");
        // 店舗詳細ページへリダイレクト
        return "redirect:/shops/" + shopId;
    }
}
