package com.hao.greatinrxjava.manager.file;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by liuxuehao on 16/12/21.
 */

public class FileManager {
    private static final FileManager INSTANCE = new FileManager();
    private static WeakReference<Context> mContextRef;

    private FileManager() {
    }

    public static FileManager getInstance(Context context) {
        mContextRef = new WeakReference<Context>(context);
        return INSTANCE;
    }

    /**
     * 写到应用的内部目录
     *
     * @param fileName
     * @param content
     * @return
     */
    public boolean writeFilesDir(String fileName, String content) {
        File file = mContextRef.get().getFilesDir();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 写到应用临时缓存文件的内部目录
     *
     * @param fileName
     * @param content
     * @return
     */
    public boolean writeCacheDir(String fileName, String content) {
        File file = mContextRef.get().getCacheDir();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 写到应用外部的私有目录
     *
     * @param fileName
     * @param content
     * @return
     */
    public boolean writeExternalFilesDir(String fileName, String content) {
        if (!isExternalStorageWritable()) return false;
        File file = mContextRef.get().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 写到应用外部的共有目录
     *
     * @param fileName
     * @param content
     * @return
     */
    public boolean writeExternalStorageDirectory(String fileName, String content) {
        if (!isExternalStorageWritable()) return false;
        File file = Environment.getExternalStorageDirectory();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 写到应用外部的共有目录(指定类型)
     *
     * @param fileName
     * @param content
     * @return
     */
    public boolean writeExternalStoragePublicDirectory(String fileName, String content) {
        if (!isExternalStorageWritable()) return false;
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
