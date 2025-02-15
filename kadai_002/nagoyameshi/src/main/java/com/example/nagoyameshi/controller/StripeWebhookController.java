package com.example.nagoyameshi.controller;

import org.springframework.beans.factory.annotation.Value; // application.properties から値を取得するためのアノテーション
import org.springframework.http.HttpStatus; // HTTPステータスコードを表現するクラス
import org.springframework.http.ResponseEntity; // HTTPレスポンスを作成するためのクラス
import org.springframework.stereotype.Controller; // コントローラとして指定するアノテーション
import org.springframework.web.bind.annotation.PostMapping; // POSTリクエスト用のマッピング
import org.springframework.web.bind.annotation.RequestBody; // HTTPリクエストのボディを受け取るアノテーション
import org.springframework.web.bind.annotation.RequestHeader; // HTTPリクエストのヘッダーを取得するアノテーション

import com.example.nagoyameshi.service.StripeService;
import com.stripe.Stripe; // Stripe APIを使用するためのクラス
import com.stripe.exception.SignatureVerificationException; // Webhookシグネチャ検証失敗時の例外
import com.stripe.model.Event; // Stripeイベントを表現するクラス
import com.stripe.net.Webhook; // Stripe Webhookのユーティリティクラス

/**
 * Stripe Webhook用のコントローラ
 * StripeからのWebhookイベントを受信して処理します。
 */
@Controller
public class StripeWebhookController {
	private final StripeService stripeService; // Stripe関連処理を担当するサービス

	@Value("${stripe.api-key}") // application.properties から Stripe APIキーを取得
	private String stripeApiKey;

	@Value("${stripe.webhook-secret}") // application.properties から Webhookシークレットを取得
	private String webhookSecret;

	/**
	 * コンストラクタで StripeService を注入
	 */
	public StripeWebhookController(StripeService stripeService) {
		this.stripeService = stripeService;
	}

	/**
	 * Stripe Webhookを処理するエンドポイント
	 * 
	 * @param payload 受信したリクエストのボディ
	 * @param sigHeader Stripe-Signature ヘッダーの値
	 * @return 処理結果のレスポンス
	 */
	@PostMapping("/stripe/webhook")
	public ResponseEntity<String> webhook(@RequestBody String payload,
			@RequestHeader("Stripe-Signature") String sigHeader) {
		// Stripe APIキーを設定
		Stripe.apiKey = stripeApiKey;
		Event event = null;

		try {
			// Webhookシグネチャを検証し、イベントを構築
			event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
		} catch (SignatureVerificationException e) {
			// シグネチャ検証が失敗した場合、400 BAD_REQUEST を返す
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		// イベントタイプが "checkout.session.completed" の場合の処理
		if ("checkout.session.completed".equals(event.getType())) {
			// StripeService を使用してセッション完了イベントを処理
			stripeService.processSessionCompleted(event);
		}

		// 処理が成功した場合、200 OK を返す
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}
	
}
