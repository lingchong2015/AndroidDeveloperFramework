package com.curry.stephen.lcandroidlib.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.curry.stephen.lcandroidlib.net.LCRequestManager;

/**
 * 业务无关公用逻辑Activity基类.
 * Created by Administrator on 2017/1/21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 初始化变量，包括Intent数据与Activity内的成员变量。
     */
    protected abstract void initVariables();

    /**
     * 加载Layout布局文件，初始化控件。
     * @param savedInstanceState 保存状态参数的键值对。
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 调用MobileAPI获取数据。
     */
    protected abstract void loadData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables();
        initViews(savedInstanceState);
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LCRequestManager.cancelAllRequest(true);
    }

    @Override
    protected void onPause() {
        super.onPause();

        LCRequestManager.cancelAllRequest(true);
    }
}
