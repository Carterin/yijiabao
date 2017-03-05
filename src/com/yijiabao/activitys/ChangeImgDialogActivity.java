package com.yijiabao.activitys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.yijiabao.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;

public class ChangeImgDialogActivity extends Activity {

	private static int CAMERA_REQUEST_CODE = 1;
	private static int PHOTO_REQUEST_CODE = 2;
	private static int CROP_REQUEST_CODE = 3;
	private ImageView head_image11;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_img_dialog);
		
		Button carmera = (Button) findViewById(R.id.carmera);
		Button photo = (Button) findViewById(R.id.photo);
		Button quxiao = (Button) findViewById(R.id.quxiao);
		head_image11 = (ImageView) findViewById(R.id.head_image11);
		
		carmera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, CAMERA_REQUEST_CODE);
			
			}
		});
		
		photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, PHOTO_REQUEST_CODE);
			
			}
		});
		
		quxiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		
		
		
		
	}
	
	//将图片content格式转成file格式
			private Uri contentUri(Uri uri){
				
				try {
					InputStream is = getContentResolver().openInputStream(uri);
					Bitmap bitmap = BitmapFactory.decodeStream(is);
					is.close();
					return saveBitmap(bitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				
				
				
				
			}
			
			//将图片保存到SD卡中
			private Uri saveBitmap(Bitmap bm){
				File file = new File(Environment.getExternalStorageDirectory() + "/com.myphoto");
				if (!file.exists()) {
					file.mkdir();
				}
				File img = new File(file.getAbsolutePath()+"/photo.png");
				try {
					FileOutputStream fos = new FileOutputStream(img);
					bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
					fos.flush();
					fos.close();
					return Uri.fromFile(img);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				
				
			}
			
			
			//图像裁剪方法
			private void startImageZoom(Uri uri){
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(uri, "image/*");
				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 1);
				intent.putExtra("aspectY", 1);
				intent.putExtra("outputX", 150);
				intent.putExtra("outputY", 150);
				intent.putExtra("return-data", true);
				startActivityForResult(intent, CROP_REQUEST_CODE);
			}
			

			
			@Override
			protected void onActivityResult(int requestCode, int resultCode, Intent data) {
				// TODO Auto-generated method stub
				if(requestCode == CAMERA_REQUEST_CODE){
					if(data == null){
						return;
					}
					else{
						Bundle extras = data.getExtras();
						if (extras != null) {
							Bitmap bm = extras.getParcelable("data");
							Uri uri = saveBitmap(bm);
							startImageZoom(uri);
						}
					}
				}
				else if (requestCode == PHOTO_REQUEST_CODE) {
					if (data == null) {
						return;
					}
					Uri uri = data.getData();
					Uri uri2 = contentUri(uri);
					startImageZoom(uri2);
					
					
				}
				else if (requestCode == CROP_REQUEST_CODE) {
					if (data == null) {
						return;
					}
					Bundle extras = data.getExtras();
					Bitmap bm = extras.getParcelable("data");
					saveBitmap(bm);
					
					
					finish();
					
					
				}
				
			}
	
	
}
