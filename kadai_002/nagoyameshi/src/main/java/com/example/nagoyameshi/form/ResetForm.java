package com.example.nagoyameshi.form;


import jakarta.validation.constraints.Email; // メールアドレス形式のバリデーション
import jakarta.validation.constraints.NotBlank; // 空白チェック用のバリデーション

import lombok.Data; // Lombokでゲッター・セッター等を自動生成

/**
 * ResetFormクラス
 */
@Data // Lombokの@Dataアノテーションでゲッター、セッター、toString、equals、hashCodeを自動生成
public class ResetForm {    

    /**
     * メールアドレス
     * - ユーザーのメールアドレスを入力するフィールド。
     * - 空白または正しい形式でない場合はエラーメッセージを表示します。
     */
    @NotBlank(message = "メールアドレスを入力してください。")
    @Email(message = "メールアドレスは正しい形式で入力してください。")
    private String email;
    
}
