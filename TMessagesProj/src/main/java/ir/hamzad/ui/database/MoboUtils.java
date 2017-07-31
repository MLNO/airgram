package org.telegram.ui.database;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings.Secure;

import org.telegram.ui.database.calendar.PersianCalendar;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.telegram.messenger.BuildConfig;

public class MoboUtils {
    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            return 1;
        }
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return BuildConfig.FLAVOR;
        }
    }

    public static String getAppName(Context context) {
        return getAppName(context.getPackageName());
    }

    public static String getAppName(String packageName) {
        return packageName.substring(packageName.lastIndexOf(".") + 1);
    }

    public static String getShamsiDate(Date date) {
        return new PersianCalendar(date).toString();
    }

    public static String intToString(int num, int digits) {
        char[] zeros = new char[digits];
        Arrays.fill(zeros, '0');
        return new DecimalFormat(String.valueOf(zeros)).format((long) num);
    }

    public static void gotoAppComments(Context context) {
        Intent intent = new Intent("android.intent.action.EDIT");
        intent.setData(Uri.parse("bazaar://details?id=" + context.getPackageName()));
        context.startActivity(intent);
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getAndroidId(Context context) {
        return Secure.getString(context.getContentResolver(), "android_id");
    }

    public static int generateRandomNumber() {
        return new Random(System.currentTimeMillis()).nextInt();
    }

    public static synchronized File getHanistaDirectory() {
        File dir;
        synchronized (MoboUtils.class) {
            dir = new File(getExternalStorageDirectory() + File.separator + "Hanista" + File.separator);
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
        return dir;
    }

    public static synchronized File getExternalStorageDirectory() {
        File externalStorageDirectory;
        synchronized (MoboUtils.class) {
            externalStorageDirectory = Environment.getExternalStorageDirectory();
            if (!(externalStorageDirectory == null || externalStorageDirectory.exists() || externalStorageDirectory.mkdirs())) {
                externalStorageDirectory = null;
            }
        }
        return externalStorageDirectory;
    }

    public static boolean isEmptyString(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }


}
