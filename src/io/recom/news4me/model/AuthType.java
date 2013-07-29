package io.recom.news4me.model;

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
