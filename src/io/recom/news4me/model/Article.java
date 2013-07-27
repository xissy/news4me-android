package io.recom.news4me.model;

import io.recom.news4me.helper.JsonHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Article {

	JSONObject articleJsonObject;

	boolean isStarred;

	public Article(JSONObject jsonObject) {
		this.articleJsonObject = jsonObject;

		this.isStarred = articleJsonObject.optBoolean("isStarred", false);
	}

	public int getId() {
		return articleJsonObject.optInt("id", -1);
	}

	public String getTitle() {
		return articleJsonObject.optString("title", "Untitled");
	}

	public String getUrl() {
		return articleJsonObject.optString("articleUrl", null);
	}

	public String getPubDateFromNowString() {
		return articleJsonObject.optString("pubDateFromNowString");
	}

	public String getSiteTitle() {
		return articleJsonObject.optString("siteTitle");
	}

	public String getKeywordsString() {
		String keywordsString = "";

		try {
			JSONArray keywordArray = articleJsonObject.getJSONArray("keywords");
			keywordsString = JsonHelper.JoinArray(keywordArray, ", ");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return keywordsString;
	}

	public String getImageUrl() {
		return articleJsonObject.optString("imageUrl", null);
	}

	public String getSummarizedSentencesString() {
		String summarizedSentences = "<html><body style='margin-top: 0; margin-down: 0;'>";

		try {
			JSONArray summarizedSentencesArray = articleJsonObject
					.getJSONArray("summarizedSentences");

			for (int i = 0; i < summarizedSentencesArray.length(); i++) {
				summarizedSentences += "<p align='justify'>"
						+ summarizedSentencesArray.getString(i) + "</p>";
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		summarizedSentences += "</body></html>";

		return summarizedSentences;
	}

	public String getWhyWordsString() {
		String whyWordsString = "";

		try {
			JSONArray whyWordArray = articleJsonObject.getJSONArray("words");
			whyWordsString = JsonHelper.JoinArray(whyWordArray, ", ");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return whyWordsString;
	}

	public boolean isStarred() {
		return isStarred;
	}

	public void isStarred(boolean isStarred) {
		this.isStarred = isStarred;
	}

}
