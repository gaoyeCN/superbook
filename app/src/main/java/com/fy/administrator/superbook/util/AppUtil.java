package com.fy.administrator.superbook.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.List;

/**
 * 创建者：Administrator
 * 时间：2015/12/3  10:29
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class AppUtil {
    public static int getVersionCode(Context context){
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        }catch(PackageManager.NameNotFoundException e){
            return 0;
        }
    }


    public static boolean isActivityFront(Context context,String className) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = mActivityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            if (className.equals(tasksInfo.get(0).topActivity
                    .getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isActivityExist(Context context,String className){
        Intent intent = new Intent();
        intent.setClassName("com.fy.administrator.superbook", className);
        if (context.getPackageManager().resolveActivity(intent, 0) == null) {
            // 说明系统中不存在这个activity
            return false;
        }else {
            return true;
        }
    }
}
