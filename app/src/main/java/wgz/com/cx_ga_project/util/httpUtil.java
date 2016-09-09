package wgz.com.cx_ga_project.util;

import android.support.design.widget.Snackbar;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by qwerr on 2016/5/9.
 */
public class httpUtil {
    public static InputStream getIS(String path){
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            int responseCode = conn.getResponseCode();
            if (responseCode ==200){
                InputStream inputStream = conn.getInputStream();
                return inputStream;

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
    public static Snackbar showSnackBar(View view, String message) {
        Snackbar snackbar=Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
        return snackbar;
    }
    public static String getStr(String path,String encode){
        String str = "";
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POSt");
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            int responseCode = conn.getResponseCode();
            if (responseCode==200){
               str = changeIS(conn.getInputStream(),encode);


            }




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return str;
    }
    private static String changeIS(InputStream inputStream, String encode) throws IOException {
        String jsonstr = null;
        ByteArrayOutputStream bos= new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;

        while ((len=inputStream.read(data))!=-1){
            bos.write(data,0,len);

        }

        jsonstr = new String(bos.toByteArray(),encode);
        inputStream.close();


        return jsonstr;
    }

}
