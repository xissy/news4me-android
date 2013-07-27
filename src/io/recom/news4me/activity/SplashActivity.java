package io.recom.news4me.activity;

import io.recom.news.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends FlurrySherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
	}

	@Override
	public void onStart() {
		super.onStart();

		String prefsName = getString(R.string.prefs_name);
		SharedPreferences prefs = getSharedPreferences(prefsName, 0);
		boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

		if (isLoggedIn) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		} else {
			Intent intent = new Intent(this, IntroActivity.class);
			startActivity(intent);
			finish();
		}
	}

}
