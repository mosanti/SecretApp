package santi.secretapp.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import santi.secretapp.R;

/**
 * Created by santi.mo on 2016/9/26.
 */
public class AppsListActivity extends Activity {

    // Tab tag enum,定义的创建两个TAG的名称
    private static final String TAB_TAG_PERSONAL_APP = "personal_app";
    private static final String TAB_TAG_SYSTEM_APP = "system_app";

    // View item filed,定义的每一个item包含的元素
    public static final String VIEW_ITEM_ICON = "package_icon";
    public static final String VIEW_ITEM_TEXT = "package_text";
    public static final String VIEW_ITEM_CHECKBOX = "package_checkbox";
    public static final String VIEW_ITEM_PACKAGE_NAME = "package_name";    // Only for save to selected apps list

    // For save tab widget
    private TabHost mTabHost = null;
    private Context mContext;
    private PackageManager mPM;

    // For personal app list
    private List<Map<String, Object>> mPersonalAppList = null;
    private AppListAdapter mPersonalAppAdapter = null;

    // For system app list
    private List<Map<String, Object>> mSystemAppList = null;
    private AppListAdapter mSystemAppAdapter = null;

    private static final String TAG = "AppsListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_apps_list_disabled_apps);

        Log.i(TAG, "onCreate(), Create AppsListActivity ui!");
        initTabHost();
        mContext = this;
        mPM = getPackageManager();

        // Load package in background 开启同步线程加载applist
        LoadPackageTask loadPackageTask = new LoadPackageTask(this);
        loadPackageTask.execute("");
    }

    private void initTabHost() {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabHost.addTab(mTabHost.newTabSpec(TAB_TAG_PERSONAL_APP)
                .setContent(R.id.personalAppLinerLayout).setIndicator(getString(R.string.personal_apps_title)));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_TAG_SYSTEM_APP)
                .setContent(R.id.systemAppLinerLayout).setIndicator(getString(R.string.system_apps_title)));
    }

    private class LoadPackageTask extends AsyncTask<String, Integer, Boolean> {

        private ProgressDialog mProgressDialog;
        private final Context mContext;

        public LoadPackageTask(Context context) {
            mContext = context;
            createProgressDialog();
        }

        private void createProgressDialog() {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setTitle(getString(R.string.progress_dialog_title));
            mProgressDialog.setMessage(getString(R.string.progress_dialog_message));
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
            Log.i(TAG, "createProgressDialog(), ProgressDialog shows");
        }

        @Override

        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            Log.i(TAG, "doInBackground(), Begin load and sort package list!");

            // Load and sort package list ,装载和分类apk
            loadPackageList();
            sortPackageList();

            return null;
        }

        private void loadPackageList() {
            mPersonalAppList = new ArrayList<Map<String, Object>>();
            mSystemAppList = new ArrayList<Map<String, Object>>();

            //获取包名,通过查询包含CATEGORY_LAUNCHER的标签获取包名
            Intent searchIntent = new Intent(Intent.ACTION_MAIN,null);
            searchIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            //ResolveInfo为包含系统中所有Activity信息的集合,通过查询包含CATEGORY_LAUNCHER的标签获取包名
            List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(searchIntent,0);
            for(ResolveInfo resolveInfo : resolveInfoList) {
                String pkgName = resolveInfo.activityInfo.packageName;
                if (pkgName.equals("santi.secretapp")){
                    continue;
                }

                //通过包名获取apk信息
                ApplicationInfo appInfo;
                try {
                    appInfo = getPackageManager().getApplicationInfo(pkgName,0);
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e(TAG, "can not found appInfo about package:" + pkgName);
                    continue;
                }
                if(appInfo == null){
                    Log.e(TAG, "appInfo is null about package:" + pkgName);
                    continue;
                }
            }
        }

        private void sortPackageList() {
        }
    }
}
