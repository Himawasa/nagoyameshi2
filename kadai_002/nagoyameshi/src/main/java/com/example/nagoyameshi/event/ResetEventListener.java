package com.example.nagoyameshi.event;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.event.EventListener; // イベントをリスニングするためのアノテーション
import org.springframework.mail.SimpleMailMessage; // 簡易なメールメッセージを表現するクラス
import org.springframework.mail.javamail.JavaMailSender; // メール送信をサポートするインターフェース
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component; // スプリングのコンポーネントクラスを指定

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * ResetEventListenerクラス
 * 
 * このクラスは、`ResetEvent`が発生した際にリスニングし、
 * パスワードリセットメールを送信する役割を担います。
 */
@Component
@RequiredArgsConstructor
public class ResetEventListener {

	private final JavaMailSender javaMailSender; // メール送信用サービス
	private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
	/**
	 * サインアップイベントをリスニングし、パスワードを再発行してメールを送信します。
	 * 
	 * @param resetEvent イベント（`ResetEvent`）
	 */
	@EventListener // イベントリスナーとして動作するメソッド
	private void onResetEvent(ResetEvent resetEvent) {

		// メール送信に必要な情報を設定
		String recipientAddress = resetEvent.getEmail(); // メール送信先アドレス
		String subject = "パスワード再発行"; // メールの件名
		String message = "新しいパスワードはこちらです。"; // メール本文
		String newPassword = RandomStringUtils.randomAlphabetic(16);
		String confirmationUrl = resetEvent.getRequestUrl() + "/login"; // ログインURL

		// ハッシュ済みの最新パスワードを保存
		User user = userRepository.findByEmail(resetEvent.getEmail());
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);

		// メールメッセージを構築
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(recipientAddress); // 受信者
		mailMessage.setSubject(subject); // 件名
		mailMessage.setText(message + "\n" + newPassword + "\n" + confirmationUrl); // 本文
		// メールを送信
		javaMailSender.send(mailMessage);
	}
}
