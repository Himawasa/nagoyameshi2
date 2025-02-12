package com.example.nagoyameshi.service;

// 必要なインポート
import org.springframework.stereotype.Service; // サービスクラスとしてSpringに登録するためのアノテーション
import org.springframework.transaction.annotation.Transactional; // トランザクション管理

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Shop; // 店舗エンティティ
import com.example.nagoyameshi.entity.User; // ユーザーエンティティ
import com.example.nagoyameshi.form.ReviewEditForm; // レビュー編集用フォーム
import com.example.nagoyameshi.form.ReviewRegisterForm; // レビュー登録用フォーム
import com.example.nagoyameshi.repository.ReviewRepository; // レビューデータベース操作用リポジトリ

/**
 * レビューに関するビジネスロジックを提供するサービスクラス
 */
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository; // レビューデータベース操作用リポジトリ

    /**
     * コンストラクタ
     *
     * @param reviewRepository ReviewRepository のインスタンス
     */
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * 新しいレビューを作成して保存する
     *
     * @param Shop 店舗情報
     * @param user ユーザー情報
     * @param reviewRegisterForm レビュー登録用フォーム
     */
    @Transactional
    public void create(Shop Shop, User user, ReviewRegisterForm reviewRegisterForm) {
        // Reviewクラスのインスタンスを生成
        Review review = new Review();

        // reviewインスタンスにフォームから取得したデータをセット
        review.setShop(Shop); // 対象の店舗を設定
        review.setUser(user); // レビューを書いたユーザーを設定
        review.setComment(reviewRegisterForm.getComment()); // レビューのコメントを設定

        // データベースに保存
        reviewRepository.save(review);
    }

    /**
     * 既存のレビューを更新する
     *
     * @param reviewEditForm レビュー編集用フォーム
     */
    @Transactional
    public void update(ReviewEditForm reviewEditForm) {
        // データベースから編集対象のレビューを取得
        Review review = reviewRepository.getReferenceById(reviewEditForm.getId());

        // reviewインスタンスにフォームのデータを上書き設定
        review.setComment(reviewEditForm.getComment()); // 新しいコメントを設定

        // データベースに保存
        reviewRepository.save(review);
    }

    /**
     * ユーザーがすでに特定の店舗にレビューを投稿しているか確認する
     *
     * @param Shop 店舗情報
     * @param user ユーザー情報
     * @return レビュー済みなら true、それ以外は false
     */
    public boolean hasUserAlreadyReviewed(Shop Shop, User user) {
        // データベースでユーザーがすでにレビューを投稿しているかを確認
        if (reviewRepository.findByShopAndUser(Shop, user) != null) {
            return true; // レビュー済み
        }
        return false; // 未レビュー
    }
}
