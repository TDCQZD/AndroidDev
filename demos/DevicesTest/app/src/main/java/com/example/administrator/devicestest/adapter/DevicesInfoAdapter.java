package com.example.administrator.devicestest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.devicestest.R;
import com.example.administrator.devicestest.view.DevicesnfoView;

import java.util.List;

/**
 * @Description:
 * @author: Administrator
 * @Date: 2018/3/22 15:58
 */

public class DevicesInfoAdapter extends ArrayAdapter<DevicesnfoView>{
    private int id;


    public DevicesInfoAdapter(@NonNull Context context,  int layoutId, List<DevicesnfoView> infos) {
        super(context, layoutId, infos);
        this.id=layoutId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DevicesnfoView devicesnfoView=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(id,parent,false);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvInfo = (TextView) view.findViewById(R.id.tv_info);

        tvName.setText(devicesnfoView.getName());
        tvInfo.setText(devicesnfoView.getInfo());
        return view;
    }
}
