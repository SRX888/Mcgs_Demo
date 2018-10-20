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


    //doc studio view start
    private TextView mTvIndex1_0;
    private TextView mTvIndex1_1;
    private TextView mTvIndex1_2;
    private TextView mTvIndex1_3;
    private TextView mTvIndex1_4;
    private TextView mTvIndex1_5;
    private ScrollView mScrollView1;

    //doc studio view end


    //doc  rotation start
    private TextView mTvIndex2_0;
    private TextView mTvIndex2_1;
    private TextView mTvIndex2_2;
    private TextView mTvIndex2_3;
    private TextView mTvIndex2_4;
    private ScrollView mScrollView2;
    //doc rotation end

    //doc  rotation start
    private TextView mTvIndex3_0;
    private TextView mTvIndex3_1;
    private TextView mTvIndex3_2;
    private TextView mTvIndex3_3;
    private TextView mTvIndex3_4;
    private ScrollView mScrollView3;
    //doc rotation end


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initDocView0();
                    break;
                case 1:
                    initStudioView();
                    break;
                case 2:
                    initRatotionw();
                    break;
                case 3:
                    initCalibrateView();
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
            case 1:
                view = mUtils.getFragmentView(mContext, R.layout.doc_1_fragment);
                break;
            case 2:
                view = mUtils.getFragmentView(mContext, R.layout.doc_2_fragment);
                break;
            case 3:
                view = mUtils.getFragmentView(mContext, R.layout.doc_3_fragment);
                break;
            default:
                view = mUtils.getFragmentView(mContext, R.layout.docfragment);
                break;
        }
        return view;
    }

    private void initDocView0() {
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


    private void initRatotionw() {
        mTvIndex2_0 = (TextView) view.findViewById(R.id.indexes2_0);
        mTvIndex2_1 = (TextView) view.findViewById(R.id.indexes2_1);
        mTvIndex2_2 = (TextView) view.findViewById(R.id.indexes2_2);
        mTvIndex2_3 = (TextView) view.findViewById(R.id.indexes2_3);
        mTvIndex2_4 = (TextView) view.findViewById(R.id.indexes2_4);
        mTvIndex2_0.setClickable(true);
        mTvIndex2_0.setOnClickListener(this);
        mTvIndex2_1.setClickable(true);
        mTvIndex2_1.setOnClickListener(this);
        mTvIndex2_2.setClickable(true);
        mTvIndex2_2.setOnClickListener(this);
        mTvIndex2_3.setClickable(true);
        mTvIndex2_3.setOnClickListener(this);
        mTvIndex2_4.setClickable(true);
        mTvIndex2_4.setOnClickListener(this);
        mScrollView2 = (ScrollView) view.findViewById(R.id.scrollview2);
    }


    private void initCalibrateView() {
        mTvIndex3_0 = (TextView) view.findViewById(R.id.indexes3_0);
        mTvIndex3_1 = (TextView) view.findViewById(R.id.indexes3_1);
        mTvIndex3_2 = (TextView) view.findViewById(R.id.indexes3_2);
        mTvIndex3_3 = (TextView) view.findViewById(R.id.indexes3_3);
        mTvIndex3_4 = (TextView) view.findViewById(R.id.indexes3_4);
        mTvIndex3_0.setClickable(true);
        mTvIndex3_0.setOnClickListener(this);
        mTvIndex3_1.setClickable(true);
        mTvIndex3_1.setOnClickListener(this);
        mTvIndex3_2.setClickable(true);
        mTvIndex3_2.setOnClickListener(this);
        mTvIndex3_3.setClickable(true);
        mTvIndex3_3.setOnClickListener(this);
        mTvIndex3_4.setClickable(true);
        mTvIndex3_4.setOnClickListener(this);
        mScrollView3 = (ScrollView) view.findViewById(R.id.scrollview3);
    }


    private void initStudioView() {
        mTvIndex1_0 = (TextView) view.findViewById(R.id.indexes1_0);
        mTvIndex1_1 = (TextView) view.findViewById(R.id.indexes1_1);
        mTvIndex1_2 = (TextView) view.findViewById(R.id.indexes1_2);
        mTvIndex1_3 = (TextView) view.findViewById(R.id.indexes1_3);
        mTvIndex1_4 = (TextView) view.findViewById(R.id.indexes1_4);
        mTvIndex1_0.setClickable(true);
        mTvIndex1_0.setOnClickListener(this);
        mTvIndex1_1.setClickable(true);
        mTvIndex1_1.setOnClickListener(this);
        mTvIndex1_2.setClickable(true);
        mTvIndex1_2.setOnClickListener(this);
        mTvIndex1_3.setClickable(true);
        mTvIndex1_3.setOnClickListener(this);
        mTvIndex1_4.setClickable(true);
        mTvIndex1_4.setOnClickListener(this);
        mScrollView1 = (ScrollView) view.findViewById(R.id.scrollview1);
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

            case R.id.indexes1_0:
                setScrollViewPosition(mScrollView1, 0);
                break;
            case R.id.indexes1_1:
                setScrollViewPosition(mScrollView1, 230);
                break;
            case R.id.indexes1_2:
                setScrollViewPosition(mScrollView1, 5450);
                break;
            case R.id.indexes1_3:
                setScrollViewPosition(mScrollView1, 6850);
                break;
            case R.id.indexes1_4:
                setScrollViewPosition(mScrollView1, 9950);
                break;

            case R.id.indexes2_0:
                setScrollViewPosition(mScrollView2, 0);
                break;
            case R.id.indexes2_1:
                setScrollViewPosition(mScrollView2, 1830);
                break;
            case R.id.indexes2_2:
                setScrollViewPosition(mScrollView2, 2350);
                break;
            case R.id.indexes2_3:
                setScrollViewPosition(mScrollView2, 4930);
                break;
            case R.id.indexes2_4:
                setScrollViewPosition(mScrollView2, 6000);
                break;
            case R.id.indexes3_0:
                setScrollViewPosition(mScrollView3, 0);
                break;
            case R.id.indexes3_1:
                setScrollViewPosition(mScrollView3, 980);
                break;
            case R.id.indexes3_2:
                setScrollViewPosition(mScrollView3, 1060);
                break;
            case R.id.indexes3_3:
                setScrollViewPosition(mScrollView3, 2150);
                break;
            case R.id.indexes3_4:
                setScrollViewPosition(mScrollView3, 6300);
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
