package com.mcgs.srx.switchFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mcgs.srx.McgsFragment.AppFragment;
import com.mcgs.srx.R;
import com.mcgs.srx.Util.Utils;
import com.mcgs.srx.adapter.AppAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ApplicationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ApplicationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplicationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String TAG = "AppFragment";
    private String str;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Utils mUtils;
    private String[] strs;

    private ListView listView;
    private AppAdapter mAppAdapter;
    private AppFragment mAppFragment;
    public static int mPosition;

    private OnFragmentInteractionListener mListener;

    public ApplicationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApplicationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApplicationFragment newInstance(String param1, String param2) {
        ApplicationFragment fragment = new ApplicationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mUtils = Utils.getInstance();
        mUtils.init(getContext());
        strs = mUtils.getStringArray(R.array.mcgs_app_value);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_app, null);
//        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
//            //得到数据
//        str = getArguments().getString(TAG);
//        tv_title.setText(str);
        listView = (ListView) view.findViewById(R.id.app_listview);
        mAppAdapter = new AppAdapter(getActivity(), strs);
        listView.setAdapter(mAppAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View view1, int position, long l) {
                //拿到当前位置
                mPosition = position;
                //即使刷新adapter
                mAppAdapter.notifyDataSetChanged();
                for (int i = 0; i < strs.length; i++) {
                    mAppFragment = new AppFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                            .beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container_app, mAppFragment);
                    Bundle bundle = new Bundle();
                    bundle.putInt(mAppFragment.TAG, position);
                    mAppFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }
            }
        });

        //创建AppFragment对象
        mAppFragment = new AppFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_app, mAppFragment);
        //通过bundle传值给AppFragment
        Bundle bundle = new Bundle();
        bundle.putString(mAppFragment.TAG, strs[mPosition]);
        mAppFragment.setArguments(bundle);
        fragmentTransaction.commit();


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
