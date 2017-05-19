package com.curry.stephen.lcandroidlib.cache;

import com.curry.stephen.lcandroidlib.utils.AndroidFileSystemHelper;
import com.curry.stephen.lcandroidlib.utils.MD5Helper;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/2/16.
 */

public class CacheManager {

    private static CacheManager sCacheManager;

    private static final String APP_CACHE_PATH = AndroidFileSystemHelper.getExternalStorageDirectory().getPath() + "/lcappcache/";
    private static final long SDCARD_MIN_SPACE = 1024 * 1024 * 10;

    private CacheManager() {

    }

    public static synchronized CacheManager getInstance() {
        if (sCacheManager == null) {
            sCacheManager = new CacheManager();
        }
        return sCacheManager;
    }

    public boolean isCacheAvailable() {
        return AndroidFileSystemHelper.getStorageState() == AndroidFileSystemHelper.StorageAvailable.ReadAndWrite;
    }

    public void clearCache() {
        if (!isCacheAvailable()) {
            return;
        }
        File file = new File(APP_CACHE_PATH);
        deleteAllFileAndDirectory(file);
    }

    private static void deleteAllFileAndDirectory(File file) {
        for (File item : file.listFiles()) {
            if (item.isDirectory()) {
                deleteAllFileAndDirectory(item);
            }
            item.delete();
        }
    }

    public void initCacheDirectory() {
        if (!isCacheAvailable()) {
            return;
        }

        File appCacheDirectory = new File(APP_CACHE_PATH);
        if (!appCacheDirectory.exists()) {
            appCacheDirectory.mkdirs();
        }

        if (AndroidFileSystemHelper.getSDSize() < SDCARD_MIN_SPACE) {
            clearCache();
        }
    }

    public boolean string2Cache(String key, String data, long expiredTime) {
        boolean result = false;
        try {
            String md5Key = MD5Helper.getEncryptedString(key);
            CacheStringItem stringCacheItem = new CacheStringItem(md5Key, data, expiredTime);
            result = putStringIntoCache(stringCacheItem);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private synchronized boolean putStringIntoCache(CacheStringItem stringCacheItem) {
        if (AndroidFileSystemHelper.getSDSize() >= SDCARD_MIN_SPACE) {
            AndroidFileSystemHelper.saveObject(APP_CACHE_PATH + stringCacheItem.getKey(), stringCacheItem);
            return true;
        } else {
            return false;
        }
    }

    public String cache2String(String key) {
        String result = null;
        try {
            String md5Key = MD5Helper.getEncryptedString(key);
            if (isContainedKeyInCacheDirectory(md5Key)) {
                CacheStringItem stringCacheItem = getStringFromCache(md5Key);
                if (stringCacheItem != null) {
                    result = stringCacheItem.getData();
                }
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean isContainedKeyInCacheDirectory(String key) {
        File file = new File(APP_CACHE_PATH + key);
        return file.exists();
    }

    private synchronized CacheStringItem getStringFromCache(String key) {
        CacheStringItem stringCacheItem = null;
        Object object = AndroidFileSystemHelper.restoreObject(APP_CACHE_PATH + key);
        if (object != null) {
            CacheStringItem temp = (CacheStringItem) object;
            if (temp.getTimeStamp() <= System.currentTimeMillis()) {
                stringCacheItem = temp;
            }
        }
        return stringCacheItem;
    }
}
