package io.recom.news4me.activity;

import io.recom.news.R;
import io.recom.news4me.adapter.NewsActionListAdapter;
import io.recom.news4me.adapter.articles.ArticlesListAdapter;
import io.recom.news4me.adapter.articles.DeletedArticlesListAdapter;
import io.recom.news4me.adapter.articles.ReadArticlesListAdapter;
import io.recom.news4me.adapter.articles.RecommendedArticlesListAdapter;
import io.recom.news4me.adapter.articles.ScrappedArticlesListAdapter;
import io.recom.news4me.adapter.articles.ShownArticlesListAdapter;
import io.recom.news4me.adapter.articles.StarredArticlesListAdapter;
import io.recom.news4me.helper.NewsType;
import shared.ui.actionscontentview.ActionsContentView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.androidquery.AQuery;
import com.facebook.Session;

public class MainActivity extends FlurrySherlockFragmentActivity {

	AQuery aq;
	ActionBar actionBar;
	ArticlesListAdapter newsListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setSupportProgressBarIndeterminate(true);
		setSupportProgressBarIndeterminateVisibility(true);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		actionBar = getSupportActionBar();
		actionBar.setTitle(R.string.app_name);
		actionBar.setSubtitle(R.string.recommended_news);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		aq = new AQuery(this);

		NewsActionListAdapter newsActionListAdapter = new NewsActionListAdapter(
				this);
		aq.id(R.id.actions).adapter(newsActionListAdapter).getListView()
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						switch (position) {
						case 1:
							changeNewsListAdapter(NewsType.Recommended);
							break;
						case 2:
							changeNewsListAdapter(NewsType.Read);
							break;
						case 3:
							changeNewsListAdapter(NewsType.Starred);
							break;
						case 4:
							changeNewsListAdapter(NewsType.Deleted);
							break;
						case 6:
							Session session = Session.getActiveSession();
							if (session != null) {
								session.closeAndClearTokenInformation();
							}

							String prefsName = getString(R.string.prefs_name);
							SharedPreferences prefs = getSharedPreferences(
									prefsName, 0);
							Editor editor = prefs.edit();
							editor.putBoolean("isLoggedIn", false);
							editor.putString("logInService", null);
							editor.putString("facebookUserId", null);
							editor.commit();

							Intent intent = new Intent(MainActivity.this,
									IntroActivity.class);
							startActivity(intent);
							finish();
							break;
						default:
						}
					}
				});

		newsListAdapter = new RecommendedArticlesListAdapter(this);
		aq.id(R.id.list_news).adapter(newsListAdapter);

		if (Session.getActiveSession() != null
				&& Session.getActiveSession().isOpened()) {
			newsListAdapter.loadMoreNews();
		} else {
			Intent intent = new Intent(this, IntroActivity.class);
			startActivity(intent);

			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			ActionsContentView actionContentView = (ActionsContentView) aq.id(
					R.id.actionsContentView).getView();
			actionContentView.toggleActions();

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		Intent setIntent = new Intent(Intent.ACTION_MAIN);
		setIntent.addCategory(Intent.CATEGORY_HOME);
		setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(setIntent);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	private void changeNewsListAdapter(NewsType newsType) {
		if (newsType == NewsType.Recommended) {
			newsListAdapter = new RecommendedArticlesListAdapter(
					MainActivity.this);
			actionBar.setSubtitle(getString(R.string.recommended_news));
		} else if (newsType == NewsType.Read) {
			newsListAdapter = new ReadArticlesListAdapter(MainActivity.this);
			actionBar.setSubtitle(getString(R.string.read_news));
		} else if (newsType == NewsType.Starred) {
			newsListAdapter = new StarredArticlesListAdapter(MainActivity.this);
			actionBar.setSubtitle(getString(R.string.starred_news));
		} else if (newsType == NewsType.Scrapped) {
			newsListAdapter = new ScrappedArticlesListAdapter(MainActivity.this);
			actionBar.setSubtitle(getString(R.string.scrapped_news));
		} else if (newsType == NewsType.Deleted) {
			newsListAdapter = new DeletedArticlesListAdapter(MainActivity.this);
			actionBar.setSubtitle(getString(R.string.deleted_news));
		} else if (newsType == NewsType.Shown) {
			newsListAdapter = new ShownArticlesListAdapter(MainActivity.this);
			actionBar.setSubtitle(getString(R.string.shown_news));
		}

		aq.id(R.id.list_news).adapter(newsListAdapter);
		newsListAdapter.loadMoreNews();

		ActionsContentView actionContentView = (ActionsContentView) aq.id(
				R.id.actionsContentView).getView();
		actionContentView.showContent();
	}

}
