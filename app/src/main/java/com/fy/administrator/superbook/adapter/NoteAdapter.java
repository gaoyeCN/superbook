package com.fy.administrator.superbook.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fy.administrator.superbook.R;
import com.fy.administrator.superbook.db.bean.Note;

@SuppressLint("SimpleDateFormat")
public class NoteAdapter extends BaseAdapter{
	private List<Note> notes;
	private Context context;
	private SimpleDateFormat sf= new SimpleDateFormat("yyyy/MM/dd HH:mm");
	private Animation animation;
	public NoteAdapter(Context context,List<Note> notes){
		this.notes = notes;
		this.context = context;
		this.animation = AnimationUtils.loadAnimation(context,R.anim.toleft_show);
	}

	@Override
	public int getCount() {
		return notes.size();
	}

	@Override
	public Object getItem(int position) {
		return notes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		Note note = notes.get(position);
		
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_lv_book, null);
			viewHolder.date = (TextView) convertView.findViewById(R.id.tv_lv_date);
			viewHolder.info = (TextView) convertView.findViewById(R.id.tv_lv_info);
			viewHolder.top = (TextView) convertView.findViewById(R.id.tv_time_top);
			viewHolder.buttom = (TextView) convertView.findViewById(R.id.tv_time_buttom);
			viewHolder.rl_main = (RelativeLayout) convertView.findViewById(R.id.rl_main);
			//viewHolder.rl_main.startAnimation(animation);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.date.setText(sf.format(new Date(note.getDate())));
		viewHolder.info.setText(note.getInfo().length() > 10 ? note.getInfo().substring(0, 10) : note.getInfo());
		if (position == 0){
			viewHolder.top.setVisibility(View.GONE);
		}else {
			viewHolder.top.setVisibility(View.VISIBLE);
		}
		if (position == notes.size() - 1){
			viewHolder.buttom.setVisibility(View.GONE);
		}else {
			viewHolder.buttom.setVisibility(View.VISIBLE);
		}
		return convertView;
	}



	final static class ViewHolder {
		TextView date;
		TextView info;
		TextView top;
		TextView buttom;
		RelativeLayout rl_main;
	}

}
