package io.recom.news4me.fragment;

import io.recom.news.R;
import io.recom.news4me.activity.MainActivity;

import java.util.Arrays;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class LogInFragment extends Fragment {

	AQuery aq;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, container, false);

		aq = new AQuery(getActivity(), view);

		LoginButton authButton = (LoginButton) aq
				.find(R.id.facebookLogInButton).getButton();
		authButton.setFragment(this);
		authButton.setReadPermissions(Arrays.asList("email", "read_stream"));

		aq.id(R.id.centeralProgressBar).visibility(View.GONE);

		if (Session.getActiveSession().isOpened()) {
			aq.id(R.id.centeralProgressBar).visibility(View.VISIBLE);

			Intent intent = new Intent(getActivity(), MainActivity.class);
			getActivity().startActivity(intent);

			getActivity().finish();
		}

		return view;
	}

	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		aq.id(R.id.centeralProgressBar).visibility(View.VISIBLE);

		if (state == SessionState.OPENED) {
			Request.executeMeRequestAsync(session,
					new Request.GraphUserCallback() {
						@Override
						public void onCompleted(GraphUser user,
								Response response) {
							if (user != null) {
								String facebookUserId = user.getId();

								String prefsName = getString(R.string.prefs_name);
								SharedPreferences prefs = getActivity()
										.getSharedPreferences(prefsName, 0);
								Editor editor = prefs.edit();
								editor.putBoolean("isLoggedIn", true);
								editor.putString("logInService", "facebook");
								editor.putString("facebookUserId",
										facebookUserId);
								editor.commit();

								Intent intent = new Intent(getActivity(),
										MainActivity.class);
								getActivity().startActivity(intent);

								getActivity().finish();

								aq.id(R.id.centeralProgressBar).visibility(
										View.GONE);
							}
						}
					});
		} else if (state == SessionState.CLOSED
				|| state == SessionState.CLOSED_LOGIN_FAILED) {
			aq.id(R.id.centeralProgressBar).visibility(View.GONE);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		uiHelper.onDestroy();
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

}
