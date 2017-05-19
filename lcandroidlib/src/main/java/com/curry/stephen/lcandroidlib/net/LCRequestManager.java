package com.curry.stephen.lcandroidlib.net;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by Administrator on 2017/1/30.
 */

public class LCRequestManager {

    private static AsyncHttpClient sAsyncHttpClient;

    private LCRequestManager() {

    }

    public static AsyncHttpClient getAsyncHttpClient() {
        if (sAsyncHttpClient == null) {
            sAsyncHttpClient = new AsyncHttpClient();
        }
        return sAsyncHttpClient;
    }

    public static void cancelAllRequest(boolean mayInterruptIfRunning) {
        if (sAsyncHttpClient != null) {
            sAsyncHttpClient.cancelAllRequests(mayInterruptIfRunning);
        }
    }
}
