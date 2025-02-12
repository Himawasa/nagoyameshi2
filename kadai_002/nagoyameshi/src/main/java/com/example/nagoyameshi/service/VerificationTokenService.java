package com.example.nagoyameshi.service;

//必要なインポート
import org.springframework.stereotype.Service; // このクラスをサービス層として定義するアノテーション
import org.springframework.transaction.annotation.Transactional; // トランザクション管理を行うアノテーション

import com.example.nagoyameshi.entity.User; // ユーザー情報を表すエンティティクラス
import com.example.nagoyameshi.entity.VerificationToken; // 認証トークンを表すエンティティクラス
import com.example.nagoyameshi.repository.VerificationTokenRepository; // 認証トークンを操作するリポジトリ

/**
* 認証トークンに関連するビジネスロジックを担当するサービスクラス。
* メール認証に必要なトークンの生成や検索を行います。
*/
@Service
public class VerificationTokenService {

	// 認証トークンを操作するリポジトリ
	private final VerificationTokenRepository verificationTokenRepository;

	/**
	 * コンストラクタでリポジトリを注入。
	 * @param verificationTokenRepository 認証トークンリポジトリ
	 */
	public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
		this.verificationTokenRepository = verificationTokenRepository;
	}

	/**
	 * 認証トークンを作成し、データベースに保存します。
	 *
	 * @param user トークンを紐づけるユーザーオブジェクト
	 * @param token 作成するトークンの文字列
	 */
	@Transactional // メソッド内の操作をトランザクションとして扱う
	public void create(User user, String token) {
		// 新しいVerificationTokenオブジェクトを作成
		VerificationToken verificationToken = new VerificationToken();

		// トークンにユーザー情報を紐づける
		verificationToken.setUser(user);
		// トークン文字列を設定
		verificationToken.setToken(token);

		// データベースに保存
		verificationTokenRepository.save(verificationToken);
	}

	/**
	 * トークン文字列を使ってデータベースから認証トークンを検索します。
	 *
	 * @param token 検索対象のトークン文字列
	 * @return 見つかったVerificationTokenオブジェクト、またはnull
	 */
	public VerificationToken getVerificationToken(String token) {
		// リポジトリを使用してトークンを検索
		return verificationTokenRepository.findByToken(token);
	}
}
