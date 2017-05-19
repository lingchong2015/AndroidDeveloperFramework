package com.curry.stephen.lcandroidlib.application;

import android.app.Application;

import com.curry.stephen.lcandroidlib.cache.CacheManager;
import com.curry.stephen.lcandroidlib.entity.User;
import com.curry.stephen.lcandroidlib.net.LCRequestManager;
import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by Administrator on 2017/2/15.
 */

public class LCApplication extends Application {

    private AsyncHttpClient mAsyncHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();

        mAsyncHttpClient = LCRequestManager.getAsyncHttpClient();
        CacheManager.getInstance().initCacheDirectory();
        User.getInstance();
    }
}
