package com.example.nagoyameshi.controller;

import java.util.List; // リスト型のインポート

import org.springframework.stereotype.Controller; // Spring MVCのコントローラであることを示すアノテーション
import org.springframework.ui.Model; // ビューにデータを渡すためのオブジェクト
import org.springframework.web.bind.annotation.GetMapping; // GETリクエストを処理するためのアノテーション

import com.example.nagoyameshi.entity.Shop; // 名古屋飯店舗エンティティ
import com.example.nagoyameshi.repository.ShopRepository; // 店舗データベース操作用のリポジトリ

/**
 * ホーム画面を管理するコントローラ
 * ユーザーが最初にアクセスするホームページを表示するための処理を担当。
 */
@Controller
public class HomeController {
    private final ShopRepository shopRepository; // 店舗データ操作を行うリポジトリ

    /**
     * コンストラクタ
     * @param shopRepository 店舗データを操作するリポジトリ
     */
    public HomeController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository; // shopRepositoryをDIで注入
    }

    /**
     * ホーム画面を表示する
     * @param model ビューにデータを渡すためのオブジェクト
     * @return ホーム画面のテンプレート名
     */
    @GetMapping("/")
    public String index(Model model) {
        // 最新の10件の店舗データを取得する
        List<Shop> newShops = shopRepository.findTop10ByOrderByCreatedAtDesc();
        // ビューに "newShops" という名前でデータを渡す
        model.addAttribute("newShops", newShops);

        // ホーム画面のテンプレート "index" を返す
        return "index";
    }
}
