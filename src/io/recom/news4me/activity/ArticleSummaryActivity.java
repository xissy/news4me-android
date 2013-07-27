package io.recom.news4me.activity;

import io.recom.news4me.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.androidquery.AQuery;

public class ArticleSummaryActivity extends FlurrySherlockFragmentActivity {

	AQuery aq;
	int articleId;
	String articleUrl;
	String articleOriginalUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_summary);

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		aq = new AQuery(this);

		Bundle bundle = getIntent().getExtras();
		articleId = bundle.getInt("articleId");

		String articleUrl = "http://news.recom.io/articles/" + articleId
				+ "/html";
		aq.id(R.id.articleWebView).getWebView().loadUrl(articleUrl);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.article_summary, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}

		if (item.getItemId() == R.id.menu_show_original_article) {
			String articleOriginalUrl = "http://news.recom.io/articles/"
					+ articleId + "/redirect";
			Uri uri = Uri.parse(articleOriginalUrl);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
