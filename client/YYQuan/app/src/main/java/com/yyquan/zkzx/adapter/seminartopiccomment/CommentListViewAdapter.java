package com.yyquan.zkzx.adapter.seminartopiccomment;
/*
 * Created by JHL, 2018/10/9
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rockerhieu.emojicon.EmojiconTextView;
import com.squareup.picasso.Picasso;
import com.vdurmont.emoji.EmojiParser;
import com.yyquan.zkzx.R;
import com.yyquan.zkzx.activity.GlobalApplication;
import com.yyquan.zkzx.entity.tb_topicprocessuserpraise;
import com.yyquan.zkzx.util.DateTool;

import java.util.ArrayList;
import java.util.List;

public class CommentListViewAdapter extends BaseAdapter {
    Context context;
    List<tb_topicprocessuserpraise> commentlist= new ArrayList<>();
    final String TAG ="CommentListViewAdapter";
    public CommentListViewAdapter(Context context, List<tb_topicprocessuserpraise> commentlist){
        this.context = context;
        this.commentlist = commentlist;
    }
    @Override
    public int getCount() {
        return commentlist.size();
    }

    @Override
    public Object getItem(int position) {
        return commentlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG,"getView " + position + " " + convertView);
        CommentListViewAdapter.ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_comment, null);
            holder = new CommentListViewAdapter.ViewHolder();
            holder.tv_content = (EmojiconTextView) convertView.findViewById(R.id.emotv_topic_item_textView_content);
            holder.tv_username = (TextView) convertView.findViewById(R.id.tv_comment_username);
            holder.tv_comment_time = (TextView) convertView.findViewById(R.id.tv_comment_time);
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.civ_user_icon);
            convertView.setTag(holder);
        }else{
            holder =(CommentListViewAdapter.ViewHolder) convertView.getTag();
        }

        holder.tv_content.setText(EmojiParser.parseToUnicode(commentlist.get(position).getComment()));
        holder.tv_username.setText(commentlist.get(position).getUsername());
        holder.tv_comment_time.setText(DateTool.DateToString(  commentlist.get(position).getCreatetime(), "yyyy-MM-dd HH:mm:ss"));
        String url = ((GlobalApplication)context.getApplicationContext()).ip_icon + commentlist.get(position).getUsericon();
        Log.d(TAG, "getView url=: " + url);
        Picasso.with(context).load(url).resize(200,200).centerInside().placeholder(R.mipmap.aio_image_default_round).error(R.mipmap.aio_image_default_round).into(holder.iv_icon);
        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_icon;
        private TextView tv_username;
        private TextView tv_location;
        private TextView tv_comment_time;
        private EmojiconTextView tv_content;
        private TextView tv_praise;
        private TextView tv_lou;

    }
}
