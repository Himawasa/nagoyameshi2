package com.example.nagoyameshi.controller;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest; // HTTPリクエスト情報

import org.springframework.data.domain.Page; // ページネーション用
import org.springframework.data.domain.Pageable; // ページネーションの設定
import org.springframework.data.domain.Sort.Direction; // 並び順の指定
import org.springframework.data.web.PageableDefault; // ページネーションのデフォルト設定
import org.springframework.security.core.annotation.AuthenticationPrincipal; // 認証情報の取得
import org.springframework.stereotype.Controller; // コントローラの指定
import org.springframework.ui.Model; // ビューにデータを渡すためのオブジェクト
import org.springframework.validation.BindingResult; // バリデーションの結果を格納
import org.springframework.validation.annotation.Validated; // バリデーションの有効化
import org.springframework.web.bind.annotation.GetMapping; // GETリクエスト用のマッピング
import org.springframework.web.bind.annotation.ModelAttribute; // モデル属性を取得
import org.springframework.web.bind.annotation.PathVariable; // URLのパス変数を取得
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // リダイレクト時にメッセージを渡す

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.ReservationService;
import com.example.nagoyameshi.service.StripeService;
import com.example.nagoyameshi.util.NagoyameshiUtils;

import lombok.RequiredArgsConstructor;

/**
 * 予約関連のリクエストを処理するコントローラ
 */
@Controller
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationRepository reservationRepository; // 予約データ操作用リポジトリ
	private final ShopRepository shopRepository; // 店舗データ操作用リポジトリ
	private final ReservationService reservationService; // 予約処理サービス
	private final StripeService stripeService; // Stripe決済サービス

	/**
	 * ユーザーの予約一覧を表示
	 */
	@GetMapping("/reservations")
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {
		User user = userDetailsImpl.getUser(); // 認証済みユーザーを取得
		Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable); // ユーザーの予約を取得

		model.addAttribute("reservationPage", reservationPage); // ページ情報をビューに渡す

		return "reservations/index"; // 予約一覧ページを表示
	}

	/**
	 * 予約入力画面の処理
	 */
	@GetMapping("/shops/{id}/reservations/input")
	public String input(@PathVariable(name = "id") Integer id,
			@ModelAttribute @Validated ReservationInputForm reservationInputForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes,
			Model model) {
		// 確認画面にリダイレクト
		redirectAttributes.addFlashAttribute("reservationInputForm", reservationInputForm);
		return "redirect:/shops/{id}/reservations/confirm";
	}

	/**
	 * 予約確認画面の処理
	 */
	@GetMapping("/shops/{id}/reservations/confirm")
	public String confirm(@PathVariable(name = "id") Integer id,
			@ModelAttribute ReservationInputForm reservationInputForm,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			HttpServletRequest httpServletRequest,
			Model model) {
		Shop shop = shopRepository.getReferenceById(id); // 店舗データを取得
		User user = userDetailsImpl.getUser(); // 認証済みユーザーを取得

		// 来店日時を取得
		LocalDateTime reservationDate = LocalDateTime.parse(reservationInputForm.getCommingDate(),
				NagoyameshiUtils.COMMING_DATE_TIME_FORMATTER);

		// 宿泊料金を計算
		Integer price = shop.getPrice();
		Integer amount = reservationService.calculateAmount(reservationInputForm.getNumberOfPeople(), price);

		// 予約登録フォームを作成
		ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(shop.getId(), user.getId(),
				reservationDate.format(NagoyameshiUtils.COMMING_DATE_TIME_FORMATTER),
				reservationInputForm.getNumberOfPeople(), amount);

		// Stripeのセッションを作成
		// NOTE 決済処理はモックのためしない
		String sessionId = stripeService.createStripeSession(shop.getName(), reservationRegisterForm,
				httpServletRequest);

		// ビューにデータを渡す
		model.addAttribute("shop", shop);
		model.addAttribute("reservationRegisterForm", reservationRegisterForm);
		model.addAttribute("sessionId", sessionId);

		return "reservations/confirm"; // 予約確認画面を表示
	}

	/**
	 * 予約をキャンセル
	 * @param id
	 * @return
	 */
	@PostMapping("/reservation/cancel/{id}")
	public String cancel(@PathVariable(name = "id") Integer id) {
		var reservation = reservationRepository.findById(id).orElseGet(null);

		// ありえないエラー
		if (reservation == null) {
			return "redirect:/reservations?error";
		}
		// Stripeへ支払いキャンセルを実施する
		try {
			stripeService.cancelReservation(reservation.getPaymentId());
		} catch (Exception e) {
			return "redirect:/reservations?cancelError";
		}
		// DBから予約を削除する
		reservationRepository.deleteById(id);
		
		return "redirect:/reservations?cancel";
	}
}
