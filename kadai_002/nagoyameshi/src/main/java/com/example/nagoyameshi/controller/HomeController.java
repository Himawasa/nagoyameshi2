package com.example.nagoyameshi.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List; // リスト型のインポート

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller; // Spring MVCのコントローラであることを示すアノテーション
import org.springframework.ui.Model; // ビューにデータを渡すためのオブジェクト
import org.springframework.web.bind.annotation.GetMapping; // GETリクエストを処理するためのアノテーション

import com.example.nagoyameshi.entity.Shop; // 名古屋飯店舗エンティティ
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.ShopRepository; // 店舗データベース操作用のリポジトリ
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

/**
 * ホーム画面を管理するコントローラ
 * ユーザーが最初にアクセスするホームページを表示するための処理を担当。
 */
@Controller
@RequiredArgsConstructor
public class HomeController {
	private final ShopRepository shopRepository; // 店舗データ操作を行うリポジトリ
	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;

	/**
	 * ホーム画面を表示する
	 * @param model
	 * @param httpRequest
	 * @param userDetailsImpl
	 * @return
	 */
	@GetMapping("/")
	public String index(Model model, HttpServletRequest httpRequest,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {

		// リクエストのクエリ文字列を取得
		String queryString = httpRequest.getQueryString();

		// クエリ文字列に "subscribe" が含まれているかをチェック
		if (queryString != null && queryString.contains("subscribe")) {
			upgradeRole(userDetailsImpl);
		}

		// クエリ文字列に "unSubscribe" が含まれているかをチェック
		if (queryString != null && queryString.contains("unSubscribe")) {
			downgradeRole(userDetailsImpl);
		}

		// 最新の10件の店舗データを取得する
		List<Shop> newShops = shopRepository.findTop10ByOrderByCreatedAtDesc();
		// ビューに "newShops" という名前でデータを渡す
		model.addAttribute("newShops", newShops);
		// categoryListを設定
		model.addAttribute("categories", categoryRepository.findAll());

		// ホーム画面のテンプレート "index" を返す
		return "index";
	}

	/**
	 * 有料会員登録後のHome画面でRoleをUpgradeする
	 * @param userDetailsImpl
	 */
	private void upgradeRole(UserDetailsImpl userDetailsImpl) {
		var user = userRepository.findById(userDetailsImpl.getUser().getId());

		boolean isUpgrade = user.isPresent() && user.get().getRole().getId() == 2;

		// 資格更新権限なし
		if (!isUpgrade) {
			return;
		}

		// 新しいロールだけを持つGrantedAuthorityのリストを作成
		Collection<GrantedAuthority> newAuthorities = new ArrayList<>();
		newAuthorities.add(new SimpleGrantedAuthority("ROLE_MEMBER")); // 新しいロールを追加
		var newUser = new UserDetailsImpl(user.get(), newAuthorities);
		// 新しいAuthenticationオブジェクトを作成
		Authentication newAuth = new UsernamePasswordAuthenticationToken(
				newUser,
				null,
				newAuthorities // 新しいロールのみを含む
		);
		// SecurityContextに新しいAuthenticationを設定
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}

	/**
	 * 定期購読を解除した後Role権限を剥奪する
	 * @param userDetailsImpl
	 */
	private void downgradeRole(UserDetailsImpl userDetailsImpl) {
		var user = userRepository.findById(userDetailsImpl.getUser().getId());

		boolean isUpgrade = user.isPresent() && user.get().getRole().getId() == 1;

		// 資格更新権限なし
		if (!isUpgrade) {
			return;
		}

		// 新しいロールだけを持つGrantedAuthorityのリストを作成
		Collection<GrantedAuthority> newAuthorities = new ArrayList<>();
		newAuthorities.add(new SimpleGrantedAuthority("ROLE_GENERAL")); // 新しいロールを追加
		var newUser = new UserDetailsImpl(user.get(), newAuthorities);
		// 新しいAuthenticationオブジェクトを作成
		Authentication newAuth = new UsernamePasswordAuthenticationToken(
				newUser,
				null,
				newAuthorities // 新しいロールのみを含む
		);
		// SecurityContextに新しいAuthenticationを設定
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}
}
