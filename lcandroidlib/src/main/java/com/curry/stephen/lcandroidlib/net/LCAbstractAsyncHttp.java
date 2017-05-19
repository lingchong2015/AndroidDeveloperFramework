package com.curry.stephen.lcandroidlib.net;

import android.app.ProgressDialog;
import android.content.Context;

import com.curry.stephen.lcandroidlib.R;
import com.curry.stephen.lcandroidlib.cache.CacheManager;
import com.curry.stephen.lcandroidlib.global.Constants;
import com.curry.stephen.lcandroidlib.interfaces.IMockService;
import com.curry.stephen.lcandroidlib.utils.DateTimeHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.cookie.Cookie;

/**
 * Created by Administrator on 2017/2/13.
 */

abstract class LCAbstractAsyncHttp {

    public abstract void successPostJsonCallBack(int statusCode, Header[] headers, JSONObject jsonObject);

    public abstract void failurePostJsonCallBack(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject);

    public abstract void successPostJsonCallBack(int statusCode, Header[] headers, JSONArray response);

    public abstract void failurePostJsonCallBack(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse);

    public abstract void successGetJsonCallBack(int statusCode, Header[] headers, String responseString);

    public abstract void failureGetJsonCallBack(int statusCode, Header[] headers, String responseString, Throwable throwable);

    public abstract void successGetJsonCallBack(int statusCode, Header[] headers, JSONObject response);

    public abstract void successGetJsonCallBack(int statusCode, Header[] headers, JSONArray response);

    public abstract void failureGetJsonCallBack(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse);

    public abstract void failureGetJsonCallBack(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse);

    public abstract void successPostJsonCallBack(int statusCode, Header[] headers, String responseString);

    public abstract void failurePostJsonCallBack(int statusCode, Header[] headers, String responseString, Throwable throwable);

    public abstract void successGetFromCacheCallBack(String data);

    public abstract void successGetMockDataCallBack(String data);

    public abstract void failureGetMockDataCallBack(int statusCode);

    private WeakReference<Context> mContextWeakReference;
    protected ProgressDialog mProgressDialog;

    public ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }

    public void postJson(Context context, URLData urlData, HttpEntity httpEntity) {
        postJson(context, null, urlData, httpEntity);
    }

    public void postJson(Context context, Header[] headers, URLData urlData, HttpEntity httpEntity) {
        mContextWeakReference = new WeakReference<>(context);
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContextWeakReference.get());
            mProgressDialog.setMessage(mContextWeakReference.get().getString(R.string.loading_data));
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();

        if (processByMockClass(urlData.getMockClass())) {
            mProgressDialog.dismiss();
            return;
        }

        LCRequestManager.getAsyncHttpClient().post(mContextWeakReference.get(), urlData.getUrl(), headers, httpEntity, Constants.JSON_CONTENT_TYPE, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                mProgressDialog.dismiss();
                successPostJsonCallBack(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mProgressDialog.dismiss();
                failurePostJsonCallBack(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                mProgressDialog.dismiss();
                successPostJsonCallBack(statusCode, headers, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                mProgressDialog.dismiss();
                failurePostJsonCallBack(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                mProgressDialog.dismiss();
                successPostJsonCallBack(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mProgressDialog.dismiss();
                failurePostJsonCallBack(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public void getJsonUpdateForced(Context context, URLData urlData, RequestParams requestParams) {
        urlData.setExpires(0);
        getJson(context, null, urlData, requestParams);
    }

    public void getJsonUpdateForced(Context context, Header[] headers, URLData urlData, RequestParams requestParams) {
        urlData.setExpires(0);
        getJson(context, headers, urlData, requestParams);
    }

    public void getJson(Context context, URLData urlData, RequestParams requestParams) {
        getJson(context, null, urlData, requestParams);
    }

    public void getJson(Context context, Header[] headers, final URLData urlData, final RequestParams requestParams) {
        mContextWeakReference = new WeakReference<>(context);
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContextWeakReference.get());
            mProgressDialog.setMessage(mContextWeakReference.get().getString(R.string.loading_data));
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();

        if (processByMockClass(urlData.getMockClass())) {
            mProgressDialog.dismiss();
            return;
        }

        if (urlData.getExpires() > 0) {
            String key = AsyncHttpClient.getUrlWithQueryString(true, urlData.getUrl(), requestParams);
            String data = CacheManager.getInstance().cache2String(key);
            if (data != null) {
                mProgressDialog.dismiss();
                successGetFromCacheCallBack(data);
                return;
            }
        }
        LCRequestManager.getAsyncHttpClient().get(mContextWeakReference.get(), urlData.getUrl(), headers, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                mProgressDialog.dismiss();
                if (urlData.getExpires() > 0) {
                    String key = AsyncHttpClient.getUrlWithQueryString(true, urlData.getUrl(), requestParams);
                    CacheManager.getInstance().string2Cache(key, responseString, DateTimeHelper.addSeconds(urlData.getExpires()));
                }
                successGetJsonCallBack(statusCode, headers, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                mProgressDialog.dismiss();
                failureGetJsonCallBack(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                mProgressDialog.dismiss();
                if (urlData.getExpires() > 0) {
                    String key = AsyncHttpClient.getUrlWithQueryString(true, urlData.getUrl(), requestParams);
                    CacheManager.getInstance().string2Cache(key, response.toString(), DateTimeHelper.addSeconds(urlData.getExpires()));
                }
                successGetJsonCallBack(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mProgressDialog.dismiss();
                failureGetJsonCallBack(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                mProgressDialog.dismiss();
                if (urlData.getExpires() > 0) {
                    String key = AsyncHttpClient.getUrlWithQueryString(true, urlData.getUrl(), requestParams);
                    CacheManager.getInstance().string2Cache(key, response.toString(), DateTimeHelper.addSeconds(urlData.getExpires()));
                }
                successGetJsonCallBack(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mProgressDialog.dismiss();
                failureGetJsonCallBack(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private boolean processByMockClass(String mockClass) {
        if (mockClass != null) {
            try {
                IMockService mockService = (IMockService) Class.forName(mockClass).newInstance();
                String data = mockService.getJsonData();
                int statusCode = mockService.getStatusCode();
                if (statusCode == 200) {
                    successGetMockDataCallBack(data);
                } else {
                    failureGetMockDataCallBack(statusCode);
                }
                return true;
            } catch (InstantiationException e) {
                e.printStackTrace();
                return false;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }
}
