package com.example.nagoyameshi.form;

// Jakarta Validationのアノテーションをインポート
import jakarta.validation.constraints.NotBlank; // 空文字列やnullを拒否するアノテーション

// Hibernateのバリデーションアノテーションをインポート
import org.hibernate.validator.constraints.Length; // 文字列の長さを制限するアノテーション

// Lombokのアノテーションをインポート
import lombok.Data; // ゲッター・セッター、toString、equals、hashCodeを自動生成

/**
 * レビュー登録のためのフォームクラス。
 * 入力値のバリデーションを含む。
 */
@Data
public class ReviewRegisterForm {

    /**
     * コメント内容 (必須項目)
     * 空文字列を許可しない。300文字以内である必要がある。
     */
    @NotBlank(message = "コメントを入力してください。") // 空白やnullの入力を禁止し、エラーメッセージを設定
    @Length(max = 300, message = "コメントは300文字以内で入力してください。") // コメントの最大文字数を制限
    private String comment;
}
