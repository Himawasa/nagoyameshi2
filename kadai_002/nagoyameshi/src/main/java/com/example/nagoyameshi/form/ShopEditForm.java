package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ShopEditFormクラスは、既存店舗の情報を編集するためのフォームデータを受け取るクラスです。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopEditForm {

    /** 店舗ID（編集対象の店舗を識別するために必須） */
    @NotNull
    private Integer id;

    /** 店舗名（必須） */
    @NotBlank
    private String name;

    /** カテゴリID（必須） */
    @NotNull
    private Integer categoryId;

    /** 店舗説明（必須） */
    @NotBlank
    private String description;

    /** 平均価格（必須） */
    @NotNull
    private Integer price;

    /** 郵便番号（必須） */
    @NotBlank
    private String postalCode;

    /** 住所（必須） */
    @NotBlank
    private String address;

    /** 電話番号（必須） */
    @NotBlank
    private String phoneNumber;

    /** 営業時間（必須） */
    @NotBlank
    private String businessHours;

    /** 定休日（任意） */
    private String regularHoliday;

    /** 画像ファイル（任意） */
    private MultipartFile image;
}