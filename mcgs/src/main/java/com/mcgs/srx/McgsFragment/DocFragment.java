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

import com.mcgs.srx.R;
import com.mcgs.srx.Util.JustifyTextView;
import com.mcgs.srx.Util.Utils;

import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.styles.Github;

public class DocFragment extends Fragment {

    public static final String TAG = "DocFragment";
    private JustifyTextView mTextView;
    private int position;
    private View view;
    private Utils mUtils;
    private Context mContext;


    //doc 0 view start
    private MarkdownView mContentMarkdownView0;
    private static final String KEY_FILENAME_0 = "rotation.md";
    //doc 0 view end

    //doc studio view start
    private MarkdownView mContentMarkdownView1;
    private static final String KEY_FILENAME_1 = "rotation.md";
    //doc studio view end

    //doc  rotation start
    private MarkdownView mContentMarkdownView2;
    private static final String KEY_FILENAME_2 = "rotation.md";
    //doc rotation end

    //doc  rotation start
    private MarkdownView mContentMarkdownView3;
    private static final String KEY_FILENAME_3 = "rotation.md";
    //doc rotation end

    //doc  ndk start
    private MarkdownView mContentMarkdownView4;
    private static final String KEY_FILENAME_4 = "rotation.md";
    //doc ndk end

    //doc  jni start
    private MarkdownView mContentMarkdownView5;
    private static final String KEY_FILENAME_5 = "rotation.md";
    //doc jni end


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
                    initNdkView();
                    break;
                case 5:
                    initJniView();
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
            case 4:
                view = mUtils.getFragmentView(mContext, R.layout.doc_4_fragment);
                break;
            case 5:
                view = mUtils.getFragmentView(mContext, R.layout.doc_5_fragment);
                break;
            default:
                view = mUtils.getFragmentView(mContext, R.layout.docfragment);
                break;
        }
        return view;
    }

    private void initDocView0() {
        mContentMarkdownView0 = (MarkdownView) view.findViewById(R.id.content_markdownview0);
        initContent(mContentMarkdownView0, KEY_FILENAME_0);
    }

    private void initStudioView() {
        mContentMarkdownView1 = (MarkdownView) view.findViewById(R.id.content_markdownview1);
        initContent(mContentMarkdownView1, KEY_FILENAME_1);
    }

    private void initRatotionw() {
        mContentMarkdownView2 = (MarkdownView) view.findViewById(R.id.content_markdownview2);
        initContent(mContentMarkdownView2, KEY_FILENAME_2);
    }

    private void initCalibrateView() {
        mContentMarkdownView3 = (MarkdownView) view.findViewById(R.id.content_markdownview3);
        initContent(mContentMarkdownView3, KEY_FILENAME_3);
    }

    private void initNdkView() {
        mContentMarkdownView4 = (MarkdownView) view.findViewById(R.id.content_markdownview4);
        initContent(mContentMarkdownView4, KEY_FILENAME_4);
    }

    private void initJniView() {
        mContentMarkdownView5 = (MarkdownView) view.findViewById(R.id.content_markdownview5);
        initContent(mContentMarkdownView5, KEY_FILENAME_5);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void initContent(MarkdownView markdownView, String filename) {
        try {
            markdownView.addStyleSheet(new Github());
            markdownView.loadMarkdownFromAsset(filename);
        } catch (Exception e) {
            e.printStackTrace();
            markdownView.loadMarkdown("Error");
        }
    }

}
