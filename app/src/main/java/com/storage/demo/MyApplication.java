package com.storage.demo;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjy on 2017/4/18.
 */

public class MyApplication extends Application {
    private static MyApplication MyApplication;

    private List<Activity> listActOfALL = new ArrayList<Activity>();//退出app使用
    private List<Activity> listActOfSome = new ArrayList<Activity>();//关闭多个使用

    public static MyApplication getInstance() {
        return MyApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication = this;
    }

    /**
     * application管理所有activity,暂不用广播
     */

    public void addActOfAll(Activity activity) {
        listActOfALL.add(activity);
        Log.d("SJY", "Current Acitvity Size :" + getCurrentActivitySize());
    }

    public void removeOneActOfAll(Activity activity) {
        listActOfALL.remove(activity);
        activity.finish();
        Log.d("SJY", "Current Acitvity Size :" + getCurrentActivitySize());
    }

    /**
     * 退出程序
     */
    public void exit() {
        for (Activity activity : listActOfALL) {
            activity.finish();
        }
    }

    /**
     * act的数量
     *
     * @return
     */
    public int getCurrentActivitySize() {
        return listActOfALL.size();
    }

    /**
     * 管理多个界面使用,不同于 管理所有界面
     */

    public void addOneActOfSome(Activity activity) {
        listActOfSome.add(activity);
    }

    public void removeOneActOfSome(Activity activity) {
        listActOfSome.remove(activity);
        activity.finish();
    }

    public void closeAllActOFSome() {
        for (Activity activity : listActOfSome) {
            activity.finish();
        }
        //清空数据
        listActOfSome.clear();
    }
}


