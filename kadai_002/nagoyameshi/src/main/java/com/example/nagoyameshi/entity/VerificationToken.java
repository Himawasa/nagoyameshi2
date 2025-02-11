package com.example.nagoyameshi.entity;


import java.sql.Timestamp; // タイムスタンプを表すクラス

import jakarta.persistence.Column; // カラムを指定するアノテーション
import jakarta.persistence.Entity; // エンティティを表すアノテーション
import jakarta.persistence.GeneratedValue; // 主キーの自動生成を指定するアノテーション
import jakarta.persistence.GenerationType; // 主キー生成戦略を定義する列挙型
import jakarta.persistence.Id; // 主キーを表すアノテーション
import jakarta.persistence.JoinColumn; // 外部キーのマッピングを指定するアノテーション
import jakarta.persistence.OneToOne; // 1対1のリレーションを指定するアノテーション
import jakarta.persistence.Table; // テーブル名を指定するアノテーション

import lombok.Data; // Lombokのアノテーションで、標準的なメソッドを自動生成

/**
 * VerificationTokenエンティティクラス
 * 
 * このクラスは、ユーザーのメール認証用トークンを管理するためのデータモデルです。
 * データベースの "verification_tokens" テーブルとマッピングされています。
 */
@Entity // このクラスがデータベースのエンティティであることを指定
@Table(name = "verification_tokens") // このエンティティが "verification_tokens" テーブルに対応することを指定
@Data // Lombokを使用して、ゲッター、セッター、toString、equals、hashCodeを自動生成
public class VerificationToken {    
    /**
     * 主キー（ID）
     * 自動生成されるユニークな識別子。
     * データベースの "id" 列と対応。
     */
    @Id // 主キーを指定
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // 主キーの値をデータベースのAUTO_INCREMENT機能で自動生成
    @Column(name = "id") // データベースの列名を "id" にマッピング
    private Integer id;

    /**
     * ユーザー情報（Userエンティティとの1対1のリレーション）
     * データベースの "user_id" 列と対応。
     * トークンに関連付けられたユーザー情報を保持します。
     */
    @OneToOne // 1対1の関係を指定
    @JoinColumn(name = "user_id") // 外部キーを "user_id" にマッピング
    private User user;    

    /**
     * 認証トークン
     * データベースの "token" 列に対応。
     * ユーザー認証のために利用される一意の文字列。
     */
    @Column(name = "token") // データベースの列名を "token" にマッピング
    private String token;

    /**
     * 作成日時
     * データベースの "created_at" 列に対応。
     * トークンが生成された日時を表します。
     * デフォルトで現在の日時が設定され、挿入後は更新不可。
     */
    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    /**
     * 更新日時
     * データベースの "updated_at" 列に対応。
     * トークン情報が最後に更新された日時を表します。
     * デフォルトで現在の日時が設定され、更新時に自動的に値が更新されます。
     */
    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;        
}
