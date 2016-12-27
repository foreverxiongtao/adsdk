package com.mobile.adsdk.utils;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/*
 *
 * 版 权 :@Copyright 北京优多鲜道科技有限公司版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :1.0
 *
 * 创建日期 :2016/4/14 下午10:10
 *
 * 描 述 :文件工具类
 *
 * 修订日期 :
 */
public class AbFileUtils {
    /**
     * 将输入流转换为字节数组
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static byte[] getBytesFromStream(InputStream is) throws IOException {

        int len;
        int size = 1024;
        byte[] buf;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        buf = new byte[size];
        while ((len = is.read(buf, 0, size)) != -1) {
            bos.write(buf, 0, len);
        }
        buf = bos.toByteArray();

        return buf;
    }

    /**
     * 将字节数组保存到文件中
     *
     * @param bytes
     * @param path
     */
    public static void saveBytesToFile(byte[] bytes, String path) {
        FileOutputStream fileOuputStream = null;
        try {

            fileOuputStream = new FileOutputStream(path);

            fileOuputStream.write(bytes);

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOuputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置储存文件路径
     *
     * @param context
     * @param filePath
     * @return
     */
    public static String geFilePathLogcat(Context context, String filePath) {
        String path_logcat = "";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            path_logcat = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + "adsdk" + File.separator + filePath;
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            path_logcat = context.getFilesDir().getAbsolutePath()
                    + File.separator + filePath;
        }
        File file = new File(path_logcat);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path_logcat;
    }

    public static void deleteFile(File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
}
