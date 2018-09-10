package com.mcgs.srx.McgsFragment;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.mcgs.srx.R;
import com.mcgs.srx.Util.JustifyTextView;
import com.mcgs.srx.Util.Utils;
import com.mcgs.srx.ethernet.EthernetSetting;

public class AppFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    public static final String TAG = "AppFragment";
    private int position;
    private View view;

    private Utils mUtils;
    private Context mContext;
    private JustifyTextView mTextView;


    //app rotation statrt
    private static final String KEY_ROTATION = "key_rotation";
    private RadioButton mRadioRotation_0;
    private RadioButton mRadioRotation_90;
    private RadioButton mRadioRotation_180;
    private RadioButton mRadioRotation_270;
    private RadioGroup mRadioGroupRotation;
    private Button mRotationRestore;
    //app rotation end


    //app ethernet start
    private static final String KEY_EHTERNET_STATUS = "key_ethernet_status";
    private Switch mSwitchEthernet;
    private Button mBtnEthernetSetings;
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
        mRadioRotation_0 = (RadioButton) view.findViewById(R.id.rotation_0);
        mRadioRotation_90 = (RadioButton) view.findViewById(R.id.rotation_90);
        mRadioRotation_180 = (RadioButton) view.findViewById(R.id.rotation_180);
        mRadioRotation_270 = (RadioButton) view.findViewById(R.id.rotation_270);
        mRadioGroupRotation = (RadioGroup) view.findViewById(R.id.radiogroup);
        mRotationRestore = (Button) view.findViewById(R.id.rotation_btn_restore);
        mRotationRestore.setOnClickListener(this);
        mRadioGroupRotation.setOnCheckedChangeListener(this);
        int value = mUtils.getIntPreference(KEY_ROTATION, 0);
        switch (value) {
            case 0:
                mRadioRotation_0.setChecked(true);
                break;
            case 1:
                mRadioRotation_90.setChecked(true);
                break;
            case 2:
                mRadioRotation_180.setChecked(true);
                break;
            case 3:
                mRadioRotation_270.setChecked(true);
                break;
        }

    }


    private void initEthernetView() {
        mSwitchEthernet = (Switch) view.findViewById(R.id.eth_switch_on);
        mSwitchEthernet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean b) {
                if (b) {
                    //TODO
                    mUtils.setBooleanPreference(KEY_EHTERNET_STATUS, b);
                    mBtnEthernetSetings.setEnabled(b);
                } else {
                    //TODO
                    mUtils.setBooleanPreference(KEY_EHTERNET_STATUS, b);
                    mBtnEthernetSetings.setEnabled(b);
                }

            }
        });
        mBtnEthernetSetings = (Button) view.findViewById(R.id.eth_setting);
        mBtnEthernetSetings.setOnClickListener(this);
        boolean isEthernetOn = mUtils.getBooleanPreference(KEY_EHTERNET_STATUS, false);
        mBtnEthernetSetings.setEnabled(isEthernetOn);
        mSwitchEthernet.setChecked(isEthernetOn);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rotation_btn_restore:
                showRotationDialogRestore();
                break;
            case R.id.eth_setting:
                Log.i("srx", "onClick:eth_settingeth_setting ");
                Intent intent = new Intent();
                intent.setClass(mContext, EthernetSetting.class);
                startActivity(intent);
                break;

            case R.id.btn_calibtate:
                startAPP(KEY_PACKAGENAME,KEY_PACKAGENAME_ACTIVITY);
                break;

        }

    }

    private void startAPP(String packagename,String activity) {
//        PackageManager packageManager = getContext().getPackageManager();
//        Intent intent = new Intent();
//        intent = packageManager.getLaunchIntentForPackage(packagename);
//        if (intent == null) {
//            Toast.makeText(getContext(), R.string.app_start_error, Toast.LENGTH_LONG).show();
//        } else {
//            startActivity(intent);
//        }

        Intent intent = new Intent();
        ComponentName cn = new ComponentName(packagename,activity);
        intent.setComponent(cn);
        intent.setAction("android.intent.action.MAIN");
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getContext(), R.string.app_start_error,Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rotation_0:
                mUtils.setIntPreference(KEY_ROTATION, 0);
                break;
            case R.id.rotation_90:
                mUtils.setIntPreference(KEY_ROTATION, 1);
                break;
            case R.id.rotation_180:
                mUtils.setIntPreference(KEY_ROTATION, 2);
                break;
            case R.id.rotation_270:
                mUtils.setIntPreference(KEY_ROTATION, 3);
                break;

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


    private void showRotationDialogRestore() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.btn_restore)
                .setMessage(R.string.btn_restore_message)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                mUtils.setIntPreference(KEY_ROTATION, 0);
                                mRadioRotation_0.setChecked(true);

                            }
                        }).setNegativeButton(android.R.string.cancel, null)
                .create().show();
    }


}
