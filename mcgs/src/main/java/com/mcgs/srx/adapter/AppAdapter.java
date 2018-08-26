package com.mcgs.srx.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mcgs.srx.R;
import com.mcgs.srx.switchFragment.ApplicationFragment;
import com.mcgs.srx.switchFragment.DocumentFragment;

public class AppAdapter extends BaseAdapter {

	private Context context;
	private String[] strings;
	public static int mPosition;

	public AppAdapter(Context context, String[] strings){
		this.context =context;
		this.strings = strings;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return strings.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return strings[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_app, null);
		TextView tv = (TextView) convertView.findViewById(R.id.tv);
		mPosition = position;
		tv.setText(strings[position]);
		if (position == ApplicationFragment.mPosition) {
			convertView.setBackgroundResource(R.drawable.tongcheng_all_bg01);
		} else {
			convertView.setBackgroundColor(Color.parseColor("#f4f4f4"));
		}
		return convertView;
	}
}
