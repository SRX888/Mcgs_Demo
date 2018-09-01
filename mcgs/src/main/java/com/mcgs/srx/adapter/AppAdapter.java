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
		View view = null;
		ViewHodler mViewHodler = null;

		if(convertView ==null) {
			view = LayoutInflater.from(context).inflate(R.layout.listview_item_app,null);
			mViewHodler = new ViewHodler();
			mViewHodler.mTextView = (TextView)view.findViewById(R.id.tv);
			view.setTag(mViewHodler);
		}else{
			view = convertView;
			mViewHodler = (ViewHodler) view.getTag();
		}

		mPosition = position;
		mViewHodler.mTextView.setText(strings[position]);
		if (position == ApplicationFragment.mPosition) {
			view.setBackgroundResource(R.drawable.tongcheng_all_bg01);
		} else {
			view.setBackgroundColor(Color.parseColor("#f4f4f4"));
		}
		return view;
	}

	private static class ViewHodler{
		TextView mTextView;
	}
}
