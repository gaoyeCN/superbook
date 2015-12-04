package com.fy.administrator.superbook.fragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

import com.fy.administrator.superbook.R;
import com.fy.administrator.superbook.activity.ChoseActivity;
import com.fy.administrator.superbook.activity.InfoActivity;
import com.fy.administrator.superbook.adapter.ManyBookAdapter;
import com.fy.administrator.superbook.comparator.ManyBookComparator;
import com.fy.administrator.superbook.values.Constants;
import com.fy.administrator.superbook.db.bean.ManyBook;
import com.fy.administrator.superbook.db.dao.DatabaseHelper;


public class BFragment extends Fragment {
	private View view;
	private DatabaseHelper mHelper;
	private List<ManyBook> books;
	private ListView mLv;
	private ManyBookAdapter adapter;
	private LayoutInflater inflate;
	private Builder mBuilder;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (null != view) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (null != parent) {
				parent.removeView(view);
			}
		} else {
			view = inflater.inflate(R.layout.fragment_a, null);
			
		}
		this.inflate = inflater;
		init(view);
		setData();
		setlisteners();
		return view;
	}

	private void setlisteners() {
		mLv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				ManyBook book = (ManyBook) parent.getItemAtPosition(position);
				//createDialog(book);
				  Intent intent = new Intent(getActivity(), ChoseActivity.class);
		  			intent.putExtra("choseBook", book);
		  			intent.putExtra(Constants.TYPE, Constants.TYPE_MANYBOOK);
		  			startActivity(intent);
				return true;
			}
		});
		mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ManyBook book = (ManyBook) parent.getItemAtPosition(position);
				Intent intent = new Intent(getActivity(), InfoActivity.class);
				intent.putExtra(Constants.BOOK,book);
				startActivity(intent);
			}
		});
	}
	
	protected void createDialog(final ManyBook book) {
		mBuilder.setView(inflate.inflate(R.layout.layout_dialog, null));
		final Dialog dialog = mBuilder.create();
		dialog.show();
		Window dialogWindow = dialog.getWindow();
	WindowManager m = getActivity().getWindowManager();
       Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
       WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
      p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
      dialogWindow.setGravity(Gravity.CENTER);
      dialogWindow.setAttributes(p);
      
      Button bt_delete = (Button) dialog.findViewById(R.id.dialog_delete);
      Button bt_edit = (Button) dialog.findViewById(R.id.dialog_edite);
      Button bt_solve = (Button) dialog.findViewById(R.id.dialog_solve);
      Button bt_chose = (Button) dialog.findViewById(R.id.dialog_chose);
      
      bt_delete.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
				mHelper.getManyBookDao().delete(book);
				//Constants.isManyBookChange = true;
				reLoadList();
				dialog.dismiss();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
      });
      bt_edit.setOnClickListener(new OnClickListener() {
    	  @Override
    	  public void onClick(View v) {
    		  
    	  }
      });
      bt_solve.setOnClickListener(new OnClickListener() {
    	  @Override
    	  public void onClick(View v) {
    		  Intent intent = new Intent(getActivity(), ChoseActivity.class);
  			intent.putExtra("choseBook", book);
  			intent.putExtra(Constants.TYPE, Constants.TYPE_MANYBOOK);
  			startActivity(intent);
  			dialog.dismiss();
    	  }
      });
      
      bt_chose.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), ChoseActivity.class);
			intent.putExtra("choseBook", book);
			intent.putExtra(Constants.TYPE, Constants.TYPE_MANYBOOK);
			startActivity(intent);
			dialog.dismiss();
		}
      });
	}

	protected void reLoadList() {
		/*if (Constants.isManyBookChange) {
			Constants.isManyBookChange = false;
			try {
				books.clear();
				List<ManyBook> sqList = mHelper.getManyBookDao().queryForAll();
				for (int i = 0; i < sqList.size(); i++) {
					ManyBook sqlBook = sqList.get(i);
					books.add(sqlBook);
				}
				bookSort();
			} catch (SQLException e) {
				books = new ArrayList<ManyBook>();
				e.printStackTrace();
			}
			adapter.notifyDataSetChanged();
		}*/
	}

	public void bookSort(){
		Collections.sort(this.books,new ManyBookComparator());
	}

	private void setData() {
		mHelper = DatabaseHelper.getHelper(getActivity());
		try {
			books = mHelper.getManyBookDao().queryForAll();
		} catch (SQLException e) {
			books = new ArrayList<ManyBook>();
			e.printStackTrace();
		}
		bookSort();
		adapter = new ManyBookAdapter(getActivity(), books);
		mLv.setAdapter(adapter);
	}

	private void init(View view) {
		mLv = (ListView) view.findViewById(R.id.lv_fragment);
		mBuilder = new Builder(getActivity());
	}

	@Override
	public void onResume() {
		super.onResume();
		reLoadList();
	}


}
