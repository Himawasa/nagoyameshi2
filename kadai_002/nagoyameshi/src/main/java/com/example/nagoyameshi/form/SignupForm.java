package com.example.nagoyameshi.form;


import jakarta.validation.constraints.Email; // メールアドレス形式のバリデーション
import jakarta.validation.constraints.NotBlank; // 空白チェック用のバリデーション

import org.hibernate.validator.constraints.Length; // パスワードの文字数制限に使用

import lombok.Data; // Lombokでゲッター・セッター等を自動生成

/**
 * SignupFormクラス
 * 
 * このクラスは、新規会員登録フォームの入力データを保持し、
 * バリデーションを行うために使用されます。
 */
@Data // Lombokの@Dataアノテーションでゲッター、セッター、toString、equals、hashCodeを自動生成
public class SignupForm {    

    /**
     * 氏名
     * - ユーザーの名前を入力するフィールド。
     * - 空白の場合はエラーメッセージを表示します。
     */
    @NotBlank(message = "氏名を入力してください。")
    private String name;
    
    /**
     * フリガナ
     * - ユーザーのフリガナ（カタカナ）を入力するフィールド。
     * - 空白の場合はエラーメッセージを表示します。
     */
    @NotBlank(message = "フリガナを入力してください。")
    private String furigana;
    
    /**
     * 郵便番号
     * - ユーザーの郵便番号を入力するフィールド。
     * - 空白の場合はエラーメッセージを表示します。
     */
    @NotBlank(message = "郵便番号を入力してください。")
    private String postalCode;
    
    /**
     * 住所
     * - ユーザーの住所を入力するフィールド。
     * - 空白の場合はエラーメッセージを表示します。
     */
    @NotBlank(message = "住所を入力してください。")
    private String address;
    
    /**
     * 電話番号
     * - ユーザーの電話番号を入力するフィールド。
     * - 空白の場合はエラーメッセージを表示します。
     */
    @NotBlank(message = "電話番号を入力してください。")
    private String phoneNumber;
    
    /**
     * メールアドレス
     * - ユーザーのメールアドレスを入力するフィールド。
     * - 空白または正しい形式でない場合はエラーメッセージを表示します。
     */
    @NotBlank(message = "メールアドレスを入力してください。")
    @Email(message = "メールアドレスは正しい形式で入力してください。")
    private String email;
    
    /**
     * パスワード
     * - ユーザーが登録時に設定するパスワードを入力するフィールド。
     * - 空白の場合、または8文字未満の場合はエラーメッセージを表示します。
     */
    @NotBlank(message = "パスワードを入力してください。")
    @Length(min = 8, message = "パスワードは8文字以上で入力してください。")
    private String password;    
    
    /**
     * パスワード（確認用）
     * - 入力したパスワードと同じ内容を確認用に入力するフィールド。
     * - 空白の場合はエラーメッセージを表示します。
     */
    @NotBlank(message = "パスワード（確認用）を入力してください。")
    private String passwordConfirmation;    
}
