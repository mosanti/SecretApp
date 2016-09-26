package santi.secretapp.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import santi.secretapp.R;

/**
 * Created by user on 16-9-26.
 */
public class DisabledAppsActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "DisabledAppsActivity";

    private ActionBar mActionBar;
    private Button mDisAppBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disabled_app);
        Log.i(TAG, "onCreate(), Create DisabledAppsActivity ui!");

        mActionBar = getActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_actionbar_back);
            mActionBar.setDisplayShowHomeEnabled(false);
        }

        mDisAppBtn = (Button) findViewById(R.id.btn_start_disabled_app);
        mDisAppBtn.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_start_disabled_app:
                Intent intent = new Intent(this,AppsListActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
