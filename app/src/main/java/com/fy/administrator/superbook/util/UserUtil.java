package com.fy.administrator.superbook.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
/**
 * 
 * @author 
 *
 */
public class UserUtil {

	public static String getVersion(Context context){
		PackageManager pm = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = pm.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}



	//保存加号位置
	public static void saveAddPosition(Context context,float[] position) {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putFloat("add_position_x",position[0]);
		editor.putFloat("add_position_y",position[1]);
		editor.commit();
	}

	
	public static float[] getAddPosition(Context context) {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				Context.MODE_PRIVATE);
		return new float[]{sp.getFloat("add_position_x",-1f),sp.getFloat("add_position_y",-1f)};
	}
}
