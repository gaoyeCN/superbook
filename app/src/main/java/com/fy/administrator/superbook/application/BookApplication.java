package com.fy.administrator.superbook.application;

import android.app.Application;

import com.fy.administrator.superbook.values.UserValue;

import cn.jpush.android.api.JPushInterface;

public class BookApplication extends Application {
	private static BookApplication instance;
	@Override
	public void onCreate() {
		super.onCreate();
		JPushInterface.setDebugMode(UserValue.isDebug);
		JPushInterface.init(this);
		JPushInterface.setLatestNotificationNumber(this,5);
	}
	
	
	public BookApplication(){
	}
	
	public static synchronized BookApplication getInstance(){
		if(instance == null){
			instance = new BookApplication();
		}
		return instance;
	}
}
