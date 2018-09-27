package com.yyquan.zkzx.fragment.question;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.demievil.library.RefreshLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yyquan.zkzx.R;
import com.yyquan.zkzx.activity.GlobalApplication;
import com.yyquan.zkzx.activity.QuestionContentActivity;
import com.yyquan.zkzx.adapter.QuestionTitleListViewAdapter;
import com.yyquan.zkzx.entity.CommonConstant;
import com.yyquan.zkzx.entity.tb_problem;
import com.yyquan.zkzx.activity.GlobalApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/*
 * Created by JHL, 2018.09.10
 */

public class QuestionTitleFragment extends Fragment implements  View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {

    private  View view;
    View footerLayout;

    RefreshLayout mRefreshLayout;
    ListView mlistview;
    private QuestionTitleListViewAdapter mAdapter;
    private ArrayList<tb_problem> list;
    private String questionStatusType;
    private TextView tv_more;
    private ProgressBar pb;
    int index = 0;
    int questions_size;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null){
            // 问题管理列表，使用的是问题fragment_question_title这样的listview控件
            view = inflater.inflate(R.layout.fragment_question_title, container, false);
            mlistview = (ListView) view.findViewById(R.id.fragment_content_listview);
            mRefreshLayout = (RefreshLayout) view.findViewById(R.id.fragment_content_swipe_container);
            // list_item_more.xml layout, 加载更多
            footerLayout = getActivity().getLayoutInflater().inflate(R.layout.list_item_more, null);
            tv_more = (TextView) footerLayout.findViewById(R.id.text_more);
            pb = (ProgressBar) footerLayout.findViewById(R.id.load_progress_bar);
            tv_more.setOnClickListener(this);
            mlistview.addFooterView(footerLayout);
            mRefreshLayout.setOnRefreshListener(this);
            mRefreshLayout.setOnLoadListener(this);
            mlistview.setOnItemClickListener(this);
            mRefreshLayout.setChildView(mlistview);
            mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_dark,
                    android.R.color.holo_red_light,
                    android.R.color.black);
            list = new ArrayList<>();
            Bundle bd = getArguments();
            if (bd != null) {
                questionStatusType = (String) bd.getSerializable(CommonConstant.TABPAGE_ENTITY);
                getData(questionStatusType);
            }else{
               getData("全部"); // 如果未有viewpager里面传过来，则取全部。 测试也可用
            }
        }
        return view;
    }
    /**
     * 获取数据
     */
    private void getData(String  questionStatusType) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String paraType = "";
        if( questionStatusType.equalsIgnoreCase("全部") ){
            paraType = "-1";
        }
        if( questionStatusType.equalsIgnoreCase("进行中") ){
            paraType = "1";
        }
        if( questionStatusType.equalsIgnoreCase("未开始") ){
            paraType = "0";
        }
        if( questionStatusType.equalsIgnoreCase("已结束") ){
            paraType = "2";
        }
        params.put("type", paraType);
        params.put("limit", index);
        params.put("action", "get_question_list");
        client.post( ((GlobalApplication)getActivity().getApplicationContext()).ip_user_question, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                // Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                if (str != null) {

                    try {
                        JSONObject object = new JSONObject(str);

                        if (object.getString("code").equals("success")) {
                            JSONArray array = object.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                object = array.getJSONObject(i);
                                tb_problem content = new tb_problem();
                                content.setQuestionid(object.getString("questionid"));
                                content.setProblemname(object.getString("problemname"));
                                content.setProblemcontent(object.getString("problemcontent"));
                                content.setProblemname(object.getString("problemname"));
                                String target = object.getString("askthetime");
                                try {
                                    if(("null".equalsIgnoreCase(target) == false) && target!= null && ("".equalsIgnoreCase(target) == false)) {
                                        Date result = new  Date( Long.valueOf( target));
                                        content.setAskthetime(result);
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                list.add(content);
                            }
                            Message m = Message.obtain(h, 1); // 发送消息绑定数据
                            h.sendMessage(m);
                        }
                    } catch (JSONException e) {
                        mRefreshLayout.setLoading(false);
                        mRefreshLayout.setRefreshing(false);
                        e.printStackTrace();

                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getActivity(), "网络链接失败，请查看网络设置", Toast.LENGTH_SHORT).show();
                mRefreshLayout.setLoading(false);
                mRefreshLayout.setRefreshing(false);
            }
        });
    }
    /**
     * 业务逻辑处理
     */
    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (index == 0) {
                        mAdapter  = new QuestionTitleListViewAdapter(getActivity(), list);
                        mlistview.setAdapter(mAdapter);
                       // mlistview.deferNotifyDataSetChanged();
                        mRefreshLayout.setRefreshing(false);
                    } else {
                        mAdapter.setList(list);
                        mAdapter.notifyDataSetChanged();
                        tv_more.setVisibility(View.VISIBLE);
                        pb.setVisibility(View.GONE);
                        mRefreshLayout.setLoading(false);
                    }
                    break;
            }
        }
    };

    /**
     * 可见时调用
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && view != null) {

        } else {

        }
    }


    /**
     * 单例模式，获取数据并实例化
     *
     * @param questionState
     * @return
     */
    public static QuestionTitleFragment newInstance(String questionState) {
        Bundle bd = new Bundle();
        bd.putSerializable(CommonConstant.TABPAGE_ENTITY, questionState);
        QuestionTitleFragment fragment = new QuestionTitleFragment();
        fragment.setArguments(bd);
        return fragment;
    }


    /**
     * 加载更多数据
     */
    private void loadData() {
        index += 10;
        if (questions_size == list.size()) {
            tv_more.setText("数据已加载完毕");
            tv_more.setEnabled(false);
            return;
        }
        tv_more.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
        getData(questionStatusType);
    }

    /**
     * 上拉加载方法
     */
    @Override
    public void onLoad() {
        loadData();
    }

    /**
     * 下拉刷新方法
     */
    @Override
    public void onRefresh() {
        index = 0;
        tv_more.setEnabled(true);
        tv_more.setText("加载更多");
        list = new ArrayList<tb_problem>();
        getData(questionStatusType);
        tv_more.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);
        mRefreshLayout.setLoading(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position < list.size()) {
            Intent intent = new Intent(getActivity(),QuestionContentActivity.class);
            intent.putExtra("question_content", list.get(position));
            // [tbd]
            //intent.putExtra("user", ((MainActivity) this.getActivity()).user);
            startActivity(intent);
        }


        //Toast.makeText(getActivity(), position + "11", Toast.LENGTH_SHORT).show();


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
