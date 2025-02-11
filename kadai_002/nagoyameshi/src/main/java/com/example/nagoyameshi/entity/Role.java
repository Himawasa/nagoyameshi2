package com.example.nagoyameshi.entity;

import jakarta.persistence.Column; // カラムのマッピングを指定するアノテーション
import jakarta.persistence.Entity; // このクラスがエンティティであることを示すアノテーション
import jakarta.persistence.GeneratedValue; // 主キーの値を自動生成するためのアノテーション
import jakarta.persistence.GenerationType; // 主キー生成戦略を指定する列挙型
import jakarta.persistence.Id; // 主キーを指定するアノテーション
import jakarta.persistence.Table; // 対応するテーブル名を指定するアノテーション

import lombok.Data; // Lombokの@Dataアノテーションで、ゲッター、セッターなどを自動生成

/**
 * Roleエンティティクラス
 * "roles" テーブルと対応するクラス。
 * ユーザーの権限（ロール）情報を管理するためのデータモデル。
 */
@Entity // このクラスがデータベースのエンティティであることを指定
@Table(name = "roles") // このエンティティが "roles" テーブルと対応していることを指定
@Data // Lombokを使用して、ゲッター、セッター、toString、equals、hashCodeを自動生成
public class Role {

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
     * ロール名
     * データベースの "name" 列と対応。
     * 例: "ROLE_ADMIN", "ROLE_USER" など
     */
    @Column(name = "name") // データベースの列名を "name" にマッピング
    private String name;       
}
