package com.yyquan.zkzx.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yyquan.zkzx.R;
import com.yyquan.zkzx.util.FilePathResolver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileUploadActivity extends Activity implements OnClickListener {

	public static final int SUCCESS = 2;
	public static final int FAILD = 3;
	public static int RESULT_LOAD_FILE = 1;
	private TextView cancel;
	private TextView upload;
	private EditText pathView;
	private Button buttonLoadImage;
	private Button buttonBrowseFile;
	private String picturePath;
	private View show;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_upload);
		initView();
		initData();
	}

	private Handler mHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {

			case SUCCESS:
				show.setVisibility(View.INVISIBLE);
				picturePath = "";
				pathView.setText(picturePath);
				Toast.makeText(getApplicationContext(), "上传成功！", Toast.LENGTH_LONG).show();
				break;
			case FAILD:
				show.setVisibility(View.INVISIBLE);
				Toast.makeText(getApplicationContext(), "上传失败！", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
			return false;
		}

	});

	private void initView() {
		cancel = (TextView) findViewById(R.id.cancel);
		upload = (TextView) findViewById(R.id.upload);
		buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
		buttonBrowseFile = (Button) findViewById(R.id.buttonBrowseFile);

		cancel.setOnClickListener(this);
		upload.setOnClickListener(this);
		buttonLoadImage.setOnClickListener(this);
		buttonBrowseFile.setOnClickListener(this);
		show = findViewById(R.id.show);
		pathView = (EditText) findViewById(R.id.file_path);
		pathView.setKeyListener(null);
	}

	private void initData() {
		picturePath = "";
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel:
			finish();
			break;
		case R.id.buttonLoadPicture:
			Intent intent = new Intent(getApplicationContext(), FileSelectActivity.class);

			startActivityForResult(intent, RESULT_LOAD_FILE);
			break;
		case R.id.upload:
			uploadFile();
			break;
		 case R.id.buttonBrowseFile:
			showFileChooser();
			break;
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
			picturePath = data.getStringExtra("path");
			pathView.setText(picturePath);
		}
		if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {
			// Get the Uri of the selected file
			Uri uri = data.getData();
			Log.d(TAG, "File Uri: " + uri.toString());
			// Get the path
			String path = null;
			try {
				path = FilePathResolver.getPath(this.getApplicationContext(), uri);
				picturePath = path;
				pathView.setText(path);
				Toast.makeText(this,"selected path:" + path, Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.d(TAG, "File Path: " + path);
			// Get the file instance
			// File file = new File(path);
			// Initiate the upload
		}
	}
	private String GetFileName(String msg){
		int i = msg.length();
		int j = msg.lastIndexOf("/") + 1;
		String a = msg.substring(j, i) ;
		System.out.println(a);
		return a;
	}


	private void uploadFile() {
		show.setVisibility(View.VISIBLE);
		new Thread() {
			@Override
			public void run() {
				Message msg = Message.obtain();
				// 服务器的访问路径
				String uploadUrl = ((GlobalApplication) getApplicationContext()).ip_dservlet;
				Map<String, File> files = new HashMap<String, File>();
				
				String name = GetFileName(picturePath);
				files.put(name, new File(picturePath));
				//files.put("test.jpg", new File(picturePath));
				Log.d("str--->>>", picturePath);
				try {
					String str = HttpPost.post(uploadUrl, new HashMap<String,String>(), files);
					System.out.println("str--->>>" + str);
					msg.what = SUCCESS;
				} catch (Exception e) {
					msg.what = FAILD;
				}
				mHandler.sendMessage(msg);
				
			}
		}.start();
	}

	@Override
	protected void onDestroy() {
		show.setVisibility(View.INVISIBLE);
		super.onDestroy();
	}
}