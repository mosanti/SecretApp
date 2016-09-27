package santi.secretapp.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import santi.secretapp.R;
import santi.secretapp.utils.DisabledAppInfo;

/**
 * Created by santi.mo on 16-9-26.
 */
public class DisabledAppsActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "DisabledAppsActivity";

    private Context mContext;
    private ActionBar mActionBar;
    private Button mDisAppBtn;
    private LinearLayout mEmptyDisAppLayout;
    private LinearLayout mDisAppLayout;
    private GridView mDisAppGridView;
    private GridViewAdapter mGridViewAdapter;
    private List<DisabledAppInfo> mDisabledAppList = new ArrayList<DisabledAppInfo>();

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

        mEmptyDisAppLayout = (LinearLayout) findViewById(R.id.empty_disabled_app_Layout);
        mDisAppLayout = (LinearLayout) findViewById(R.id.disabled_app_Layout);
        mDisAppGridView = (GridView) findViewById(R.id.gridView);

        mContext = this;

        mGridViewAdapter = new GridViewAdapter();
        mDisAppGridView.setAdapter(mGridViewAdapter);
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
                Log.d(TAG,"onClick: start AppsListActivity");
                break;
            default:
                break;
        }
    }

    private class GridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mDisabledAppList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            View gridView;
            ViewHolder viewHolder;
            if(view != null && view instanceof LinearLayout) {
                gridView = view;
                viewHolder = (ViewHolder) view.getTag();
            } else {
                gridView = View.inflate(mContext,R.layout.grid_item,null);
                viewHolder = new ViewHolder();
                viewHolder.mDisabled_app_icon = (ImageView) view.findViewById(R.id.disabled_app_icon);
                viewHolder.mDisabled_app_name = (TextView) view.findViewById(R.id.disabled_app_mane);
                gridView.setTag(viewHolder);
            }
            if(position < mDisabledAppList.size()) {
                DisabledAppInfo appInfo = mDisabledAppList.get(position);
                viewHolder.mDisabled_app_icon.setImageDrawable(appInfo.getAppIcon());
                viewHolder.mDisabled_app_name.setText(appInfo.getAppName());
                Log.e(TAG, "GridViewAdapter-getView position:" + position + ", mDisabled_app_icon:" + appInfo.getAppIcon()
                        + ", mDisabled_app_name:" + appInfo.getAppName());
            } else {
                Log.e(TAG, "GridViewAdapter-getView Error position:" + position);
            }
            return gridView;
        }
    }

    private static class ViewHolder {
        ImageView mDisabled_app_icon;
        TextView mDisabled_app_name;
    }
}
