package com.sy.appletree.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/10/9.
 */

/**
 * 自定义应用
 */
public class BaseApplication extends Application {
    private static String spName = "app_tree_sp";//sp文件名字
    public static String sSesstion;
    private List<Activity> mActivities = new ArrayList<>();
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initClient();
    }

    public static String getSpName() {
        return spName;
    }

    //初始化OkHttpUtils
    private void initClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    public static Context getContext() {
        return mContext;
    }

    //    public void addActivity(BaseActivity activity) {
//        mActivities.add(activity);
//    }
//
//    public void close() {
//        for (Activity activity : mActivities) {
//            //Log.e("", "close:" + activity.getLocalClassName());
//            if (activity != null) {
//                activity.finish();
//            }
//
//        }
//    }
//    public void removeActivity(Activity activity) {
//        for (Activity ac : mActivities) {
//            //Log.e("", "close:" + activity.getLocalClassName());
//            if (ac.equals(activity)) {
//                mActivities.remove(ac);
//                break;
//            }
//        }
//    }

}
