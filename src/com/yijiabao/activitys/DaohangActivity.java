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
		//��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext  
		//ע��÷���Ҫ��setContentView����֮ǰʵ��  
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_daohang);

		//�ؼ���ʼ��
		turn = (Button) findViewById(R.id.bt_turn);
		search = (Button) findViewById(R.id.bt_search);
		qidian = (AutoCompleteTextView) findViewById(R.id.autoTV_qidian);
		zhongdian = (AutoCompleteTextView) findViewById(R.id.autoTV_zhongdian);

		


		//���ֶԵ���ť
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

		//������ť
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


		//ʵ����LocationClient��
		locationClient = new LocationClient(getApplicationContext());
		//ע���������
		locationClient.registerLocationListener(myListener);
		//���ö�λ����
		this.setLocationOption();		
		//��ʼ��λ
		locationClient.start();

		//mpoiSearch = PoiSearch.newInstance();
		//	mpoiSearch.setOnGetPoiSearchResultListener(this);
		msuggestionSearch = SuggestionSearch.newInstance();
		msuggestionSearch.setOnGetSuggestionResultListener(this);
		adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.autocompletetextview);
		zhongdian.setAdapter(adapter);
		qidian.setAdapter(adapter);



		//�������������ַ����仯ʱ����̬�����б�
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

		//�������������ַ����仯ʱ����̬�����б�
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







	}//onCreate��������λ��


	//��λ����������
	private void setLocationOption() {
		// TODO Auto-generated method stub
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);//��GPS
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//���ö�λģʽ���߾�ȷ��
		option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��Ϊgcj02
		option.setScanSpan(5000);//���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setIsNeedAddress(true);//���صĶ�λ���������ַ��Ϣ
		option.setNeedDeviceDirect(true);//���صĶ�λ��Ϣ��������ֻ���ͷ�ķ���
		locationClient.setLocOption(option);
	}

	@Override  
	protected void onDestroy() {  
		// �ͷ���Դ
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
