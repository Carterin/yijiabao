package com.yijiabao.activitys;

import java.io.File;

import com.yijiabao.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GerenxinxiActivity extends Activity {

	private Button carmera;
	private Button photo;
	private Button quxiao;
	private ImageView head_image11;
	private String path = Environment.getExternalStorageDirectory()+"/com.myphoto/photo.png";
	private Bitmap bitmap;
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gerenxinxi);
		
		RelativeLayout head_img_change = (RelativeLayout) findViewById(R.id.head_img_change);
		RelativeLayout myname = (RelativeLayout) findViewById(R.id.myname);
		RelativeLayout sex = (RelativeLayout) findViewById(R.id.sex);
		RelativeLayout change = (RelativeLayout) findViewById(R.id.change);
		carmera = (Button) findViewById(R.id.carmera);
		photo = (Button) findViewById(R.id.photo);
		quxiao = (Button) findViewById(R.id.quxiao);
		head_image11 = (ImageView) findViewById(R.id.head_image11);
		
		
		head_img_change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(getApplicationContext(), ChangeImgDialogActivity.class);
				startActivity(intent);
				
			}
		});
		
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File file = new File(path);
			if (file.exists()) {
				bitmap = BitmapFactory.decodeFile(path);
				head_image11.setImageBitmap(bitmap);
				
			}else{
				Resources resources = getResources();
				bitmap = BitmapFactory.decodeResource(resources, R.drawable.head);
				head_image11.setImageBitmap(bitmap);
				
			}
		}
		
		
		
		
	}
	

	/** 
     * Ë¢ÐÂ 
     */  
    private void refresh() {  
        finish();  
        Intent intent = new Intent(getApplicationContext(), GerenxinxiActivity.class);  
        startActivity(intent);  
    }
	
	
	
	
}
