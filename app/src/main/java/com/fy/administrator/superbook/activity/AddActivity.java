package com.fy.administrator.superbook.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.fy.administrator.superbook.R;
import com.fy.administrator.superbook.db.bean.Note;
import com.fy.administrator.superbook.db.dao.DatabaseHelper;
import com.fy.administrator.superbook.location.MyLocationListener;
import com.fy.administrator.superbook.util.StringUtil;
import com.fy.administrator.superbook.util.ToastUtil;

import java.sql.SQLException;

public class AddActivity extends BaseActivity implements OnClickListener,BDLocationListener{
	private TextView title_left,title_middle,title_right,tv_location;
	private EditText etInfo;
	//private Button btSubmit;
	private DatabaseHelper mHelper;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener;
	private String address;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_book);
		
		initView();
		setData();
		setListeners();
		initLocation();
	}

	protected void setListeners() {
		title_left.setOnClickListener(this);
		//btSubmit.setOnClickListener(this);
		title_right.setOnClickListener(this);
		mLocationClient.registerLocationListener(this);
	}

	protected void setData() {
		myListener = new MyLocationListener(this);
		title_left.setText("返回");
		title_middle.setText("添加记事本");
		title_right.setText("添加");
		title_right.setVisibility(View.VISIBLE);
		
		mHelper = DatabaseHelper.getHelper(this);
		mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
	}

	protected void initView() {
		title_left = (TextView) findViewById(R.id.title_left);
		title_middle = (TextView) findViewById(R.id.title_middle);
		title_right = (TextView) findViewById(R.id.title_right);

		tv_location = (TextView) findViewById(R.id.tv_location);

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
		case R.id.title_right:
			startSubmit();
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
			note.setAddress(address);
			try {
				mHelper.getDao(Note.class).create(note);
				finish();
			} catch (SQLException e) {
				e.printStackTrace();
				ToastUtil.showToast(this, "添加失败");
			}
	}

	private void initLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
		);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
		int span=1000;
		option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);//可选，默认false,设置是否使用gps
		option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location != null){
			//Toast.makeText(this,"address:" + location.getCity() + " l:" + location.getLatitude(),Toast.LENGTH_SHORT).show();
			address = location.getAddrStr();
			if (address != null){
				tv_location.setText(address);
				mLocationClient.stop();
			}
		}
	}
}
