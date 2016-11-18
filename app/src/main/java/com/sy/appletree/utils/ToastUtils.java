package com.sy.appletree.utils;

import android.widget.Toast;

import com.sy.appletree.base.BaseApplication;

/**
 * Created by 郁智良
 * on 2016/11/17 0017.
 * des
 */

public class ToastUtils {
    public static void toast(String message) {
        Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
