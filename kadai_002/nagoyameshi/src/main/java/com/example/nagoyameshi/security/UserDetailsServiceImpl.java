package com.example.nagoyameshi.security;

import java.util.ArrayList; // 権限情報を保持するためのリストクラス
import java.util.Collection; // コレクションデータ型を使用するため

import org.springframework.security.core.GrantedAuthority; // 権限を表すインターフェース
import org.springframework.security.core.authority.SimpleGrantedAuthority; // 権限を具体化するクラス
import org.springframework.security.core.userdetails.UserDetails; // 認証対象ユーザーの詳細情報を保持するインターフェース
import org.springframework.security.core.userdetails.UserDetailsService; // 認証用ユーザー情報をロードするためのインターフェース
import org.springframework.security.core.userdetails.UsernameNotFoundException; // ユーザーが見つからない場合にスローされる例外
import org.springframework.stereotype.Service; // Spring Serviceクラスを示すアノテーション

import com.example.nagoyameshi.entity.User; // ユーザーエンティティ
import com.example.nagoyameshi.repository.UserRepository; // ユーザー情報を操作するリポジトリ

/**
 * Spring Securityで認証用のユーザー情報を提供するサービスクラス。
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository; // ユーザー情報を取得するためのリポジトリ

    /**
     * コンストラクタ
     *
     * @param userRepository ユーザー情報を取得するリポジトリ
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * ユーザー名（メールアドレス）を使用して認証情報を取得します。
     *
     * @param email ログイン時に使用するメールアドレス
     * @return UserDetails 認証用のユーザー情報
     * @throws UsernameNotFoundException ユーザーが見つからない場合
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // メールアドレスでユーザーを検索
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
        }

        // ユーザーの権限情報を設定
        String roleName = user.getRole().getName();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roleName));

        // UserDetailsImplを作成して返す
        return new UserDetailsImpl(user, authorities);
    }
}
