package com.mcgs.srx.McgsFragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mcgs.srx.R;
import com.mcgs.srx.Util.JustifyTextView;
import com.mcgs.srx.Util.Utils;

public class SetFragment extends Fragment {

    public static final String TAG = "SetFragment";
    private int position;
    private View view;


    private Utils mUtils;
    private Context mContext;
    private JustifyTextView mTextView;


    private JustifyTextView mDemoVersiontv;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //TODO
                    break;
                case 1:
                    //TODO
                    mDemoVersiontv = (JustifyTextView) view.findViewById(R.id.tv_demo_version);
                    StringBuilder stringBuilder = new StringBuilder();
                    StringBuilder version = stringBuilder.append(getStr(R.string.demo_version))
                            .append(getStr(R.string.version_name));
                    mDemoVersiontv.setText(version);


                    break;

                default:
                    mTextView = (JustifyTextView) view.findViewById(R.id.tv_title);
                    mTextView.setText("default");
                    break;
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mUtils = Utils.getInstance();
        mUtils.init(getContext());
        mTextView = new JustifyTextView(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        position = getArguments().getInt(TAG);
        mHandler.sendEmptyMessage(position);
        Log.i("test", "onCreateView:==== " + position);
        switch (position) {
            case 0:
                view = mUtils.getFragmentView(mContext, R.layout.about_device_fragment);
                break;
            case 1:
                view = mUtils.getFragmentView(mContext, R.layout.about_mcgc_demo_fragment);
                break;
            default:
                view = mUtils.getFragmentView(mContext, R.layout.appfragment);
                break;
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private String getStr(int id) {
        return mContext.getApplicationContext().getString(id);
    }

}
