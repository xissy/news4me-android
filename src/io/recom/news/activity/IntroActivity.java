package io.recom.news.activity;

import io.recom.news.R;
import io.recom.news.adapter.IntroFragmentAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class IntroActivity extends SherlockFragmentActivity {

	IntroFragmentAdapter introFragmentAdapter;
	ViewPager introViewPager;
	PageIndicator introPageIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);

		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();

		introFragmentAdapter = new IntroFragmentAdapter(
				getSupportFragmentManager());

		introViewPager = (ViewPager) findViewById(R.id.introViewPager);
		introViewPager.setAdapter(introFragmentAdapter);

		introPageIndicator = (CirclePageIndicator) findViewById(R.id.introPageIndicator);
		introPageIndicator.setViewPager(introViewPager);
	}

}
