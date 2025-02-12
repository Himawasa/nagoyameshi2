package com.example.nagoyameshi.form;

// Jakarta Validationのアノテーションをインポート
import jakarta.validation.constraints.NotBlank; // 空文字列やnullを拒否するアノテーション
import jakarta.validation.constraints.NotNull; // nullを拒否するアノテーション

// Hibernateのバリデーションアノテーションをインポート
import org.hibernate.validator.constraints.Length; // 文字列の長さ制限を設定するアノテーション

// Lombokのアノテーションをインポート
import lombok.AllArgsConstructor; // 全フィールドを含むコンストラクタを自動生成
import lombok.Data; // ゲッター・セッター、toString、equals、hashCodeを自動生成

/**
 * レビュー編集のためのフォームクラス。
 * 入力値のバリデーションを含む。
 */
@Data
@AllArgsConstructor
public class ReviewEditForm {

    /**
     * レビューID (必須項目)
     * nullを許可しない。
     */
    @NotNull
    private Integer id;

    /**
     * コメント内容 (必須項目)
     * 空文字列を許可しない。300文字以内である必要がある。
     */
    @NotBlank(message = "コメントを入力してください。")
    @Length(max = 300, message = "コメントは300文字以内で入力してください。")
    private String comment;
}
