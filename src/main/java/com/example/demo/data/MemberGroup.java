package com.example.demo.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * db에 ADMIN, USER로 되어 있는데... 값을 숫자로 가져오네.
 */
public enum MemberGroup implements GlobalEnum {

	ADMIN(10), USER(30);

	private Integer value;

	private MemberGroup(Integer value) {
		this.value = value;
	}

	@JsonCreator
	public static MemberGroup of(String value) {
		for (MemberGroup o : MemberGroup.values()) {
			if (o.value.equals(Integer.parseInt(value)))
				return o;
		}
		return null;
	}

	@Override
	public String getName() {
		return this.name();
	}

	@JsonValue
	@Override
	public String getValue() {
		return String.valueOf(value);
	}

}