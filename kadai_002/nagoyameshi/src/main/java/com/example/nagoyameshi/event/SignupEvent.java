package com.example.nagoyameshi.event;

import org.springframework.context.ApplicationEvent; // Springのアプリケーションイベントを扱うクラス

import com.example.nagoyameshi.entity.User; // ユーザーエンティティをインポート

import lombok.Getter; // Lombokのアノテーションでゲッターを自動生成

/**
 * SignupEventクラス
 * 
 * このクラスは、ユーザーが会員登録を行った際に発生するカスタムイベントを表現します。
 * Springのイベントリスナー機能を利用して、認証メール送信などの処理をトリガーする目的で使用されます。
 */
@Getter // Lombokを使用して、フィールドのゲッターメソッドを自動生成
public class SignupEvent extends ApplicationEvent {
    /**
     * ユーザー情報
     * 会員登録を行ったユーザーを表します。
     */
    private User user;

    /**
     * リクエストURL
     * 会員登録のリクエストが行われたURL（認証メールに記載するリンクの生成に使用されます）。
     */
    private String requestUrl;

    /**
     * コンストラクタ
     * 
     * @param source      イベントの発生元オブジェクト（通常はトリガー元のクラスのインスタンス）
     * @param user        会員登録を行ったユーザー
     * @param requestUrl  認証メールに使用するリクエストURL
     */
    public SignupEvent(Object source, User user, String requestUrl) {
        super(source); // 親クラスのコンストラクタを呼び出し、イベントの発生元を指定

        // フィールドを初期化
        this.user = user;        
        this.requestUrl = requestUrl;
    }
}
