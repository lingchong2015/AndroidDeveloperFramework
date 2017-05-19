package com.curry.stephen.lcandroidlib.entity;

import com.curry.stephen.lcandroidlib.utils.AndroidFileSystemHelper;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/2/19.
 */

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mUsername;
    private String mPassword;
    private String mAccount;
    private String mRealName;
    private boolean mIsLogin;
    private int mIntField;
    private long mLongField;
    private boolean mBooleanFiled;
    private List<String> mStringListFiled;
    private long mTimeFiled;
    private String[][] mStrings2DimesFiled;
    private int[][] mInts22DimensFiled;
    private Calendar mCalendarFiled;
    private static User sUser;

    private static final String USER_CACHE_PATH = AndroidFileSystemHelper.getExternalStorageDirectory().getPath() + "/lcusercache/";
    private static final String TAG = User.class.getSimpleName();

    private User() {

    }

    public static User getInstance() {
        if (sUser == null) {
            sUser = (User) AndroidFileSystemHelper.restoreObject(USER_CACHE_PATH + TAG);
            if (sUser == null) {
                sUser = new User();
                AndroidFileSystemHelper.saveObject(USER_CACHE_PATH + TAG, sUser);
            }
        }
        return sUser;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getAccount() {
        return mAccount;
    }

    public void setAccount(String account) {
        mAccount = account;
    }

    public String getRealName() {
        return mRealName;
    }

    public void setRealName(String realName) {
        mRealName = realName;
    }

    public boolean isLogin() {
        return mIsLogin;
    }

    public void setLogin(boolean login) {
        mIsLogin = login;
    }

    public int getIntField() {
        return mIntField;
    }

    public void setIntField(int intField) {
        mIntField = intField;
    }

    public long getLongField() {
        return mLongField;
    }

    public void setLongField(long longField) {
        mLongField = longField;
    }

    public boolean isBooleanFiled() {
        return mBooleanFiled;
    }

    public void setBooleanFiled(boolean booleanFiled) {
        mBooleanFiled = booleanFiled;
    }

    public List<String> getStringListFiled() {
        return mStringListFiled;
    }

    public void setStringListFiled(List<String> stringListFiled) {
        mStringListFiled = stringListFiled;
    }

    public long getTimeFiled() {
        return mTimeFiled;
    }

    public void setTimeFiled(long timeFiled) {
        mTimeFiled = timeFiled;
    }

    public String[][] getStrings2DimesFiled() {
        return mStrings2DimesFiled;
    }

    public void setStrings2DimesFiled(String[][] strings2DimesFiled) {
        mStrings2DimesFiled = strings2DimesFiled;
    }

    public int[][] getInts22DimensFiled() {
        return mInts22DimensFiled;
    }

    public void setInts22DimensFiled(int[][] ints22DimensFiled) {
        mInts22DimensFiled = ints22DimensFiled;
    }

    public Calendar getCalendarFiled() {
        return mCalendarFiled;
    }

    public void setCalendarFiled(Calendar calendarFiled) {
        mCalendarFiled = calendarFiled;
    }

    public void save() {
        AndroidFileSystemHelper.saveObject(USER_CACHE_PATH + TAG, sUser);
    }

    public void reset() {
        sUser = null;
        AndroidFileSystemHelper.saveObject(USER_CACHE_PATH + TAG, null);
    }

    private User readResolve() throws ObjectStreamException, CloneNotSupportedException {
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUsername='" + mUsername + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mAccount='" + mAccount + '\'' +
                ", mRealName='" + mRealName + '\'' +
                ", mIsLogin=" + mIsLogin +
                ", mIntField=" + mIntField +
                ", mLongField=" + mLongField +
                ", mBooleanFiled=" + mBooleanFiled +
                ", mStringListFiled=" + mStringListFiled +
                ", mTimeFiled=" + mTimeFiled +
                ", mStrings2DimesFiled=" + Arrays.toString(mStrings2DimesFiled) +
                ", mInts22DimensFiled=" + Arrays.toString(mInts22DimensFiled) +
                ", mCalendarFiled=" + mCalendarFiled +
                '}';
    }
}
