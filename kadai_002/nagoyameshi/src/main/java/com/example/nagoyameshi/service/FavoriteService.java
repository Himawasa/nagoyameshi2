package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.FavoriteRepository;

/**
 * お気に入り関連のビジネスロジックを管理するサービスクラス。
 */
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    /**
     * コンストラクタでFavoriteRepositoryを注入。
     * 
     * @param favoriteRepository Favoriteリポジトリのインスタンス
     */
    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    /**
     * お気に入りを作成するメソッド。
     * 
     * @param Shop 対象の店舗
     * @param user 対象のユーザー
     */
    @Transactional
    public void create(Shop Shop, User user) {
        // Favoriteクラスのインスタンスを生成
        Favorite favorite = new Favorite();

        // 店舗とユーザーを設定
        favorite.setShop(Shop);
        favorite.setUser(user);

        // お気に入りを保存
        favoriteRepository.save(favorite);
    }

    /**
     * 指定された店舗とユーザーが既にお気に入りに登録されているかを判定する。
     * 
     * @param Shop 対象の店舗
     * @param user 対象のユーザー
     * @return お気に入り登録されている場合はtrue、そうでない場合はfalse
     */
    public boolean isFavorite(Shop Shop, User user) {
        // お気に入りが存在するかを判定
        if (favoriteRepository.findByShopAndUser(Shop, user) != null) {
            return true; // お気に入りが存在する場合はtrueを返却
        }
        return false; // お気に入りが存在しない場合はfalseを返却
    }
}
