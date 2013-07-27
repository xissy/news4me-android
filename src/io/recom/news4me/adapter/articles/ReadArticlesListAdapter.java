package io.recom.news4me.adapter.articles;

import io.recom.news4me.helper.NewsType;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ReadArticlesListAdapter extends ArticlesListAdapter {

	public ReadArticlesListAdapter(SherlockFragmentActivity activity) {
		super(activity);

		newsType = NewsType.Read;
	}

}
