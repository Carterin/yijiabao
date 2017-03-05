package com.yijiabao.activitys;

import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.yijiabao.R;
import com.yijiabao.RouteLineAdapter;
import com.yijiabao.overlayutil.DrivingRouteOverlay;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Search_MapActivity extends Activity {

	private MapView mMapView;
	private BaiduMap mybaiduMap;
	private Button shishijiaotong;
	private LatLng ll;
	private MapStatusUpdate update;
	private String city;
	private RoutePlanSearch msearch;
	private DrivingRouteResult nowresultd;
	private DrivingRouteLine route;
	//��λ�������
	private LocationClient locationClient;
	public static int flag = 0;
	
	boolean isFristLoc = true;//�Ƿ��״ζ�λ
	
	public BDLocationListener myListener = new BDLocationListener() {

		

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			//mMapView���ٺ��ٴ����½��յ�λ��
			if (location == null||mMapView == null) {
				return;
			}
			
			//�˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
			.direction(100).latitude(location.getLatitude())
			.longitude(location.getLongitude()).build();
			
			mybaiduMap.setMyLocationData(locData);//���ö�λ����
			
			if (isFristLoc) {
				isFristLoc= false;
				
				ll = new LatLng(location.getLatitude(), location.getLongitude());
				update = MapStatusUpdateFactory.newLatLngZoom(ll, 16);
				mybaiduMap.animateMapStatus(update);
			}
			
			System.out.println(location.getCity());
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
		setContentView(R.layout.activity_search_map);
		
		mMapView = (MapView) findViewById(R.id.bmapView);  
		mybaiduMap = mMapView.getMap();
		
		
		//������λͼ��
		mybaiduMap.setMyLocationEnabled(true);
		//ʵ����LocationClient��
		locationClient = new LocationClient(getApplicationContext());
		//ע���������
		locationClient.registerLocationListener(myListener);
		//���ö�λ����
		this.setLocationOption();		
		//��ʼ��λ
		locationClient.start();
		
		//ʵʱ��ͨӵ��ͼ
		
		shishijiaotong = (Button) findViewById(R.id.shishijiaotong);
		shishijiaotong.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (flag==0) {
					mybaiduMap.setTrafficEnabled(true);					
					flag=1;
				}
				else {
					mybaiduMap.setTrafficEnabled(false);					
					flag=0;
				}
			}
		});
	
		//�����ض�λ
		Button chongdingwei = (Button) findViewById(R.id.chongdingwei);
		chongdingwei.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mybaiduMap.animateMapStatus(update);
				
			}
		});
		
		//�����ϸ�ҳ�洫�ص�����
		Bundle bundle = getIntent().getExtras();
		String qidian = bundle.getString("qidian");
		String zhongdian = bundle.getString("zhongdian");
		System.out.println(qidian);
		System.out.println(zhongdian);
		
		//�����ݳ���·�滮����ʾ��
		msearch = RoutePlanSearch.newInstance();
		
		//�����ݳ���·�滮����������
		OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
			
			

			@Override
			public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
				// TODO Auto-generated method stub
				//��ȡ����·�߹滮���
			}
			
			@Override
			public void onGetTransitRouteResult(TransitRouteResult arg0) {
				// TODO Auto-generated method stub
				//��ȡ����·�߹滮���
			}
			
			@Override
			public void onGetMassTransitRouteResult(MassTransitRouteResult arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetIndoorRouteResult(IndoorRouteResult arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetDrivingRouteResult(DrivingRouteResult arg0) {
				// TODO Auto-generated method stub
				//��ȡ�ݳ�·�߹滮���
				
				if(null == arg0 || arg0.error != SearchResult.ERRORNO.NO_ERROR){
					Toast.makeText(getApplicationContext(), "��Ǹ��δ�ҵ������", Toast.LENGTH_SHORT).show();
				}
				
				if (arg0.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
					//���յ��;�����ַ�����壬ͨ�����½ӿڻ�ȡ�����ѯ��Ϣ
					//arg0.getSuggestAddrInfo();
					return;
				}
				
				if (arg0.error == SearchResult.ERRORNO.NO_ERROR) {
					
					/*//��·����һ��
					if (arg0.getRouteLines().size()>1) {
						nowresultd = arg0;
						MyTransitDlg myTransitDlg = new MyTransitDlg(getApplicationContext(), arg0.getRouteLines(), RouteLineAdapter.Type.DRIVING_ROUTE);
						myTransitDlg.setOnItemInDlgclickLister(new OnItemInDlgClickListener() {
							
							

							@Override
							public void onItemClick(int position) {
								route = nowresultd.getRouteLines().get(position);  
		                        DrivingRouteOverlay overlay = new DrivingRouteOverlay(mybaiduMap);  
		                        mybaiduMap.setOnMarkerClickListener(overlay);  
		                        overlay.setData(nowresultd.getRouteLines().get(position));  
		                        overlay.addToMap();  
		                        overlay.zoomToSpan();
							}
						});
						
						myTransitDlg.show();
						
						
					}else if(arg0.getRouteLines().size() == 1){
						route = arg0.getRouteLines().get(0);  */
		                DrivingRouteOverlay overlay = new DrivingRouteOverlay(mybaiduMap);  
		  
		                mybaiduMap.setOnMarkerClickListener(overlay);  
		                overlay.setData(arg0.getRouteLines().get(0));  
		                overlay.addToMap();  
		                overlay.zoomToSpan();  
		            /*} else {  
		                return ;  
					}*/
					
				}
				
				
				
				
			}
			
			@Override
			public void onGetBikingRouteResult(BikingRouteResult arg0) {
				// TODO Auto-generated method stub
				//��ȡ����·�߹滮���
			}
		};
		
		//���üݳ���·�滮����������
		msearch.setOnGetRoutePlanResultListener(listener);
		
		//׼���������յ���Ϣ
		
		PlanNode locationNode = PlanNode.withLocation(ll);
		
		PlanNode qidianNode = PlanNode.withCityNameAndPlaceName(city, qidian);
		PlanNode zhongdianNode = PlanNode.withCityNameAndPlaceName(city, zhongdian);
		
		//����ݳ���·�滮����
		if(qidian.isEmpty()||zhongdian.isEmpty()){
			Toast.makeText(getApplicationContext(), "��ѯʧ�ܣ������յ㲻��Ϊ�գ�", Toast.LENGTH_SHORT).show();
		}else if("�ҵ�λ��".equals(qidian)){
			msearch.drivingSearch((new DrivingRoutePlanOption()).from(locationNode).to(zhongdianNode));
		}else if("�ҵ�λ��".equals(zhongdian)){
			msearch.drivingSearch((new DrivingRoutePlanOption()).from(qidianNode).to(locationNode));
		}else{
		msearch.drivingSearch((new DrivingRoutePlanOption()).from(qidianNode).to(zhongdianNode));
		}
		
		
		
		
		
		
		
		
		
    }  //onCreate��������
	
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

    //�����������ڵĳ���
	@Override  
    protected void onDestroy() {  
    	// �ͷ���Դ
    	locationClient.stop();
    	mybaiduMap.setMyLocationEnabled(false);
        super.onDestroy();
        mMapView.onDestroy();
        
        //�ͷż���ʵ��
        msearch.destroy();
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onPause();  
        } 
    
    //��ӦDlg�е�item���
    interface OnItemInDlgClickListener{
    	public void onItemClick( int position); 
    }
    
 /*// ��·��ѡ���Dialog 
    class MyTransitDlg extends Dialog {

    	private RouteLineAdapter mTransitAdapter;
    	private List< ? extends RouteLine> mtransitRouteLines;
    	
    	OnItemInDlgClickListener onItemInDlgClickListener;
    	
    	public MyTransitDlg(Context context, int themeResId) {
    		super(context, themeResId);
    		// TODO Auto-generated constructor stub
    	}
    	
    	public MyTransitDlg(Context context, List< ? extends RouteLine> transitRouteLines,  RouteLineAdapter.Type type) {
    		// TODO Auto-generated constructor stub
    		this(context,0);
    		 mtransitRouteLines = transitRouteLines;
    		mTransitAdapter = new  RouteLineAdapter( context, mtransitRouteLines , type);  
            requestWindowFeature(Window.FEATURE_NO_TITLE);
    		
    	}

    	@Override
    	protected void onCreate(Bundle savedInstanceState) {
    		// TODO Auto-generated method stub
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.acticity_transit_dialog);
    		
    		ListView transitRouteList = (ListView) findViewById(R.id.transitList);
    		transitRouteList.setAdapter(mTransitAdapter);
    		transitRouteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    			@Override
    			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
    					long arg3) {
    				// TODO Auto-generated method stub
    				onItemInDlgClickListener.onItemClick(arg2);
    				dismiss();
    			}
    		});
    	}
    	
    	public void setOnItemInDlgclickLister(OnItemInDlgClickListener itemListener){
    		onItemInDlgClickListener = itemListener;
    	}
    	

    }*/
    
    

}
