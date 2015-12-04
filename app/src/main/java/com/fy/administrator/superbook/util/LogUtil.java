package com.fy.administrator.superbook.util;

import android.util.Log;

import com.fy.administrator.superbook.values.UserValue;

/**
 * 创建者：Administrator
 * 时间：2015/12/4  13:37
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class LogUtil{
    private static final String TAG = "com.fy.administrator.superbook.util.LogUtil";
    public static void i(String message){
        if (UserValue.isDebug){
            Log.i(TAG,message);
        }
    }
}
