package com.mcgs.srx;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mcgs.srx.BottomNavigation.BottomNavigationViewHelper;
import com.mcgs.srx.switchFragment.ApplicationFragment;
import com.mcgs.srx.switchFragment.DocumentFragment;
import com.mcgs.srx.switchFragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;



// TODO:(1)创建新的Activity，创建时选择 BottomNavigationActivity
public class MainActivity extends AppCompatActivity implements DocumentFragment.OnFragmentInteractionListener,ApplicationFragment.OnFragmentInteractionListener,SettingFragment.OnFragmentInteractionListener
        ,ViewPager.OnPageChangeListener{

    private ViewPager mViewPagerContent;
    private MenuItem menuItem;
    private BottomNavigationView navigation;

    // TODO:(3)BottomNavigationView 按钮的选中／点击事件
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // TODO:(5) 实现联动，点击按钮时，滑动到指定的ViewPager页面
            // 1、滑动过去，用mViewPagerContent.setCurrentItem(index);
            // 2、直接切换过去，用mViewPagerContent.setCurrentItem(0,false);  false就代表不进行滑动
            switch (item.getItemId()) {
                case R.id.navigation_choiceness:
                    mViewPagerContent.setCurrentItem(0,false);
                    return true;
                case R.id.navigation_discover:
                    mViewPagerContent.setCurrentItem(1,false);
                    return true;
                case R.id.navigation_service:
                    mViewPagerContent.setCurrentItem(2,false);
                    return true;

            }
            return false;
        }

    };

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        setFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initUI(){
        mViewPagerContent = (ViewPager) findViewById(R.id.vp_content);
        mViewPagerContent.addOnPageChangeListener(this);
        // TODO:(9)禁止ViewPager的滑动(就启用下面的代码)，可选项
//        mViewPagerContent.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // TODO:(10)去掉按钮的滑动切换效果，可选项
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(navigation);
    }

    private void setFragment(){
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(DocumentFragment.newInstance("what","why"));
        fragments.add(ApplicationFragment.newInstance("what","why"));
        fragments.add(SettingFragment.newInstance("what","why"));
        mViewPagerContent.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragments));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

    /////////////////////////////OnPageChangeListener////////////////////////////////////////////////////
    // TODO:(6) 实现ViewPager.OnPageChangeListener接口
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        // TODO:(7) 实现联动，滑动ViewPager时，对应的按钮为选中状态
        if (menuItem != null){
            menuItem.setChecked(false);
        }else {
            navigation.getMenu().getItem(0).setChecked(false);
        }
        menuItem = navigation.getMenu().getItem(position);
        menuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    // TODO:(8)创建ViewPager的Adapter
    // 1、如果Fragment过多，使用FragmentStatePagerAdapter，可以节省内存资源
    // 2、如果Fragment过少（少于5个）,FragmentPagerAdapter，可以增加性能
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            fragmentList = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
