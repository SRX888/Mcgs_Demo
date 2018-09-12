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

public class AppFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AppFragment";
    private int position;
    private View view;

    private Utils mUtils;
    private Context mContext;
    private JustifyTextView mTextView;


    //app rotation statrt
    private static final String KEY_PACKAGENAME_ROTATION = "cn.com.srx.mcgsconfig";
    private static final String KEY_PACKAGENAME_ACTIVITY_ROTATION = "cn.com.srx.rorationset.RotationSet";
    private Button mRotationRestore;
    //app rotation end


    //app ethernet start
    private static final String KEY_EHTERNET_STATUS = "key_ethernet_status";
    private Button mBtnEthernetSetings;
    private static final String KEY_PACKAGENAME_ETHERNET = "cn.com.srx.mcgsconfig";
    private static final String KEY_PACKAGENAME_ACTIVITY_THERNET = "cn.com.srx.ethernetset.EthernetSettings";
    //app ethernet end


    //app calibrate  start
    private Button mCalibrateBtn;
    private static final String KEY_PACKAGENAME = "cn.com.srx.tscalibration";
    private static final String KEY_PACKAGENAME_ACTIVITY = "cn.com.srx.tscalibration.MainActivity";
    //app calibrate  end

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initEthernetView();
                    break;
                case 1:
                    mTextView = (JustifyTextView) view.findViewById(R.id.tv_title);
                    mTextView.setText(R.string.serialport_documentation);
                    break;
                case 2:
                    initRotationView();
                    break;
                case 3:
                    initClibrateView();
                    break;
                case 4:
                    mTextView = (JustifyTextView) view.findViewById(R.id.tv_title);
                    mTextView.setText(R.string.usb_set_documentation);
                    break;
                default:
                    mTextView = (JustifyTextView) view.findViewById(R.id.tv_title);
                    mTextView.setText("default");
                    break;
            }
        }
    };


    private void initClibrateView() {
        mCalibrateBtn = (Button) view.findViewById(R.id.btn_calibtate);
        mCalibrateBtn.setOnClickListener(this);
    }

    private void initRotationView() {
        mRotationRestore = (Button) view.findViewById(R.id.btn_rotation);
        mRotationRestore.setOnClickListener(this);
    }


    private void initEthernetView() {
        mBtnEthernetSetings = (Button) view.findViewById(R.id.btn_ethernet);
        mBtnEthernetSetings.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_rotation:
                startAPP(KEY_PACKAGENAME_ROTATION,KEY_PACKAGENAME_ACTIVITY_ROTATION);
                break;
            case R.id.btn_ethernet:
                startAPP(KEY_PACKAGENAME_ETHERNET,KEY_PACKAGENAME_ACTIVITY_THERNET);
                break;

            case R.id.btn_calibtate:
                startAPP(KEY_PACKAGENAME,KEY_PACKAGENAME_ACTIVITY);
                break;

        }

    }

    private void startAPP(String packagename,String activity) {

        Intent intent = new Intent();
        ComponentName cn = new ComponentName(packagename,activity);
        intent.setComponent(cn);
        intent.setAction("android.intent.action.MAIN");
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getContext(), R.string.app_start_error,Toast.LENGTH_SHORT).show();
            Log.i("srx", "startAPP: E:"+e);
        }


    }



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
                view = mUtils.getFragmentView(mContext, R.layout.app_ethernet_fragment);
                break;
            case 1:
                view = mUtils.getFragmentView(mContext, R.layout.appfragment);
                break;
            case 2:
                view = mUtils.getFragmentView(mContext, R.layout.app_rotation_fragment);
                break;
            case 3:
                view = mUtils.getFragmentView(mContext, R.layout.app_calibrate_fragment);
                break;
            case 4:
                view = mUtils.getFragmentView(mContext, R.layout.appfragment);
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

}
