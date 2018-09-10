package com.yyquan.jzh.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yyquan.jzh.R;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class FileSelectActivity extends ListActivity {
	private static final String root = new String(Environment
			.getExternalStorageDirectory().getPath() + File.separator);
	private TextView tv;// 显示文件的目录
	private File[] files;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fileupload);
		tv = (TextView) findViewById(R.id.currPath);
		getFiles(root);
	}

	public void getFiles(String path) {
		tv.setText(path);
		File f = new File(path);
		// 得到所有子文件和文件夹
		File[] tem = f.listFiles();
		// 如果当前的目录不是在顶层目录，就把父目录要到files数组中的第一个
		if (!path.equals(root)) {
			files = new File[tem.length + 1];
			System.arraycopy(tem, 0, files, 1, tem.length);
			files[0] = f.getParentFile();
		} else {
			files = tem;
		}
		sortFilesByDirectory(files);
		// 为ListActivity设置Adapter
		setListAdapter(new Adapter(this, files, files.length == tem.length));
	}

	// 对文件进行排序
	private void sortFilesByDirectory(File[] files) {
		Arrays.sort(files, new Comparator<File>() {
			public int compare(File f1, File f2) {
				return Long.valueOf(f1.length()).compareTo(f2.length());
			}
		});
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		File f = files[position];
		if (!f.canRead()) {
			Toast.makeText(this, "文件不可读", Toast.LENGTH_SHORT).show();
			return;
		}
		if (f.isFile()) {// 为文件
			String path = f.getAbsolutePath();
			Intent intent = new Intent();
			intent.putExtra("path", path);
			setResult(FileUploadActivity.RESULT_LOAD_FILE, intent);
			finish();
		} else {
			getFiles(f.getAbsolutePath());
		}
	}

	class Adapter extends BaseAdapter {
		private File[] files;
		private boolean istop;
		private Context context;

		public Adapter(Context context, File[] files, boolean istop) {
			this.context = context;
			this.files = files;
			this.istop = istop;
		}

		@Override
		public int getCount() {
			return files.length;
		}

		@Override
		public Object getItem(int position) {
			return files[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				holder = new Holder();
				convertView = View.inflate(context, R.layout.item_fileupload,
						null);
				holder.iv = (ImageView) convertView
						.findViewById(R.id.adapter_icon);
				holder.tv = (TextView) convertView
						.findViewById(R.id.adapter_txt);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			// 设置convertView中控件的值
			setconvertViewRow(position, holder);
			return convertView;
		}

		private void setconvertViewRow(int position, Holder holder) {
			File f = files[position];
			holder.tv.setText(f.getName());
			if (!istop && position == 0) {// 不是在顶层目录
				// 加载后退图标
				holder.iv.setImageResource(R.drawable.back_up);
			} else if (f.isFile()) {// 是文件
				// 加载文件图标
				holder.iv.setImageResource(R.drawable.file);
			} else {// 文件夹
					// 加载文件夹图标
				holder.iv.setImageResource(R.drawable.dir);
			}
		}

		class Holder {
			private ImageView iv;
			private TextView tv;
		}
	}
}
