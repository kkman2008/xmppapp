package com.yyquan.zkzx.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.yyquan.zkzx.R;
import com.yyquan.zkzx.fragment.regster.PasswordFragment;
import com.yyquan.zkzx.fragment.regster.PhoneRegsterFragment;
import com.yyquan.zkzx.fragment.regster.PhoneRegsterToMessageFragment;
import com.yyquan.zkzx.fragment.regster.PasswordFragment;


public class PhoneRegsterActivity extends FragmentActivity {
// 电话注册
    FragmentManager manager;
    public String phone;
    PhoneRegsterToMessageFragment ptf;
    PasswordFragment pf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_phone_regster);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.regster_FrameLayout,
                    new PhoneRegsterFragment()).commit();
        }
    }
    /**
     * 跳转到用户资料注册页面
     */
    public void gotoPhoneRegsterToMessgae() {
        if (manager == null) {
            manager = this.getSupportFragmentManager();
        }
        FragmentTransaction ft = manager.beginTransaction();
        ptf = (PhoneRegsterToMessageFragment) manager
                .findFragmentByTag("message");
        if (ptf == null) {
            ptf = new PhoneRegsterToMessageFragment();
        }
        ft.replace(R.id.regster_FrameLayout, ptf, "message");
        ft.addToBackStack(null);
        ft.commit();
    }


    /**
     * 跳转到用户资料注册页面
     */
    public void gotoGetPassword() {
        if (manager == null) {
            manager = this.getSupportFragmentManager();
        }
        FragmentTransaction ft = manager.beginTransaction();
        pf = (PasswordFragment) manager
                .findFragmentByTag("password");
        if (pf == null) {
            pf = new PasswordFragment();

        }
        ft.replace(R.id.regster_FrameLayout, pf, "password");
        ft.addToBackStack(null);
        ft.commit();
    }
}
