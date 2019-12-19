package ze.example.com.e_school.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;

import ze.example.com.e_school.R;
import ze.example.com.e_school.asynctask.PostAsyncTask;
import ze.example.com.e_school.constant.Constant;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, ViewTreeObserver.OnGlobalLayoutListener, TextWatcher {
    private String TAG = "ifu25";

    private ImageButton mIbNavigationBack;
    private LinearLayout mLlLoginPull;
    private View mLlLoginLayer;
    private LinearLayout mLlLoginOptions;
    private EditText mEtLoginUsername;
    private EditText mEtLoginPwd;
    private LinearLayout mLlLoginUsername;
    private ImageView mIvLoginUsernameDel;
    private Button mBtLoginSubmit;
    private LinearLayout mLlLoginPwd;
    private ImageView mIvLoginPwdDel;
    private ImageView mIvLoginLogo;
    private LinearLayout mLayBackBar;
    private TextView mTvLoginForgetPwd;
    private Button mBtLoginRegister;

    //全局Toast
    private Toast mToast;
    private int mLogoHeight;
    private int mLogoWidth;
    static String a="";
    int flag=0;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    //拿到数据
                    a=(String) msg.obj;
                    flag=1;
                    if (a.equals("100")){//200密码错误 300用户不存在
                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("SUCCESS")
                                .setContentText("登录成功")
                                .setConfirmText("确定")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }else if (a.equals("200")){
                        new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error")
                                .setConfirmText("确定")
                                .setContentText("密码错误")
                                .show();
                    }else if (a.equals("300")){
                        new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error")
                                .setConfirmText("确定")
                                .setContentText("用户不存在")
                                .show();
                    }else {
                        new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error")
                                .setConfirmText("确定")
                                .setContentText("请检查网络连接状态")
                                .show();
                    }
                    a="";
                    break;
                case 2:
                    if (a.equals("100")){//200密码错误 300用户不存在
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    }else if (a.equals("200")){
                        new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error")
                                .setConfirmText("确定")
                                .setContentText("密码错误")
                                .show();
                    }else if (a.equals("300")){
                        new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error")
                                .setConfirmText("确定")
                                .setContentText("用户不存在")
                                .show();
                    }else {
                        new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error")
                                .setConfirmText("确定")
                                .setContentText("请检查网络连接状态")
                                .show();
                    }
                    flag=2;
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();
    }

    //初始化视图
    private void initView() {
        //登录层、下拉层、其它登录方式层
        mLlLoginLayer = findViewById(R.id.ll_login_layer);
        //导航栏+返回按钮
        mLayBackBar = findViewById(R.id.ly_retrieve_bar);
        mIbNavigationBack = findViewById(R.id.ib_navigation_back);
        //logo
        mIvLoginLogo = findViewById(R.id.iv_login_logo);
        //username
        mLlLoginUsername = findViewById(R.id.ll_login_username);
        mEtLoginUsername = findViewById(R.id.et_login_username);
        mIvLoginUsernameDel = findViewById(R.id.iv_login_username_del);
        //passwd
        mLlLoginPwd = findViewById(R.id.ll_login_pwd);
        mEtLoginPwd = findViewById(R.id.et_login_pwd);
        mIvLoginPwdDel = findViewById(R.id.iv_login_pwd_del);
        //提交、注册
        mBtLoginSubmit = findViewById(R.id.bt_login_submit);
        mBtLoginRegister = findViewById(R.id.bt_login_register);
        //忘记密码
        mTvLoginForgetPwd = findViewById(R.id.tv_login_forget_pwd);
        mTvLoginForgetPwd.setOnClickListener(this);
        //注册点击事件
        mIbNavigationBack.setOnClickListener(this);
        mEtLoginUsername.setOnClickListener(this);
        mIvLoginUsernameDel.setOnClickListener(this);
        mBtLoginSubmit.setOnClickListener(this);
        mBtLoginRegister.setOnClickListener(this);
        mEtLoginPwd.setOnClickListener(this);
        mIvLoginPwdDel.setOnClickListener(this);
        //注册其它事件
        mLayBackBar.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mEtLoginUsername.setOnFocusChangeListener(this);
        mEtLoginUsername.addTextChangedListener(this);
        mEtLoginPwd.setOnFocusChangeListener(this);
        mEtLoginPwd.addTextChangedListener(this);
    }
    //点击
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_login_submit:
                //获取编辑文本内的用户密码
                String param="userAccount="+mEtLoginUsername.getText().toString().trim()+"&userPassword="+mEtLoginPwd.getText().toString().trim();
                new PostAsyncTask(param,handler).execute(Constant.URL_Login);
                //判断账号密码正确 登陆时该账号无人使用


//                Intent intent = new Intent(this,MainActivity.class);
//                startActivity(intent);
//                 Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                break;
                case R.id.bt_login_register:
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Warm")
                            .setContentText("不支持注册,请联系管理员")
                            .setConfirmText("嗯嗯")
                            .show();
                    break;
            case R.id.tv_login_forget_pwd:
                new SweetAlertDialog(this)
                        .setTitleText("忘了就忘了滚")
                        .setConfirmText("脑瘫")
                        .show();
                break;
        }
    }
    @Override
    public void afterTextChanged(Editable editable) {

    }



    @Override
    public void onFocusChange(View view, boolean b) {
        int id = view.getId();

        if (id == R.id.et_login_username) {
            if (b) {
                mLlLoginUsername.setActivated(true);
                mLlLoginPwd.setActivated(false);
            }
        } else {
            if (b) {
                mLlLoginPwd.setActivated(true);
                mLlLoginUsername.setActivated(false);
            }
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
    @Override
    public void onGlobalLayout() {

    }
    /**
     * 点击非编辑区域收起键盘
     * 获取点击事件
     */
    @CallSuper
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() ==  MotionEvent.ACTION_DOWN ) {
            View view = getCurrentFocus();
            if (isShouldHideKeyBord(view, ev)) {
                hideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判定当前是否需要隐藏
     */
    protected boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
            //return !(ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
