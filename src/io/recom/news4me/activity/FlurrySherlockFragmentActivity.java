package io.recom.news4me.activity;

import io.recom.news4me.R;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.flurry.android.FlurryAgent;

public class FlurrySherlockFragmentActivity extends SherlockFragmentActivity {

	@Override
	public void onStart() {
		super.onStart();

		String flurryApiKey = getString(R.string.flurry_api_key);
		FlurryAgent.onStartSession(this, flurryApiKey);
	}

	@Override
	public void onStop() {
		FlurryAgent.onEndSession(this);

		super.onStop();
	}

}
