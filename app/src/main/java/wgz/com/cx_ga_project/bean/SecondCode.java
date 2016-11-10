package wgz.com.cx_ga_project.bean;

import android.graphics.Bitmap;

/**
 * Created by wgz on 2016/11/10.
 */

public class SecondCode {

    private String str;
    private Bitmap bitmap;

    public SecondCode(String str, Bitmap bitmap) {
        this.str = str;
        this.bitmap = bitmap;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
