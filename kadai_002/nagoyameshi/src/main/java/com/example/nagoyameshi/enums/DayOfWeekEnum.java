package com.example.nagoyameshi.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum DayOfWeekEnum {
	MONDAY(1, "月曜"), TUESDAY(2, "火曜"), WEDNESDAY(3, "水曜"), THURSDAY(4, "木曜"), FRIDAY(5, "金曜"), SATURDAY(6,
			"土曜"), SUNDAY(7, "日曜"), NONE(0, "なし");

	private final int value;
	private final String displayName;
	private static Map<String, Integer> RESOLVER = new HashMap<>();

	static {
		Stream.of(DayOfWeekEnum.values()).forEach(d -> {
			RESOLVER.put(d.getDisplayName(), d.getValue());
		});
	};

	DayOfWeekEnum(int value, String displayName) {
		this.value = value;
		this.displayName = displayName;
	}

	public int getValue() {
		return value;
	}

	public String getDisplayName() {
		return displayName;
	}

	public static int fromDisplayName(String weekName) {
		return RESOLVER.get(weekName);
	}
}
