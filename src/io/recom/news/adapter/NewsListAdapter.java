package io.recom.news.adapter;

import io.recom.news.R;
import io.recom.news.TypefaceManager;
import io.recom.news.activity.IntroActivity;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.facebook.Session;

public class NewsListAdapter extends BaseAdapter implements ListAdapter {

	SherlockActivity activity;
	List<JSONObject> newsList;
	AQuery aq;

	public NewsListAdapter(SherlockActivity activity, List<JSONObject> newsList) {
		this.activity = activity;
		this.newsList = newsList;
		this.aq = new AQuery(activity);
	}

	@Override
	public int getCount() {
		int newsListSize = newsList.size();

		if (newsListSize > 0) {
			return newsListSize + 1;
		} else {
			return newsListSize;
		}
	}

	@Override
	public JSONObject getItem(int position) {
		return newsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position == newsList.size()) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(parent
						.getContext());
				convertView = inflater.inflate(R.layout.item_footer, parent,
						false);
			}
			return convertView;
		}

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.item_news, parent, false);
		}

		JSONObject article = getItem(position);

		final AQuery aq = new AQuery(convertView);

		final int articleId = article.optInt("id", -1);
		if (articleId >= 0) {
			aq.clicked(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Uri uri = Uri.parse("http://news.recom.io/articles/"
							+ articleId + "/redirect");
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					activity.startActivity(intent);
				}
			});
		}

		aq.id(R.id.articleTitleText).text(
				article.optString("title", "No Title"));

		aq.id(R.id.publisherText).text(
				article.optString("pubDateFromNowString") + " - "
						+ article.optString("siteTitle"));

		try {
			String keywordsText = "";
			JSONArray keywordsArray = article.getJSONArray("keywords");
			for (int i = 0; i < keywordsArray.length(); i++) {
				keywordsText += keywordsArray.getString(i);
				if (i < keywordsArray.length() - 1) {
					keywordsText += ", ";
				}
			}
			if (keywordsText.length() == 0) {
				aq.id(R.id.keywordsText).visibility(View.GONE);
			} else {
				aq.id(R.id.keywordsText).text(keywordsText);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String imageUrl = article.optString("imageUrl", null);
		if (imageUrl != null) {
			aq.id(R.id.articleImage)
					.image(imageUrl, true, true, 0, 0, null, AQuery.FADE_IN,
							AQuery.RATIO_PRESERVE).visibility(View.VISIBLE);
		} else {
			aq.id(R.id.articleImage).visibility(View.GONE);
		}

		String summarizedSentences = "";
		summarizedSentences += "<html><body style='margin-top: 0; margin-down: 0;'>";
		try {
			JSONArray summarizedSentencesArray = article
					.getJSONArray("summarizedSentences");

			for (int i = 0; i < summarizedSentencesArray.length(); i++) {
				summarizedSentences += "<p align='justify'>"
						+ summarizedSentencesArray.getString(i) + "</p>";
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		summarizedSentences += "</body></html>";

		aq.id(R.id.summarizedSentencesText)
				.getWebView()
				.loadData(summarizedSentences, "text/html; charset=UTF-8", null);

		try {
			String whyText = "왜? ";
			JSONArray whyArray = article.getJSONArray("words");
			for (int i = 0; i < whyArray.length(); i++) {
				whyText += whyArray.getString(i);
				if (i < whyArray.length() - 1) {
					whyText += ", ";
				}
			}
			if (whyText.length() == 0) {
				aq.id(R.id.whyText).visibility(View.GONE);
			} else {
				aq.id(R.id.whyText).text(whyText);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Typeface fontAwesomeTypeface = TypefaceManager.FontAwesome(activity
				.getAssets());

		aq.id(R.id.removeText).text(String.valueOf((char) 0xf00d))
				.getTextView().setTypeface(fontAwesomeTypeface);
		aq.id(R.id.starText).text(String.valueOf((char) 0xf006)).getTextView()
				.setTypeface(fontAwesomeTypeface);
		aq.id(R.id.saveText).text(String.valueOf((char) 0xf093)).getTextView()
				.setTypeface(fontAwesomeTypeface);
		aq.id(R.id.shareText).text(String.valueOf((char) 0xf045)).getTextView()
				.setTypeface(fontAwesomeTypeface);

		if (position == newsList.size() - 1) {
			loadMoreNews();
		}

		return convertView;
	}

	public void loadMoreNews() {
		activity.setSupportProgressBarIndeterminate(true);
		activity.setSupportProgressBarIndeterminateVisibility(true);

		Session session = Session.getActiveSession();

		if (session == null) {
			Intent intent = new Intent(activity, IntroActivity.class);
			activity.startActivity(intent);

			activity.finish();
			return;
		}

		String prefsName = activity.getString(R.string.prefs_name);
		SharedPreferences prefs = activity.getSharedPreferences(prefsName, 0);
		String facebookUserId = prefs.getString("facebookUserId", "");

		if (facebookUserId.equals("")) {
			return;
		}

		String url = "http://news.recom.io/api/v1/news/" + facebookUserId
				+ "?accessToken=" + session.getAccessToken();
		aq.ajax(url, JSONArray.class, new AjaxCallback<JSONArray>() {
			@Override
			public void callback(String url, JSONArray jsonArray,
					AjaxStatus status) {
				if (jsonArray != null) {
					// successful ajax call, show status code and json content
					for (int i = 0; i < jsonArray.length(); i++) {
						try {
							newsList.add(jsonArray.getJSONObject(i));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				} else {
					// ajax error, show error code
					Toast.makeText(aq.getContext(),
							"Error:" + status.getCode(), Toast.LENGTH_LONG)
							.show();
				}

				notifyDataSetChanged();

				activity.setSupportProgressBarIndeterminate(false);
				activity.setSupportProgressBarIndeterminateVisibility(false);
			}
		});
	}
}
