package io.recom.news4me.adapter.articles;

import io.recom.news4me.model.NewsType;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class RecommendedArticlesListAdapter extends ArticlesListAdapter {

	public RecommendedArticlesListAdapter(SherlockFragmentActivity activity) {
		super(activity);

		newsType = NewsType.Recommended;
	}

}
