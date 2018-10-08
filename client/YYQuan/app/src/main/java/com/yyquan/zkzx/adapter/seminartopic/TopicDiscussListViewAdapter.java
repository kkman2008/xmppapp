package com.yyquan.zkzx.adapter.seminartopic;
/*
 * Created by JHL, 2018/9/17
 *  研讨主题的观点列表
 *  原有的参照是， 资讯主题（资讯）- 研讨主题； 资讯评论 - 研讨观点； 观点评价，暂无参考
 *  格式课参考， PingLunListViewAdapter
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rockerhieu.emojicon.EmojiconTextView;
import com.vdurmont.emoji.EmojiParser;
import com.yyquan.zkzx.R;
import com.yyquan.zkzx.entity.tb_topicforumprocess;

import java.util.ArrayList;
import java.util.Date;

public class TopicDiscussListViewAdapter  extends BaseAdapter {
    Context context;
    ArrayList<tb_topicforumprocess> mdiscusslist;
    String user;
    String url_icon;

    public  TopicDiscussListViewAdapter(Context context, ArrayList<tb_topicforumprocess> discuss, String user){
        this.context = context;
        this.mdiscusslist = discuss;
        this.user = user;
        url_icon  = null; // [tbd]
    }

    public  void setList(ArrayList<tb_topicforumprocess> discusslist ) { mdiscusslist = discusslist;}

    @Override
    public int getCount() {
        return mdiscusslist.size();
    }

    @Override
    public Object getItem(int position) {
        return mdiscusslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if( convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_seminar_discuss, null);
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.listview_pinglun_item_imageview_icon);
            holder.tv_name = (TextView) convertView.findViewById(R.id.listview_pinglun_item_textView_name);
            holder.tv_location = (TextView) convertView.findViewById(R.id.listview_pinglun_item_textView_location);
            holder.tv_time = (TextView) convertView.findViewById(R.id.listview_pinglun_item_textView_time);
            holder.tv_content = (EmojiconTextView) convertView.findViewById(R.id.emotv_topic_item_textView_content);
            holder.tv_zan = (TextView) convertView.findViewById(R.id.listview_pinglun_item_textView_zan);
            holder.tv_lou = (TextView) convertView.findViewById(R.id.listview_pinglun_item_textView_lou);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String stime = "";
        holder.tv_name.setText(mdiscusslist.get(position).getUsername());
        // 设置评论时间
        Date date = new Date();
        long time = date.getTime();
        long sytime = time - mdiscusslist.get(position).getDiscusstime().getTime();
        long ltime = sytime / 1000;

        if (ltime >= 0 && ltime < 60) {
            if (ltime == 0) {
                stime = "刚刚";
            } else {
                stime = ltime + "秒前";
            }

        } else if (ltime >= 60 && ltime < 3600) {
            stime = ltime / 60 + "分钟前";
        } else if (ltime >= 3600 && ltime < 3600 * 24) {
            stime = ltime / 3600 + "小时前";
        } else if (ltime >= 3600 * 24 && ltime < 3600 * 48) {
            stime = "昨天";
        } else if (ltime >= 3600 * 48 && ltime < 3600 * 72) {
            stime = "前天";
        } else if (ltime >= 3600 * 72) {
            stime = ltime / 86400 + "天前";
        } else {
            stime = "1212122";
        }

        holder.tv_time.setText(stime);
        holder.tv_content.setText(EmojiParser.parseToUnicode(  mdiscusslist.get(position).getDiscusscontent()));
        holder.tv_zan.setText(mdiscusslist.get(position).getPraisecount() + " ");
        //holder.tv_lou.setVisibility(View.GONE);
        // 本项目中地理位置不需要
        holder.tv_location.setVisibility(View.GONE);

        Drawable nav_up = null;
        // 当前用户是否已经点赞
        // [tbd]
        //nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        //holder.tv_zan.setCompoundDrawables(null, null, nav_up, null);
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_name;
        private TextView tv_location;
        private TextView tv_time;
        private EmojiconTextView tv_content;
        private TextView tv_zan;
        private TextView tv_lou;
        private ImageView iv_icon;
    }
}
