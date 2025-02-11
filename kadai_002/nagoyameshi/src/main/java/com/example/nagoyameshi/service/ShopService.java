package com.example.nagoyameshi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.form.ShopEditForm;
import com.example.nagoyameshi.form.ShopRegisterForm;
import com.example.nagoyameshi.repository.ShopRepository;

/**
 * 店舗に関する処理を行うサービスクラス。
 * このクラスは、データベース操作やビジネスロジックを担当します。
 */
@Service
public class ShopService {
    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    /**
     * ページネーションと検索に対応した店舗データを取得するメソッド。
     *
     * @param pageRequest ページング情報
     * @param keyword     検索キーワード
     * @return ページングされた店舗データ
     */
    public Page<Shop> findShops(PageRequest pageRequest, String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return shopRepository.findAll(pageRequest);
        } else {
            return shopRepository.findByNameContainingIgnoreCase(keyword, pageRequest);
        }
    }

    /**
     * 指定したIDの店舗を取得するメソッド。
     *
     * @param id 店舗のID
     * @return Optionalでラップされた店舗データ
     */
    public Optional<Shop> findById(Integer id) {
        return shopRepository.findById(id);
    }

    /**
     * 新しい店舗を登録するメソッド。
     * 
     * @param shopRegisterForm フォームから送信された店舗登録データ
     */
    @Transactional
    public void create(ShopRegisterForm shopRegisterForm) {
        Shop shop = new Shop();
        MultipartFile imageFile = shopRegisterForm.getImage();

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename();
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile, filePath);
            shop.setImage(hashedImageName);
        }

        shop.setName(shopRegisterForm.getName());
        shop.setCategoryId(shopRegisterForm.getCategoryId());
        shop.setDescription(shopRegisterForm.getDescription());
        shop.setPrice(shopRegisterForm.getPrice());
        shop.setPostalCode(shopRegisterForm.getPostalCode());
        shop.setAddress(shopRegisterForm.getAddress());
        shop.setPhoneNumber(shopRegisterForm.getPhoneNumber());
        shop.setBusinessHours(shopRegisterForm.getBusinessHours());
        shop.setRegularHoliday(shopRegisterForm.getRegularHoliday());

        shopRepository.save(shop);
    }

    /**
     * 既存の店舗データを更新するメソッド。
     * 
     * @param shopEditForm フォームから送信された編集用データ
     */
    @Transactional
    public void update(ShopEditForm shopEditForm) {
        Shop shop = shopRepository.findById(shopEditForm.getId())
                .orElseThrow(() -> new IllegalArgumentException("店舗が見つかりません。ID: " + shopEditForm.getId()));

        shop.setName(shopEditForm.getName());
        shop.setCategoryId(shopEditForm.getCategoryId());
        shop.setDescription(shopEditForm.getDescription());
        shop.setPrice(shopEditForm.getPrice());
        shop.setPostalCode(shopEditForm.getPostalCode());
        shop.setAddress(shopEditForm.getAddress());
        shop.setPhoneNumber(shopEditForm.getPhoneNumber());
        shop.setBusinessHours(shopEditForm.getBusinessHours());
        shop.setRegularHoliday(shopEditForm.getRegularHoliday());

        // 画像のアップロード処理
        MultipartFile imageFile = shopEditForm.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename();
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile, filePath);
            shop.setImage(hashedImageName);
        }

        shopRepository.save(shop);
    }

    /**
     * UUIDを使って生成したファイル名を返す
     * @param fileName 元のファイル名
     * @return 変換後の一意なファイル名
     */
    public String generateNewFileName(String fileName) {
        String[] fileNames = fileName.split("\\.");
        for (int i = 0; i < fileNames.length - 1; i++) {
            fileNames[i] = UUID.randomUUID().toString();
        }
        return String.join(".", fileNames);
    }

    /**
     * 画像ファイルを指定したファイルパスにコピーするメソッド
     * @param imageFile アップロードされた画像
     * @param filePath 保存先のパス
     */
    public void copyImageFile(MultipartFile imageFile, Path filePath) {
        try {
            Files.copy(imageFile.getInputStream(), filePath);
        } catch (IOException e) {
            throw new RuntimeException("画像の保存に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 店舗データをIDで削除するメソッド。
     * 
     * @param id 削除対象の店舗のID
     */
    @Transactional
    public void deleteById(Integer id) {
        if (!shopRepository.existsById(id)) {
            throw new IllegalArgumentException("削除しようとした店舗が見つかりません。ID: " + id);
        }
        shopRepository.deleteById(id);
    }
}
