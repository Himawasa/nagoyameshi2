package com.example.nagoyameshi.event;

import org.springframework.context.ApplicationEventPublisher; // スプリングのイベントを発行するためのインターフェース
import org.springframework.stereotype.Component; // スプリングコンポーネントのアノテーション

import com.example.nagoyameshi.entity.User; // ユーザーエンティティをインポート

/**
 * SignupEventPublisherクラス
 * 
 * このクラスは、サインアップイベントを発行する役割を持ちます。
 * 他のコンポーネントがこのイベントをリスニングし、特定の処理を実行します。
 */
@Component // スプリング管理のコンポーネントとして登録
public class SignupEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher; // イベント発行用のインターフェース
    
    /**
     * コンストラクタ
     * 
     * @param applicationEventPublisher スプリングのイベント発行インターフェース
     */
    public SignupEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;                
    }
    
    /**
     * サインアップイベントを発行します。
     * 
     * @param user サインアップしたユーザー情報
     * @param requestUrl リクエストのURL
     */
    public void publishSignupEvent(User user, String requestUrl) {
        // SignupEventオブジェクトを作成してイベントを発行
        applicationEventPublisher.publishEvent(new SignupEvent(this, user, requestUrl));
    }
}
