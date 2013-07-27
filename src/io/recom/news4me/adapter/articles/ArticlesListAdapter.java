package io.recom.news4me.adapter.articles;

import io.recom.news.R;
import io.recom.news4me.activity.IntroActivity;
import io.recom.news4me.helper.AjaxHelper;
import io.recom.news4me.helper.ArticleActionType;
import io.recom.news4me.helper.AuthType;
import io.recom.news4me.helper.NewsType;
import io.recom.news4me.helper.TypefaceManager;
import io.recom.news4me.helper.UrlStringHelper;
import io.recom.news4me.model.Article;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

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

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.facebook.Session;

public abstract class ArticlesListAdapter extends BaseAdapter implements
		ListAdapter {

	protected SherlockFragmentActivity activity;
	protected List<Article> articleList;
	protected AQuery aq;

	protected NewsType newsType;

	public ArticlesListAdapter(SherlockFragmentActivity activity) {
		this.activity = activity;
		this.articleList = new ArrayList<Article>();
		this.aq = new AQuery(activity);
	}

	@Override
	public int getCount() {
		return articleList.size();
	}

	@Override
	public Article getItem(int position) {
		return articleList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.item_news, parent, false);
		}

		final AQuery aq = new AQuery(convertView);

		final Article article = getItem(position);
		final int articleId = article.getId();
		final String articleTitle = article.getTitle();
		final String articleUrl = article.getUrl();

		String prefsName = activity.getString(R.string.prefs_name);
		SharedPreferences prefs = activity.getSharedPreferences(prefsName, 0);
		final String facebookUserId = prefs.getString("facebookUserId", null);

		if (articleId < 0 || facebookUserId == null) {
			Intent intent = new Intent(activity, IntroActivity.class);
			activity.startActivity(intent);
			activity.finish();
			return convertView;
		}

		final String articleRedirectUrl = UrlStringHelper.getArticleActionUrl(
				ArticleActionType.Redirect, articleId, AuthType.Facebook,
				facebookUserId);

		aq.clicked(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse(articleRedirectUrl);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				activity.startActivity(intent);
			}
		});

		aq.id(R.id.articleTitleText).text(articleTitle);

		aq.id(R.id.publisherText).text(
				article.getPubDateFromNowString() + " - "
						+ article.getSiteTitle());

		String keywordsText = article.getKeywordsString();
		if (keywordsText.length() == 0) {
			aq.id(R.id.keywordsText).visibility(View.GONE);
		} else {
			aq.id(R.id.keywordsText).text(keywordsText);
		}

		String imageUrl = article.getImageUrl();
		if (imageUrl != null) {
			aq.id(R.id.articleImage)
					.image(imageUrl, true, true, 0, 0, null, AQuery.FADE_IN,
							AQuery.RATIO_PRESERVE).visibility(View.VISIBLE);
		} else {
			aq.id(R.id.articleImage).visibility(View.GONE);
		}

		String summarizedSentences = article.getSummarizedSentencesString();

		aq.id(R.id.summarizedSentencesText)
				.getWebView()
				.loadData(summarizedSentences, "text/html; charset=UTF-8", null);

		String whyText = article.getWhyWordsString();

		if (whyText.length() == 0) {
			aq.id(R.id.whyText).visibility(View.GONE);
		} else {
			aq.id(R.id.whyText).text(
					activity.getString(R.string.why) + "? " + whyText);
		}

		Typeface fontAwesomeTypeface = TypefaceManager.FontAwesome(activity
				.getAssets());

		aq.id(R.id.removeText).text(String.valueOf((char) 0xf00d))
				.clicked(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String articleDeleteUrl = UrlStringHelper
								.getArticleActionUrl(ArticleActionType.Delete,
										articleId, AuthType.Facebook,
										facebookUserId);
						AjaxHelper.call(aq, articleDeleteUrl);

						String articleDeleteReadActionUrl = UrlStringHelper
								.getArticleDeleteActionUrl(
										ArticleActionType.Read, articleId,
										AuthType.Facebook, facebookUserId);
						AjaxHelper.call(aq, articleDeleteReadActionUrl);

						articleList.remove(position);
						notifyDataSetChanged();
					}
				}).getTextView().setTypeface(fontAwesomeTypeface);

		aq.id(R.id.starText).text(String.valueOf((char) 0xf006))
				.clicked(new OnClickListener() {
					@Override
					public void onClick(View v) {
						article.isStarred(!article.isStarred());

						if (article.isStarred()) {
							String articleStarUrl = UrlStringHelper
									.getArticleActionUrl(
											ArticleActionType.Star, articleId,
											AuthType.Facebook, facebookUserId);
							AjaxHelper.call(aq, articleStarUrl);

							aq.id(R.id.starText).textColor(
									activity.getResources().getColor(
											R.color.icon_button_enabled));
						} else {
							String articleStarUrl = UrlStringHelper
									.getArticleDeleteActionUrl(
											ArticleActionType.Star, articleId,
											AuthType.Facebook, facebookUserId);
							AjaxHelper.call(aq, articleStarUrl);

							aq.id(R.id.starText).textColor(
									activity.getResources().getColor(
											R.color.icon_button_disabled));
						}
					}
				}).getTextView().setTypeface(fontAwesomeTypeface);

		if (article.isStarred()) {
			aq.id(R.id.starText).textColor(
					activity.getResources().getColor(
							R.color.icon_button_enabled));
		} else {
			aq.id(R.id.starText).textColor(
					activity.getResources().getColor(
							R.color.icon_button_disabled));
		}

		aq.id(R.id.shareText).text(String.valueOf((char) 0xf045))
				.clicked(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String shareActionExtraText = articleTitle + "\n"
								+ articleUrl;
						shareAction(shareActionExtraText);

						String articleShareUrl = UrlStringHelper
								.getArticleActionUrl(ArticleActionType.Share,
										articleId, AuthType.Facebook,
										facebookUserId);
						AjaxHelper.call(aq, articleShareUrl);
					}
				}).getTextView().setTypeface(fontAwesomeTypeface);

		String articleShowUrl = UrlStringHelper.getArticleActionUrl(
				ArticleActionType.Show, articleId, AuthType.Facebook,
				facebookUserId);
		AjaxHelper.call(aq, articleShowUrl);

		if (position == articleList.size() - 1) {
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
		String facebookUserId = prefs.getString("facebookUserId", null);

		if (facebookUserId == null) {
			return;
		}

		String newsUrl = UrlStringHelper.getNewsListUrl(AuthType.Facebook,
				facebookUserId, session.getAccessToken(), newsType,
				articleList.size());

		final int prevNewsListSize = articleList.size();

		aq.ajax(newsUrl, JSONArray.class, new AjaxCallback<JSONArray>() {
			@Override
			public void callback(String url, JSONArray jsonArray,
					AjaxStatus status) {
				if (jsonArray != null) {
					for (int i = 0; i < jsonArray.length(); i++) {
						try {
							articleList.add(new Article(jsonArray
									.getJSONObject(i)));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				} else {
					Toast.makeText(aq.getContext(),
							"Error:" + status.getCode(), Toast.LENGTH_LONG)
							.show();
				}

				if (articleList.size() > prevNewsListSize) {
					notifyDataSetChanged();
				}

				activity.setSupportProgressBarIndeterminate(false);
				activity.setSupportProgressBarIndeterminateVisibility(false);
			}
		});
	}

	protected void shareAction(String extraText) {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, extraText);
		sendIntent.setType("text/plain");
		activity.startActivity(sendIntent);
	}

}
