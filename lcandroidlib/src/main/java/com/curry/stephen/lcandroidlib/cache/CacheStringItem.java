package com.curry.stephen.lcandroidlib.cache;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/16.
 */

public class CacheStringItem implements Serializable {

    private String mKey;
    private String mData;
    private long timeStamp;

    public CacheStringItem(String key, String data, long timeStamp) {
        mKey = key;
        mData = data;
        this.timeStamp = timeStamp;
    }

    public String getKey() {
        return mKey;
    }

    public String getData() {
        return mData;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
