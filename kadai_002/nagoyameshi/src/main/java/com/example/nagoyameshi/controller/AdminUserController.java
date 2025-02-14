package com.example.nagoyameshi.controller;

// 必要なクラスをインポート
import org.springframework.data.domain.Page; // ページネーションのためのクラス
import org.springframework.data.domain.Pageable; // ページ情報を扱うクラス
import org.springframework.data.domain.Sort.Direction; // ソート順を指定するクラス
import org.springframework.data.web.PageableDefault; // ページネーションのデフォルト設定
import org.springframework.stereotype.Controller; // Spring MVCのコントローラクラスを示すアノテーション
import org.springframework.ui.Model; // ビューにデータを渡すためのクラス
// 各種リクエストマッピング用のアノテーションをインポート
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.UserRepository;

/**
 * AdminUserControllerクラス
 * 管理者がユーザー情報を管理するためのコントローラー。
 * ユーザーの一覧表示と詳細表示の機能を提供。
 */
@Controller
@RequestMapping("/admin/users") // このクラスのすべてのエンドポイントは "/admin/users" で始まる
public class AdminUserController {
	private final UserRepository userRepository; // ユーザー情報を操作するためのリポジトリ

	/**
	 * コンストラクタ
	 * UserRepositoryを依存性注入（DI）で受け取る
	 */
	public AdminUserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * ユーザー一覧を表示する
	 * @param keyword 検索キーワード（オプション）
	 * @param pageable ページネーション情報
	 * @param model ビューにデータを渡すためのオブジェクト
	 * @return ユーザー一覧ページ
	 */
	@GetMapping
	public String index(@RequestParam(name = "keyword", required = false) String keyword,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {
		Page<User> userPage; // ページネーションされたユーザー情報を格納

		// キーワードで検索
		if (keyword != null && !keyword.isEmpty()) {
			userPage = userRepository.findByEmailLike("%" + keyword + "%", pageable);
		} else {
			userPage = userRepository.findAll(pageable); // キーワードがない場合はすべてのユーザーを取得
		}

		model.addAttribute("userPage", userPage); // ページネーションされたユーザー情報をビューに渡す
		model.addAttribute("keyword", keyword); // 検索キーワードをビューに渡す

		return "admin/users/index"; // ユーザー一覧ページのテンプレート名
	}

	/**
	 * ユーザー詳細を表示する
	 * @param id 表示するユーザーのID
	 * @param model ビューにデータを渡すためのオブジェクト
	 * @return ユーザー詳細ページ
	 */
	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model) {
		User user = userRepository.getReferenceById(id); // 指定されたIDのユーザーを取得

		model.addAttribute("user", user); // ユーザー情報をビューに渡す

		return "admin/users/show"; // ユーザー詳細ページのテンプレート名
	}
}
