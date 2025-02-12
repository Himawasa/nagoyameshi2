package com.example.nagoyameshi.service;

import java.time.LocalDateTime;
import java.util.Map; // 支払い情報を格納するためのマップ

import org.springframework.stereotype.Service; // サービス層として定義するためのアノテーション
import org.springframework.transaction.annotation.Transactional; // トランザクション処理を保証するアノテーション

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * 予約に関する処理を行うサービスクラス。
 */
@Service
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository reservationRepository; // 予約データを操作するリポジトリ
	private final ShopRepository shopRepository; // 店舗データを操作するリポジトリ
	private final UserRepository userRepository; // ユーザーデータを操作するリポジトリ

	/**
	 * 支払い情報を基に予約データを作成して保存するメソッド。
	 * 
	 * @param paymentIntentObject 支払い情報を格納したマップ
	 */
	@Transactional // このメソッドがトランザクションとして実行されることを保証
	public void create(Map<String, String> paymentIntentObject) {
		Reservation reservation = new Reservation(); // 新しい予約エンティティを作成

		// 支払い情報から店舗IDとユーザーIDを取得
		Integer houseId = Integer.valueOf(paymentIntentObject.get("houseId"));
		Integer userId = Integer.valueOf(paymentIntentObject.get("userId"));

		// 店舗情報とユーザー情報をリポジトリから取得
		Shop shop = shopRepository.getReferenceById(houseId);
		User user = userRepository.getReferenceById(userId);

		// 支払い情報から来店日、来店人数、料金を取得
		LocalDateTime reservationDate = LocalDateTime.parse(paymentIntentObject.get("reservationDate"));
		Integer numberOfPeople = Integer.valueOf(paymentIntentObject.get("numberOfPeople"));
		Integer amount = Integer.valueOf(paymentIntentObject.get("amount"));

		// 取得した情報を予約エンティティに設定
		reservation.setShop(shop);
		reservation.setUser(user);
		reservation.setNumberOfPeople(numberOfPeople);
		reservation.setReservationDate(reservationDate);
		reservation.setAmount(amount);

		// 予約データをデータベースに保存
		reservationRepository.save(reservation);
	}

	/**
	 * 宿泊料金を計算するメソッド。
	 * 
	 * @param numberOfCount 来店人数
	 * @param price 金額
	 * @return 
	 */
	public Integer calculateAmount(Integer numberOfCount, Integer price) {
		// 宿泊料金を計算（1泊料金 × 宿泊日数）
		return price * (int) numberOfCount;
	}
}
