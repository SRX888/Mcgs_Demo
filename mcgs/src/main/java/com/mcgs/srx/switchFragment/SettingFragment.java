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

import com.mcgs.srx.McgsFragment.SetFragment;
import com.mcgs.srx.R;
import com.mcgs.srx.Util.Utils;
import com.mcgs.srx.adapter.SetAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String TAG = "SetFragment";
    private String str;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Utils mUtils;
    private String[] strs;

    private ListView listView;
    private SetAdapter mSetAdapter;
    private SetFragment mSetFragment;
    public static int mPosition;

    private OnFragmentInteractionListener mListener;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
        strs = mUtils.getStringArray(R.array.mcgs_set_value);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_setting, null);
//        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
//              //得到数据
//        str = getArguments().getString(TAG);
//        tv_title.setText(str);
        listView = (ListView) view.findViewById(R.id.listview_set);
        mSetAdapter = new SetAdapter(getActivity(), strs);
        listView.setAdapter(mSetAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View view1, int position, long l) {
                //拿到当前位置
                mPosition = position;
                //即使刷新adapter
                mSetAdapter.notifyDataSetChanged();
                for (int i = 0; i < strs.length; i++) {
                    mSetFragment = new SetFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                            .beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container_set, mSetFragment);
                    Bundle bundle = new Bundle();
                    bundle.putString(SetFragment.TAG, strs[position]);
                    mSetFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }
            }
        });

        //创建SetFragment对象
        mSetFragment = new SetFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_set, mSetFragment);
        //通过bundle传值给MyFragment
        Bundle bundle = new Bundle();
        bundle.putString(SetFragment.TAG, strs[mPosition]);
        mSetFragment.setArguments(bundle);
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
