package com.yyquan.zkzx.fragment.seminartopic;
/*
 * Created by JHL, 2018/9/17
 * 讨论内容列表， 可参考pinglunFragment
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.demievil.library.RefreshLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yyquan.zkzx.R;
import com.yyquan.zkzx.activity.ActivityUnitTest;
import com.yyquan.zkzx.activity.GlobalApplication;
import com.yyquan.zkzx.activity.SeminarTopic.TopicContentActivity;
import com.yyquan.zkzx.adapter.seminartopic.TopicDiscussListViewAdapter;
import com.yyquan.zkzx.entity.User;
import com.yyquan.zkzx.entity.tb_theme;
import com.yyquan.zkzx.entity.tb_topicforumprocess;
import com.yyquan.zkzx.util.DateTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class DiscussListviewFragement extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener, View.OnClickListener {
    private View view;
    private TextView tv_title;
    private TextView tv_author;
    private TextView tv_time;
    private TextView tv_total;
    private Intent intent;
    private tb_theme topicContent;
    private User user;
    private ListView listview;
    private RefreshLayout mRefreshLayout;
    ArrayList<tb_topicforumprocess> list;
    TopicDiscussListViewAdapter adapter;
    View footerLayout;
    private TextView tv_more;
    private ProgressBar pb;
    private String search_url  ;
    int pinglun_size = 0 ;
    int index = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            list = new ArrayList<>();
            view = inflater.inflate(R.layout.fragment_seminar_discuss, container, false);
            footerLayout = getActivity().getLayoutInflater().inflate(R.layout.list_item_more, null);
            tv_more = (TextView) footerLayout.findViewById(R.id.text_more);
            tv_more.setOnClickListener(this);
            pb = (ProgressBar) footerLayout.findViewById(R.id.load_progress_bar);
            intent = getActivity().getIntent();
            topicContent = (tb_theme) intent.getSerializableExtra("seminar_item_content");
            if( topicContent == null){
                // for test purpose
                topicContent = new tb_theme();
                topicContent.setSubjectid("327e2fb7-2c3c-4d64-bbc5-0f00ae80d2bd");
                topicContent.setDisintegratortime(new Date());
                topicContent.setPhase(1);
                topicContent.setQuestionname("测试主题");
                topicContent.setQuestioncontent("主题内容");
            }
            tv_title = (TextView) view.findViewById(R.id.news_content_textView_title);
            tv_author = (TextView) view.findViewById(R.id.news_content_textView_author);
            tv_time = (TextView) view.findViewById(R.id.news_content_textView_time);
            tv_total = (TextView) view.findViewById(R.id.news_content_textView_total);
            listview = (ListView) view.findViewById(R.id.news_content_listView);
            listview.addFooterView(footerLayout);
            mRefreshLayout = (RefreshLayout) view.findViewById(R.id.fragment_content_swipe_container);
            mRefreshLayout.setOnRefreshListener(this);
            mRefreshLayout.setOnLoadListener(this);
            mRefreshLayout.setChildView(listview);
            mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_dark,
                    android.R.color.holo_red_light,
                    android.R.color.black);
            tv_title.setText(topicContent.getQuestionname());
            String sContent  = topicContent.getQuestioncontent();
            if( sContent != null && sContent.length() > 12){
                sContent = sContent.substring(0,11) + "...";
            }
            tv_author.setText(sContent);
            tv_time.setText(DateTool.DateToString(topicContent.getDisintegratortime(), "yyyy-MM-dd"));

            if(getActivity().getClass().getName().contains( "TopicContentActivity") == true){
                user = ((TopicContentActivity) getActivity()).user;
            }else {
                user = ((ActivityUnitTest) getActivity()).user;
            }
            search_url = ((GlobalApplication) getActivity().getApplication()).ip_seminar_topic;

            getData(topicContent.getSubjectid(), 0);

        }
        return view;
    }

    /**
     * 根据文章id获取评论
     *
     * @param pcid
     */
    public void getData(String pcid, int limit) {
        if(pcid == null){
            pcid = "327e2fb7-2c3c-4d64-bbc5-0f00ae80d2bd";
        }
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("discusstype", pcid);
        //params.put("user", user.getUser());
        params.put("limit", limit);
        // params.put("cid", content.getCid());
        params.put("action", "get_seminar_discuss_list");
        client.post(search_url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                {
                    String str = new String(responseBody);
                    if (str != null) {
                        try {
                            JSONObject object = new JSONObject(str);
                            if (object.getString("code").equals("success")) {
                                JSONArray array = object.getJSONArray("data");

                                // use fastjson to deserialize the json data as the POJO object tb_topicforumprocess
                                list = new ArrayList<tb_topicforumprocess>( JSON.parseArray(array.toString(), tb_topicforumprocess.class));
                                if( getActivity() != null && getActivity().getClass() != null
                                && getActivity().getClass().getName() !=null && getActivity().getClass().getName().contains( "TopicContentActivity") == true) {
                                    if (((TopicContentActivity) getActivity()) == null) {
                                        return;
                                    } else {
                                        // ((TopicContentActivity) getActivity()).tv_discuss.setText(pinglun_size + "评");
                                    }
                                    tv_total.setText("热门研讨(" + pinglun_size + ")");
                                    ((TopicContentActivity) getActivity()).pl_size = pinglun_size;
                                }
                                if (index == 0) {
                                    adapter = new TopicDiscussListViewAdapter(getActivity(), list, user.getUser());
                                    listview.setAdapter(adapter);
                                    listview.setVisibility(View.VISIBLE);
                                } else {
                                    adapter.setList(list);
                                    adapter.notifyDataSetChanged();
                                    tv_more.setVisibility(View.VISIBLE);
                                    pb.setVisibility(View.GONE);
                                }
                            } else {
                                tv_total.setText("暂无评论");
                                listview.setVisibility(View.GONE);
                            }
                            mRefreshLayout.setRefreshing(false);
                            mRefreshLayout.setLoading(false);
                        } catch (JSONException e) {
                            mRefreshLayout.setRefreshing(false);
                            mRefreshLayout.setLoading(false);
                            e.printStackTrace();
                        }
                    } else {
                        mRefreshLayout.setLoading(false);
                        mRefreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mRefreshLayout.setRefreshing(false);
                mRefreshLayout.setLoading(false);
            }
        });
    }


    @Override
    public void onLoad() {
        loadData();
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        index = 0;
        tv_more.setEnabled(true);
        tv_more.setText("加载更多");
        list = new ArrayList<>();
        getData(topicContent.getSubjectid(), 0);
        tv_more.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);
        mRefreshLayout.setLoading(false);
    }


    /**
     * 加载更多数据
     */
    private void loadData() {
        index += 10;
        if (pinglun_size == list.size()) {
            tv_more.setText("数据已加载完毕");
            tv_more.setEnabled(false);
            return;
        }
        tv_more.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
        getData(topicContent.getSubjectid(), index);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_more:
                loadData();
                break;
        }
    }
}
