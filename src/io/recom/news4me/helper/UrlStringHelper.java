package io.recom.news4me.helper;

public class UrlStringHelper {

	static String baseUrl = "http://news.recom.io/api/v1";

	public static String getNewsListUrl(AuthType authType, String userId,
			String accessToken, NewsType newsType, int offset) {
		String url = baseUrl + "/news/" + authType.toString() + "/" + userId;

		if (newsType != NewsType.Recommended) {
			if (offset < 0) {
				offset = 0;
			}
			url += "/" + newsType.toString() + "/" + offset;
		}

		if (accessToken != null) {
			url += "?accessToken=" + accessToken;
		}

		return url;
	}

	public static String getArticleActionUrl(ArticleActionType articleActionType,
			int articleId, AuthType authType, String userId) {
		String url = baseUrl + "/articles/" + articleId + "/"
				+ articleActionType.toString() + "/from/" + authType.toString()
				+ "/" + userId;
		return url;
	}

	public static String getArticleDeleteActionUrl(
			ArticleActionType articleActionType, int articleId,
			AuthType authType, String userId) {
		String url = baseUrl + "/articles/" + articleId + "/"
				+ articleActionType.toString() + "/delete/from/"
				+ authType.toString() + "/" + userId;
		return url;
	}

}
