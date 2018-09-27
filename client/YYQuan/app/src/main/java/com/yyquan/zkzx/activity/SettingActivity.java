package com.yyquan.zkzx.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yyquan.zkzx.R;
import com.yyquan.zkzx.util.SharedPreferencesUtil;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    public  static  String TAG = "SettingActivity";
    private EditText etServerIP;
    private EditText etServerPort;
    private Button btnUpdate;
    private  LinearLayout llBack;

    public void initialView() {
        etServerIP = (EditText) findViewById(R.id.et_ip);
        etServerIP.setText( SharedPreferencesUtil.getString(this, "IPAddress", "IPAddress"));
        etServerPort = (EditText) findViewById(R.id.et_port);
        etServerPort.setText(SharedPreferencesUtil.getString(this,"IPAddress","Port"));
        btnUpdate = (Button)findViewById(R.id.btn_save);
        llBack = (LinearLayout) findViewById(R.id.server_config_layout__back);
        btnUpdate.setOnClickListener(this);
        llBack.setOnClickListener(this);
        //etServerIP.setOnClickListener(this);
    }
    @Override
    public void onCreate(Bundle state){
        super.onCreate(state);
        setContentView(R.layout.activity_setting);
        initialView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                //Update the server IP
                String etIPStr = etServerIP.getText().toString().trim();
                SharedPreferencesUtil.setString(this, "IPAddress", "IPAddress", etIPStr);
                String etPortStr = etServerPort.getText().toString().trim();
                SharedPreferencesUtil.setString(this, "IPAddress", "Port",etPortStr);
                break;
            case R.id.et_ip:
                Log.d(TAG, "onClick: et_ip...");
            case R.id.server_config_layout__back:
                finish();
                break;
            default:
                break;
        }
    }
}
