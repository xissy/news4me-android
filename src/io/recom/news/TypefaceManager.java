package io.recom.news;

import android.content.res.AssetManager;
import android.graphics.Typeface;

public class TypefaceManager {

	protected static Typeface _awesome = null;

	public static Typeface FontAwesome(AssetManager assets) {
		if (_awesome == null) {
			_awesome = Typeface.createFromAsset(assets,
					"fontawesome-webfont.ttf");
		}
		return _awesome;
	}

}
