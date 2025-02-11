package com.example.nagoyameshi.security;

// Spring Securityの必要なクラスをインポート
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * NAGOYAMESHI アプリのSpring Security設定クラス
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    /**
     * HTTPセキュリティの設定を行う
     *
     * @param http HTTPセキュリティ設定のオブジェクト
     * @return SecurityFilterChain セキュリティフィルターチェーン
     * @throws Exception 設定エラー
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                // すべてのユーザーに許可するページ
                .requestMatchers(
                    "/css/**",
                    "/images/**",
                    "/js/**",
                    "/storage/**",
                    "/",
                    "/signup/**",
                    "/shops",         // 店舗一覧
                    "/shops/{id}",    // 店舗詳細
                    "/login",
                    "/admin/**",      // 管理者ページ（現在は誰でもアクセス可能）
                    "/stripe/webhook" ,// ストライプのWebHook
                    "/admin/shops/new",   // 新規登録ページへのアクセスを許可
                    "/admin/shops"        // 一覧ページも許可
                    
                ).permitAll()
                // 有料会員のみがアクセスできる機能
                .requestMatchers(
                    "/shops/{id}/reviews", // 店舗のレビュー投稿
                    "/shops/{id}/reserve"  // 予約機能
                ).hasRole("PREMIUM")
                // その他は全て認証が必要
                .anyRequest().authenticated()
            )
            // ログイン設定
            .formLogin((form) -> form
                .loginPage("/login")               // ログインページ
                .loginProcessingUrl("/login")      // ログイン処理
                .defaultSuccessUrl("/?loggedIn")   // ログイン成功時のリダイレクト先
                .failureUrl("/login?error")        // ログイン失敗時のリダイレクト先
                .permitAll()
            )
            // ログアウト設定
            .logout((logout) -> logout
                .logoutSuccessUrl("/?loggedOut")  // ログアウト成功時
                .permitAll()
            )
            // CSRF保護（ストライプのWebhookは無効化）
            .csrf().ignoringRequestMatchers("/stripe/webhook");

        return http.build();
    }

    /**
     * パスワードのエンコーダーを定義
     *
     * @return PasswordEncoder パスワードエンコーダー
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}