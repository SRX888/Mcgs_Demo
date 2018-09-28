package com.mcgs.srx.McgsSet;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcgs.srx.R;
import com.mcgs.srx.Util.JniUtil;

public class BeeSet extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener, View.OnClickListener {
    private TextView title;
    private ImageView button_back;
    private Intent intent;
    private EditTextPreference mEditTextBee;
    private Preference mUsbRestore;
    private static final String KEY_USB_RESTORE = "key_restore";
    private static final String KEY_BEE_SET_TIME = "key_lcd_bee_set";

    private JniUtil mJniUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.mcgs_bee_set);
        setCustomActionBar();
        button_back.setOnClickListener(this);

        mEditTextBee = (EditTextPreference)findPreference(KEY_BEE_SET_TIME);
        mEditTextBee.getEditText().setSingleLine();
        mEditTextBee.getEditText().setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(3)});
        //  mEditTextbrightness.getEditText().addTextChangedListener(prefWatcher);
        mUsbRestore = (Preference) findPreference(KEY_USB_RESTORE);
        mUsbRestore.setOnPreferenceClickListener(this);
        mJniUtil = JniUtil.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEditTextBee.setSummary(mEditTextBee.getSharedPreferences().getString(KEY_BEE_SET_TIME, getString(R.string.mcgs_bee_set_default)));

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String value = null;
     if(key.equals(KEY_BEE_SET_TIME)){
            Log.d("settings", " key:" + key);
            value = mEditTextBee.getSharedPreferences().getString(KEY_BEE_SET_TIME, getString(R.string.mcgs_bee_set_default));
            mEditTextBee.setSummary(value);

            //TODO
         int time = Integer.valueOf(value);
         mJniUtil.setBeeTime(time);
        }


    }

    private void setCustomActionBar() {
        // TODO Auto-generated method stub
        View actionbar_title = LayoutInflater.from(this).inflate(
                R.layout.actionbar_layout_bee, null);

        title = (TextView) actionbar_title.findViewById(R.id.title);

        button_back = (ImageView) actionbar_title
                .findViewById(R.id.button_back);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        getActionBar().setCustomView(actionbar_title, params);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);

    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.button_back:
                finish();
            default:
                break;
        }
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals(KEY_USB_RESTORE)) {
            showBeeDialogRestore();
        }
        return false;
    }

    private void showBeeDialogRestore() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.btn_restore)
                .setMessage(R.string.btn_restore_message)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                mEditTextBee.getEditor().putString(KEY_BEE_SET_TIME, getString(R.string.mcgs_bee_set_default)).commit();
                                mEditTextBee.setSummary(getString(R.string.mcgs_bee_set_default));
                                mEditTextBee.setText(getString(R.string.mcgs_bee_set_default));

                            }
                        }).setNegativeButton(android.R.string.cancel, null)
                .create().show();
    }

}
