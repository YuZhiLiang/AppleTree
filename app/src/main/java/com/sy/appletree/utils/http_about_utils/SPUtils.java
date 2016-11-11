package com.sy.appletree.utils.http_about_utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.sy.appletree.base.BaseApplication;

/**
 * Created by 郁智良
 * on 2016/11/10 0010.
 * des
 */

public class SPUtils {
    public static String getSession() {
        SharedPreferences sharedPreferences = BaseApplication.getContext()
                .getSharedPreferences(BaseApplication.getSpName(), Context.MODE_PRIVATE);
        String session= sharedPreferences.getString("session", "sessionError");
        return session;
    }

    public static void putSession(String session) {
        SharedPreferences sharedPreferences = BaseApplication.getContext()
                .getSharedPreferences(BaseApplication.getSpName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("session", session);
        Log.e("session" ,session);
        editor.commit();//提交修改

    }
}
