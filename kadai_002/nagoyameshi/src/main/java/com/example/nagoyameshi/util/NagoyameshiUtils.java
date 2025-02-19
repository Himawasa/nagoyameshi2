package com.example.nagoyameshi.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.core.context.SecurityContextHolder;

import com.example.nagoyameshi.security.UserDetailsImpl;

/**
 * Nagoyameshiプロジェクトで汎用的に使用する雑多なクラス
 */
public class NagoyameshiUtils {

	// 来店日のフォーマット
	public static final String COMMING_DATE_FORMAT = "yyyy-MM-dd";
	// 来店日のフォーマッター
	public static final DateTimeFormatter COMMING_DATE_FORMATTER = DateTimeFormatter
			.ofPattern(COMMING_DATE_FORMAT);
	// 来店日時のフォーマット
	public static final String COMMING_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
	// 来店日時のフォーマッター
	public static final DateTimeFormatter COMMING_DATE_TIME_FORMATTER = DateTimeFormatter
			.ofPattern(COMMING_DATE_TIME_FORMAT);

	private NagoyameshiUtils() {
	}

	/**
	 * LocalDateTime#toStringの不要なTを除去する
	 * @param dateTime
	 * @return
	 */
	public static String replaceTWithSpace(String dateTime) {
		if (dateTime == null) {
			return null; // null の場合はそのまま返す
		}
		return dateTime.replace("T", " ");
	}

	/**
	 * 指定された LocalDateTime が現在の日時より後かどうかを判定します。
	 *
	 * @param dateTime 判定する LocalDateTime
	 * @return 現在の日時より後であれば true、それ以外は false
	 */
	public static boolean isAfterNow(LocalDateTime dateTime) {
		if (dateTime == null) {
			throw new IllegalArgumentException("dateTime must not be null");
		}
		return dateTime.isAfter(LocalDateTime.now());
	}

	/**
	 * spring-security認証情報principalからuserIdを取得する
	 * @return
	 */
	public static Integer getCurrentUserId() {
		return getLoginUserDetail() != null ? getLoginUserDetail().getUser().getId() : null;
	}

	/**
	 * ログイン中のユーザ-を取得する
	 * @return
	 */
	public static UserDetailsImpl getLoginUserDetail() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetailsImpl user) {
			return user; // userIdを返す
		}
		return null;
	}
}
