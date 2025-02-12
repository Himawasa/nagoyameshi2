package com.example.nagoyameshi.event;

import org.springframework.context.ApplicationEvent; // Springのアプリケーションイベントを扱うクラス

import lombok.Getter; // Lombokのアノテーションでゲッターを自動生成

/**
 * ResetEventクラス
 * 
 * このクラスは、ユーザーがパスワードの再発行を実施した際に発生するカスタムイベントを表現します。
 * Springのイベントリスナー機能を利用して、認証メール送信などの処理をトリガーする目的で使用されます。
 */
@Getter // Lombokを使用して、フィールドのゲッターメソッドを自動生成
public class ResetEvent extends ApplicationEvent {

	/**
	 * メールアドレス
	 */
	private String email;

	/**
	 * リクエストURL
	 * 会員登録のリクエストが行われたURL（認証メールに記載するリンクの生成に使用されます）。
	 */
	private String requestUrl;

	/**
	 * コンストラクタ
	 * 
	 * @param source      イベントの発生元オブジェクト（通常はトリガー元のクラスのインスタンス）
	 * @param email       再発行用のEmail
	 * @param requestUrl  認証メールに使用するリクエストURL
	 */
	public ResetEvent(Object source, String email, String requestUrl) {
		super(source); // 親クラスのコンストラクタを呼び出し、イベントの発生元を指定

		// フィールドを初期化
		this.email = email;
		this.requestUrl = requestUrl;
	}
}
