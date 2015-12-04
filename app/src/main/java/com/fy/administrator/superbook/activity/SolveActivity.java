package com.fy.administrator.superbook.activity;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fy.administrator.superbook.R;
import com.fy.administrator.superbook.db.bean.ManyBook;
import com.fy.administrator.superbook.db.dao.DatabaseHelper;
import com.fy.administrator.superbook.util.StringUtil;
import com.fy.administrator.superbook.util.ToastUtil;

public class SolveActivity extends Activity implements OnClickListener{
	private int positions[];
	private TextView title_left,title_middle,title_right,tv_resulte;
	private Button bt_solve;
	private DatabaseHelper mHelper;
	private List<ManyBook> books;
	private EditText et_people;
	private List<Integer> solvedList;
	private Builder mBuilder;
	//CommonDialog dialog;
	private String TAG = "com.fy.accountbook.activity.SolveActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sove);
		
		initView();
		setData();
		setListeners();
	}
	
	private void hideInput(){
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); 
	}

	private void setListeners() {
		title_left.setOnClickListener(this);
		bt_solve.setOnClickListener(this);
	}

	private void setData() {
		positions = getIntent().getIntArrayExtra("positions");
		mHelper = DatabaseHelper.getHelper(this);
		
		title_middle.setText("均摊");
		title_right.setVisibility(View.GONE);
		
		solvedList = new ArrayList<Integer>();
		
		try {
			books = mHelper.getManyBookDao().queryForAll();
		} catch (SQLException e) {
			books = new ArrayList<ManyBook>();
			e.printStackTrace();
		}
	}

	private void initView() {
		title_left = (TextView) findViewById(R.id.title_left);
		title_middle = (TextView) findViewById(R.id.title_middle);
		title_right = (TextView) findViewById(R.id.title_right);
		
		bt_solve = (Button) findViewById(R.id.bt_solve_submit);
		tv_resulte = (TextView) findViewById(R.id.tv_solve_resulte);
		
		et_people = (EditText) findViewById(R.id.et_solve_people);
		
		mBuilder = new Builder(this,R.style.CustomDialog);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.title_left:
			finish();
			break;
		case R.id.bt_solve_submit:
			if("均摊".equals(bt_solve.getText())){
				judgeSolve();
			}else if("已均摊".equals(bt_solve.getText())){
				submitSolve();
			}
			break;
		}
	}

	private void startSolve() {
		int people = 1;
		try {
			people = Integer.valueOf(et_people.getText().toString().trim());
			int money = 0;
			ManyBook book = null;
			for(int i = 0;i<books.size();i++){
				if(positions[i] != 0){
					book = books.get(i);
					money += book.getMoney();
				}
			}
			DecimalFormat decimalFormat=new DecimalFormat(".00");
			tv_resulte.setText("总计:" + money + "\n人数:" + people + "\n人均:" + decimalFormat.format(((float)money / (float)people)));
			bt_solve.setText("已均摊");
		} catch (Exception e) {
		}
	}

	private void judgeSolve() {
		if(StringUtil.isEmpty(et_people.getText().toString().trim())){
			ToastUtil.showToast(this, "请输入钱数");
			return;
		}
		if(Integer.valueOf(et_people.getText().toString().trim()) == 0){
			ToastUtil.showToast(this, "请输入正确的钱数");
			return;
		}
		ManyBook book = null;
		for(int i = 0;i<books.size();i++){
			if(positions[i] != 0){
				book = books.get(i);
				if(book.isSolve()){
					solvedList.add(i);
				}
			}
		}
		if(solvedList.size() == 0){
			startSolve();
		}else{
			createDialog();
		}
	}
	
	private void createDialog() {
		hideInput();
		/*mBuilder.setMessage("\n选择的包含已经均摊过的，是否继续？\n")
		.setPositiveButton("继续", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				startSolve();
				dialog.dismiss();
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		})
		.setNeutralButton("去除均摊过的并继续", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				for(int i = 0;i<books.size();i++){
					if(solvedList.contains(i)){
						books.remove(i);
						i--;
					}
				}
				startSolve();
				dialog.dismiss();
			}
		});
		Dialog dialog = mBuilder.create();
		dialog.show();*/
		mBuilder.setView(LayoutInflater.from(this).inflate(R.layout.dialog_juntan, null));
		final Dialog dialog = mBuilder.create();
		//dialog = new CommonDialog(this, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, R.layout.dialog_juntan, R.style.Theme_dialog, Gravity.CENTER);
		//dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
		//dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		TextView tvJunTan = (TextView) dialog.findViewById(R.id.tv_juntan);
		Button btLeft = (Button) dialog.findViewById(R.id.bt_left);
		Button btMiddle = (Button) dialog.findViewById(R.id.bt_middle);
		Button btRight = (Button) dialog.findViewById(R.id.bt_right);
		tvJunTan.setText("选择的包含已经均摊过的，是否继续？");
		btLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		btMiddle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "已均摊List：" + solvedList.toString());
				Log.i(TAG, "去除之前：" + books.toString());
				for(int i = 0;i<books.size();i++){
					if(solvedList.contains(i)){
						books.remove(i);
						solvedList.remove((Integer)i);
						i--;
					}
				}
				Log.i(TAG, "去除之后：" + books.toString());
				startSolve();
				dialog.dismiss();
			}
		});
		btRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startSolve();
				dialog.dismiss();
			}
		});
	}

	private void submitSolve(){
		try {
			List<ManyBook> books = mHelper.getManyBookDao().queryForAll();
			for (int i = 0; i < books.size(); i++) {
				if(positions[i] != 0){
					ManyBook book = books.get(i);
					book.setSolve(true);
					mHelper.getManyBookDao().update(book);
				}
			}
			finish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
