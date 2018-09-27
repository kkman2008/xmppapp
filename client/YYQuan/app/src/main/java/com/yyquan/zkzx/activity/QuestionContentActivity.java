package com.yyquan.zkzx.activity;
/*
 * Created by JHL, 2018/9/12
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yyquan.zkzx.R;
import com.yyquan.zkzx.entity.tb_problem;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QuestionContentActivity extends BaseActivity  implements  View.OnClickListener {

    @Bind(R.id.question_content_textView_title)
    TextView tvTitle;
    @Bind(R.id.question_content_textView_time)
    TextView tvQuestionTime;
    @Bind(R.id.tv_question_content)
    TextView tvQuestionContent;
    @Bind(R.id.ll_back)
    LinearLayout llback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_content);
        ButterKnife.bind(this);
        initialView();
    }
    @Override
    public void initialView() {
        Intent intent = getIntent();
        tb_problem modelQuestion = (tb_problem) intent.getSerializableExtra("question_content");
        tvTitle.setText(modelQuestion.getProblemname());
        String pattern = "yyyy-MM-dd";
        Date questionTime = modelQuestion.getAskthetime();
        if(questionTime != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            tvQuestionTime.setText(date);
        }
        tvQuestionContent.setText( modelQuestion.getProblemcontent() );
        llback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
            default:break;
        }
    }
}
