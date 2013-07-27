package io.recom.news4me.helper;

import org.json.JSONArray;
import org.json.JSONException;

public class JsonHelper {

	public static String JoinArray(JSONArray jsonArray, String spiltter) {
		String result = "";

		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				result += jsonArray.getString(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (i < jsonArray.length() - 1) {
				result += ", ";
			}
		}

		return result;
	}

}
