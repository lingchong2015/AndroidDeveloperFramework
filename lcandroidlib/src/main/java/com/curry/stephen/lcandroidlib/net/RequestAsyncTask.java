package com.curry.stephen.lcandroidlib.net;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/1/26.
 */

public abstract class RequestAsyncTask extends AsyncTask<String, Void, Response> {

    public abstract void onSuccess(String content);

    public abstract void onFail(String errorMessage);

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Response doInBackground(String... params) {
        switch (params[0]) {
            case "getJson":
                return getResponseFromURL(params[1]);
            case "postJson":
                return postResponseFromURL(params[1], params[2]);
            default:
                return getResponseFromURL(params[1]);
        }
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);

        if (response != null && !response.isError()) {
            onSuccess(response.getResult());
        } else {
            if (response != null) {
                onFail(response.getErrorMessage());
            } else {
                onFail("请求服务发生未知错误！");
            }
        }
    }

    private Response getResponseFromURL(String mobileAPI) {
        Response response = new Response();
        try {
            URL url = new URL(mobileAPI);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, length);
                    byteArrayOutputStream.flush();
                }
                response.setError(false);
                response.setErrorType(0);
                response.setErrorMessage("");
                response.setResult(byteArrayOutputStream.toString("utf-8"));
                return response;
            } else {
                return failedHandler(code);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            response.setError(true);
            response.setErrorType(-3);
            response.setErrorMessage(e.getMessage());
            response.setResult("");
            return response;
        }
    }

    private Response postResponseFromURL(String mobileAPI, String post) {
        Response response = new Response();
        try {
            URL url = new URL(mobileAPI);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            printWriter.write(post);
            printWriter.flush();
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, length);
                    byteArrayOutputStream.flush();
                }
                response.setError(false);
                response.setErrorType(0);
                response.setErrorMessage("");
                response.setResult(byteArrayOutputStream.toString("utf-8"));
                return response;
            } else {
                return failedHandler(code);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            response.setError(true);
            response.setErrorType(-3);
            response.setErrorMessage(e.getMessage());
            response.setResult("");
            return response;
        }
    }

    private Response failedHandler(int code) {
        Response response = new Response();
        if (code == 404) {
            response.setError(true);
            response.setErrorType(-1);
            response.setErrorMessage("未找到请求资源,请查看网络连接地址是否设置正确");
            response.setResult("");
            return response;
        } else if (code == 500) {
            response.setError(true);
            response.setErrorType(2);
            response.setErrorMessage("服务器出现错误");
            response.setResult("");
            return response;
        } else {
            response.setError(true);
            response.setErrorType(-2);
            response.setErrorMessage("未处理异常抛出,连接终止");
            response.setResult("");
            return response;
        }
    }
}
