package com.example.nagoyameshi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

//アプリケーション内のエンティティ
import com.example.nagoyameshi.entity.Role; // Roleエンティティの操作をサポート

/**
* Roleエンティティを操作するためのリポジトリインターフェース。
* Spring Data JPAにより、自動でCRUD機能が提供されます。
*/
public interface RoleRepository extends JpaRepository<Role, Integer> {
 /**
  * ロール名でRoleエンティティを検索します。
  *
  * @param name ロール名
  * @return 検索結果のRole
  */
 public Role findByName(String name);
}
