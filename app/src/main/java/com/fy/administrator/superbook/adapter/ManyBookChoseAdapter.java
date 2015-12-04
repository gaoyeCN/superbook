package com.fy.administrator.superbook.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fy.administrator.superbook.R;
import com.fy.administrator.superbook.db.bean.ManyBook;

@SuppressLint("SimpleDateFormat")
public class ManyBookChoseAdapter extends BaseAdapter{
	private List<ManyBook> books;
	private Context context;
	private int positions[];
	SimpleDateFormat sf= new SimpleDateFormat("yyyy/MM/dd HH:mm");
	public ManyBookChoseAdapter(Context context,List<ManyBook> books,int positions[],ManyBook choseBook){
		this.books = books;
		this.context = context;
		this.positions = positions;
		positions[getChosed(choseBook)] = 1;
	}
	
	public int getChosed(ManyBook book){
		for(int i = 0;i<books.size();i++){
			ManyBook mBook = books.get(i);
			if(mBook.getId() == book.getId()){
				return i;
			}
		}
		return 0;
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
		ManyBook book = books.get(position);
		
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_lv_book, null);
			viewHolder.date = (TextView) convertView.findViewById(R.id.tv_lv_date);
			viewHolder.info = (TextView) convertView.findViewById(R.id.tv_lv_info);
			viewHolder.isSolve = (TextView) convertView.findViewById(R.id.tv_lv_isSolve);
			viewHolder.money = (TextView) convertView.findViewById(R.id.tv_lv_money);
			viewHolder.tvChose = (TextView) convertView.findViewById(R.id.iv_lv_icon);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		if(book.isSolve()){
			viewHolder.isSolve.setText(Html.fromHtml("<font color='blue'>已均摊</font>"));
		}else{
			viewHolder.isSolve.setText(Html.fromHtml("<font color='red'>未均摊</font>"));
		}
		viewHolder.money.setText(Html.fromHtml("<font color='red'><i>Money: </i></font>" + book.getMoney()));
		viewHolder.date.setText(sf.format(new Date(book.getDate())));
		viewHolder.info.setText(book.getInfo().length() > 10 ? book.getInfo().substring(0, 10) : book.getInfo());
		viewHolder.tvChose.setVisibility(View.VISIBLE);
		viewHolder.tvChose.setGravity(Gravity.CENTER);
		if(positions[position] == 0){
			viewHolder.tvChose.setBackgroundResource(R.color.nochosed);
			viewHolder.tvChose.setText("未选择");
			
		}else{
			viewHolder.tvChose.setBackgroundResource(R.color.chosed);
			viewHolder.tvChose.setText("已选择");
		}
		
		return convertView;
	}
	
	final static class ViewHolder {
		TextView date;
		TextView info;
		TextView money;
		TextView isSolve;
		TextView tvChose;
	}

}
