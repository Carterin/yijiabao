package com.yijiabao;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //���巽�������Լ�ȥѡ�񣬴η�����Ϊ�˼���banner����������������������Խ����Ȩ�޿��Ÿ�ʹ����ȥѡ��
        Glide.with(context).load(path).into(imageView);
    }
}
