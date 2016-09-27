package santi.secretapp.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by santi.mo on 16-9-27.
 */
public class DisabledAppInfo {

    private String packageName;
    private String appName;
    private Drawable appIcon;
    private boolean disabled;

    public DisabledAppInfo(String packageName, String appName, Drawable appIcon, boolean disabled) {
        this.packageName = packageName;
        this.appName = appName;
        this.appIcon = appIcon;
        this.disabled = disabled;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getAppName() {
        return appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
