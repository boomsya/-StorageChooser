package com.boomsya.storagechooser.utils;


import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

public class DiskUtil {

    public static final String IN_KB = "KB";
    public static final String IN_MB = "MB";
    public static final String IN_GB = "GB";
    public static final String SC_PREFERENCE_KEY = "storage_chooser_path";
    public static java.lang.String SC_CHOOSER_FLAG = "storage_chooser_type";

    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static void saveChooserPathPreference(SharedPreferences sharedPreferences, String path) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SC_PREFERENCE_KEY, path);
            editor.apply();
        } catch (NullPointerException e) {
            Log.e("StorageChooser", "No sharedPreference was supplied. Supply sharedPreferencesObject via withPreference() or disable saving with actionSave(false)");
        }
    }

    public static boolean isLollipopAndAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
