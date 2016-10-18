package wgz.com.cx_ga_project.util;

import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.activity.AskForLeaveActivity;
import wgz.com.cx_ga_project.adapter.chatAdapter.ChatAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.entity.DatrixCreat;
import wgz.datatom.com.utillibrary.util.LogUtil;
import wgz.datatom.com.utillibrary.util.ToastUtil;

/**
 * Created by wgz on 2016/10/9.
 */

public class DatrixUtil {
    private static String DATRIXURL = "http://101.231.77.242:9001/preview/getImage?fileid=";
    private static String DATRIXURL2 = "&token=X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=";
    private static final int UPLOADPIC = 1;
    private static final int UPLOADVIDEO = 2;
    private String fileid;
    List<String> paths = new ArrayList<>();
    List<String> ids = new ArrayList<>();
    private View rootview;
    private AfterFinish afterFinish;
    private String path = "";

    /**
     * 根据图片地址list上传多个图片
     *
     * @param paths
     * @param rootview
     */
    public DatrixUtil(List<String> paths, View rootview) {
        ids.clear();
        this.paths = paths;
        this.rootview = rootview;

    }

    public DatrixUtil(String path, View rootview) {
        this.path = path;
        ids.clear();
        this.rootview = rootview;

    }


    /**
     * 有end图片的上传图片
     */
    public void DatrixUpLoadPic() {
        LogUtil.d("datrixUtil pathssize:" + paths.size());
        paths.remove(paths.size() - 1);
        for (int i = 0; i < paths.size(); i++) {

            DatrixCreate(paths.get(i),UPLOADPIC);
        }


    }

    /**
     * 只传一张图片
     */
    public void DatrixUpLoadPic2() {
        LogUtil.d("datrixUtil pathssize:" + paths.size());
        for (int i = 0; i < paths.size(); i++) {

            DatrixCreate(paths.get(i),UPLOADPIC);
        }


    }

    /**
     * 上传视频
     */
    public void DatrixUpLoadVideo() {
        LogUtil.d("datrixUtil path:" + path);

        DatrixCreate(path,UPLOADVIDEO);
    }


    private void DatrixCreate(final String path, final int type) {
        app.apiService.uploadFileWithRequestBodyTest(SomeUtil.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DatrixCreat>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("Detrix_upPic_error:" + e.toString());
                    }

                    @Override
                    public void onNext(DatrixCreat datrixCreat) {
                        LogUtil.d("Detrix_upPic_create : code :" + datrixCreat.getCode().toString());
                        if (datrixCreat.getCode().equals(200)) {
                            LogUtil.d("Detrix_upPic_create :" + datrixCreat.getResult().getFileid());
                            fileid = datrixCreat.getResult().getFileid();
                            ids.add(fileid);
                            LogUtil.d("Detrix_upPic_create :" + datrixCreat.getResult().toString());
                            DatrixDoWrite(path, fileid,type);

                        } else {

                            SomeUtil.showSnackBar(rootview, "创建上传图片失败!");
                        }

                    }
                });
    }

    private void DatrixDoWrite(String path, String fileid,int type) {
        String size = "";
        Map<String, RequestBody> bodyMap = new HashMap<>();

        if (type==UPLOADPIC){
            File file = new File(path);
            bodyMap.put("file" + 1 + "\" ; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            size = file.length() + "";
        }
        if (type==UPLOADVIDEO){
            File file = new File(path);
            bodyMap.put("file" + 1 + "\" ; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("video/*"), file));
            size = file.length() + "";
        }



        LogUtil.d("file size : " + size);
        LogUtil.d("fileid  : " + fileid);
        app.apiService.detrixWrite(fileid, "0", size, bodyMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("detrix_write_Response_error :" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("detrix_write_Response :" + s);
                        if (s.contains("\t\"code\":\t200")) {
                            DatrixDoFinish();
                        } else {
                            SomeUtil.showSnackBar(rootview, "写入文件失败!");
                            ToastUtil.showLong(app.getApp().getApplicationContext()," code ："+s);
                        }
                    }
                });


    }

    private void DatrixDoFinish() {
        app.apiService.detrixfinish(fileid, " ")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (s.contains("200")) {
                            if (afterFinish != null) {
                                afterFinish.afterfinish(fileid, ids);
                            }


                        } else {
                            SomeUtil.showSnackBar(rootview, "网络错误，请稍后！");
                        }
                    }
                });


    }

    public interface AfterFinish {
        void afterfinish(String fileid, List<String> ids);
    }

    public void setOnAfterFinish(AfterFinish afterFinish) {
        this.afterFinish = afterFinish;

    }

}
