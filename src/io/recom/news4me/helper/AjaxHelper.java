package io.recom.news4me.helper;

import java.io.InputStream;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

public class AjaxHelper {

	public static void call(AQuery aq, String url,
			AjaxCallback<InputStream> callback) {
		if (callback == null) {
			callback = new AjaxCallback<InputStream>() {
				public void callback(String url, InputStream inputStream,
						AjaxStatus status) {
				};
			};
		}

		aq.ajax(url, InputStream.class, callback);
	}

	public static void call(AQuery aq, String url) {
		call(aq, url, null);
	}

}
