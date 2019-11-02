package com.example.homecctv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VideoListAdapter extends BaseAdapter{

    LayoutInflater mInflator;

    public VideoListAdapter(Context context){
        mInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lookup.videoArr.size();
    }

    @Override
    public Object getItem(int position) {
        return lookup.videoArr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(Integer.toString(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = mInflator.inflate(R.layout.videolistitem, parent, false);
        TextView textNum = convertView.findViewById(R.id.textView_videoListNum);
        TextView textName = convertView.findViewById(R.id.textView_videoListName);
        textNum.setText(Integer.toString(position));
        textName.setText(getItem(position).toString());

        return convertView;
    }
}
