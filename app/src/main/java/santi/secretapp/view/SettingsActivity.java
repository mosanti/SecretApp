package santi.secretapp.view;


import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import santi.secretapp.R;

/**
 * Created by santi.mo on 16-9-22.
 */
public class SettingsActivity extends PreferenceActivity implements
        Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener{

    private static final String TAG = "SettingsActivity";
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setings_preference);
        Log.i(TAG, "onCreate(), Create SettingsActivity ui!");

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

    @Override
    protected void onResume() {
        super.onResume();
        //TokenUtils.startSelfLock(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
