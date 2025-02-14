package com.example.nagoyameshi.form;

import lombok.AllArgsConstructor; // すべてのフィールドを引数に持つコンストラクタを自動生成
import lombok.Data; // Lombokによるゲッター、セッター、toString、equals、hashCodeの自動生成

/**
 * ReservationRegisterFormクラス
 * 
 * このクラスは、予約情報を管理するためのデータモデルとして使用されます。
 * 特に、予約の登録処理においてフォームデータを一時的に保持します。
 */
@Data // Lombokの@Dataアノテーションを使用して、ゲッター、セッター、toString、equals、hashCodeを自動生成
@AllArgsConstructor // 全てのフィールドを引数に持つコンストラクタを自動生成
public class ReservationRegisterForm {    

    /**
     * 店舗ID
     * - 予約する店舗のIDを保持します。
     */
    private Integer shopId;
        
    /**
     * ユーザーID
     * - 予約を行ったユーザーのIDを保持します。
     */
    private Integer userId;    
        
    /**
     * 来店日時
     * - 来店日時保持します。
     * - 文字列形式（例: "yyyy-mm-dd hh:mm"）。
     */
    private String reservationDate;    
        
    /**
     * 来店人数
     * - 予約に含まれる来店人数を保持します。
     */
    private Integer numberOfPeople;
    
    /**
     * 合計料金
     * - 来店料金の合計額を保持します。
     */
    private Integer amount;    
}
