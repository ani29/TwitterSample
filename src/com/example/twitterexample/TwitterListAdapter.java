package com.example.twitterexample;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Aniruddh 
 * this class is used to fill data in List View
 * 
 */
public class TwitterListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<TwitterFeed> mListData;
	private final LayoutInflater mLayoutInflater;
	public ImageLoader imageLoader;

	public TwitterListAdapter(Context ctx, ArrayList<TwitterFeed> mTwitterList) {
		super();
		mLayoutInflater = LayoutInflater.from(ctx);
		mContext = ctx;
		mListData = mTwitterList;
		imageLoader = new ImageLoader(mContext);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return mListData.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		final ListViewHolder holder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.listviewitem,
					parent, false);
			holder = new ListViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.namelistitem);
			holder.tweet = (TextView) convertView
					.findViewById(R.id.detaillistitem);
			holder.thumbnail = (ImageView) convertView
					.findViewById(R.id.listthumbnail);
			convertView.setTag(holder);
		} else {
			holder = (ListViewHolder) convertView.getTag();
		}
		holder.thumbnail.setBackgroundResource(R.drawable.ic_launcher);
		holder.name.setText(mListData.get(position).userName);
		holder.tweet.setText(mListData.get(position).tweet);
		imageLoader.DisplayImage(mListData.get(position).thumbURL,
				holder.thumbnail);
		return convertView;
	}
/**
 * 
 * @author Aniruddh
 *
 */
	private static class ListViewHolder {
		protected ImageView thumbnail;
		protected TextView name;
		protected TextView tweet;
	}

}
