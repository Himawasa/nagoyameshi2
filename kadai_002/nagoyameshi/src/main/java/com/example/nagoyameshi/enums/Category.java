package com.example.nagoyameshi.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
	Japanese_Cuisine(1, "和食"), //
	Western_Cuisine(2, "洋食"), //
	Chinese(3, "中華"), // 
	Ramen(4, "ラーメン"), //
	Izakaya(5, "居酒屋"); //

	private int categoryId;
	private String name;
	private static final Map<Integer, String> CATEGORY_RESOLVER = new HashMap<>();

	static {
		Stream.of(Category.values()).forEach(c -> {
			CATEGORY_RESOLVER.put(c.getCategoryId(), c.getName());
		});
	}

	/**
	 * カテゴリーIDからカテゴリ名を取得する
	 * 
	 * @param categoryId
	 * @return
	 */
	public static String getCategoryName(Integer categoryId) {
		return CATEGORY_RESOLVER.get(categoryId);
	}
}
