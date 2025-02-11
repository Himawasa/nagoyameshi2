package com.example.nagoyameshi.controller;

import jakarta.servlet.http.HttpServletRequest; // HTTPリクエストを操作するためのクラス

// 必要なクラスをインポート
import org.springframework.stereotype.Controller; // Spring MVCのコントローラであることを示すアノテーション
import org.springframework.ui.Model; // ビューにデータを渡すためのオブジェクト
import org.springframework.validation.BindingResult; // フォームのバリデーション結果を格納するオブジェクト
import org.springframework.validation.FieldError; // バリデーションエラーを表すオブジェクト
import org.springframework.validation.annotation.Validated; // 入力フォームのバリデーションを有効にするアノテーション
import org.springframework.web.bind.annotation.GetMapping; // GETリクエスト用のマッピング
import org.springframework.web.bind.annotation.ModelAttribute; // モデル属性を受け取るアノテーション
import org.springframework.web.bind.annotation.PostMapping; // POSTリクエスト用のマッピング
import org.springframework.web.bind.annotation.RequestParam; // リクエストパラメータを受け取るアノテーション
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // リダイレクト時にメッセージを渡すためのオブジェクト

import com.example.nagoyameshi.entity.User; // ユーザーエンティティ
import com.example.nagoyameshi.entity.VerificationToken; // 認証トークンエンティティ
import com.example.nagoyameshi.event.SignupEventPublisher; // 会員登録イベントのパブリッシャー
import com.example.nagoyameshi.form.SignupForm; // 会員登録用の入力フォーム
import com.example.nagoyameshi.service.UserService; // ユーザー関連のサービス
import com.example.nagoyameshi.service.VerificationTokenService; // 認証トークン関連のサービス

/**
 * AuthControllerクラス
 * ユーザーの認証および会員登録を管理するコントローラー。
 */
@Controller
public class AuthController {

    private final UserService userService; // ユーザー操作を提供するサービス
    private final SignupEventPublisher signupEventPublisher; // 会員登録イベントのパブリッシャー
    private final VerificationTokenService verificationTokenService; // 認証トークンサービス

    /**
     * コンストラクタ
     * DIを使用して依存オブジェクトを注入する。
     */
    public AuthController(UserService userService, SignupEventPublisher signupEventPublisher, VerificationTokenService verificationTokenService) {
        this.userService = userService;
        this.signupEventPublisher = signupEventPublisher;
        this.verificationTokenService = verificationTokenService;
    }

    /**
     * ログイン画面を表示する
     *
     * @return ログイン画面のテンプレート名
     */
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    /**
     * 会員登録画面を表示する
     *
     * @param model ビューにデータを渡すためのオブジェクト
     * @return 会員登録画面のテンプレート名
     */
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "auth/signup";
    }

    /**
     * 会員登録処理を行う
     *
     * @param signupForm         入力された会員登録フォーム
     * @param bindingResult      フォームのバリデーション結果
     * @param redirectAttributes リダイレクト時にメッセージを渡すオブジェクト
     * @param httpServletRequest HTTPリクエスト情報
     * @return リダイレクト先のURL
     */
    @PostMapping("/signup")
    public String signup(@ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (userService.isEmailRegistered(signupForm.getEmail())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
            bindingResult.addError(fieldError);
        }

        if (!userService.isSamePassword(signupForm.getPassword(), signupForm.getPasswordConfirmation())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません。");
            bindingResult.addError(fieldError);
        }

        if (bindingResult.hasErrors()) {
            return "auth/signup";
        }

        User createdUser = userService.create(signupForm);
        String requestUrl = new String(httpServletRequest.getRequestURL());
        signupEventPublisher.publishSignupEvent(createdUser, requestUrl);
        redirectAttributes.addFlashAttribute("successMessage", "メールに記載されたリンクをクリックし、登録を完了してください。");
        return "redirect:/";
    }

    /**
     * 認証トークンの検証処理を行う
     *
     * @param token 認証トークン
     * @param model ビューにデータを渡すためのオブジェクト
     * @return 認証結果画面のテンプレート名
     */
    @GetMapping("/signup/verify")
    public String verify(@RequestParam(name = "token") String token, Model model) {
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);

        if (verificationToken != null) {
            User user = verificationToken.getUser();
            userService.enableUser(user);
            model.addAttribute("successMessage", "会員登録が完了しました。");
        } else {
            model.addAttribute("errorMessage", "無効なトークンです。");
        }

        return "auth/verify";
    }
}
