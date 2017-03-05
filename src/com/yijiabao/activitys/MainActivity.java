package com.yijiabao.activitys;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yijiabao.GlideImageLoader;
import com.yijiabao.R;
import com.yijiabao.R.array;
import com.yijiabao.R.id;
import com.yijiabao.R.layout;
import com.yijiabao.view.CircleImageView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Banner banner;
	private Button daohan;
	private Button jiayouzhan;
	private Button tinchechang;
	private Button weizhang;
	private Button cheliangxinxi;
	private Button yinyue;
	private ImageView head_img;
	private SlidingMenu slidingMenu;
	private String path = Environment.getExternalStorageDirectory()+"/com.myphoto/photo.png";
	private Bitmap bitmap;
	private static int flag = 0;
	private CircleImageView head_image;
	private Button denglu_bt;
	private TextView name_tv;
	private String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 
		
		setContentView(R.layout.activity_main);
		
		
		slidingMenu = new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.smenu);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingwidth);
		
		
		
		String[] images= getResources().getStringArray(R.array.url);
		String[] titles = getResources().getStringArray(R.array.title);
		List<?> list = Arrays.asList(images);
        List<?> arrayList = new ArrayList<>(list);
		banner = (Banner) findViewById(R.id.banner);
		
		//设置轮播图宽度和高度，建议最好按照图片的比例设置，效果更好
        banner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getScreenH(this)*3/10));
        
        
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(arrayList);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.ZoomOut);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置点击事件
        banner.setOnBannerClickListener(listener);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        
        head_img = (ImageView) findViewById(R.id.main_head_img);
        daohan = (Button) findViewById(R.id.daohan_but);
        jiayouzhan = (Button) findViewById(R.id.jiayouzhan_but);
        tinchechang = (Button) findViewById(R.id.tinchechang_but);
        weizhang = (Button) findViewById(R.id.weizhang_but);
        cheliangxinxi = (Button) findViewById(R.id.cheliangxinxi_but);
        yinyue = (Button) findViewById(R.id.yinyue_but);
        
        //导航按钮
        daohan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), DaohangActivity.class);
				startActivity(intent);
			}
		});
        
        //违章查询按钮
        weizhang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), WeizhangActivity.class);
				startActivity(intent);
			}
		});
        
        
        
        
        head_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				slidingMenu.toggle(true);
			}
		});
        
        
      //侧边栏部分的按钮
        head_image = (CircleImageView) findViewById(R.id.head_image);
        name_tv = (TextView) findViewById(R.id.name_tv);
        LinearLayout macar_list = (LinearLayout) findViewById(R.id.mycar_list);
        LinearLayout dingdan_list = (LinearLayout) findViewById(R.id.dingdan_list);
        LinearLayout sao = (LinearLayout) findViewById(R.id.sao);
        LinearLayout about = (LinearLayout) findViewById(R.id.about);
        denglu_bt = (Button) findViewById(R.id.denglu_bt);
        
        
      //设置头像
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File file = new File(path);
			if (file.exists()) {
				bitmap = BitmapFactory.decodeFile(path);
				//head_img.setImageBitmap(bitmap);
				//head_image.setImageBitmap(bitmap);
				
			}else{
				Resources resources = getResources();
				bitmap = BitmapFactory.decodeResource(resources, R.drawable.head);
				//head_img.setImageBitmap(bitmap);
				//head_image.setImageBitmap(bitmap);
			}
		}
        head_img.setImageBitmap(bitmap);
		head_image.setImageBitmap(bitmap);
		
        
        
        head_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), GerenxinxiActivity.class);
				startActivity(intent);
			}
		});
        
      //获取用户名
        username = name_tv.getText().toString().trim();
        if (!"请登录".equals(username)) {
			
        	denglu_bt.setText("注        销");
        	denglu_bt.setBackgroundResource(R.drawable.zhuxiao_btn);
			
		}
        
        //登录按钮点击事件
        denglu_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ("请登录".equals(username)) {
					
					name_tv.setText("小白");
					denglu_bt.setBackgroundResource(R.drawable.zhuxiao_btn);
					denglu_bt.setText("注        销");
		        	username = name_tv.getText().toString().trim();
				}else{
					
					zhuxiaoDialog();
					
					name_tv.setText("请登录");
					denglu_bt.setBackgroundResource(R.drawable.denglu_btn);
					denglu_bt.setText("登        录");
		        	username = name_tv.getText().toString().trim();
				}
			}

			
		});
        
        
        

	}
	
	OnBannerClickListener listener=new OnBannerClickListener() {
		@Override
		public void OnBannerClick(int position) {
			Toast.makeText(getApplicationContext(), "点击：" + position, Toast.LENGTH_SHORT).show();
	        Log.e("--",position+"");
		}
	};
	
	//注销提示框
	private void zhuxiaoDialog() {
		// TODO Auto-generated method stub
//		new AlertDialog.Builder(context)
		
		
		
	}
	
	
	
	
	 //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
        head_img.setImageBitmap(bitmap);
		head_image.setImageBitmap(bitmap);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
    

    
    
    public int getScreenH(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
}
