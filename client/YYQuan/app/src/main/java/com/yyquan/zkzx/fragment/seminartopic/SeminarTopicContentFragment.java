package com.yyquan.zkzx.fragment.seminartopic;
/*
 * Created by JHL, 2018/9/17
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yyquan.zkzx.R;
import com.yyquan.zkzx.activity.ActivityUnitTest;
import com.yyquan.zkzx.activity.SeminarTopic.TopicContentActivity;
import com.yyquan.zkzx.entity.tb_theme;
import com.yyquan.zkzx.util.DateTool;
import com.yyquan.zkzx.view.DialogView;
import com.yyquan.zkzx.activity.ActivityUnitTest;
import com.yyquan.zkzx.util.DateTool;
import com.yyquan.zkzx.view.DialogView;

public class SeminarTopicContentFragment extends Fragment {
    View layout_view;
    private View view;
    //private View layout_view;
    private TextView tv_title;
    private TextView tv_content;
    private TextView tv_time;

    private LinearLayout ll_content;
    tb_theme sTopicContent;
    String url = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            DialogView.Initial(getActivity(),"正在加载内容......");
            DialogView.show();
            view = inflater.inflate(R.layout.fragment_seminar_topic_content, container, false);
            if(getActivity().getClass().getName().contains( "TopicContentActivity") == true) {
                sTopicContent = ((TopicContentActivity) getActivity()).content;
            }else {
                sTopicContent = ((ActivityUnitTest) getActivity()).topicContent;
            }
            //url = sTopicContent.content_url;
            tv_title = (TextView) view.findViewById(R.id.seminar_content_textView_title);
            tv_content= (TextView) view.findViewById(R.id.tv_seminar_content);
            tv_time = (TextView) view.findViewById(R.id.seminar_content_textView_time);

            tv_title.setText(sTopicContent.getQuestionname());
            //tv_author.setText(sTopicContent.getSpeechmode());
            tv_time.setText(DateTool.DateToString( sTopicContent.getDisintegratortime(), "yyyy-MM-dd"));
            tv_content.setText(sTopicContent.getQuestioncontent());
            DialogView.dismiss();
        }
        return view;
    }
}
