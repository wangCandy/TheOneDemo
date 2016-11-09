package com.wly.onedemo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

/**
 * Created by Candy on 2016/11/7.
 */

@SuppressLint("WorldReadableFiles")
public class FileUtil<T> {

    public FileUtil() {
    }

    /**
     * 将对象保存到内部存储
     *
     * @param context
     * @param fileName 文件名
     * @param bean     对象
     * @return true 保存成功
     */
    public boolean writeObjectIntoLocal(Context context, String fileName, T bean) {
        try {
            // 通过openFileOutput方法得到一个输出流，方法参数为创建的文件名（不能有斜杠）
            File file = new File(context.getCacheDir(), fileName + ".txt");
//            @SuppressWarnings("deprecation")
//            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_WORLD_READABLE);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(bean);//写入
            fos.close();//关闭输入流
            oos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 读取内部存储对象
     *
     * @param context
     * @param fileName 文件名
     * @return
     */
    @SuppressWarnings("unchecked")
    public T readObjectFromLocal(Context context, String fileName) {
        T bean;
        try {
//            FileInputStream fis = context.openFileInput(fielName);//获得输入流
            File file = new File(context.getCacheDir(), fileName + ".txt");
            FileInputStream fis = new FileInputStream(file);

            ObjectInputStream ois = new ObjectInputStream(fis);
            bean = (T) ois.readObject();
            fis.close();
            ois.close();
            return bean;
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            return null;
        } catch (OptionalDataException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 递归删除文件和文件夹
     * @param file    要删除的根目录
     */
    public static void recursionDeleteFile(File file){
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                recursionDeleteFile(f);
            }
            file.delete();
        }
    }
}

