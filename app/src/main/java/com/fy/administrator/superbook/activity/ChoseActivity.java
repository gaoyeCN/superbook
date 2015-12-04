package com.fy.administrator.superbook.activity;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fy.administrator.superbook.R;
import com.fy.administrator.superbook.adapter.ManyBookChoseAdapter;
import com.fy.administrator.superbook.adapter.NoteChoseAdapter;
import com.fy.administrator.superbook.adapter.SingleBookChoseAdapter;
import com.fy.administrator.superbook.comparator.ManyBookComparator;
import com.fy.administrator.superbook.comparator.NoteComparator;
import com.fy.administrator.superbook.comparator.SingleBookComparator;
import com.fy.administrator.superbook.values.Constants;
import com.fy.administrator.superbook.db.bean.ManyBook;
import com.fy.administrator.superbook.db.bean.Note;
import com.fy.administrator.superbook.db.bean.SingleBook;
import com.fy.administrator.superbook.db.dao.DatabaseHelper;
import com.fy.administrator.superbook.util.ToastUtil;

public class ChoseActivity extends BaseActivity implements OnClickListener{
	private ListView mLv;
	private Button mBt;
	private TextView title_left,title_middle,title_right;
	private int type;
	private BaseAdapter adapter;
	private int poistions[];
	private List<SingleBook> singleBooks;
	private List<ManyBook> manyBooks;
	private List<Note> notes;
	private DatabaseHelper mHelper;
	private SingleBook choseSingleBook;
	private ManyBook choseManyBook;
	private Note note;
	private Builder mBuilder;
	private boolean selectAll;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chose);
		
		initView();
		setData();
		setListeners();
	}

	protected void setListeners() {
		mLv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(poistions[position] == 0){
					poistions[position] = 1;
				}else{
					poistions[position] = 0;
				}
				int size = 0;
				for (int i = 0;i<poistions.length;i++){
					if (poistions[i] == 0)
						break;;
					size++;
				}
				if (size == notes.size()){
					selectAll = true;
					title_right.setText("取消选择");
				}
				adapter.notifyDataSetChanged();
			}
		});
		
		mBt.setOnClickListener(this);
		title_left.setOnClickListener(this);
		title_right.setOnClickListener(this);
	}

	protected void setData() {
		title_left.setText("返回");
		title_middle.setText("删除记事本");
		title_right.setText("全选");
		title_right.setVisibility(View.VISIBLE);
		mBt.setText("删除");
		
		mHelper = DatabaseHelper.getHelper(this);
		type = getIntent().getIntExtra(Constants.TYPE, 0);
		int position = 0;
		try {
			switch(type){
			case Constants.TYPE_SINGLEBOOK:
				choseSingleBook = (SingleBook) getIntent().getSerializableExtra("choseBook");
				singleBooks = mHelper.getSingleBookDao().queryForAll();
				Collections.sort(singleBooks, new SingleBookComparator());
				poistions = new int[singleBooks.size()];
				adapter = new SingleBookChoseAdapter(this, singleBooks,poistions,choseSingleBook);
				position = ((SingleBookChoseAdapter)adapter).getChosed(choseSingleBook);
				break;
			case Constants.TYPE_MANYBOOK:
				title_right.setVisibility(View.VISIBLE);
				choseManyBook = (ManyBook) getIntent().getSerializableExtra("choseBook");
				manyBooks = mHelper.getManyBookDao().queryForAll();
				Collections.sort(manyBooks, new ManyBookComparator());
				poistions = new int[manyBooks.size()];
				adapter = new ManyBookChoseAdapter(this, manyBooks,poistions,choseManyBook);
				position = ((ManyBookChoseAdapter)adapter).getChosed(choseManyBook);
				break;
			case Constants.TYPE_NOTE:
				note = (Note) getIntent().getSerializableExtra("choseBook");
				notes = mHelper.getNoteDao().queryForAll();
				Collections.sort(notes, new NoteComparator());
				poistions = new int[notes.size()];
				adapter = new NoteChoseAdapter(this, notes,poistions,note);
				position = ((NoteChoseAdapter)adapter).getChosed(note);
				break;
			}
			mLv.setAdapter(adapter);
			mLv.setSelection(position);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	protected void initView() {
		mLv = (ListView) findViewById(R.id.lv_chose);
		mBt = (Button) findViewById(R.id.bt_chose_submit);
		
		title_left = (TextView) findViewById(R.id.title_left);
		title_middle = (TextView) findViewById(R.id.title_middle);
		title_right = (TextView) findViewById(R.id.title_right);
		
		mBuilder = new Builder(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bt_chose_submit:
			startDelete();
			break;
		case R.id.title_left:
			finish();
			break;
		case R.id.title_right:
			//solve();
			selectAll();
			break;
		}
	}

	private void selectAll() {
		selectAll = !selectAll;
		for (int i = 0;i < poistions.length;i++){
			if (selectAll){
				poistions[i] = 1;
				title_right.setText("取消选择");
			}else{
				poistions[i] = 0;
				title_right.setText("全选");
			}
		}
		adapter.notifyDataSetChanged();
	}

	private void solve() {
		Intent intent = new Intent(this, SolveActivity.class);
		intent.putExtra("positions", poistions);
		startActivity(intent);
		finish();
	}

	private void startDelete() {
		switch(type){
		case Constants.TYPE_MANYBOOK:
			startDeleteManyBook();
			break;
		case Constants.TYPE_NOTE:
			startDeleteNote();
			break;
		case Constants.TYPE_SINGLEBOOK:
			startDeleteSingleBook();
			break;
		}
	}

	private void startDeleteSingleBook() {
		for(int i = 0;i<poistions.length;i++){
			if(poistions[i] != 0){
				SingleBook mBook = (SingleBook) adapter.getItem(i);
				try {
					mHelper.getSingleBookDao().delete(mBook);
				} catch (SQLException e) {
					e.printStackTrace();
					ToastUtil.showToast(this, "删除失败");
					return;
				}
			}
		}
		finish();
	}
	private void startDeleteManyBook() {
		for(int i = 0;i<poistions.length;i++){
			if(poistions[i] != 0){
				ManyBook mBook = (ManyBook) adapter.getItem(i);
				try {
					mHelper.getManyBookDao().delete(mBook);
				} catch (SQLException e) {
					e.printStackTrace();
					ToastUtil.showToast(this, "删除失败");
					return;
				}
			}
		}
		finish();
	}
	private void startDeleteNote() {
		for(int i = 0;i<poistions.length;i++){
			if(poistions[i] != 0){
				Note note = (Note) adapter.getItem(i);
				try {
					mHelper.getNoteDao().delete(note);
				} catch (SQLException e) {
					e.printStackTrace();
					ToastUtil.showToast(this, "删除失败");
					return;
				}
			}
		}
		finish();
	}
}
