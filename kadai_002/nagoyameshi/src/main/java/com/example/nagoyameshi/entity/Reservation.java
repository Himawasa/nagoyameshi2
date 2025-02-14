package com.example.nagoyameshi.entity;

import java.sql.Timestamp; // データベースのタイムスタンプ型を使用
import java.time.LocalDateTime;

import jakarta.persistence.Column; // カラムのマッピングを指定するアノテーション
import jakarta.persistence.Entity; // このクラスがエンティティであることを示すアノテーション
import jakarta.persistence.GeneratedValue; // 主キーの値を自動生成するアノテーション
import jakarta.persistence.GenerationType; // 主キー生成戦略を指定する列挙型
import jakarta.persistence.Id; // 主キーを示すアノテーション
import jakarta.persistence.JoinColumn; // 外部キーの関連付けを指定するアノテーション
import jakarta.persistence.ManyToOne; // 多対一の関連を指定するアノテーション
import jakarta.persistence.Table; // 対応するテーブル名を指定するアノテーション

import org.springframework.format.annotation.DateTimeFormat;

import com.example.nagoyameshi.util.NagoyameshiUtils;

import lombok.Data;

/**
 * Reservationエンティティクラス
 * "reservations" テーブルと対応するクラス。
 * 店舗予約情報を管理するためのデータモデル。
 */
@Entity // このクラスがデータベースのエンティティであることを指定
@Table(name = "reservations") // このエンティティが "reservations" テーブルと対応していることを指定
@Data // Lombokを使用して、ゲッター、セッター、toString、equals、hashCodeを自動生成
public class Reservation {

	/**
	 * 主キー（ID）
	 * データベースの "id" 列と対応。
	 * 自動生成されるユニークな識別子。
	 */
	@Id // 主キーを指定
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 主キーの値をデータベースのAUTO_INCREMENT機能で自動生成
	@Column(name = "id") // データベースの列名を "id" にマッピング
	private Integer id;

	/**
	 * 店舗（Shopエンティティとの関連）
	 * データベースの "shop_id" 列と対応。
	 * 予約された店舗を参照する外部キー。
	 */
	@ManyToOne // 多対一のリレーションを指定
	@JoinColumn(name = "shop_id") // 外部キー列を "shop_id" にマッピング
	private Shop shop;

	/**
	 * ユーザー（Userエンティティとの関連）
	 * データベースの "user_id" 列と対応。
	 * この予約を行ったユーザーを参照する外部キー。
	 */
	@ManyToOne // 多対一のリレーションを指定
	@JoinColumn(name = "user_id") // 外部キー列を "user_id" にマッピング
	private User user;

	/**
	 * Stripe決済に使用した一意のID
	 */
	@JoinColumn(name = "payment_id")
	private String paymentId;
	
	/**
	 * 予約日
	 * データベースの "reservation_date" 列と対応。
	 * 予約の来店日時を表します。
	 */
	@Column(name = "reservation_date") // データベースの列名を "reservation_date" にマッピング
	@DateTimeFormat(pattern = NagoyameshiUtils.COMMING_DATE_TIME_FORMAT)
	private LocalDateTime reservationDate;

	/**
	 * 来店人数
	 * データベースの "number_of_people" 列と対応。
	 * 宿泊する人数を表します。
	 */
	@Column(name = "number_of_people") // データベースの列名を "number_of_people" にマッピング
	private Integer numberOfPeople;

	/**
	 * 合計金額
	 * データベースの "amount" 列と対応。
	 * 宿泊の合計料金を表します。
	 */
	@Column(name = "amount") // データベースの列名を "amount" にマッピング
	private Integer amount;

	/**
	 * 作成日時
	 * データベースの "created_at" 列と対応。
	 * レコードが作成された日時を表します。
	 * データベースが自動的に値を挿入します。
	 */
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;

	/**
	 * 更新日時
	 * データベースの "updated_at" 列と対応。
	 * レコードが最後に更新された日時を表します。
	 * データベースが自動的に値を更新します。
	 */
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;
}
