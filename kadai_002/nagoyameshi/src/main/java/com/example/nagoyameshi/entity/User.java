package com.example.nagoyameshi.entity;

import java.sql.Timestamp; // データベースのタイムスタンプを扱うクラス

import jakarta.persistence.Column; // カラムのマッピングを指定するアノテーション
import jakarta.persistence.Entity; // このクラスがエンティティであることを示すアノテーション
import jakarta.persistence.GeneratedValue; // 主キーの値を自動生成するアノテーション
import jakarta.persistence.GenerationType; // 主キー生成戦略を指定する列挙型
import jakarta.persistence.Id; // 主キーを指定するアノテーション
import jakarta.persistence.JoinColumn; // 外部キーを指定するアノテーション
import jakarta.persistence.ManyToOne; // 多対一のリレーションを指定するアノテーション
import jakarta.persistence.Table; // 対応するテーブル名を指定するアノテーション

import lombok.Data; // Lombokの@Dataアノテーションで、ゲッター、セッターなどを自動生成

/**
 * Userエンティティクラス
 * データベースの "users" テーブルに対応するクラス。
 * アプリケーション内でユーザー情報を管理するために使用されます。
 */
@Entity // このクラスがデータベースのエンティティであることを指定
@Table(name = "users") // このエンティティが "users" テーブルに対応していることを指定
@Data // Lombokを使用して、ゲッター、セッター、toString、equals、hashCodeを自動生成
public class User {
    /**
     * 主キー（ID）
     * データベースの "id" 列に対応。
     * 自動生成されるユニークな識別子。
     */
    @Id // 主キーを指定
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 主キーの値をデータベースのAUTO_INCREMENT機能で自動生成
    @Column(name = "id") // データベースの列名を "id" にマッピング
    private Integer id;

    /**
     * ユーザー名
     * データベースの "name" 列に対応。
     */
    @Column(name = "name") // データベースの列名を "name" にマッピング
    private String name;

    /**
     * ユーザー名のふりがな
     * データベースの "furigana" 列に対応。
     */
    @Column(name = "furigana") // データベースの列名を "furigana" にマッピング
    private String furigana;

    /**
     * 郵便番号
     * データベースの "postal_code" 列に対応。
     */
    @Column(name = "postal_code") // データベースの列名を "postal_code" にマッピング
    private String postalCode;

    /**
     * 住所
     * データベースの "address" 列に対応。
     */
    @Column(name = "address") // データベースの列名を "address" にマッピング
    private String address;

    /**
     * 電話番号
     * データベースの "phone_number" 列に対応。
     */
    @Column(name = "phone_number") // データベースの列名を "phone_number" にマッピング
    private String phoneNumber;

    /**
     * メールアドレス
     * データベースの "email" 列に対応。
     */
    @Column(name = "email") // データベースの列名を "email" にマッピング
    private String email;

    /**
     * パスワード
     * データベースの "password" 列に対応。
     * 暗号化されたパスワードが保存されます。
     */
    @Column(name = "password") // データベースの列名を "password" にマッピング
    private String password;

    /**
     * 役割（ロール）
     * データベースの "role_id" 列に対応。
     * Roleエンティティとの多対一のリレーション。
     */
    @ManyToOne // 多対一の関係を指定
    @JoinColumn(name = "role_id") // 外部キー列を "role_id" にマッピング
    private Role role;

    /**
     * 有効状態
     * データベースの "enabled" 列に対応。
     * true: アカウント有効、false: アカウント無効。
     */
    @Column(name = "enabled") // データベースの列名を "enabled" にマッピング
    private Boolean enabled;

    /**
     * 作成日時
     * データベースの "created_at" 列に対応。
     * レコードが作成された日時。
     * デフォルトで現在の日時が設定され、挿入後は更新不可。
     */
    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    /**
     * 更新日時
     * データベースの "updated_at" 列に対応。
     * レコードが最後に更新された日時。
     * デフォルトで現在の日時が設定され、更新時に自動的に値が更新されます。
     */
    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;
}
