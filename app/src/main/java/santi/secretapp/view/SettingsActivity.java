package santi.secretapp.view;


import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;

import santi.secretapp.R;

/**
 * Created by shengyang.mo on 16-9-22.
 */
public class SettingsActivity extends PreferenceActivity implements
        Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener{

    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setings_preference);

        mActionBar = getActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_actionbar_back);
        mActionBar.setDisplayShowHomeEnabled(false);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {

        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        return false;
    }
}
