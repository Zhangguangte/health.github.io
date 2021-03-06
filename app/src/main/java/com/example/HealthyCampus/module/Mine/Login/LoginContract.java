package com.example.HealthyCampus.module.Mine.Login;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

public interface LoginContract {

    //专门来处理view的变化
    interface View extends BaseView {
        Context getContext();

        void setLoginButtonEnable();     //登录按钮点击

        void setSeePasswordEnable(boolean value);    //可视密码图标点击

        void setShowPassword();                     //设置密码可视

        void setLoginHint(EditText editText, ImageView imageView);        //设置输入框提示内容

        void listenLoginEditTextStatus();        //监听输入框输入变化

        void focusLoginEditTextStatus();        //焦点输入框变化

        void cleanLoginEditText(EditText editText, ImageView imageView);      //清除输入框内的内容

        void setSeeClearEnable(boolean value, ImageView imageView);    //可视清除图标点击/

        void showLoginError(Throwable throwable);        //登录失败

        void jumpToMain();             //跳转主界面

        void showProgressView();       //显示加载视图

        void initProgressView();       //设置加载视图

        void dismissProgressView();       //关闭加载视图
    }

    //处理业务逻辑
    abstract class Presenter extends BasePresenter<LoginContract.View> {

        protected abstract void login(LoginForm loginForm,String password);    //登录

        protected abstract void listenLoginEditText() throws Exception;         //监听输入框内容

        protected abstract LoginForm encapsulationData(String username, String _password);     //封装数据

        protected abstract void foucusLoginEditText() throws Exception;         //焦点输入框

        protected abstract void initUserInformation(UserVo userVo, String password) throws Exception;         //保存用户信息


    }

}
