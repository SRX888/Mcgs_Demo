package com.mcgs.srx.McgsFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcgs.srx.R;

public class AppFragment extends Fragment {
	
	public static final String TAG = "AppFragment";
	private String str;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.appfragment, null);
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		//得到数据
		str = getArguments().getString(TAG);
		tv_title.setText(str);
		return view;
	}
}
