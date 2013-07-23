package io.recom.news.activity;

import io.recom.news.R;
import io.recom.news.adapter.NewsListAdapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.Window;
import com.androidquery.AQuery;
import com.facebook.Session;

public class MainActivity extends SherlockActivity {

	AQuery aq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActionBar actionBar = getSupportActionBar();
		// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setDisplayShowTitleEnabled(true);

		setSupportProgressBarIndeterminate(true);
		setSupportProgressBarIndeterminateVisibility(true);

		aq = new AQuery(this);

		List<JSONObject> newsList = new ArrayList<JSONObject>();
		NewsListAdapter newsListAdapter = new NewsListAdapter(this, newsList);
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Session.getActiveSession().onActivityResult(this, requestCode,
		// resultCode, data);
	}

	@Override
	public void onBackPressed() {
		Intent setIntent = new Intent(Intent.ACTION_MAIN);
		setIntent.addCategory(Intent.CATEGORY_HOME);
		setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(setIntent);
	}

}
