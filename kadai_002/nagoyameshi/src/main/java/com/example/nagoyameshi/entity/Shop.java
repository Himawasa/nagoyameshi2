package com.example.nagoyameshi.entity;

import java.sql.Timestamp; // データベースのタイムスタンプ型に対応

import jakarta.persistence.Column; // データベースのカラムを指定するアノテーション
import jakarta.persistence.Entity; // このクラスをエンティティとして指定するアノテーション
import jakarta.persistence.GeneratedValue; // 主キーの値を自動生成するためのアノテーション
import jakarta.persistence.GenerationType; // 主キーの生成戦略を指定する列挙型
import jakarta.persistence.Id; // 主キーを指定するアノテーション
import jakarta.persistence.Table; // テーブル名を指定するアノテーション

import lombok.Data; // Lombokの@Dataアノテーションで、ゲッター・セッター、toString、equals、hashCodeを自動生成

/**
 * Shopエンティティクラス
 * "shops" テーブルと対応するクラスです。
 * 店舗情報を表現するデータモデル。
 */
@Entity // このクラスがデータベースのエンティティであることを指定
@Table(name = "shops") // このエンティティが "shops" テーブルと対応していることを指定
@Data // Lombokを使用して、ゲッター、セッター、toString、equals、hashCodeを自動生成
public class Shop {

    /**
     * 主キー（ID）
     * データベースの "id" 列と対応します。
     * 自動生成されるユニークな識別子。
     */
    @Id // 主キーを指定
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // データベースの列名を "id" にマッピング
    private Integer id;

    /**
     * カテゴリID
     * データベースの "category_id" 列と対応します。
     */
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    /**
     * 店舗名
     * データベースの "name" 列と対応します。
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 店舗の画像ファイル名
     * データベースの "image" 列と対応します。
     */
    @Column(name = "image")
    private String image;

    /**
     * 店舗の説明
     * データベースの "description" 列と対応します。
     */
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * 営業時間
     * データベースの "business_hours" 列と対応します。
     */
    @Column(name = "business_hours")
    private String businessHours;

    /**
     * 価格
     * データベースの "price" 列と対応します。
     */
    @Column(name = "price")
    private Integer price;

    /**
     * 郵便番号
     * データベースの "postal_code" 列と対応します。
     */
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    /**
     * 住所
     * データベースの "address" 列と対応します。
     */
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * 電話番号
     * データベースの "phone_number" 列と対応します。
     */
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    /**
     * 定休日
     * データベースの "regular_holiday" 列と対応します。
     */
    @Column(name = "regular_holiday")
    private String regularHoliday;

    /**
     * 作成日時
     * データベースの "created_at" 列と対応します。
     */
    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    /**
     * 更新日時
     * データベースの "updated_at" 列と対応します。
     */
    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;
}