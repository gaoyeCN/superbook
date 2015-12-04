package com.fy.administrator.superbook.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fy.administrator.superbook.R;
import com.fy.administrator.superbook.db.bean.SingleBook;

@SuppressLint("SimpleDateFormat")
public class SingleBookAdapter extends BaseAdapter{
	private List<SingleBook> books;
	private Context context;
	SimpleDateFormat sf= new SimpleDateFormat("yyyy/MM/dd HH:mm");
	public SingleBookAdapter(Context context,List<SingleBook> books){
		this.books = books;
		this.context = context;
	}

	@Override
	public int getCount() {
		return books.size();
	}

	@Override
	public Object getItem(int position) {
		return books.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		SingleBook book = books.get(position);
		
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_lv_book, null);
			viewHolder.date = (TextView) convertView.findViewById(R.id.tv_lv_date);
			viewHolder.info = (TextView) convertView.findViewById(R.id.tv_lv_info);
			viewHolder.isSolve = (TextView) convertView.findViewById(R.id.tv_lv_isSolve);
			viewHolder.money = (TextView) convertView.findViewById(R.id.tv_lv_money);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.date.setText(sf.format(new Date(book.getDate())));
		viewHolder.info.setText(book.getInfo().length() > 10 ? book.getInfo().substring(0, 10) : book.getInfo());
		viewHolder.isSolve.setVisibility(View.GONE);
		viewHolder.money.setText(Html.fromHtml("<font color='red'><i>Money: </i></font>" + book.getMoney()));
		
		return convertView;
	}
	
	final static class ViewHolder {
		TextView date;
		TextView info;
		TextView money;
		TextView isSolve;
	}

}
