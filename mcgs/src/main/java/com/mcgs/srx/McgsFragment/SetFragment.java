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

public class SetFragment extends Fragment implements View.OnClickListener {
	
	public static final String TAG = "SetFragment";
    private int position;
    private View view;


    //usb Set start
    private Button btnUsbmode;
    private static final String KEY_PACKAGENAME_USBMODE = "com.mcgs.srx";
    private static final String KEY_PACKAGENAME_ACTIVITY_USBMODE = "com.mcgs.srx.McgsSet.UsbSetting";
    //usb Set end

    //lcd set start
    private Button btnLcdSet;
    private static final String KEY_PACKAGENAME_LCD = "com.mcgs.srx";
    private static final String KEY_PACKAGENAME_ACTIVITY_LCD = "com.mcgs.srx.McgsSet.LcdSet";
    //lcd set end


    //bee set start
    private Button btnBeeSet;
    private static final String KEY_PACKAGENAME_BEE = "com.mcgs.srx";
    private static final String KEY_PACKAGENAME_ACTIVITY_BEE = "com.mcgs.srx.McgsSet.BeeSet";

    //bee set end


    private Utils mUtils;
    private Context mContext;
    private JustifyTextView mTextView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initUsbModeView();
                    break;
                case 1:
                    initLcdSetView();
                    break;
                case 2:
                    initBeeSetView();
                    break;
                case 3:
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
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_usb_set:
                startAPP(KEY_PACKAGENAME_USBMODE,KEY_PACKAGENAME_ACTIVITY_USBMODE);
                break;
            case R.id.btn_lcd_set:
                startAPP(KEY_PACKAGENAME_LCD,KEY_PACKAGENAME_ACTIVITY_LCD);
                break;
            case R.id.btn_bee_set:
                startAPP(KEY_PACKAGENAME_BEE,KEY_PACKAGENAME_ACTIVITY_BEE);
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


    private void initUsbModeView(){
        btnUsbmode = (Button)view.findViewById(R.id.btn_usb_set);
        btnUsbmode.setOnClickListener(this);
    }

    private void initLcdSetView(){
        btnLcdSet = (Button)view.findViewById(R.id.btn_lcd_set);
        btnLcdSet.setOnClickListener(this);
    }

    private void initBeeSetView(){
        btnBeeSet = (Button)view.findViewById(R.id.btn_bee_set);
        btnBeeSet.setOnClickListener(this);
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
                view = mUtils.getFragmentView(mContext, R.layout.set_usb_fragment);
                break;
            case 1:
                view = mUtils.getFragmentView(mContext, R.layout.set_lcd_fragment);
                break;
            case 2:
                view = mUtils.getFragmentView(mContext, R.layout.set_bee_fragment);
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
