package com.example.nagoyameshi.event;

import org.springframework.context.ApplicationEventPublisher; // スプリングのイベントを発行するためのインターフェース
import org.springframework.stereotype.Component; // スプリングコンポーネントのアノテーション

/**
 * ResetEventPublisherクラス
 * 
 * このクラスは、リセットイベントを発行する役割を持ちます。
 * 他のコンポーネントがこのイベントをリスニングし、特定の処理を実行します。
 */
@Component // スプリング管理のコンポーネントとして登録
public class ResetEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher; // イベント発行用のインターフェース
    
    /**
     * コンストラクタ
     * 
     * @param applicationEventPublisher スプリングのイベント発行インターフェース
     */
    public ResetEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;                
    }
    
    /**
     * サインアップイベントを発行します。
     * 
     * @param user サインアップしたユーザー情報
     * @param requestUrl リクエストのURL
     */
    public void publishResetEvent(String email, String requestUrl) {
        // SignupEventオブジェクトを作成してイベントを発行
        applicationEventPublisher.publishEvent(new ResetEvent(this, email, requestUrl));
    }
}
