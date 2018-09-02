package com.mcgs.srx.McgsFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcgs.srx.R;
import com.mcgs.srx.Util.JustifyTextView;

public class DocFragment extends Fragment {

    public static final String TAG = "DocFragment";

    private JustifyTextView mTextView;
    private int postion;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mTextView.setText(R.string.ethernet_documentation);
                    break;
                case 1:
                    mTextView.setText(R.string.serialport_documentation);
                    break;
                case 2:
                    mTextView.setText(R.string.rotation_documentation);
                    break;
                case 3:
                    mTextView.setText(R.string.tp_calibration_documentation);
                    break;
                case 4:
                    mTextView.setText(R.string.usb_set_documentation);
                    break;
                default:
                    mTextView.setText("default");
                    break;
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextView = new JustifyTextView(getContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.i("srx", "positon : ==" + postion);
        View view = inflater.inflate(R.layout.docfragment, null);
        mTextView = (JustifyTextView) view.findViewById(R.id.tv_title);

        postion = getArguments().getInt(TAG);
        mHandler.sendEmptyMessage(postion);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
