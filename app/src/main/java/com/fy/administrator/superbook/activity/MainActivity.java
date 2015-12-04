package com.fy.administrator.superbook.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fy.administrator.superbook.R;
import com.fy.administrator.superbook.adapter.MainFragmentAdapter;
import com.fy.administrator.superbook.values.Constants;
import com.fy.administrator.superbook.fragment.AFragment;
import com.fy.administrator.superbook.fragment.BFragment;
import com.fy.administrator.superbook.fragment.CFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private TextView table_left, table_middle, table_right,title_left,title_middle,title_right;
	private int curPage;

	private final String A_TAG = "firstpage";
	private final String B_TAG = "secondpage";
	private final String C_TAG = "threadpage";
	
	private boolean notFirst;
	private MainFragmentAdapter mAdapter;
	private ViewPager vp_main;

	private List<Fragment> fgs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		setData();
		setListeners();
		setTitle();
		setButtomTab();
	}

	private void setData() {
		fgs = new ArrayList<Fragment>();
		fgs.add(new AFragment());
		fgs.add(new BFragment());
		fgs.add(new CFragment());
		mAdapter = new MainFragmentAdapter(getSupportFragmentManager(),fgs);
		vp_main.setAdapter(mAdapter);
	}


	private void initView() {
		table_left = (TextView) findViewById(R.id.tab_buttom_left);
		table_middle = (TextView) findViewById(R.id.tab_buttom_middle);
		table_right = (TextView) findViewById(R.id.tab_buttom_right);
		
		title_left = (TextView) findViewById(R.id.title_left);
		title_middle = (TextView) findViewById(R.id.title_middle);
		title_right = (TextView) findViewById(R.id.title_right);

		vp_main = (ViewPager)findViewById(R.id.vp_main);
	}
	
	private void setListeners() {
		table_left.setOnClickListener(this);
		table_middle.setOnClickListener(this);
		table_right.setOnClickListener(this);
		
		title_right.setOnClickListener(this);
		vp_main.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {
				curPage = position;
				setButtomTab();
				setTitle();
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}



	

	private void setButtomTab() {
		table_left.setTextSize(16);
		table_right.setTextSize(16);
		table_middle.setTextSize(16);
		table_left.setTextColor(Color.parseColor("#252b37"));
		table_middle.setTextColor(Color.parseColor("#252b37"));
		table_right.setTextColor(Color.parseColor("#252b37"));
		switch (curPage) {
		case 0:
			table_left.setTextSize(22);
			table_left.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 1:
			table_middle.setTextSize(22);
			table_middle.setTextColor(Color.parseColor("#ffffff"));
			break;
		case 2:
			table_right.setTextSize(22);
			table_right.setTextColor(Color.parseColor("#ffffff"));
			break;
		}
	}

	private void setTitle() {
		switch (curPage) {
		case 0:
			title_middle.setText("个人账本");
			break;
		case 1:
			title_middle.setText("多人账本");
			break;
		case 2:
			title_middle.setText("记事本");
			break;
		}
		
		title_left.setVisibility(View.GONE);
		title_right.setText("添加");
		
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.tab_buttom_left:
			setPager(0);
			break;
		case R.id.tab_buttom_middle:
			setPager(1);
			break;
		case R.id.tab_buttom_right:
			setPager(2);
			break;
		case R.id.title_right:
			intent = new Intent(MainActivity.this, AddActivity.class);
			intent.putExtra(Constants.TYPE, curPage);
			startActivity(intent);
			break;
		}
	}

	private void setPager(int page) {
		vp_main.setCurrentItem(page);
	}
}
