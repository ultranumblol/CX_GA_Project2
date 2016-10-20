package wgz.com.cx_ga_project.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by wgz on 2016/10/19.
 */

public class fileUtil {
    private static final int SIZE = 4 * 1024 * 1024;// 定义单个文件的大小这里采用4m


    public static void splitFile(File file) {
        try {
            delAllFile("/storage/sdcard0/temp");
            FileInputStream fs = new FileInputStream(file);
            // 定义缓冲区
            byte[] b = new byte[SIZE];
            FileOutputStream fo = null;
            int len = 0;
            int count = 0;

            /**
             * 切割文件时，记录 切割文件的名称和切割的子文件个数以方便合并
             * 这个信息为了简单描述，使用键值对的方式，用到了properties对象
             */
            Properties pro = new Properties();
            // 定义输出的文件夹路径
            File dir = new File("/storage/sdcard0/temp");
            // 判断文件夹是否存在，不存在则创建
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 切割文件
            while ((len = fs.read(b)) != -1) {
                fo = new FileOutputStream(new File(dir, (count++) + ".3gp"));
                fo.write(b, 0, len);
                fo.close();
            }
           /* // 将被切割的文件信息保存到properties中
            pro.setProperty("partCount", count + "");
            pro.setProperty("fileName", file.getName());
            fo = new FileOutputStream(new File(dir, (count++) + ".properties"));
            // 写入properties文件
            pro.store(fo, "save file info");
            fo.close();
            fs.close();*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除指定文件夹下所有文件
//param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }
}
