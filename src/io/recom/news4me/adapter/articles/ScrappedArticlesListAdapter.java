package io.recom.news4me.adapter.articles;

import io.recom.news4me.model.NewsType;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ScrappedArticlesListAdapter extends ArticlesListAdapter {

	public ScrappedArticlesListAdapter(SherlockFragmentActivity activity) {
		super(activity);

		newsType = NewsType.Scrapped;
	}

}
