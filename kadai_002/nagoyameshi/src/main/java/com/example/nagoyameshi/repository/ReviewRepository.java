package com.example.nagoyameshi.repository;

import java.util.List; // List型を使用するためのインポート

import org.springframework.data.domain.Page; // ページング機能をサポートするためのインポート
import org.springframework.data.domain.Pageable; // ページング条件を指定するためのインポート
import org.springframework.data.jpa.repository.JpaRepository; // JpaRepositoryを継承するためのインポート

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;

/**
 * Reviewエンティティに対するデータ操作を行うリポジトリインターフェース。
 * JpaRepositoryを継承することで基本的なCRUD操作が利用可能。
 */
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	/**
	 * 指定されたShopに関連する最新の6件のレビューを取得するメソッド。
	 * @param Shop 対象のShopエンティティ
	 * @return レビューのリスト (最大6件)
	 */
	List<Review> findTop6ByShopOrderByCreatedAtDesc(Shop shop);

	/**
	 * 指定されたShopとUserに関連するレビューを1件取得するメソッド。
	 * @param Shop 対象のShopエンティティ
	 * @param user 対象のUserエンティティ
	 * @return 該当するレビュー (なければnull)
	 */
	Review findByShopAndUser(Shop Shop, User user);

	/**
	 * 指定されたShopに関連するレビューの件数を取得するメソッド。
	 * @param Shop 対象のShopエンティティ
	 * @return レビューの件数
	 */
	long countByShop(Shop Shop);

	/**
	 * 指定されたShopに関連するレビューを作成日時の降順でページングして取得するメソッド。
	 * @param Shop 対象のShopエンティティ
	 * @param pageable ページング条件
	 * @return レビューのページング結果
	 */
	Page<Review> findByShopOrderByCreatedAtDesc(Shop Shop, Pageable pageable);
}
