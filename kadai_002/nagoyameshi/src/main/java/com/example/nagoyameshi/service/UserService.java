package com.example.nagoyameshi.service;

import org.apache.commons.lang3.math.NumberUtils;
//必要なインポート
import org.springframework.security.crypto.password.PasswordEncoder; // パスワードを安全にハッシュ化するためのクラス
import org.springframework.stereotype.Service; // サービスクラスとしてスプリングに認識させるためのアノテーション
import org.springframework.transaction.annotation.Transactional; // トランザクション管理のためのアノテーション

import com.example.nagoyameshi.entity.Role; // ユーザーの役割（ロール）を表すエンティティクラス
import com.example.nagoyameshi.entity.User; // ユーザー情報を表すエンティティクラス
import com.example.nagoyameshi.form.SignupForm; // 新規登録フォームのデータを扱うクラス
import com.example.nagoyameshi.form.UserEditForm; // ユーザー編集フォームのデータを扱うクラス
import com.example.nagoyameshi.repository.RoleRepository; // ロール情報を操作するリポジトリ
import com.example.nagoyameshi.repository.UserRepository; // ユーザー情報を操作するリポジトリ
import com.stripe.model.checkout.Session;

/**
* ユーザーに関するビジネスロジックを担当するサービスクラス。
* ユーザー登録、更新、認証関連の機能を提供します。
*/
@Service
public class UserService {
	// ユーザー情報を操作するリポジトリ
	private final UserRepository userRepository;
	// ロール情報を操作するリポジトリ
	private final RoleRepository roleRepository;
	// パスワードをハッシュ化するためのエンコーダ
	private final PasswordEncoder passwordEncoder;

	/**
	 * コンストラクタでリポジトリとエンコーダを注入。
	 * @param userRepository ユーザーリポジトリ
	 * @param roleRepository ロールリポジトリ
	 * @param passwordEncoder パスワードエンコーダ
	 */
	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * 新規ユーザーを作成し、データベースに保存する。
	 *
	 * @param signupForm 新規登録フォームのデータ
	 * @return 作成されたユーザーオブジェクト
	 */
	@Transactional
	public User create(SignupForm signupForm) {
		// 新しいユーザーオブジェクトを作成
		User user = new User();
		// 一般ユーザー（ROLE_GENERAL）ロールを取得
		Role role = roleRepository.findByName("ROLE_GENERAL");

		// フォームデータをエンティティにセット
		user.setName(signupForm.getName());
		user.setFurigana(signupForm.getFurigana());
		user.setPostalCode(signupForm.getPostalCode());
		user.setAddress(signupForm.getAddress());
		user.setPhoneNumber(signupForm.getPhoneNumber());
		user.setEmail(signupForm.getEmail());
		user.setPassword(passwordEncoder.encode(signupForm.getPassword())); // パスワードをハッシュ化
		user.setRole(role); // 一般ユーザーのロールを設定
		user.setEnabled(false); // 初期状態では無効化

		// データベースに保存
		return userRepository.save(user);
	}

	/**
	 * 既存ユーザーの情報を更新する。
	 *
	 * @param userEditForm 編集フォームのデータ
	 */
	@Transactional
	public void update(UserEditForm userEditForm) {
		// ユーザーIDで既存のユーザーを取得
		User user = userRepository.getReferenceById(userEditForm.getId());

		// フォームデータをエンティティにセット
		user.setName(userEditForm.getName());
		user.setFurigana(userEditForm.getFurigana());
		user.setPostalCode(userEditForm.getPostalCode());
		user.setAddress(userEditForm.getAddress());
		user.setPhoneNumber(userEditForm.getPhoneNumber());
		user.setEmail(userEditForm.getEmail());

		// データベースを更新
		userRepository.save(user);
	}

	/**
	 * メールアドレスが既に登録済みかどうかを確認する。
	 *
	 * @param email チェック対象のメールアドレス
	 * @return 登録済みの場合はtrue、未登録の場合はfalse
	 */
	public boolean isEmailRegistered(String email) {
		User user = userRepository.findByEmail(email);
		return user != null;
	}

	/**
	 * パスワードとパスワード（確認用）が一致するかを確認する。
	 *
	 * @param password 入力されたパスワード
	 * @param passwordConfirmation 確認用パスワード
	 * @return 一致する場合はtrue、一致しない場合はfalse
	 */
	public boolean isSamePassword(String password, String passwordConfirmation) {
		return password.equals(passwordConfirmation);
	}

	/**
	 * ユーザーを有効化する（メール認証完了時などに使用）。
	 *
	 * @param user 有効化するユーザーオブジェクト
	 */
	@Transactional
	public void enableUser(User user) {
		user.setEnabled(true); // ユーザーを有効に設定
		userRepository.save(user); // データベースを更新
	}

	/**
	 * メールアドレスが変更されたかを確認する。
	 *
	 * @param userEditForm 編集フォームのデータ
	 * @return 変更されていればtrue、されていなければfalse
	 */
	public boolean isEmailChanged(UserEditForm userEditForm) {
		// 現在のユーザー情報を取得
		User currentUser = userRepository.getReferenceById(userEditForm.getId());
		// 現在のメールアドレスと新しいメールアドレスを比較
		return !userEditForm.getEmail().equals(currentUser.getEmail());
	}

	/**
	 * 有料会員登録にアップグレードする
	 * @param userId
	 */
	public void upgradeSubscribeAccount(Session session) {
		var userId = session.getMetadata().get("userId");
		// ユーザーIDで既存のユーザーを取得
		User user = userRepository.getReferenceById(NumberUtils.toInt(userId));
		// 会員ユーザー（ROLE_MEMBER）ロールを取得
		Role role = roleRepository.findByName("ROLE_MEMBER");
		user.setRole(role);
		// サブスクリプションIDを設定
		user.setSubscriptionId(session.getSubscription());
		// データベースに保存
		userRepository.save(user);
	}
}
