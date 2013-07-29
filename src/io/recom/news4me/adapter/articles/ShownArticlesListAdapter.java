package io.recom.news4me.adapter.articles;

import io.recom.news4me.model.NewsType;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ShownArticlesListAdapter extends ArticlesListAdapter {

	public ShownArticlesListAdapter(SherlockFragmentActivity activity) {
		super(activity);

		newsType = NewsType.Shown;
	}

}
