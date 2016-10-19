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
    private static final int SIZE = 4 * 1024 * 1024;// 定义单个文件的大小这里采用1m


    private static void splitFile(File file) {
        try {
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
            File dir = new File("C:/parfiles");
            // 判断文件夹是否存在，不存在则创建
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 切割文件
            while ((len = fs.read(b)) != -1) {
                fo = new FileOutputStream(new File(dir, (count++) + ".part"));
                fo.write(b, 0, len);
                fo.close();
            }
            // 将被切割的文件信息保存到properties中
            pro.setProperty("partCount", count + "");
            pro.setProperty("fileName", file.getName());
            fo = new FileOutputStream(new File(dir, (count++) + ".properties"));
            // 写入properties文件
            pro.store(fo, "save file info");
            fo.close();
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void read() {
        long from = 4 + 1;//从该字节开始读，自己测注意中文是两个字节
        try {
            File file = new File("d:\\文件上传\\ss.txt");
            FileInputStream bis = new FileInputStream(file);
            bis.skip(from - 1);//文件指向前一字节
            @SuppressWarnings("resource")
//指定文件位置读取的文件流
                    InputStream sbs = new BufferedInputStream(bis);
//存入文件，以便检测
            File file1 = new File("d:\\文件上传\\ss1.txt");
            OutputStream os = null;
            try {
                os = new FileOutputStream(file1);
                byte buffer[] = new byte[4 * 1024];
                int len = 0;
                while ((len = sbs.read(buffer)) != -1)//
                {
                    os.write(buffer, 0, len);
                }
                os.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    os.close();
                } catch (IOException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }


}
