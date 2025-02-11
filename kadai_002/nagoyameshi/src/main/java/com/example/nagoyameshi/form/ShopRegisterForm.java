package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ShopRegisterForm {
    @NotBlank
    private String name;

    @NotNull
    private Integer categoryId;

    @NotBlank
    private String description;

    @NotNull
    private Integer price;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String businessHours;

    private String regularHoliday;
    
    private MultipartFile image;
}
