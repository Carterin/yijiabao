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
	//定位相关声明
	private LocationClient locationClient;
	public static int flag = 0;
	
	boolean isFristLoc = true;//是否首次定位
	
	public BDLocationListener myListener = new BDLocationListener() {

		

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			//mMapView销毁后不再处理新接收的位置
			if (location == null||mMapView == null) {
				return;
			}
			
			//此处设置开发者获取到的方向信息，顺时针0-360
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
			.direction(100).latitude(location.getLatitude())
			.longitude(location.getLongitude()).build();
			
			mybaiduMap.setMyLocationData(locData);//设置定位数据
			
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
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        //注意该方法要再setContentView方法之前实现  
        SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_search_map);
		
		mMapView = (MapView) findViewById(R.id.bmapView);  
		mybaiduMap = mMapView.getMap();
		
		
		//开启定位图层
		mybaiduMap.setMyLocationEnabled(true);
		//实例化LocationClient类
		locationClient = new LocationClient(getApplicationContext());
		//注册监听函数
		locationClient.registerLocationListener(myListener);
		//设置定位参数
		this.setLocationOption();		
		//开始定位
		locationClient.start();
		
		//实时交通拥堵图
		
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
	
		//开启重定位
		Button chongdingwei = (Button) findViewById(R.id.chongdingwei);
		chongdingwei.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mybaiduMap.animateMapStatus(update);
				
			}
		});
		
		//接收上个页面传回的数据
		Bundle bundle = getIntent().getExtras();
		String qidian = bundle.getString("qidian");
		String zhongdian = bundle.getString("zhongdian");
		System.out.println(qidian);
		System.out.println(zhongdian);
		
		//创建驾车线路规划检索示例
		msearch = RoutePlanSearch.newInstance();
		
		//创建驾车线路规划检索监听者
		OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
			
			

			@Override
			public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
				// TODO Auto-generated method stub
				//获取步行路线规划结果
			}
			
			@Override
			public void onGetTransitRouteResult(TransitRouteResult arg0) {
				// TODO Auto-generated method stub
				//获取公交路线规划结果
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
				//获取驾车路线规划结果
				
				if(null == arg0 || arg0.error != SearchResult.ERRORNO.NO_ERROR){
					Toast.makeText(getApplicationContext(), "抱歉，未找到结果！", Toast.LENGTH_SHORT).show();
				}
				
				if (arg0.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
					//起终点或途径点地址有歧义，通过以下接口获取建议查询信息
					//arg0.getSuggestAddrInfo();
					return;
				}
				
				if (arg0.error == SearchResult.ERRORNO.NO_ERROR) {
					
					/*//线路多于一条
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
				//获取骑行路线规划结果
			}
		};
		
		//设置驾车线路规划检索监听者
		msearch.setOnGetRoutePlanResultListener(listener);
		
		//准备检索起终点信息
		
		PlanNode locationNode = PlanNode.withLocation(ll);
		
		PlanNode qidianNode = PlanNode.withCityNameAndPlaceName(city, qidian);
		PlanNode zhongdianNode = PlanNode.withCityNameAndPlaceName(city, zhongdian);
		
		//发起驾车线路规划检索
		if(qidian.isEmpty()||zhongdian.isEmpty()){
			Toast.makeText(getApplicationContext(), "查询失败！起点或终点不能为空！", Toast.LENGTH_SHORT).show();
		}else if("我的位置".equals(qidian)){
			msearch.drivingSearch((new DrivingRoutePlanOption()).from(locationNode).to(zhongdianNode));
		}else if("我的位置".equals(zhongdian)){
			msearch.drivingSearch((new DrivingRoutePlanOption()).from(qidianNode).to(locationNode));
		}else{
		msearch.drivingSearch((new DrivingRoutePlanOption()).from(qidianNode).to(zhongdianNode));
		}
		
		
		
		
		
		
		
		
		
    }  //onCreate方法结束
	
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

    //三个生命周期的出理
	@Override  
    protected void onDestroy() {  
    	// 释放资源
    	locationClient.stop();
    	mybaiduMap.setMyLocationEnabled(false);
        super.onDestroy();
        mMapView.onDestroy();
        
        //释放检索实例
        msearch.destroy();
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
        } 
    
    //响应Dlg中的item点击
    interface OnItemInDlgClickListener{
    	public void onItemClick( int position); 
    }
    
 /*// 供路线选择的Dialog 
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
