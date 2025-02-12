package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Shop;

/**
 * Shopエンティティを操作するためのリポジトリインターフェース。
 * Spring Data JPAにより、自動でCRUD機能が提供されます。
 */
public interface ShopRepository extends JpaRepository<Shop, Integer> {

	/**
	 * 名前が指定したキーワードに部分一致するShopエンティティを検索します。
	 *
	 * @param keyword 検索キーワード
	 * @param pageable ページング情報（ページサイズ、ソート順など）
	 * @return ページングされた検索結果
	 */
	Page<Shop> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

	/**
	 * 名前または住所が指定したキーワードに部分一致するShopエンティティを、
	 * 作成日時の降順で検索します。
	 *
	 * @param nameKeyword 名前の検索キーワード
	 * @param addressKeyword 住所の検索キーワード
	 * @param pageable ページング情報
	 * @return ページングされた検索結果
	 */
	Page<Shop> findByNameLikeOrAddressLikeOrderByCreatedAtDesc(String nameKeyword, String addressKeyword,
			Pageable pageable);

	/**
	 * カテゴリーが一致するShopエンティティを、
	 * 作成日時の降順で検索します。
	 * 
	 * @param categoryId カテゴリの検索キーワード
	 * @param pageable ページング情報
	 * @return ページングされた検索結果
	 */
	Page<Shop> findByCategoryIdOrderByCreatedAtDesc(Integer categoryId, Pageable pageable);

	/**
	 * 全てのShopエンティティを作成日時の降順で取得します。
	 *
	 * @param pageable ページング情報
	 * @return ページングされた全てのHouseエンティティ
	 */
	Page<Shop> findAllByOrderByCreatedAtDesc(Pageable pageable);

	/**
	 * 作成日時の降順でトップ10件のShopエンティティを取得します。
	 *
	 * @return トップ10件のShopエンティティをリストで返す
	 */
	List<Shop> findTop10ByOrderByCreatedAtDesc();
}
