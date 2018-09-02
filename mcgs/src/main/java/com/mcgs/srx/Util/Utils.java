package com.mcgs.srx.Util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.logging.Handler;

/**
 * Created by ruixiang.shen  on 2018/08/30.
 */

public class Utils {
    private static final String TAG = "utils";
    private static final boolean D = true;

    public static final String KEY_APP_POSITION = "KEY_APP_POSITION";

    private static Utils mInstance;

    private Context mContext;
    private Resources res;

    private SharedPreferences mPreferences = null;
    private SharedPreferences.Editor mEditor = null;


    public static Utils getInstance() {
        if (mInstance == null) {
            mInstance = new Utils();
        }
        return mInstance;
    }

    public void init(Context context) {
        mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreferences.edit();
        res = context.getResources();
    }

    public String[] getStringArray(int id) {
        return res.getStringArray(id);
    }

    public boolean getBooleanPreference(String key, boolean value) {
        if (D) Log.i(TAG, "getBooleanPreference: key: " + key + " value :" + value);
        return mPreferences.getBoolean(key, value);
    }

    public void setBooleanPreference(String key, boolean value){
       if(D) Log.i(TAG, "setBooleanPreference: key: " + key + " value :" + value);
        mEditor.putBoolean(key,value);
        mEditor.commit();

    }

    public String getStringPreference(String key, String value) {
        if (D) Log.i(TAG, "getStringPreference: key: " + key + " value :" + value);
        return mPreferences.getString(key, value);
    }

    public int getIntPreference(String key, int value) {
        if (D) Log.i(TAG, "getIntPreference: key: " + key + " value :" + value);
        return mPreferences.getInt(key, value);
    }

    public void setIntPreference(String key, int value){
        if (D) Log.i(TAG, "setIntPreference: key: " + key + " value :" + value);
        mEditor.putInt(key,value);
        mEditor.commit();

    }

    public void setStringPreference(String key, String value) {
        if (D) Log.i(TAG, "setStringPreference: key: " + key + " value :" + value);
        mEditor.putString(key, value);
        mEditor.commit();

    }

    public View getFragmentView(Context context, int layoutid) {
        View view = LayoutInflater.from(context).inflate(layoutid, null);
        return view;
    }


}
