package com.wlk.core.enums;

public enum LoginType {
	USER("USER"), ADMIN("ADMIN");
	private String type;

	private LoginType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return this.type.toString();
	}

}
