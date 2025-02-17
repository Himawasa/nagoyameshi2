package com.example.nagoyameshi.service;

// 必要なインポート
import java.util.Map; // メタデータ（キーと値のペア）を取り扱うためのクラス
import java.util.Optional; // Nullチェックを簡潔にするためのクラス

import jakarta.servlet.http.HttpServletRequest; // HTTP リクエスト情報を扱うためのクラス

import org.springframework.beans.factory.annotation.Value; // application.properties から値を取得するためのアノテーション
import org.springframework.stereotype.Service; // サービスクラスとしてスプリングに認識させるためのアノテーション
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.util.NagoyameshiUtils;
import com.stripe.Stripe; // Stripe API を利用するための基礎クラス
import com.stripe.exception.StripeException; // Stripe API 呼び出し時の例外を処理するためのクラス
import com.stripe.model.Event; // Stripe イベント（Webhook データなど）を表すクラス
import com.stripe.model.Refund;
import com.stripe.model.StripeObject; // Stripe API が返す基本的なオブジェクトを表すクラス
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session; // Stripe の Checkout セッションを表すクラス
import com.stripe.param.RefundCreateParams;
import com.stripe.param.SubscriptionCancelParams;
import com.stripe.param.checkout.SessionCreateParams; // Checkout セッション作成時のパラメータを設定するクラス
import com.stripe.param.checkout.SessionRetrieveParams; // Checkout セッション情報を取得するためのパラメータクラス

import lombok.RequiredArgsConstructor;

/**
 * Stripe に関連する処理を担当するサービスクラス。
 * - 支払い処理のセッション作成
 * - Webhook イベントの処理
 */
@Service
@RequiredArgsConstructor
public class StripeService {

	// application.properties ファイルから Stripe API キーを取得
	@Value("${stripe.api-key}")
	private String stripeApiKey;
	@Value("${stripe.subscribe.price-id}")
	private String priceId;
	// ReservationService を使用して予約情報を保存するための依存性
	private final ReservationService reservationService;
	private final UserService userService;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	/**
	 * Stripe の Checkout セッションを作成し、そのセッション ID を返します。
	 *
	 * @param shopName 店舗名（商品名として使用）
	 * @param reservationRegisterForm 予約データを格納したフォーム
	 * @param httpServletRequest HTTP リクエスト情報（成功時やキャンセル時の URL を作成するために使用）
	 * @return 作成された Checkout セッションの ID
	 */
	public String createStripeSession(String shopName, ReservationRegisterForm reservationRegisterForm,
			HttpServletRequest httpServletRequest) {
		// Stripe API キーを設定（初期化）
		Stripe.apiKey = stripeApiKey;

		// 現在のリクエスト URL を取得
		String requestUrl = new String(httpServletRequest.getRequestURL());

		// Stripe のセッション作成パラメータを設定
		SessionCreateParams params = SessionCreateParams.builder()
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD) // 支払い方法としてカードを許可
				.addLineItem( // 支払い項目を設定
						SessionCreateParams.LineItem.builder()
								.setPriceData(
										SessionCreateParams.LineItem.PriceData.builder()
												.setProductData(
														SessionCreateParams.LineItem.PriceData.ProductData.builder()
																.setName(shopName) // 商品名として店舗名を設定
																.build())
												.setUnitAmount((long) reservationRegisterForm.getAmount()) // 料金を設定（日本円）
												.setCurrency("jpy") // 通貨を日本円に設定
												.build())
								.setQuantity(1L) // 数量を1に設定
								.build())
				.setMode(SessionCreateParams.Mode.PAYMENT) // 支払いモード
				.setSuccessUrl(
						requestUrl.replaceAll("/shops/[0-9]+/reservations/confirm", "") + "/reservations?reserved") // 成功時のリダイレクト URL
				.setCancelUrl(requestUrl.replace("/reservations/confirm", "")) // キャンセル時のリダイレクト URL
				.setPaymentIntentData( // 支払い情報に予約データを含むメタデータを設定
						SessionCreateParams.PaymentIntentData.builder()
								.putMetadata("shopId", reservationRegisterForm.getShopId().toString())
								.putMetadata("userId", reservationRegisterForm.getUserId().toString())
								.putMetadata("reservationDate", reservationRegisterForm.getReservationDate())
								.putMetadata("numberOfPeople", reservationRegisterForm.getNumberOfPeople().toString())
								.putMetadata("amount", reservationRegisterForm.getAmount().toString())
								.build())
				.build();

		try {
			// セッションを作成し、セッション ID を返却
			Session session = Session.create(params);
			return session.getId();
		} catch (StripeException e) {
			e.printStackTrace(); // エラーが発生した場合はスタックトレースを出力
			return ""; // 空の文字列を返す
		}
	}

	/**
	 * Stripe の Sbscribe Checkout セッションを作成し、そのセッション ID を返します。
	 *
	 * @param userId ユーザーID
	 * @param httpServletRequest HTTP リクエスト情報（成功時やキャンセル時の URL を作成するために使用）
	 * @return 作成された Checkout セッションの ID
	 */
	public String createStripeSubscribeSession(Integer userId, HttpServletRequest httpServletRequest) {
		// Stripe API キーを設定（初期化）
		Stripe.apiKey = stripeApiKey;

		// 現在のリクエスト URL を取得
		String requestUrl = new String(httpServletRequest.getRequestURL());
		String successUrl = removeUserPath(requestUrl);
		// セッション作成
		SessionCreateParams params = SessionCreateParams.builder()
				.setSuccessUrl(successUrl + "/?subscribe") // 成功時のリダイレクト URL
				.setCancelUrl(requestUrl) // キャンセル時のリダイレクト URL
				.addLineItem(
						SessionCreateParams.LineItem.builder()
								.setQuantity(1L)
								.setPrice(priceId) // あなたが作成した価格ID
								.build())
				.setMode(SessionCreateParams.Mode.SUBSCRIPTION)
				.putMetadata("userId", NagoyameshiUtils.getCurrentUserId().toString())
				.build();

		try {
			Session session = Session.create(params);
			System.out.println("Session ID: " + session.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// セッションを作成し、セッション ID を返却
			Session session = Session.create(params);
			return session.getId();
		} catch (StripeException e) {
			e.printStackTrace(); // エラーが発生した場合はスタックトレースを出力
			return ""; // 空の文字列を返す
		}
	}

	private String removeUserPath(String url) {
		// "/user"以降を削除
		int index = url.indexOf("/user");
		if (index != -1) {
			return url.substring(0, index);
		}
		return url; // "/user"が見つからない場合は元のURLを返す
	}

	/**
	 * 定期購読を解除する
	 * @param userId
	 * @throws StripeException 
	 */
	@Transactional
	public void unSubscribe(Integer userId) throws StripeException {
		Stripe.apiKey = stripeApiKey;

		var opUser = userRepository.findById(userId);
		if (!opUser.isPresent()) {
			return;
		}
		User user = opUser.get();

		Subscription resource = Subscription.retrieve(user.getSubscriptionId());
		SubscriptionCancelParams params = SubscriptionCancelParams.builder().build();
		resource.cancel(params);

		user.setSubscriptionId(null);
		user.setRole(roleRepository.findByName("ROLE_GENERAL"));

		userRepository.save(user);
	}

	/**
	 * カスタマーポータルURLを返却する
	 * @param userId
	 * @param httpServletRequest
	 * @return
	 * @throws StripeException 
	 */
	public String createCustomerSupportSession(Integer userId, HttpServletRequest httpServletRequest)
			throws StripeException {
		// Stripe API キーを設定（初期化）
		Stripe.apiKey = stripeApiKey;

		// 現在のリクエスト URL を取得
		String requestUrl = new String(httpServletRequest.getRequestURL());
		String successUrl = removeUserPath(requestUrl);

		var opUser = userRepository.findById(userId);
		if (!opUser.isPresent()) {
			return "";
		}
		User user = opUser.get();

		com.stripe.param.billingportal.SessionCreateParams params = com.stripe.param.billingportal.SessionCreateParams
				.builder()
				.setCustomer(user.getCustomerId())
				.setReturnUrl(successUrl + "/?updateCreditCard") // ポータル利用後のリダイレクトURL
				.build();

		com.stripe.model.billingportal.Session session = com.stripe.model.billingportal.Session.create(params);

		return session.getUrl(); // カスタマーポータルのURLを返す
	}

	/**
	 * Webhook 経由で受信した Stripe の Checkout セッションイベントを処理し、予約を作成します。
	 *
	 * @param event Stripe イベントオブジェクト
	 */
	public void processSessionCompleted(Event event) {
		// Stripe オブジェクトを取得（セッションデータ）
		Optional<StripeObject> optionalStripeObject = event.getDataObjectDeserializer().getObject();

		// データが存在する場合のみ処理を実行
		optionalStripeObject.ifPresent(stripeObject -> {
			Session session = (Session) stripeObject; // セッションデータを取得
			SessionRetrieveParams params = SessionRetrieveParams.builder()
					.addExpand("payment_intent") // 支払い情報を展開
					.build();
			try {
				// Stripe API から最新のセッション情報を取得
				session = Session.retrieve(session.getId(), params, null);
				switch (session.getMode()) {
				case "payment" -> {
					// 予約成立の処理
					// 支払い情報から予約のメタデータを取得
					var paymentObject = session.getPaymentIntentObject();
					Map<String, String> paymentIntentObject = paymentObject.getMetadata();
					paymentIntentObject.put("paymentId", paymentObject.getId());
					// ReservationService を使用して予約を作成
					reservationService.create(paymentIntentObject);
				}
				// 有料会員登録はユーザーのRoleを変更
				case "subscription" -> userService.upgradeSubscribeAccount(session);
				default -> {
				}
				}

			} catch (StripeException e) {
				e.printStackTrace(); // エラーが発生した場合はスタックトレースを出力
			}
		});
	}

	/**
	 * 予約をキャンセルします。
	 *
	 * @param paymentIntentId キャンセルする支払いの ID
	 * @throws Exception Stripe API に関連する例外
	 */
	public void cancelReservation(String paymentIntentId) throws Exception {
		// 払い戻しを作成
		RefundCreateParams params = RefundCreateParams.builder()
				.setPaymentIntent(paymentIntentId)
				.build();
		Refund refund = Refund.create(params);

		System.out.println("Refund processed: " + refund.getId());
	}
}
