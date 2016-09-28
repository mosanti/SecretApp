package santi.secretapp.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import santi.secretapp.R;
import santi.secretapp.utils.SwitchButton;

/**
 * Created by santi.mo on 2016/9/26.
 */
public class AppsListActivity extends Activity {

    // Tab tag enum,定义的创建两个TAG的名称
    private static final String TAB_TAG_PERSONAL_APP = "personal_app";
    private static final String TAB_TAG_SYSTEM_APP = "system_app";

    // View item filed,定义的每一个item包含的元素
    public static final String VIEW_ITEM_ICON = "package_icon";
    public static final String VIEW_ITEM_TEXT = "package_text";     //应用的名字
    public static final String VIEW_ITEM_CHECKBOX = "package_checkbox";
    public static final String VIEW_ITEM_PACKAGE_NAME = "package_name";    // Only for save to selected apps list

    // These two array should be consistent
    private static final String[] VIEW_TEXT_ARRAY = new String[] { VIEW_ITEM_ICON, VIEW_ITEM_TEXT, VIEW_ITEM_CHECKBOX };
    private static final int[] VIEW_RES_ID_ARRAY = new int[] { R.id.package_icon, R.id.package_text, R.id.package_switchbtn };

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

    /**
     * Do the UI init operation after load and sort package list load and sort completed
     */
    private void initUiComponents() {
        Log.i(TAG, "initUiComponents()");

        // Initialize personal app list view
        ListView mPersonalAppListView = (ListView) findViewById(R.id.list_personal_app);
        mPersonalAppAdapter = new AppListAdapter();
        mPersonalAppListView.setAdapter(mPersonalAppAdapter);
        mPersonalAppListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        // Initialize system app list view
        ListView mSystemAppListView = (ListView) findViewById(R.id.list_system_app);
        mSystemAppAdapter = new AppListAdapter();
        mSystemAppListView.setAdapter(mSystemAppAdapter);
        mSystemAppListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
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
            Log.i(TAG, "onPostExecute(), Load and sort package list complete!");
            // Do the operation after load and sort package list completed
            //doInBackground（）完成加载/排序applist后，添加到View上
            initUiComponents();
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

            if(mProgressDialog != null){
                mProgressDialog.dismiss();
            }

            return true;
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

                //根据包名获取包的信息
                PackageInfo packageInfo = null;
                try {
                    packageInfo = getPackageManager().getPackageInfo(pkgName,0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if(packageInfo != null) {
                    //Add this package to package list
                    Map<String,Object> packageItem = new HashMap<String,Object>();

                    //具体获取详细信息
                    Drawable appIcon = getPackageManager().getApplicationIcon(packageInfo.applicationInfo);
                    packageItem.put(VIEW_ITEM_ICON,appIcon);

                    String appName = getPackageManager().getApplicationLabel(packageInfo.applicationInfo).toString();
                    packageItem.put(VIEW_ITEM_TEXT,appName);
                    packageItem.put(VIEW_ITEM_PACKAGE_NAME,packageInfo.packageName);

                    // Add if app is disabled.
                    // Add if app is disabled.
                    boolean isDisabled = !appInfo.enabled;
                    packageItem.put(VIEW_ITEM_CHECKBOX, isDisabled);

                    //is system app
                    boolean isSystemApp = false;
                    if (((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)
                            || ((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0)) {
                        isSystemApp = true;
                    }

                    if(isSystemApp) {
                        mSystemAppList.add(packageItem);
                    } else {
                        mPersonalAppList.add(packageItem);
                    }
                }
            }
        }

        // Sort package list in alphabetical order.
        private void sortPackageList() {
            PackageItemComparator pkgNameComparator = new PackageItemComparator();

            // Sort personal app list
            if(mPersonalAppList != null) {
                Collections.sort(mPersonalAppList,pkgNameComparator);
            }

            // Sort system app list
            if (mSystemAppList != null) {
                Collections.sort(mSystemAppList, pkgNameComparator);
            }

            Log.i(TAG, "sortPackageList(), PersonalAppList=" + mPersonalAppList);
            Log.i(TAG, "sortPackageList(), SystemAppList=" + mSystemAppList);
        }
    }


    /**
     * This class is used for sorting package list.
     */
    private class PackageItemComparator implements Comparator<Map<String,Object>>{

        private final String mKey;

        public PackageItemComparator() {
            mKey = AppsListActivity.VIEW_ITEM_TEXT;
        }

        //比较两个应用名字的字母顺序
        @Override
        public int compare(Map<String, Object> packageItem1, Map<String, Object> packageItem2) {
            String pkgName1 = (String)packageItem1.get(mKey);
            String pkgName2 = (String)packageItem2.get(mKey);
            return pkgName1.compareToIgnoreCase(pkgName2);
        }
    }

    private class AppListAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return isPersonalAppTabSelected() ? mPersonalAppList.size() : mSystemAppList.size();
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
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view;
            ViewHolder viewHolder;

            if(convertView != null && convertView instanceof LinearLayout) {
                view = convertView;
                viewHolder = (ViewHolder)view.getTag();
            } else {
                view = View.inflate(mContext, R.layout.package_list_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.app_icon = (ImageView) view.findViewById(R.id.package_icon);
                viewHolder.app_name = (TextView) view.findViewById(R.id.package_text);
                viewHolder.app_summary = (TextView) view.findViewById(R.id.disabled_app_summary);
                viewHolder.disabled = (SwitchButton) view.findViewById(R.id.package_switchbtn);
                view.setTag(viewHolder);
            }
            if(isPersonalAppTabSelected()){
                if(position < mPersonalAppList.size()) {
                    Map<String, Object> item = mPersonalAppList.get(position);

                    viewHolder.app_icon.setImageDrawable((Drawable) item.get(VIEW_ITEM_ICON));
                    viewHolder.app_name.setText((String) item.get(VIEW_ITEM_TEXT));
                    boolean isDisabled = (Boolean)item.get(VIEW_ITEM_CHECKBOX);
                    if(isDisabled) {
                        viewHolder.app_summary.setText(R.string.app_disable_flag_text);
                        viewHolder.disabled.setChecked(true);
                    } else {
                        viewHolder.app_summary.setText(null);
                        viewHolder.disabled.setChecked(false);
                    }
                } else {
                    Log.e(TAG, "AppListAdapter-getView Error position (PersonalAppList):" + position);
                }
            } else {
                if(position < mSystemAppList.size()) {
                    Map<String, Object> item = mSystemAppList.get(position);

                    viewHolder.app_icon.setImageDrawable((Drawable) item.get(VIEW_ITEM_ICON));
                    viewHolder.app_name.setText((String) item.get(VIEW_ITEM_TEXT));
                    boolean isDisabled = (Boolean)item.get(VIEW_ITEM_CHECKBOX);
                    if(isDisabled) {
                        viewHolder.app_summary.setText(R.string.app_disable_flag_text);
                        viewHolder.disabled.setChecked(true);
                    } else {
                        viewHolder.app_summary.setText(null);
                        viewHolder.disabled.setChecked(false);
                    }
                } else {
                    Log.e(TAG, "AppListAdapter-getView Error position (SystemAppList):" + position);
                }
            }

            return view;
        }
    }

    private class ViewHolder {
        ImageView app_icon;
        TextView app_name;
        TextView app_summary;
        SwitchButton disabled;
    }


    /**
    * is personal tab was selected
     */
    private boolean isPersonalAppTabSelected() {
        return (mTabHost.getCurrentTabTag() == TAB_TAG_PERSONAL_APP);
    }


}
