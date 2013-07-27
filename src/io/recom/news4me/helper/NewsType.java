package io.recom.news4me.helper;

public enum NewsType {
	Recommended("recommended"), Read("read"), Shown("shown"), Starred("starred"), Scrapped(
			"scrapped"), Deleted("deleted");

	String newsTypeString;

	NewsType(String newsTypeString) {
		this.newsTypeString = newsTypeString;
	}

	@Override
	public String toString() {
		return newsTypeString;
	}

}
