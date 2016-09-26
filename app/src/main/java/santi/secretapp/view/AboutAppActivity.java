package santi.secretapp.view;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import santi.secretapp.R;


/**
 * Created by santi.mo on 16-5-26.
 */
public class AboutAppActivity extends Activity {

    private static final String TAG = "AboutAppActivity";
    private ActionBar mActionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutapp);
        Log.i(TAG, "onCreate(), Create AboutAppActivity ui!");

        mActionBar = getActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_actionbar_back);
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setTitle(R.string.contact_author);
        }
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
