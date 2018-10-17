package com.mcgs.srx.McgsSet;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcgs.srx.R;
import com.mcgs.srx.Util.JniUtil;

public class LcdSet extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener, OnClickListener {
    private TextView title;
    private ImageView button_back;
    private Intent intent;
    private CheckBoxPreference  mLcdSet;
    private EditTextPreference mEditTextbrightness;
    private Preference mUsbRestore;
    private static final String KEY_USB_RESTORE = "key_restore";
    private static final String KEY_LCD_SET = "key_lcd_set";
    private static final String KEY_LCD_SET_BRIGHTNESS = "key_lcd_brightness_set";

    private JniUtil mJniUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.mcgs_lcd_set);
        setCustomActionBar();
        button_back.setOnClickListener(this);

        mLcdSet = (CheckBoxPreference) findPreference(KEY_LCD_SET);
        mEditTextbrightness = (EditTextPreference)findPreference(KEY_LCD_SET_BRIGHTNESS);
        mEditTextbrightness.getEditText().setSingleLine();
        mEditTextbrightness.getEditText().setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(4)});
      //  mEditTextbrightness.getEditText().addTextChangedListener(prefWatcher);
        mUsbRestore = (Preference) findPreference(KEY_USB_RESTORE);
        mUsbRestore.setOnPreferenceClickListener(this);

        mJniUtil = JniUtil.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEditTextbrightness.setSummary(mEditTextbrightness.getSharedPreferences().getString(KEY_LCD_SET_BRIGHTNESS, getString(R.string.mcgs_lct_set_default)));
        mEditTextbrightness.setEnabled(mLcdSet.isChecked());

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
        if (key.equals(KEY_LCD_SET)) {
            if(mLcdSet.isChecked()){
                mEditTextbrightness.setEnabled(true);

            }else {
                mEditTextbrightness.setEnabled(false);
            }

            //TODO

        }else if(key.equals(KEY_LCD_SET_BRIGHTNESS)){
            Log.d("settings", " key:" + key);
            value = mEditTextbrightness.getSharedPreferences().getString(KEY_LCD_SET_BRIGHTNESS, getString(R.string.mcgs_lct_set_default));
            mEditTextbrightness.setSummary(value);

            //TODO
            int brightness = Integer.valueOf(value);
            mJniUtil.setBacklightbrightness(brightness);
        }


    }

    private void setCustomActionBar() {
        // TODO Auto-generated method stub
        View actionbar_title = LayoutInflater.from(this).inflate(
                R.layout.actionbar_layout_lcd, null);

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
            showLcdDialogRestore();
        }
        return false;
    }

    private void showLcdDialogRestore() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.btn_restore)
                .setMessage(R.string.btn_restore_message)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                boolean commit = mEditTextbrightness.getEditor().putString(KEY_LCD_SET_BRIGHTNESS, getString(R.string.mcgs_lct_set_default)).commit();
                                mEditTextbrightness.setSummary(getString(R.string.mcgs_lct_set_default));
                                mEditTextbrightness.setText(getString(R.string.mcgs_lct_set_default));
                                mEditTextbrightness.setEnabled(false);
                                mLcdSet.setChecked(false);
                            }
                        }).setNegativeButton(android.R.string.cancel, null)
                .create().show();
    }

}