package com.yijiabao.activitys;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.yijiabao.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class DaohangActivity extends Activity implements OnGetSuggestionResultListener {

	private Button turn;
	private Button search;
	private AutoCompleteTextView qidian;
	private AutoCompleteTextView zhongdian;
	private String qidiantext;
	private String zhongdiantext;
	private String city;
	private LocationClient locationClient;
	private SuggestionSearch msuggestionSearch;
	private ArrayAdapter<String> adapter;


	public BDLocationListener myListener = new BDLocationListener() {


		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			city = location.getCity();
		}

		@Override
		public void onConnectHotSpotMessage(String arg0, int arg1) {
			// TODO Auto-generated method stub

		}
	};







	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext  
		//注意该方法要再setContentView方法之前实现  
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_daohang);

		//控件初始化
		turn = (Button) findViewById(R.id.bt_turn);
		search = (Button) findViewById(R.id.bt_search);
		qidian = (AutoCompleteTextView) findViewById(R.id.autoTV_qidian);
		zhongdian = (AutoCompleteTextView) findViewById(R.id.autoTV_zhongdian);

		


		//文字对调按钮
		turn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				qidiantext = qidian.getText().toString().trim();
				zhongdiantext = zhongdian.getText().toString().trim();
				qidian.setText(zhongdiantext);
				zhongdian.setText(qidiantext);
			}
		});

		//搜索按钮
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), Search_MapActivity.class);
				
				qidiantext = qidian.getText().toString().trim();
				zhongdiantext = zhongdian.getText().toString().trim();
				
				Bundle bundle = new Bundle();
				bundle.putString("qidian", qidiantext);
				bundle.putString("zhongdian", zhongdiantext);
				intent.putExtras(bundle);
				
				
				startActivity(intent);
			}
		});


		//实例化LocationClient类
		locationClient = new LocationClient(getApplicationContext());
		//注册监听函数
		locationClient.registerLocationListener(myListener);
		//设置定位参数
		this.setLocationOption();		
		//开始定位
		locationClient.start();

		//mpoiSearch = PoiSearch.newInstance();
		//	mpoiSearch.setOnGetPoiSearchResultListener(this);
		msuggestionSearch = SuggestionSearch.newInstance();
		msuggestionSearch.setOnGetSuggestionResultListener(this);
		adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.autocompletetextview);
		zhongdian.setAdapter(adapter);
		qidian.setAdapter(adapter);



		//当起点输入的文字发生变化时，动态更新列表
		qidian.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length()<=0) {
					return;
				}

				msuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(s.toString()).city(city));
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				/*if (s.length()<=0) {
							return;
						}
						msuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(s.toString()).city(city));
				 */}
		});

		//当起点输入的文字发生变化时，动态更新列表
		zhongdian.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length()<=0) {
					return;
				}
				msuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(s.toString()).city(city));
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				/*if (s.length()<=0) {
							return;
						}
						msuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(s.toString()).city(city));
				 */}
		});







	}//onCreate方法结束位置


	//定位参数的设置
	private void setLocationOption() {
		// TODO Auto-generated method stub
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);//打开GPS
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式（高精确）
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认为gcj02
		option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);//返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);//返回的定位信息结果包含手机机头的方向
		locationClient.setLocOption(option);
	}

	@Override  
	protected void onDestroy() {  
		// 释放资源
		locationClient.stop();
		super.onDestroy();
	}


	@Override
	public void onGetSuggestionResult(SuggestionResult arg0) {
		// TODO Auto-generated method stub
		if (arg0==null||arg0.getAllSuggestions()==null) {
			return;
		}

		//adapter.clear();
		for (SuggestionResult.SuggestionInfo info : arg0.getAllSuggestions()) {
			if (info.key!=null) {
				adapter.add(info.key);
			}
		}
		adapter.notifyDataSetChanged();
	}






}
