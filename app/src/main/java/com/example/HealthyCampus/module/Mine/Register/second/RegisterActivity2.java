package com.example.HealthyCampus.module.Mine.Register.second;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.engine.MyTextWatcher;
import com.example.HealthyCampus.common.helper.UserHelper;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.utils.DialogUtil;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.LogUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.StringUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.MainActivity;
import com.example.HealthyCampus.module.Mine.Login.LoginActivity;
import com.example.HealthyCampus.module.Mine.Register.first.RegisterActivity1;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

public class RegisterActivity2 extends BaseActivity<RegisterContract2.View, RegisterContract2.Presenter> implements RegisterContract2.View {

    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.etRepeatPwd)
    EditText etRepeatPwd;
    @BindView(R.id.etInviteCode)
    EditText etInviteCode;
    @BindView(R.id.showPassword1)
    ImageView showPassword1;
    @BindView(R.id.showPassword2)
    ImageView showPassword2;
    @BindView(R.id.returnBack2)
    ImageView returnBack2;
    @BindView(R.id.btnSign)
    Button btnSign;
    @BindView(R.id.currentPhone)
    TextView currentPhone;
    @BindView(R.id.registerPage)
    LinearLayout registerPage;


    private boolean PasswordVisible1 = false;
    private boolean PasswordVisible2 = false;
    private boolean isExistPassword1 = false;
    private boolean isExistPassword2 = false;
    private Bundle bundle;

    private MaterialDialog progressDialog;

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.colorPrimary);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.user_register2);
    }

    @Override
    protected RegisterContract2.Presenter createPresenter() {
        return new RegisterPresenter2();
    }

    @Override
    protected void initView() {
        initPhone();
        initProgressView();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        try {
            mPresenter.foucusRegisterEditText();
            mPresenter.listenRegisterEditText();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void focusRegisterEditTextStatus() {
        etPwd.setOnFocusChangeListener((v, hasFocus) -> setRegisterHint(etPwd));

        etRepeatPwd.setOnFocusChangeListener((v, hasFocus) -> setRegisterHint(etRepeatPwd));

        etInviteCode.setOnFocusChangeListener((v, hasFocus) -> setRegisterHint(etInviteCode));
    }

    @Override
    public void setRegisterHint(final EditText editText) {
        String registerHint;
        if (!TextUtils.isEmpty(editText.getHint().toString().trim())) {
            registerHint = editText.getHint().toString().trim();
            editText.setTag(registerHint);
            editText.setHint("");
        } else {
            registerHint = editText.getTag().toString().trim();
            editText.setHint(registerHint);
        }
    }

    @Override
    public void setShowPassword(final ImageView imageView, final EditText editText, boolean isShow) {
        if (isShow) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.password_visible));
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.password_invisible));
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @Override
    public void listenRegisterEditTextStatus() {
        etPwd.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.OnComplete() {
            @Override
            public void onComplete() {
                setSeePasswordEnable(showPassword1, true);
                isExistPassword1 = true;
            }

            @Override
            public void onEdit() {
                setSeePasswordEnable(showPassword1, false);
                isExistPassword1 = false;
            }

            @Override
            public void onFinish() {
                setSignButtonEnable();
            }
        }));

        etRepeatPwd.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.OnComplete() {
            @Override
            public void onComplete() {
                setSeePasswordEnable(showPassword2, true);
                isExistPassword2 = true;
            }

            @Override
            public void onEdit() {
                setSeePasswordEnable(showPassword2, true);
                isExistPassword2 = true;
            }

            @Override
            public void onFinish() {
                setSignButtonEnable();
            }
        }));
    }

    @Override
    public void setSeePasswordEnable(final ImageView imageView, boolean value) {
        imageView.setEnabled(value);
    }

    @Override
    public void setSignButtonEnable() {
        if (isExistPassword1 && isExistPassword2) {
            btnSign.setEnabled(true);
            btnSign.setBackgroundColor(getResources().getColor(R.color.clickable_color));
        } else {
            btnSign.setEnabled(false);
            btnSign.setBackgroundColor(getResources().getColor(R.color.not_clickable_color));
        }
    }

    @Override
    public void jumpToMain() {
        startActivity(new Intent(RegisterActivity2.this, MainActivity.class));
        finish();
    }

    @Override
    public void jumpToRegister1() {
        startActivity(new Intent(RegisterActivity2.this, RegisterActivity1.class));
        finish();
    }

    @Override
    public void jumpToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    public void initPhone() {
//        currentPhone.setText("13648978852");
        Intent intent = getIntent();
        bundle = intent.getExtras();
        if (null == bundle) {
            jumpToRegister1();
        }
        String phoneNo = bundle.getString("phone");

        if (TextUtils.isEmpty(phoneNo)) {
            jumpToRegister1();
        } else {
            assert phoneNo != null;
            phoneNo = phoneNo.substring(0, 3) + " **** " + phoneNo.substring(7);
            currentPhone.setText(phoneNo);
        }
    }

    @Override
    public void showProgressView() {
        progressDialog.show();
    }

    @Override
    public void initProgressView() {
        MaterialDialog.Builder builder = DialogUtil.ProgressView(this);
        builder.title(R.string.network_request)// 标题
                .content(R.string.netwotk_registering);
        progressDialog = builder.build();// 创建对话框
    }

    @Override
    public void showTipsView(String account) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.register2_register_success)
                .setMessage("您的登录账号为:" + account + "\n\n点击进入首页.")
                .setPositiveButton("确定", (dialog, which) -> {
                    dialog.dismiss();
                    jumpToMain();
                })
                .setOnCancelListener(dialog -> {
                    dialog.dismiss();
                    jumpToMain();
                })
                .show();
    }

    @Override
    public void dismissProgressView() {
        progressDialog.dismiss();
    }

    @Override
    public void showRegisterError(Throwable throwable) {
        try {
            if (throwable instanceof HttpException) {
                Response httpException = ((HttpException) throwable).response();
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
//                Log.e("LoginActivity" + "123456", "throwable.toString:" + throwable.toString());
//                Log.e("LoginActivity" + "123456", "throwable.getMessage:" + throwable.getMessage());
//                Log.e("LoginActivity" + "123456", "httpException.headers:" + httpException.headers());
//                Log.e("LoginActivity" + "123456", "httpException.message:" + httpException.message());
//                Log.e("LoginActivity" + "123456", "httpException.body:" + httpException.body());
//                Log.e("LoginActivity" + "123456", "httpException.errorBody:" + httpException.errorBody().toString());
                switch (response.code) {
                    case 999:
                        ToastUtil.show(getContext(), R.string.data_lose);
                        break;
                    case 1000:
                        ToastUtil.show(getContext(), "Bad Server");
                        break;
                    case 1003:
                        ToastUtil.show(getContext(), "Invalid Parameter");
                        break;
                    case 1004:
                        ToastUtil.show(getContext(), "电话号码已注册");
                        break;
                    default:
                        ToastUtil.show(getContext(), "未知错误1:" + throwable.getMessage());
                        break;
                }
            } else {
                ToastUtil.show(this, "未知错误:" + throwable.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.showPassword1)
    public void showPassword1(View view) {
        setShowPassword(showPassword1, etPwd, PasswordVisible1);
        PasswordVisible1 = !PasswordVisible1;
    }

    @OnClick(R.id.showPassword2)
    public void showPassword2(View view) {
        setShowPassword(showPassword2, etRepeatPwd, PasswordVisible2);
        PasswordVisible2 = !PasswordVisible2;
    }

    @OnClick(R.id.btnSign)
    public void btnSignRespond(View view) {
        String pwd = etPwd.getText().toString().trim();
        String repwd = etRepeatPwd.getText().toString().trim();
        String phoneNo = bundle.getString("phone");
//        String phoneNo = "13486799112";


//        LogUtil.logE("RegisterActivity2" + "123456", "pwd" + pwd);
//        LogUtil.logE("RegisterActivity2" + "123456", "StringUtil.isPassword(pwd)" + StringUtil.isPassword(pwd));

        if (!StringUtil.isPassword(pwd)) {
            ToastUtil.show(this, R.string.register2_password_not_in_Standard);
        } else if (!repwd.equals(pwd)) {
            ToastUtil.show(this, R.string.register2_password_not_in_matching);
        } else {
//            ToastUtil.show(this, "注册成功");
            RegisterFrom registerFrom = mPresenter.encapsulationData(phoneNo, pwd);
            try {
                mPresenter.register(registerFrom);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.returnBack2)
    public void btnBackRespond(View view) {
        jumpToLogin();
    }


}
