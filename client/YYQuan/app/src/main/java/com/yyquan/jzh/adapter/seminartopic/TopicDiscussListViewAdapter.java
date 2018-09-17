package com.yyquan.jzh.adapter.seminartopic;
/*
 * Created by JHL, 2018/9/17
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yyquan.jzh.activity.GlobalApplication;
import com.yyquan.jzh.entity.tb_topicforumprocess;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TopicDiscussListViewAdapter  extends BaseAdapter {
    Context context;
    ArrayList<tb_topicforumprocess> mdiscusslist;
    String user;
    String url_icon;

    public  TopicDiscussListViewAdapter(Context context, ArrayList<tb_topicforumprocess> discuss, String user){
        this.context = context;
        this.discuss = discuss;
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


        return convertView;
    }

    private class ViewHolder {
        private TextView tv_name;
        private TextView tv_location;
        private TextView tv_time;
        private TextView tv_content;
        private TextView tv_zan;
        private TextView tv_lou;
        private ImageView iv_icon;
    }


    private void update_zan(int pid, final int position, final TextView tv) {
        RequestParams params = new RequestParams();
        params.put("action", "update");
        params.put("pid", pid);
        params.put("user", user);
        //Toast.makeText(context, pid + "", Toast.LENGTH_SHORT).show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.post( ((GlobalApplication)context.getApplicationContext()).ifURL  + "/YfriendService/DoGetPingLun", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                mdiscusslist.get(position).setPraisecount("1");
                mdiscusslist.get(position).setPzan(Integer.parseInt((news.get(position).getPzan())) + 1 + " ");
                tv.setEnabled(true);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                tv.setEnabled(true);
            }
        });
    }
}
