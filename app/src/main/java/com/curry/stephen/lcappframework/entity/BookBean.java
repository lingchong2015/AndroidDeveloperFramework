package com.curry.stephen.lcappframework.entity;

/**
 * Created by Administrator on 2017/1/28.
 */

public class BookBean {

    private static final long serialVersionUID = 1L;

    private int mId;
    private String mName;
    private float mPrice;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        mPrice = price;
    }

    @Override
    public String toString() {
        return "BookBean{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mPrice=" + mPrice +
                '}';
    }
}
