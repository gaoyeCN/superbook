package com.fy.administrator.superbook.activity;

import java.sql.SQLException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.fy.administrator.superbook.R;
import com.fy.administrator.superbook.db.bean.Note;
import com.fy.administrator.superbook.db.dao.DatabaseHelper;
import com.fy.administrator.superbook.util.StringUtil;
import com.fy.administrator.superbook.util.ToastUtil;

public class EditActivity extends Activity implements OnClickListener{
	private TextView title_left,title_middle,title_right;
	private EditText etInfo;
	//private Button btSubmit;
	private DatabaseHelper mHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_book);
		
		initView();
		setData();
		setListeners();
	}

	private void setListeners() {
		title_left.setOnClickListener(this);
		title_middle.setOnClickListener(this);
		//btSubmit.setOnClickListener(this);
	}

	private void setData() {
		title_left.setText("返回");
		title_middle.setText("添加账本");
		title_right.setVisibility(View.GONE);
		
		mHelper = DatabaseHelper.getHelper(this);
	}

	private void initView() {
		title_left = (TextView) findViewById(R.id.title_left);
		title_middle = (TextView) findViewById(R.id.title_middle);
		title_right = (TextView) findViewById(R.id.title_right);
		
		etInfo = (EditText) findViewById(R.id.et_addBook_info);

		//btSubmit = (Button) findViewById(R.id.bt_addBook_submit);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		/*case R.id.bt_addBook_submit:
			startSubmit();
			break;*/
		case R.id.title_left:
			finish();
			break;
		}
	}

	private void startSubmit() {
		if(StringUtil.isEmpty(etInfo.getText().toString())){
			ToastUtil.showToast(this, "请输入信息");
			return;
		}
		
			Note note = new Note();
			note.setDate(System.currentTimeMillis());
			note.setInfo(etInfo.getText().toString().trim());
			try {
				mHelper.getDao(Note.class).create(note);
				finish();
			} catch (SQLException e) {
				e.printStackTrace();
				ToastUtil.showToast(this, "添加失敗");
			}
	}
}
