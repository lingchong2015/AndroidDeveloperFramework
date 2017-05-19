package com.curry.stephen.lcappframework.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/20.
 */

public class UserInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mUsername;
    private String mRealName;
    private String mPassword;

    public UserInfoBean() {
    }

    public UserInfoBean(String username, String realName, String password) {
        mUsername = username;
        mRealName = realName;
        mPassword = password;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getRealName() {
        return mRealName;
    }

    public void setRealName(String realName) {
        mRealName = realName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "mUsername='" + mUsername + '\'' +
                ", mRealName='" + mRealName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                '}';
    }
}
