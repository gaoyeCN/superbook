package com.fy.administrator.superbook.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	static Toast toast = null;
	
	public static void showToast(Context context,String mes){
		if(toast == null){
			toast = Toast.makeText(context, mes, Toast.LENGTH_SHORT);
		}else{
			toast.cancel();
			toast = Toast.makeText(context, mes, Toast.LENGTH_SHORT);
		}
		toast.show();
	}
}
