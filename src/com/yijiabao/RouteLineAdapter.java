package com.yijiabao;

import android.content.Context;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.TextView;  
  
import com.baidu.mapapi.search.core.RouteLine;  
import com.baidu.mapapi.search.route.DrivingRouteLine;  
  
import java.util.List;  
  
public class RouteLineAdapter extends BaseAdapter {  
  
    private List<? extends RouteLine> routeLines;  
    private LayoutInflater layoutInflater;  
    private Type mtype;  
  
    public RouteLineAdapter(Context context, List<?extends RouteLine> routeLines, Type type) {  
        this.routeLines = routeLines;  
        layoutInflater = LayoutInflater.from( context);  
        mtype = type;  
    }  
  
    @Override  
    public int getCount() {  
        return routeLines.size();  
    }  
  
    @Override  
    public Object getItem(int position) {  
        return position;  
    }  
  
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        NodeViewHolder holder;  
        if (convertView == null) {  
            convertView = layoutInflater.inflate(R.layout.activity_transit_item, null);  
            holder = new NodeViewHolder();  
            holder.name = (TextView) convertView.findViewById(R.id.transitName);  
            holder.lightNum = (TextView) convertView.findViewById(R.id.lightNum);  
            holder.dis = (TextView) convertView.findViewById(R.id.dis);  
            convertView.setTag(holder);  
        } else {  
            holder = (NodeViewHolder) convertView.getTag();  
        }  
  
        switch (mtype) {  
            case  TRANSIT_ROUTE:  
                holder.name.setText("·��" + (position + 1));  
                int time = routeLines.get(position).getDuration();  
                if ( time / 3600 == 0 ) {  
                    holder.lightNum.setText( "��Լ��Ҫ��" + time / 60 + "����" );  
                } else {  
                    holder.lightNum.setText( "��Լ��Ҫ��" + time / 3600 + "Сʱ" + (time % 3600) / 60 + "����" );  
                }  
                holder.dis.setText("�����Լ�ǣ�" + routeLines.get(position).getDistance());  
                break;  
  
            case DRIVING_ROUTE:  
                DrivingRouteLine drivingRouteLine = (DrivingRouteLine) routeLines.get(position);  
                holder.name.setText( "��·" + (position + 1));  
                holder.lightNum.setText( "���̵�����" + drivingRouteLine.getLightNum());  
                holder.dis.setText("ӵ�¾���Ϊ��" + drivingRouteLine.getCongestionDistance() + "��");  
                break;  
  
            default:  
  
        }  
  
        return convertView;  
    }  
  
    private class NodeViewHolder {  
  
        private TextView name;  
        private TextView lightNum;  
        private TextView dis;  
    }  
  
    public enum Type {  
        TRANSIT_ROUTE, // ����  
        DRIVING_ROUTE // �ݳ�  
    }  
}  