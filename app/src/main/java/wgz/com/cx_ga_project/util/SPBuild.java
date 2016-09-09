package wgz.com.cx_ga_project.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wgz
 */
public class SPBuild {
    private final SharedPreferences.Editor editor;

    public SPBuild(Context context) {
        this.editor=context.getSharedPreferences(SPUtils.FILE_NAME,SPUtils.MODE).edit();
    }

    public SPBuild addData(String key,Object object){
        SPUtils.putAdd(editor,key,object);
        return this;
    }

    public void build(){
        this.editor.apply();
    }
}
