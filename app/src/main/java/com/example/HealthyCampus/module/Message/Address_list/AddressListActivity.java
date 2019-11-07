package com.example.HealthyCampus.module.Message.Address_list;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.AddressListAdapter;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.vo.AddressListVo;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.SideBar;
import com.example.HealthyCampus.framework.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;


public class AddressListActivity extends BaseActivity<AddressListContract.View, AddressListContract.Presenter> implements AddressListContract.View {

    @BindView(R.id.sideBar)
    SideBar sideBar;
    @BindView(R.id.dialog)
    TextView dialog;
    @BindView(R.id.sortListView)
    ListView sortListView;
    @BindView(R.id.addressListNo)
    LinearLayout addressListNo;
    @BindView(R.id.AddressFrame)
    FrameLayout AddressFrame;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    private AddressListAdapter listAdapter;
    private ArrayList<AddressListVo> addressLists;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private AddressPinyinComparator pinyinComparator = new AddressPinyinComparator();

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.message_address_list);
    }

    @Override
    protected AddressListContract.Presenter createPresenter() {
        return new AddressListPresenter();
    }

    @Override
    protected void initView() {
        sideBar.setTextView(dialog);
        setCustomActionBar();
    }


    private void setCustomActionBar() {
        tvTitle.setText(R.string.message_address_list);
        ivBack.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    @Override
    public void showError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                if (response.code == 1001) {
                    Log.e("AddressListActi" + "123456", "response.toString:" + response.toString());
                    ToastUtil.show(this, "用户信息错误");
                } else {
                    ToastUtil.show(this, "未知错误:" + throwable.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }catch(IllegalStateException e)
            {
                e.printStackTrace();
            }
        } else {
            ToastUtil.show(this, "未知错误:" + throwable.getMessage());
        }
    }

    @Override
    public void showFriends(ArrayList<AddressListVo> addressLists) {
        if (addressLists.size() > 0) {
            Collections.sort(addressLists, pinyinComparator);   //排序
            listAdapter = new AddressListAdapter(this, addressLists);
            sortListView.setAdapter(listAdapter);
        }
    }

    // 设置右侧触摸监听
    @Override
    public void listenTouchStatus() {
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = listAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });
    }

    @Override
    public void listenItemStatus() {
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nickname = addressLists.get(position).getNickname();
                Toast.makeText(getContext(), "nickname:" + nickname, Toast.LENGTH_SHORT).show();
                Log.e("AddressListVo" + "123456", "country:" + nickname);
            }
        });
    }

    @Override
    public void sidebarShow() {
        sideBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void showViewByDataStatus(boolean value) {
        AddressFrame.setVisibility(value ? View.VISIBLE : View.GONE);
        addressListNo.setVisibility(value ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter.getFriendsInformation();
        mPresenter.listenTouch();
        mPresenter.listenItem();
    }


    @Override
    public Context getContext() {
        return this;
    }


}