package com.mcgs.srx.McgsSet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;

import com.mcgs.srx.R;
import com.mcgs.srx.Util.JniUtil;
import com.mcgs.srx.Util.Utils;

public class UsbSetting extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener, OnClickListener {

    private TextView title;
    private ImageView button_back;
    private Intent intent;
    private ListPreference mUsbmode;
    private Preference mUsbRestore;
    private static final String KEY_USB_RESTORE = "key_restore";
    private static final String KEY_USB_SET = "key_usb_mode";
    private Utils mUtils;

    private JniUtil mJniUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.mcgs_usb_set);
        setCustomActionBar();
        button_back.setOnClickListener(this);

        mUsbmode = (ListPreference) findPreference(KEY_USB_SET);
        mUsbRestore = (Preference) findPreference(KEY_USB_RESTORE);
        mUsbRestore.setOnPreferenceClickListener(this);

        mJniUtil = JniUtil.getInstance();
        mUtils = Utils.getInstance();
        mUtils.init(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        String rotation_value = mUsbmode.getSharedPreferences().getString(KEY_USB_SET, "0");
        mUsbmode.setValue(rotation_value);
        mUsbmode.setSummary(mUsbmode.getEntry());
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
        if (key.equals(KEY_USB_SET)) {
            value = mUsbmode.getValue();
            mUsbmode.setSummary(mUsbmode.getEntry());
            int mode = Integer.valueOf(value);
            //TODO
            int ret = mJniUtil.setUsbMode(mode);
            mUtils.showErrorDialog(ret);
        }
    }

    private void setCustomActionBar() {
        // TODO Auto-generated method stub
        View actionbar_title = LayoutInflater.from(this).inflate(
                R.layout.actionbar_layout_usb, null);

        title = (TextView) actionbar_title.findViewById(R.id.title);

        button_back = (ImageView) actionbar_title
                .findViewById(R.id.button_back);

        LayoutParams params = new ActionBar.LayoutParams(
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
            showUsbDialogRestore();
        }
        return false;
    }

    private void showUsbDialogRestore() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.btn_restore)
                .setMessage(R.string.btn_restore_message)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                mUsbmode.setValue(getString(R.string.mcgs_usb_mode_default));
                                mUsbmode.setSummary(mUsbmode.getEntry());

                            }
                        }).setNegativeButton(android.R.string.cancel, null)
                .create().show();
    }

}
