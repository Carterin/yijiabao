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
		
		//�����ֲ�ͼ��Ⱥ͸߶ȣ�������ð���ͼƬ�ı������ã�Ч������
        banner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getScreenH(this)*3/10));
        
        
        //����banner��ʽ
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //����ͼƬ������
        banner.setImageLoader(new GlideImageLoader());
        //����ͼƬ����
        banner.setImages(arrayList);
        //����banner����Ч��
        banner.setBannerAnimation(Transformer.ZoomOut);
        //���ñ��⼯�ϣ���banner��ʽ����ʾtitleʱ��
        banner.setBannerTitles(Arrays.asList(titles));
        //�����Զ��ֲ���Ĭ��Ϊtrue
        banner.isAutoPlay(true);
        //�����ֲ�ʱ��
        banner.setDelayTime(3000);
        //����ָʾ��λ�ã���bannerģʽ����ָʾ��ʱ��
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //���õ���¼�
        banner.setOnBannerClickListener(listener);
        //banner���÷���ȫ���������ʱ������
        banner.start();
        
        head_img = (ImageView) findViewById(R.id.main_head_img);
        daohan = (Button) findViewById(R.id.daohan_but);
        jiayouzhan = (Button) findViewById(R.id.jiayouzhan_but);
        tinchechang = (Button) findViewById(R.id.tinchechang_but);
        weizhang = (Button) findViewById(R.id.weizhang_but);
        cheliangxinxi = (Button) findViewById(R.id.cheliangxinxi_but);
        yinyue = (Button) findViewById(R.id.yinyue_but);
        
        //������ť
        daohan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), DaohangActivity.class);
				startActivity(intent);
			}
		});
        
        //Υ�²�ѯ��ť
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
        
        
      //��������ֵİ�ť
        head_image = (CircleImageView) findViewById(R.id.head_image);
        name_tv = (TextView) findViewById(R.id.name_tv);
        LinearLayout macar_list = (LinearLayout) findViewById(R.id.mycar_list);
        LinearLayout dingdan_list = (LinearLayout) findViewById(R.id.dingdan_list);
        LinearLayout sao = (LinearLayout) findViewById(R.id.sao);
        LinearLayout about = (LinearLayout) findViewById(R.id.about);
        denglu_bt = (Button) findViewById(R.id.denglu_bt);
        
        
      //����ͷ��
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
        
      //��ȡ�û���
        username = name_tv.getText().toString().trim();
        if (!"���¼".equals(username)) {
			
        	denglu_bt.setText("ע        ��");
        	denglu_bt.setBackgroundResource(R.drawable.zhuxiao_btn);
			
		}
        
        //��¼��ť����¼�
        denglu_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ("���¼".equals(username)) {
					
					name_tv.setText("С��");
					denglu_bt.setBackgroundResource(R.drawable.zhuxiao_btn);
					denglu_bt.setText("ע        ��");
		        	username = name_tv.getText().toString().trim();
				}else{
					
					zhuxiaoDialog();
					
					name_tv.setText("���¼");
					denglu_bt.setBackgroundResource(R.drawable.denglu_btn);
					denglu_bt.setText("��        ¼");
		        	username = name_tv.getText().toString().trim();
				}
			}

			
		});
        
        
        

	}
	
	OnBannerClickListener listener=new OnBannerClickListener() {
		@Override
		public void OnBannerClick(int position) {
			Toast.makeText(getApplicationContext(), "�����" + position, Toast.LENGTH_SHORT).show();
	        Log.e("--",position+"");
		}
	};
	
	//ע����ʾ��
	private void zhuxiaoDialog() {
		// TODO Auto-generated method stub
//		new AlertDialog.Builder(context)
		
		
		
	}
	
	
	
	
	 //�������Ҫ���Ǹ��õ����飬������ô����
    @Override
    protected void onStart() {
        super.onStart();
        //��ʼ�ֲ�
        banner.startAutoPlay();
        head_img.setImageBitmap(bitmap);
		head_image.setImageBitmap(bitmap);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //�����ֲ�
        banner.stopAutoPlay();
    }
    

    
    
    public int getScreenH(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
}
