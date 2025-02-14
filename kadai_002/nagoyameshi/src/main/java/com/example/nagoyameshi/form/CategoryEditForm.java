package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CategoryEditFormクラスは、既存カテゴリの情報を編集するためのフォームデータを受け取るクラスです。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEditForm {
    /** 店舗ID（編集対象のカテゴリを識別するために必須） */
    @NotNull
    private Integer id;
    /** 店舗名（必須） */
    @NotBlank
    private String name;
}