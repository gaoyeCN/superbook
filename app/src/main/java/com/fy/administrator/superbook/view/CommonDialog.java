package com.fy.administrator.superbook.view;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressWarnings("unused")
public class CommonDialog extends Dialog {

	private static int default_width = 160; // 默认宽度

	private static int default_height = 120;// 默认高度

	public static Object content;

	DialogItemClickListener itemListener;
	DialogConfirmClickListener confirmListener;

	public static Object getContent() {
		return content;
	}

	public static void setContent(Object content) {
		CommonDialog.content = content;
	}

	public CommonDialog(Context context, int width, int height, int layout,
			int style, int gravity) {
		super(context, style);
		// set content
		setContentView(layout);

		// set window params
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		// set width,height by density and gravity
		float density = getDensity(context);
		// params.width = (int) (width * density);
		// params.height = (int) (height * density);
		params.height = height;
		params.width = width;
		params.gravity = gravity;
		window.setAttributes(params);
	}

	public CommonDialog(Context context, int width, int height, View layout,
			int style) {
		super(context, style);
		setContentView(layout);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
	}

	private float getDensity(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}

	public void setDialogItemClickListener(DialogItemClickListener itemListener) {
		this.itemListener = itemListener;
	}

	public void setDialogConfirmClickListener(
			DialogConfirmClickListener confirmListener) {
		this.confirmListener = confirmListener;
	}

	public interface DialogItemClickListener {
		public void dialogItemClickListener(int position);
	}

	public interface DialogConfirmClickListener {
		public void dialogConfirmClickListener();
	}
}