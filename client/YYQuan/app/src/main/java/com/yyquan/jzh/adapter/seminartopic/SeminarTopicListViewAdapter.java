package com.yyquan.jzh.adapter.seminartopic;
/*
 * Created by JHL, 2018/9/12
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyquan.jzh.R;
import com.yyquan.jzh.entity.tb_theme;

import java.util.ArrayList;

public class SeminarTopicListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<tb_theme> topiclist;
    public String  TAG = "SeminarTopicListViewAdapter";

    public SeminarTopicListViewAdapter(Context context, ArrayList<tb_theme> list) {
        this.context = context;
        this.topiclist = list;
    }

    public void setList(ArrayList<tb_theme> lists){topiclist=lists;}

    @Override
    public int getCount() {
        return topiclist.size();
    }

    @Override
    public Object getItem(int position) {
        return topiclist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG,"getView " + position + " " + convertView);
        SeminarTopicListViewAdapter.ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_seminar_topic, null);
            holder = new SeminarTopicListViewAdapter.ViewHolder();
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.listview_item_imageview_icon);
            holder.tv_title = (TextView) convertView.findViewById(R.id.listview_item_textView_title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.listview_item_textView_pinglun);
            convertView.setTag(holder);
        }else{
            holder =(SeminarTopicListViewAdapter.ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText( topiclist.get(position).getQuestionname());
        String questioContentHint = topiclist.get(position).getQuestioncontent().replace("null","");
        if(questioContentHint.length()>12)
        {
            questioContentHint = questioContentHint.substring(0,11) + "...";
        }
        holder.tv_content.setText(questioContentHint );

        return convertView;
    }
    private class ViewHolder {
        TextView tv_title;
        TextView tv_content;
        ImageView iv_icon;
    }
}
