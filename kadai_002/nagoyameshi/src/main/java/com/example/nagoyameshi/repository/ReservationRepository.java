package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.User;

/**
 * ReservationRepository インターフェース
 * 
 * このインターフェースは、Spring Data JPA を利用して `Reservation` エンティティに関する
 * データベース操作を行うためのリポジトリです。
 */
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    /**
     * 指定されたユーザーに関連する予約データを取得し、作成日時の降順で並び替えた結果をページングで返す。
     * 
     * @param user     予約を検索する対象のユーザー
     * @param pageable ページング情報（ページ番号、ページサイズ、並び順など）
     * @return ユーザーに関連する予約データのページング結果
     */
    public Page<Reservation> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
