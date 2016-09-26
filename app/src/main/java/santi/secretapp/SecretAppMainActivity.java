package santi.secretapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup;

import santi.secretapp.utils.ShareToApp;
import santi.secretapp.view.AboutAppActivity;
import santi.secretapp.view.DisabledAppsActivity;
import santi.secretapp.view.FeedbackActivity;
import santi.secretapp.view.SettingsActivity;

public class SecretAppMainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBar mActionBar;

    private static final String TAG = "SecretAppMainActivity : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_app_main);
        Log.i(TAG, "onCreate(), Create SecretAppMainActivity ui!");

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_actionbar_slidemenu);
        final DrawerArrowDrawable indicator = new DrawerArrowDrawable(this);
        indicator.setColor(Color.WHITE);
        mActionBar.setHomeAsUpIndicator(indicator);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (((ViewGroup) drawerView).getChildAt(1).getId() == R.id.leftSideBar) {
                    indicator.setProgress(slideOffset);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_secret_app_main, menu);

        //将menu的字体颜色设置为白色。由于没找到该属性，曲线救国
        int itemCount = menu.size();
        for(int itemNum=0; itemNum < itemCount; itemNum++){
            int itemNumindex = R.id.action_settings+ itemNum;
            MenuItem  item = menu.findItem(itemNumindex);
            String itemtitle = menu.getItem(itemNum).getTitle().toString();
            SpannableString s = new SpannableString(itemtitle);
            s.setSpan(new ForegroundColorSpan(Color.WHITE) , 0, s.length(), 0);
            item.setTitle(s);
        }

        return true;
    }

    @Override
    protected void onResume() {
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_actionbar_slidemenu);
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    mActionBar.setHomeAsUpIndicator(R.drawable.ic_actionbar_slidemenu);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                    mActionBar.setHomeAsUpIndicator(R.drawable.ic_actionbar_back);
                }
                break;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this,SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.contact_author:
                Intent contactsIntent = new Intent(this, AboutAppActivity.class);
                startActivity(contactsIntent);
                break;
            case R.id.share_app:
                ShareToApp sharetoapp = new ShareToApp();
                sharetoapp.shareMsg(this);
                break;
            case R.id.support_advise:
                Intent feedbackIntent = new Intent(this, FeedbackActivity.class);
                startActivity(feedbackIntent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.userInfo:
                Intent contactsIntent = new Intent(this, AboutAppActivity.class);
                startActivity(contactsIntent);
                break;
            case R.id.disabled_app:
                Intent hideAppIntent = new Intent(this,DisabledAppsActivity.class);
                startActivity(hideAppIntent);
                break;
            case R.id.eyes_safeguard:
                break;
            case R.id.voice_tts:
                break;
            case R.id.share:
                ShareToApp sharetoapp = new ShareToApp();
                sharetoapp.shareMsg(this);
                break;
            case R.id.settings:
                Intent settingsIntent = new Intent(this,SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            default:
                break;
        }
    }
}
