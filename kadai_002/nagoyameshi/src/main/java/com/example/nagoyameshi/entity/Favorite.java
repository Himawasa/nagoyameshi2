package com.example.nagoyameshi.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

/**
 * "Favorite" エンティティクラス。
 * ユーザーのお気に入り情報を管理します。
 */
@Entity
@Table(name = "favorites") // テーブル名を "favorites" に設定
@Data // Lombokを利用してgetter/setter、toStringなどを自動生成
public class Favorite {

    // id (主キー。Integer型。テーブル側はidで作成)
    @Id // 主キーであることを指定
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主キーの値をデータベースのAUTO_INCREMENT機能で自動生成
    @Column(name = "id") // テーブルの列名を "id" にマッピング
    private Integer id;

    // shop (Shop型。テーブル側はshop_idで作成。ManyToOneを利用)
    @ManyToOne // "多対一" のリレーションを定義
    @JoinColumn(name = "shop_id") // テーブルの外部キー列を "house_id" にマッピング
    private Shop shop;

    // user (User型。テーブル側はuser_idで作成。ManyToOneを利用)
    @ManyToOne // "多対一" のリレーションを定義
    @JoinColumn(name = "user_id") // テーブルの外部キー列を "user_id" にマッピング
    private User user;

    // createdAt (Timestamp型。テーブル側はcreated_atで作成。insertable = false, updatable = falseを利用)
    @Column(name = "created_at", insertable = false, updatable = false) // データベースで自動生成される列のため更新・挿入を無効化
    private Timestamp createdAt;

    // updatedAt (Timestamp型。テーブル側はupdated_atで作成。insertable = false, updatable = falseを利用)
    @Column(name = "updated_at", insertable = false, updatable = false) // データベースで自動生成される列のため更新・挿入を無効化
    private Timestamp updatedAt;
}
