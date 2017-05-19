package com.curry.stephen.lcandroidlib.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Administrator on 2017/1/30.
 */

public class AndroidFileSystemHelper {

    private static final String TAG = AndroidFileSystemHelper.class.getSimpleName();

    public enum StorageAvailable {
        None,
        Read,
        ReadAndWrite
    }

    /**
     * 获取Android文件存储器状态。
     */
    public static StorageAvailable getStorageState() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return StorageAvailable.ReadAndWrite;
        } else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            return StorageAvailable.Read;
        } else {
            return StorageAvailable.None;
        }
    }

    /**
     * 打印应用程序目录、外部私有目录和外部公共目录信息。
     */
    public static void print(String tag, Context context) {
        String tagName;
        if (tag == null) {
            tagName = TAG;
        } else {
            tagName = tag;
        }

        Log.i(tagName, String.format("getFilesDir = %s", context.getFilesDir()));
        Log.i(tagName, String.format("getExternalFilesDir = %s", context.getExternalFilesDir(null)));
        Log.i(tagName, String.format("getDownloadCacheDirectory = %s", Environment.getDownloadCacheDirectory()));
        Log.i(tagName, String.format("getDataDirectory = %s", Environment.getDataDirectory()));
        Log.i(tagName, String.format("getExternalStorageDirectory = %s", Environment.getExternalStorageDirectory()));
//        Log.i(tagName, String.format("getExternalStoragePublicDirectory = %s", Environment.getExternalStoragePublicDirectory(null)));
        String[] files = context.fileList();
        for (String item : files) {
            Log.i(tagName, String.format("File is %s", item));
        }
    }

    /**
     * 获取应用程序目录的根目录。
     */
    public static File getAppPrivateFile(Context context) {
        return context.getFilesDir();
    }

    /**
     * 获取应用程序私有目录的特定目录。
     */
    public static File getAppPrivateFile(Context context, String filePath) {
        File file = context.getDir(filePath, Context.MODE_PRIVATE);
        return file;
    }

    /**
     * 删除应用程序根目录下的文件。
     */
    public static boolean deleteAppPrivateFile(Context context, String filePath) {
        return context.deleteFile(filePath);
    }

    /**
     * 获取特定类型的公共目录。
     */
    public static File getExternalStoragePublicDirectory(String dirType, String dirName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(dirType), dirName);
        file.mkdirs();
        return file;
    }

    /**
     * 获取特定类型的公共目录下的文件。
     */
    public static File getExternalStoragePublicFile(String dirType, String dirName, String filename) {
        File file = new File(Environment.getExternalStoragePublicDirectory(dirType), dirName);
        file = new File(file, filename);
        return file;
    }

    /**
     * 获取外部公共目录的根目录。
     */
    public static File getExternalStorageDirectory() {
        File file = Environment.getExternalStorageDirectory();
        return file;
    }

    /**
     * 获取外部私有目录的根目录。
     */
    public static File getExternalAppPrivateDirectory(Context context) {
        return context.getExternalFilesDir(null);
    }

    public static long getSDSize() {
        String path = getExternalStorageDirectory().getPath();
        StatFs statFs = new StatFs(path);
        long blockSize = statFs.getBlockSize();
        return statFs.getAvailableBlocks() * blockSize;
    }

    public static boolean saveObject(String path, Object saveObject) {
        File file = new File(path);
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        boolean isSuccess = false;
        try {
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(saveObject);
            isSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

    public static Object restoreObject(String path) {
        File file = new File(path);
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        Object object = null;
        try {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            object = objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return object;
    }
}
