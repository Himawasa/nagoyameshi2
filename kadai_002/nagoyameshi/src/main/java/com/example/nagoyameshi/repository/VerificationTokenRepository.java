package com.example.nagoyameshi.repository;

//Spring Data JPAリポジトリ
import org.springframework.data.jpa.repository.JpaRepository; // JPAを使用したCRUD操作を提供するインターフェース

//アプリケーション内のエンティティ
import com.example.nagoyameshi.entity.VerificationToken; // VerificationTokenエンティティの操作をサポート

/**
* VerificationTokenエンティティを操作するためのリポジトリインターフェース。
* Spring Data JPAにより、自動でCRUD機能が提供されます。
*/
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {
	/**
	 * トークンに基づいてVerificationTokenを検索します。
	 *
	 * @param token トークン文字列
	 * @return 検索結果のVerificationToken
	 */
	public VerificationToken findByToken(String token);
}
