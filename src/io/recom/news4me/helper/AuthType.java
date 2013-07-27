package io.recom.news4me.helper;

public enum AuthType {
	Facebook("facebook"), Twitter("twitter");

	String authTypeString;

	AuthType(String authTypeString) {
		this.authTypeString = authTypeString;
	}

	@Override
	public String toString() {
		return authTypeString;
	}
}
