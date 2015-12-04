package com.fy.administrator.superbook.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class StringUtil {
	/**
	 * 判断字符串是否为空
	 * */
	public static boolean isEmpty(String str) {
		if (str == null || str.equals("") || "null".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isMobileNum(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(14[^4,\\D])|(18[0,2-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		System.out.println(m.matches() + "---");
		return m.matches();
	}

	/**
	 * 判断字符串是否为邮箱
	 * */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 判断字符串是否包含中文
	 * */
	public static final boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	private static final boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static String removeAllSpace(String str) {
		String tmpstr = str.replace(" ", "");
		tmpstr = tmpstr.replace("+86", "");
		tmpstr = tmpstr.replace("-", "");
		return tmpstr;
	}

	/**
	 * 判断当前是否有网
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo == null) {
				return false;
			}
			return (mNetworkInfo.getState() == NetworkInfo.State.CONNECTED);
		}
		return false;
	}
	
	
	public static boolean isNext(String timeone, String timetwo, String split) {
		timeone = timeone.replace(split, "");
		timetwo = timetwo.replace(split, "");
		if (Long.parseLong(timeone) - Long.parseLong(timetwo) > 0)
			return false;
		else
			return true;

	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

}
