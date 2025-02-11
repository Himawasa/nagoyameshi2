package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank; // 空白チェックを行うバリデーション
import jakarta.validation.constraints.NotNull;  // Nullチェックを行うバリデーション

import lombok.AllArgsConstructor; // 全てのフィールドを引数に持つコンストラクタを自動生成
import lombok.Data; // ゲッター、セッター、toString、equals、hashCodeを自動生成

/**
 * UserEditForm クラス
 * 
 * このクラスは、ユーザー編集フォームで使用されるデータモデルです。
 * ユーザーが入力したデータを保持し、バリデーションを行う役割を持ちます。
 */
@Data
@AllArgsConstructor // 全てのフィールドを引数に持つコンストラクタを自動生成
public class UserEditForm {

    /**
     * ユーザーID
     * - 編集対象のユーザーを識別するためのフィールド。
     * - Nullが許されないため、必須項目です。
     */
    @NotNull
    private Integer id;
    
    /**
     * 氏名
     * - ユーザーの名前を入力するフィールド。
     * - 空白や未入力の場合はエラーとなります。
     */
    @NotBlank(message = "氏名を入力してください。")
    private String name;
    
    /**
     * フリガナ
     * - ユーザーの名前（カタカナ）を入力するフィールド。
     * - 空白や未入力の場合はエラーとなります。
     */
    @NotBlank(message = "フリガナを入力してください。")
    private String furigana;
    
    /**
     * 郵便番号
     * - ユーザーの郵便番号を入力するフィールド。
     * - 空白や未入力の場合はエラーとなります。
     */
    @NotBlank(message = "郵便番号を入力してください。")
    private String postalCode;
    
    /**
     * 住所
     * - ユーザーの住所を入力するフィールド。
     * - 空白や未入力の場合はエラーとなります。
     */
    @NotBlank(message = "住所を入力してください。")
    private String address;
    
    /**
     * 電話番号
     * - ユーザーの電話番号を入力するフィールド。
     * - 空白や未入力の場合はエラーとなります。
     */
    @NotBlank(message = "電話番号を入力してください。")
    private String phoneNumber;
    
    /**
     * メールアドレス
     * - ユーザーのメールアドレスを入力するフィールド。
     * - 空白や未入力の場合はエラーとなります。
     */
    @NotBlank(message = "メールアドレスを入力してください。")
    private String email;
}
