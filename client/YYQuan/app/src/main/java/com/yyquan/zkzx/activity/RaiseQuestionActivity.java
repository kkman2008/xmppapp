package com.yyquan.zkzx.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yyquan.zkzx.R;
import com.yyquan.zkzx.entity.tb_problem;
import com.yyquan.zkzx.util.FilePathResolver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class RaiseQuestionActivity extends BaseActivity   implements View.OnClickListener {
    public static final int SUCCESS = 1;
    public static final int FAILD = 2;
    public static final int CREATEQUESTIONSUCCESS = 3;
    public static final int CREATEQUESTIONFAILED = 4;
    // 问题标题，问题内容，选择文件，上传文件，提交问题
    public static int RESULT_LOAD_FILE = 1;

    private EditText etQuestionTitle;
    private EditText etQuestionContent;
    private EditText etFilePath;

    private Button  btnUpload;
    private Button btnSubmit;
    private View show;
    private Button btnGoConnecttoHost;
    String filePath = null;
    Uri fileUri = null;
    private LinearLayout lback;
    @Bind(R.id.tv_filename)
    TextView tvfileName;
    @Bind(R.id.iv_browse)
    ImageView ivBrowse;
    String uuidFileName;

    @Bind(R.id.btn_browse_file)
    Button btnBrowseFile;

    @Override
    public void initialView() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raisequestion);
        ButterKnife.bind(this);
        initView();
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    show.setVisibility(View.INVISIBLE);
                    filePath = "";
                    etFilePath.setText(filePath);
                    //Toast.makeText(getApplicationContext(), "上传成功！", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "handleMessage: 文件上传成功！");
                    break;
                case FAILD:
                    show.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "上传失败！", Toast.LENGTH_LONG).show();
                    break;
                case CREATEQUESTIONFAILED:
                    show.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "用户问题提交失败！", Toast.LENGTH_LONG).show();
                    break;
                case CREATEQUESTIONSUCCESS:
                    show.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "用户问题提交成功！", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    private void initView(){
        etQuestionTitle = (EditText) findViewById(R.id.et_questionTitle);
        etQuestionContent= (EditText) findViewById(R.id.et_questionContent);
        etFilePath = (EditText) findViewById(R.id.etfilepath);
        btnUpload = (Button) findViewById(R.id.buttonUpload);
        btnSubmit = (Button) findViewById(R.id.buttonquestionSubmit);
        lback = (LinearLayout) findViewById(R.id.raise_question_back);
        show =(View) findViewById(R.id.show);
        btnGoConnecttoHost =(Button) findViewById(R.id.btnConnectToHost);
        btnBrowseFile.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        // 接触空格，但不要弹出键盘. 不能由用户输入
        etFilePath.setKeyListener(null);
        lback.setOnClickListener(this);
        btnGoConnecttoHost.setOnClickListener(this);
        ivBrowse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_browse:
            case R.id.btn_browse_file:
                showFileChooser();
                break;
            case R.id.buttonUpload:
                uploadFile();
                break;
            case R.id.buttonquestionSubmit:
                createQuestion();
                break;
            case R.id.raise_question_back:
                finish();
                break;
            case R.id.btnConnectToHost:
                //redirect to connect to host
                connectToHost();
                break;
//            case R.id.iv_browse:
//                filePath = null;
//                fileUri = null;
//                tvfileName.setText("");
            default:
                break;
        }
    }

    private static final int FILE_SELECT_CODE = 0;
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    private static final String TAG = "ChooseFile";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_FILE && resultCode == RESULT_LOAD_FILE && data != null) {
            filePath = data.getStringExtra("path");
            etFilePath.setText(filePath);
        }
        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            fileUri = uri;
            Log.d(TAG, "File Uri: " + uri.toString());

            try {
                filePath = FilePathResolver.getPath(this.getApplicationContext(), uri);
                filePath = filePath;
                etFilePath.setText(filePath);
                Log.d(TAG, "onActivityResult: 文件已选择，文件路径如下； " + filePath);
                String fileName = getFileName(filePath);
                tvfileName.setText(fileName);
                //Toast.makeText(this,"selected path:" + path, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(TAG, "File Path: " + filePath);
        }
    }
    public String getFileName(String pathandname){
        int start=pathandname.lastIndexOf("/");
        int  end=pathandname.lastIndexOf(".");
        if(start!=-1 && end!=-1){
            return pathandname.substring(start+1,end);
        }else{
            return null;
        }
    }


    private void connectToHost(){
        try {
            Intent intent = new Intent(RaiseQuestionActivity.this, ChatActivity.class);
            startActivity(intent);
            Log.d(TAG, "Activity Redirect to: " + "ChatActivity");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private  void createQuestion(){
        // upload the file and create user's questions
        if( filePath !=null){
            uploadFile();
        }
        show.setVisibility(View.VISIBLE);
        // after new thread, call start to start run the thread
       String titleValue =  etQuestionTitle.getText().toString();
       String ContentValue = etQuestionContent.getText().toString();
        if(titleValue==null || titleValue.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "请输入标题", Toast.LENGTH_LONG).show();
            return;
        }
        if(ContentValue==null || ContentValue.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "请输入内容", Toast.LENGTH_LONG).show();
            return;
        }
        // action:create_question, question is json format
        String questionid = java.util.UUID.randomUUID().toString();
        RequestParams params = new RequestParams();
        tb_problem problem = new tb_problem();
        problem.setQuestionid(questionid);
        problem.setProblemname(titleValue);
        problem.setIsdelete("0");
        problem.setProblemcontent(ContentValue);
        problem.setUserid("1");
        problem.setProblemfile(uuidFileName);
        params.put("action", "create_question");
        Gson gson = new Gson();
        params.put("question", gson.toJson(problem));
        String srvUrl = ((GlobalApplication) getApplication()).ip_question;
        Log.d(TAG, "run: createQuestion");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        client.post(srvUrl, params, new AsyncHttpResponseHandler() {
            Message msg = Message.obtain();
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                msg.what = CREATEQUESTIONSUCCESS;
                mHandler.sendMessage(msg);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                msg.what = CREATEQUESTIONFAILED;
                mHandler.sendMessage(msg);
            }
        });
    }

    private void uploadFile() {
        show.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                // 服务器的访问路径
                String uploadUrl = ((GlobalApplication) getApplication()).ip_upload;
                Map<String, File> files = new HashMap<String, File>();

                String name = fun(filePath);
                files.put(name, new File(filePath));
                //files.put("test.jpg", new File(picturePath));
                Log.d("str--->>>", filePath);
                try {
                   HashMap<String,String> paraMap =   new HashMap<String,String>();
                   String fileExt = "";
                    if( name != null){
                        int i = name.lastIndexOf(".");
                        fileExt = name.substring(i);
                    }
                    uuidFileName =  UUID.randomUUID().toString() + fileExt;
                    paraMap.put("filenameuuid",uuidFileName );
                    String str = HttpPost.post(uploadUrl, paraMap , files);
                    System.out.println("str--->>>" + str);
                    msg.what = SUCCESS;
                } catch (Exception e) {
                    msg.what = FAILD;
                }
                mHandler.sendMessage(msg);
            }
        }.start();
    }

    private String fun(String msg){

        int i = msg.length();
        int j = msg.lastIndexOf("/") + 1;

        String a = msg.substring(j, i) ;
        System.out.println(a);
        return a;
    }
}