package com.mcgs.srx.Util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by ruixiang.shen  on 2018/08/30.
 */

public class Utils {
    private static final String TAG = "utils";
    private static final boolean D = true;

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

    public String[] getStringArray(int id){
        return res.getStringArray(id);
    }

    public boolean getBooleanPreference(String key, boolean value){
        if(D) Log.i(TAG, "getBooleanPreference: key: "+key+" value :"+value);
        return mPreferences.getBoolean(key,value);
    }

    public String getStringPreference(String key, String value){
        if(D) Log.i(TAG, "getStringPreference: key: "+key+" value :"+value);
        return mPreferences.getString(key,value);
    }
    public int getIntPreference(String key, int value){
        if(D) Log.i(TAG, "getIntPreference: key: "+key+" value :"+value);
        return mPreferences.getInt(key,value);
    }

    public void setStringPreference(String key, String value){
        if(D) Log.i(TAG, "setStringPreference: key: "+key+" value :"+value);
        mEditor.putString(key,value);
        mEditor.commit();

    }

}
