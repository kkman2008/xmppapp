package com.yyquan.jzh.fragment.seminartopic;
/*
 * Created by JHL, 2018/9/17
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yyquan.jzh.R;
import com.yyquan.jzh.activity.GlobalApplication;
import com.yyquan.jzh.activity.NewsContentActivity;
import com.yyquan.jzh.entity.tb_theme;
import com.yyquan.jzh.view.DialogView;

public class SeminarTopicContentFragment extends Fragment {
    View layout_view;
    private View view;
    //private View layout_view;
    private TextView tv_title;
    private TextView tv_author;
    private TextView tv_time;

    private LinearLayout ll_content;
    tb_theme sTopicContent;
    String url = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            DialogView.Initial(getActivity(),"正在加载内容......");
            view = inflater.inflate(R.layout.fragment_seminar_discuss, container, false);

            content = ((NewsContentActivity) getActivity()).content;
            url = ((NewsContentActivity) getActivity()).content_url;


            tv_title = (TextView) view.findViewById(R.id.news_content_textView_title);
            tv_author = (TextView) view.findViewById(R.id.news_content_textView_author);
            tv_time = (TextView) view.findViewById(R.id.news_content_textView_time);

            ll_content = (LinearLayout) view.findViewById(R.id.news_content_layout_content);
            tv_title.setText(content.getCtitle());
            tv_author.setText(content.getCauthor());
            tv_time.setText(content.getCtime());
            //tv_content.setText(content.getCcontent());
            DialogView.show();
            getData(content.getCid());
        }
        return view;
    }

}
