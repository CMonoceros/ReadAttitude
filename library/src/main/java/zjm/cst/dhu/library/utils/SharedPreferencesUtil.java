package zjm.cst.dhu.library.utils;

import android.content.Context;
import android.content.SharedPreferences;

import zjm.cst.dhu.library.Constant;
import zjm.cst.dhu.library.MyApplication;

/**
 * Created by zjm on 2017/5/9.
 */

public class SharedPreferencesUtil {

    public static SharedPreferences getSharedPreferences() {
        return MyApplication.getContext().getSharedPreferences(Constant.SHARED_READ_ATTITUDE, Context.MODE_PRIVATE);
    }

    public static boolean isFirstTime() {
        return getSharedPreferences().getBoolean(Constant.SHARED_FIRST_TIME, true);
    }

    public static void setFirstTime(boolean firstTime) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(Constant.SHARED_FIRST_TIME, firstTime);
        editor.apply();
    }
}
