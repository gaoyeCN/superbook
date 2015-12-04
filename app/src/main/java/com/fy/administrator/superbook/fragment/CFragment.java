package com.fy.administrator.superbook.fragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.fy.administrator.superbook.R;
import com.fy.administrator.superbook.activity.ChoseActivity;
import com.fy.administrator.superbook.activity.InfoActivity;
import com.fy.administrator.superbook.adapter.NoteAdapter;
import com.fy.administrator.superbook.comparator.NoteComparator;
import com.fy.administrator.superbook.values.Constants;
import com.fy.administrator.superbook.db.bean.Note;
import com.fy.administrator.superbook.db.dao.DatabaseHelper;


public class CFragment extends Fragment {
	private View view;
	private DatabaseHelper mHelper;
	private List<Note> notes;
	private ListView mLv;
	private NoteAdapter adapter;
	
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
				Note book = (Note) parent.getItemAtPosition(position);
				  Intent intent = new Intent(getActivity(), ChoseActivity.class);
		  			intent.putExtra("choseBook", book);
		  			intent.putExtra(Constants.TYPE, Constants.TYPE_NOTE);
		  			startActivity(intent);
				return true;
			}
		});
		mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Note book = (Note) parent.getItemAtPosition(position);
				Intent intent = new Intent(getActivity(), InfoActivity.class);
				intent.putExtra(Constants.BOOK,book);
				startActivity(intent);
			}
		});
	}

	private void setData() {
		mHelper = DatabaseHelper.getHelper(getActivity());
		try {
			notes = mHelper.getNoteDao().queryForAll();
		} catch (SQLException e) {
			notes = new ArrayList<Note>();
			e.printStackTrace();
		}
		bookSort();
		adapter = new NoteAdapter(getActivity(), notes);
		mLv.setAdapter(adapter);
	}

	public void bookSort(){
		Collections.sort(this.notes, new NoteComparator());
	}

	private void init(View view) {
		mLv = (ListView) view.findViewById(R.id.lv_fragment);
	}

	@Override
	public void onResume() {
		super.onResume();
		/*if (Constants.isNoteChange) {
			Constants.isNoteChange = false;
			try {
				notes.clear();
				List<Note> sqList = mHelper.getNoteDao().queryForAll();
				for (int i = 0; i < sqList.size(); i++) {
					Note sqlNote = sqList.get(i);
					notes.add(sqlNote);
				}
			} catch (SQLException e) {
				notes = new ArrayList<Note>();
				e.printStackTrace();
			}
			bookSort();
			adapter.notifyDataSetChanged();
		}*/
	}
}
