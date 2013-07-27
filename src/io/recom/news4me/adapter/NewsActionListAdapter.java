package io.recom.news4me.adapter;

import io.recom.news4me.R;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.androidquery.AQuery;

public class NewsActionListAdapter extends BaseAdapter implements ListAdapter {

	enum ActionItemType {
		Category, Item
	}

	class ActionItem {
		public ActionItemType actionItemType;
		public String icon;
		public String name;

		public ActionItem(ActionItemType actionItemType, String icon,
				String name) {
			this.actionItemType = actionItemType;
			this.icon = icon;
			this.name = name;
		}
	}

	Activity activity;
	List<ActionItem> actionItemList = new ArrayList<ActionItem>();
	AQuery aq;

	public NewsActionListAdapter(final Activity activity) {
		this.activity = activity;

		aq = new AQuery(activity);

		actionItemList.add(new ActionItem(ActionItemType.Category, null,
				activity.getString(R.string.news)));
		actionItemList.add(new ActionItem(ActionItemType.Item, null, activity
				.getString(R.string.recommended_news)));
		actionItemList.add(new ActionItem(ActionItemType.Item, null, activity
				.getString(R.string.read_news)));
		actionItemList.add(new ActionItem(ActionItemType.Item, null, activity
				.getString(R.string.starred_news)));
		actionItemList.add(new ActionItem(ActionItemType.Item, null, activity
				.getString(R.string.deleted_news)));

		actionItemList.add(new ActionItem(ActionItemType.Category, null,
				activity.getString(R.string.settings)));
		actionItemList.add(new ActionItem(ActionItemType.Item, null, activity
				.getString(R.string.logout)));
	}

	@Override
	public int getCount() {
		return actionItemList.size();
	}

	@Override
	public ActionItem getItem(int position) {
		return actionItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ActionItem actionItem = getItem(position);

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());

			if (actionItem.actionItemType == ActionItemType.Category) {
				convertView = inflater.inflate(
						R.layout.action_category_list_item, parent, false);
			} else {
				convertView = inflater.inflate(R.layout.action_list_item,
						parent, false);
			}
		}

		final AQuery aq = new AQuery(convertView);

		aq.id(R.id.actionItemText).text(actionItem.name);

		if (actionItem.actionItemType == ActionItemType.Category) {

		} else {

		}

		return convertView;
	}

}
