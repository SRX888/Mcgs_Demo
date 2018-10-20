package com.mcgs.srx.McgsFragment;

import android.content.Context;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mcgs.srx.R;
import com.mcgs.srx.Util.JustifyTextView;
import com.mcgs.srx.Util.Utils;

public class DocFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "DocFragment";

    private JustifyTextView mTextView;
    private int position;

    private View view;

    private Utils mUtils;
    private Context mContext;


    //doc 0 view start
    private TextView mTvIndex0_0;
    private TextView mTvIndex0_1;
    private TextView mTvIndex0_2;
    private TextView mTvIndex0_3;
    private ScrollView mScrollView0;

    //doc 0 view end


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initDocVew0();
                    break;
                case 1:
//                    mTextView.setText(R.string.developer_guide_documentation);
                    break;
                case 2:
//                    mTextView.setText(R.string.rotation_documentation);
                    break;
                case 3:
//                    mTextView.setText(R.string.tp_calibration_documentation);
                    break;
                case 4:
//                    mTextView.setText(R.string.system_common_modify);
                    break;
                default:
                    //mTextView.setText("default");
                    break;
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextView = new JustifyTextView(getContext());
        mContext = getContext();
        mUtils = Utils.getInstance();
        mUtils.init(mContext);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        position = getArguments().getInt(TAG);
        mHandler.sendEmptyMessage(position);
        Log.i("srx", "positon : ==" + position);

        switch (position) {
            case 0:
                view = mUtils.getFragmentView(mContext, R.layout.doc_0_fragment);
                break;
            default:
                view = mUtils.getFragmentView(mContext, R.layout.docfragment);
                break;
        }
        return view;
    }

    private void initDocVew0() {
        mTvIndex0_0 = (TextView) view.findViewById(R.id.indexes0_0);
        mTvIndex0_1 = (TextView) view.findViewById(R.id.indexes0_1);
        mTvIndex0_2 = (TextView) view.findViewById(R.id.indexes0_2);
        mTvIndex0_3 = (TextView) view.findViewById(R.id.indexes0_3);
        mTvIndex0_0.setClickable(true);
        mTvIndex0_0.setOnClickListener(this);
        mTvIndex0_1.setClickable(true);
        mTvIndex0_1.setOnClickListener(this);
        mTvIndex0_2.setClickable(true);
        mTvIndex0_2.setOnClickListener(this);
        mTvIndex0_3.setClickable(true);
        mTvIndex0_3.setOnClickListener(this);
        mScrollView0 = (ScrollView) view.findViewById(R.id.scrollview0);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.indexes0_0:
                setScrollViewPosition(mScrollView0, 0);
                break;
            case R.id.indexes0_1:
                setScrollViewPosition(mScrollView0, 780);
                break;
            case R.id.indexes0_2:
                setScrollViewPosition(mScrollView0, 7700);
                break;
            case R.id.indexes0_3:
                setScrollViewPosition(mScrollView0, 15000);
                break;
        }

    }


    private void setScrollViewPosition(final ScrollView mScrollView, final int position) {
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.smoothScrollTo(0, position);
            }
        });

    }

    private void MyToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
