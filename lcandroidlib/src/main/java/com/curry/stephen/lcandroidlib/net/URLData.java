package com.curry.stephen.lcandroidlib.net;

/**
 * Mobile API封装实体类。
 * 包含了Mobile API名称、过期时间、http请求类型与mock测试类全名。
 * @author Stephen Curry
 * @since lcandroidlib 0.1
 */
public class URLData {

    /**
     * Mobile API名称。
     */
    private String mKey;

    /**
     * 缓存过期时间，针对于http get，http post设置为0。
     */
    private long mExpires;

    /**
     * 请求类型，如get或post等。
     */
    private String mNetType;

    /**
     * Mobile API URL。
     */
    private String mUrl;

    /**
     * 为mock测试使用的Mock Class全路径类型，程序运行时动态加载。
     */
    private String mMockClass;

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public long getExpires() {
        return mExpires;
    }

    public void setExpires(long expires) {
        mExpires = expires;
    }

    public String getNetType() {
        return mNetType;
    }

    public void setNetType(String netType) {
        mNetType = netType;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getMockClass() {
        return mMockClass;
    }

    public void setMockClass(String mockClass) {
        mMockClass = mockClass;
    }

    @Override
    public String toString() {
        return "URLData{" +
                "mKey='" + mKey + '\'' +
                ", mExpires=" + mExpires +
                ", mNetType='" + mNetType + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", mMockClass='" + mMockClass + '\'' +
                '}';
    }
}
