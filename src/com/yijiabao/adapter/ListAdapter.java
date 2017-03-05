package com.yijiabao.adapter;

import java.util.List;

import com.yijiabao.model.ListModel;
import com.yijiabao.R;
import com.yijiabao.R.id;
import com.yijiabao.R.layout;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
	
	private  List<ListModel> mDate;
	private Context mContext;

	public ListAdapter( Context mContext,List mDate){
		this.mContext=mContext;
		this.mDate=mDate;		
	}
	

	@Override
	public int getCount() {
		return mDate.size();
	}

	@Override
	public Object getItem(int position) {
		return mDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = View.inflate(mContext, R.layout.csy_listitem_citys, null);
		
		//��ʼ��
		ListModel model=mDate.get(position) ;
		TextView txt_name =(TextView) view.findViewById(R.id.txt_name);
		//ImageView image=(ImageView)view.findViewById(R.id.iv_1);
		

		//������
		txt_name.setText(model.getTextName());
		txt_name.setTag(model.getNameId());
		//����
		return view;
	}

}