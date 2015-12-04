package com.fy.administrator.superbook.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.fy.administrator.superbook.R;
import com.fy.administrator.superbook.util.ScreenUtil;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

/**
 * 创建者：Administrator
 * 时间：2015/12/1  16:35
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public abstract  class BaseActivity extends FragmentActivity {
    protected RelativeLayout rl_title;
    protected abstract void initView();
    protected abstract void setData();
    protected abstract void setListeners();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);          //统计时长
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
    }
}
