package com.example.nagoyameshi.event;

import java.util.UUID; // 一意の識別子を生成するために使用

import org.springframework.context.event.EventListener; // イベントをリスニングするためのアノテーション
import org.springframework.mail.SimpleMailMessage; // 簡易なメールメッセージを表現するクラス
import org.springframework.mail.javamail.JavaMailSender; // メール送信をサポートするインターフェース
import org.springframework.stereotype.Component; // スプリングのコンポーネントクラスを指定

import com.example.nagoyameshi.entity.User; // ユーザーエンティティをインポート
import com.example.nagoyameshi.service.VerificationTokenService; // トークン生成サービスをインポート

/**
 * SignupEventListenerクラス
 * 
 * このクラスは、`SignupEvent`が発生した際にリスニングし、
 * ユーザー認証メールを送信する役割を担います。
 */
@Component // スプリング管理のコンポーネントとして登録
public class SignupEventListener {
    private final VerificationTokenService verificationTokenService; // 認証トークンを管理するサービス
    private final JavaMailSender javaMailSender; // メール送信用サービス
    
    /**
     * コンストラクタ
     * 
     * @param verificationTokenService 認証トークン管理サービス
     * @param mailSender メール送信サービス
     */
    public SignupEventListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender) {
        this.verificationTokenService = verificationTokenService;        
        this.javaMailSender = mailSender;
    }

    /**
     * サインアップイベントをリスニングし、認証メールを送信します。
     * 
     * @param signupEvent サインアップイベント（`SignupEvent`）
     */
    @EventListener // イベントリスナーとして動作するメソッド
    private void onSignupEvent(SignupEvent signupEvent) {
        // 会員登録を行ったユーザー情報を取得
        User user = signupEvent.getUser();
        
        // 認証トークンを生成（UUIDを使用）
        String token = UUID.randomUUID().toString();
        
        // 認証トークンを保存（ユーザー情報と関連付け）
        verificationTokenService.create(user, token);
        
        // メール送信に必要な情報を設定
        String recipientAddress = user.getEmail(); // メール送信先アドレス
        String subject = "メール認証"; // メールの件名
        String confirmationUrl = signupEvent.getRequestUrl() + "/verify?token=" + token; // 認証URL
        String message = "以下のリンクをクリックして会員登録を完了してください。"; // メール本文
        
        // メールメッセージを構築
        SimpleMailMessage mailMessage = new SimpleMailMessage(); 
        mailMessage.setTo(recipientAddress); // 受信者
        mailMessage.setSubject(subject); // 件名
        mailMessage.setText(message + "\n" + confirmationUrl); // 本文（認証リンクを含む）
        
        // メールを送信
        javaMailSender.send(mailMessage);
    }
}
