package com.curry.stephen.lcandroidlib.net;

/**
 * ErrorType赋值规范：成功为0，在访问MobileAP接口时发生异常（如网络不稳定等）为负值，在MobileAPI在服务器执行过程中发生异常为正值，Cookie失效为1。
 * Created by Administrator on 2017/1/25.
 */
public class Response {

    private boolean mIsError;
    private int mErrorType;
    private String mErrorMessage;
    private String mResult;

    public Response() {
    }

    public Response(boolean isError, int errorType, String errorMessage, String result) {
        mIsError = isError;
        mErrorType = errorType;
        mErrorMessage = errorMessage;
        mResult = result;
    }

    public boolean isError() {
        return mIsError;
    }

    public void setError(boolean error) {
        mIsError = error;
    }

    public int getErrorType() {
        return mErrorType;
    }

    public void setErrorType(int errorType) {
        mErrorType = errorType;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        mErrorMessage = errorMessage;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }
}
