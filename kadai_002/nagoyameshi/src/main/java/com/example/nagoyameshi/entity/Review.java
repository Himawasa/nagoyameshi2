package com.example.nagoyameshi.entity;

// 必要なクラスをインポート
import java.sql.Timestamp; // データベースのタイムスタンプ型に対応するクラス

// JPAのアノテーションとクラス
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data; // Lombokを使用してgetter/setterやtoStringなどを自動生成

/**
 * Reviewエンティティクラス。
 * レビュー情報を格納するデータベースの「reviews」テーブルに対応。
 */
@Entity // JPAのエンティティとして指定
@Table(name = "reviews") // 対応するデータベースのテーブル名を指定
@Data // Lombokを使用してgetter/setterを自動生成
public class Review {

    // レビューのID (主キー)
    @Id // 主キーとして指定
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // データベースのAUTO_INCREMENT機能で値を自動生成
    @Column(name = "id") 
    // テーブルの列名を "id" として指定
    private Integer id;

    // 関連するShopエンティティ (多対一のリレーション)
    @ManyToOne // 多対一のリレーションを指定
    @JoinColumn(name = "shop_id") 
    // 外部キーの列名を "house_id" として指定
    private Shop shop;

    // 関連するUserエンティティ (多対一のリレーション)
    @ManyToOne // 多対一のリレーションを指定
    @JoinColumn(name = "user_id") 
    // 外部キーの列名を "user_id" として指定
    private User user;

    // レビューの内容
    @Column(name = "comment") 
    // テーブルの列名を "comment" として指定
    private String comment;

    // レビュー作成日時 (自動設定)
    @Column(name = "created_at", insertable = false, updatable = false)
    // データベース側で自動設定される列。挿入/更新操作を無効化
    private Timestamp createdAt;

    // レビュー更新日時 (自動設定)
    @Column(name = "updated_at", insertable = false, updatable = false)
    // データベース側で自動設定される列。挿入/更新操作を無効化
    private Timestamp updatedAt;

}
