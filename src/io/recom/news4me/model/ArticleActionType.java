package io.recom.news4me.model;

public enum ArticleActionType {
	Show("show"), Read("read"), Share("share"), Star("star"), Scrap("scrap"), Delete(
			"delete"), Redirect("redirect");

	String articleActionString;

	ArticleActionType(String articleActionTypeString) {
		this.articleActionString = articleActionTypeString;
	}

	@Override
	public String toString() {
		return articleActionString;
	}
}
