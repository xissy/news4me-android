package io.recom.news4me.adapter.articles;

import io.recom.news4me.helper.NewsType;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class StarredArticlesListAdapter extends ArticlesListAdapter {

	public StarredArticlesListAdapter(SherlockFragmentActivity activity) {
		super(activity);

		newsType = NewsType.Starred;
	}

}
