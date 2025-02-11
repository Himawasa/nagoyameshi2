package com.example.nagoyameshi.security;

import java.util.Collection; // ユーザーの権限情報を保持するコレクション

import org.springframework.security.core.GrantedAuthority; // 権限（ロール）を表すインターフェース
import org.springframework.security.core.userdetails.UserDetails; // Spring Security のユーザー詳細情報を管理するインターフェース

import com.example.nagoyameshi.entity.User; // アプリケーションのユーザー情報を表すエンティティ

/**
 * Spring Securityが利用するユーザー詳細情報を管理するクラス。
 * UserDetailsインターフェースを実装しており、認証プロセスで必要な情報を提供します。
 */
public class UserDetailsImpl implements UserDetails {
    private final User user; // ユーザーエンティティ
    private final Collection<GrantedAuthority> authorities; // ユーザーの権限情報

    /**
     * コンストラクタ
     *
     * @param user ユーザーエンティティ
     * @param authorities ユーザーの権限情報
     */
    public UserDetailsImpl(User user, Collection<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    /**
     * ユーザーエンティティを取得します。
     *
     * @return ユーザーエンティティ
     */
    public User getUser() {
        return user;
    }

    /**
     * ユーザーのハッシュ化されたパスワードを取得します。
     *
     * @return ハッシュ化されたパスワード
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * ユーザー名（メールアドレス）を取得します。
     *
     * @return メールアドレス
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * ユーザーの権限情報を取得します。
     *
     * @return 権限のコレクション
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * アカウントが有効期限切れでないかを確認します。
     *
     * @return 常に `true`（今回は有効期限を使用しない）
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * アカウントがロックされていないかを確認します。
     *
     * @return 常に `true`（今回はロック機能を使用しない）
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 認証情報が有効期限切れでないかを確認します。
     *
     * @return 常に `true`（今回は認証情報の期限切れを使用しない）
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * アカウントが有効かを確認します。
     *
     * @return ユーザーの有効状態
     */
    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
