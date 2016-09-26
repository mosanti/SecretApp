package santi.secretapp.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import santi.secretapp.R;

/**
 * Created by santi.mo on 2016/9/26.
 */
public class AppsListActivity extends Activity {

    private static final String TAG = "AppsListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Log.i(TAG, "onCreate(), Create AppsListActivity ui!");
    }
}
