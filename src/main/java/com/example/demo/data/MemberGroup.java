package com.example.demo.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * db에 ADMIN, USER로 되어 있는데... 값을 숫자로 가져오네.
 */
public enum MemberGroup implements GlobalEnum {

	ADMIN("관리자", 10), USER("사용자", 30);

	private String name;
	private Integer value;

	private MemberGroup(String name, Integer value) {
		this.name = name;
		this.value = value;
	}

	@JsonCreator
	public static MemberGroup of(String value) {
		if (value == null)
			return null;
		for (MemberGroup o : MemberGroup.values()) {
			if (o.value.equals(Integer.parseInt(value)))
				return o;
		}
		return null;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@JsonValue
	@Override
	public String getValue() {
		return String.valueOf(value);
	}

}