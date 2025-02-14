package com.example.nagoyameshi.form;

import jakarta.validation.constraints.Min; // 値の最小制限をチェックするアノテーション
import jakarta.validation.constraints.NotBlank; // 空文字やnullを許容しないアノテーション
import jakarta.validation.constraints.NotNull; // nullを許容しないアノテーション

import lombok.Data; // Lombokによるゲッター、セッター、toString、equals、hashCodeの自動生成

/**
 * ReservationInputFormクラス
 * 
 * このクラスは、予約フォームの入力データを保持および検証するために使用されます。
 */
@Data // Lombokの@Dataアノテーションを使用して、ゲッター、セッター、toString、equals、hashCodeを自動生成
public class ReservationInputForm {

	/**
	 * 来店日
	 * フォーマット: "yyyy-MM-dd hh:mm"
	 * - フォームでは1つの入力項目で指定される。
	 * - 空白やnullは許容されない。
	 */
	@NotBlank(message = "来店日時を選択してください。")
	private String commingDate;

	/**
	 * 人数
	 * - 必須項目で、1以上である必要があります。
	 */
	@NotNull(message = "来店人数を入力してください。") // nullを許容しないバリデーション
	@Min(value = 1, message = "来店人数は1人以上に設定してください。") // 最小値を1に設定
	private Integer numberOfPeople;

}
