package wgz.com.cx_ga_project.util;

import android.support.v7.app.AlertDialog;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.entity.DatrixCreat;
import wgz.datatom.com.utillibrary.util.LogUtil;
import wgz.datatom.com.utillibrary.util.ToastUtil;

import static wgz.com.cx_ga_project.util.fileUtil.delAllFile;
import static wgz.com.cx_ga_project.util.fileUtil.delFolder;

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
    private static final int UPLOADLENGTH = 4 * 1024 * 1024;

    private int filesize = 0;
    private int filePieceSize = 0;
    private int filePieces = 0;
    private int currentPieces = 0;
    private int offset = 0;
    private int startsize = 0;
    private int endsize = 0;
    private int currentfilesize = 0;

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

            DatrixCreate(paths.get(i), UPLOADPIC);
        }


    }

    /**
     * 只传一张图片
     */
    public void DatrixUpLoadPic2() {
        LogUtil.d("datrixUtil pathssize:" + paths.size());
        for (int i = 0; i < paths.size(); i++) {

            DatrixCreate(paths.get(i), UPLOADPIC);
        }


    }

    /**
     * 上传视频
     */
    public void DatrixUpLoadVideo() {
        LogUtil.d("datrixUtil path:" + path);

        DatrixCreate(path, UPLOADVIDEO);
    }
    private void writeByPiece2(){
        final Map<String, RequestBody> piecebodyMap = new HashMap<>();
        File file = new File("/storage/sdcard0/temp/"+currentPieces+".3gp");
        piecebodyMap.put("file" + currentPieces + "\" ; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("video/*"), file));
        filePieceSize = (int) file.length();
        currentPieces+=1;
        SomeUtil.showSnackBar(rootview, "current:" + currentPieces  + "filepiece:"+filePieces);
        LogUtil.d("current:" + currentPieces  + "filepiece:"+filePieces);
        app.apiService.detrixWrite(fileid, startsize + "", filePieceSize+ "", piecebodyMap)
                .compose(RxUtil.<String>applySchedulers())
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
                            if (currentPieces==filePieces){
                                DatrixDoFinish();
                            }else {
                                startsize+=UPLOADLENGTH;
                                writeByPiece2();
                            }

                        } else {
                            SomeUtil.showSnackBar(rootview, "第" + currentPieces  + "块文件写入失败!");

                            //ToastUtil.showLong(app.getApp().getApplicationContext(),"current:"+currentfilesize+"filesize : "+filesize);
                            //ToastUtil.showLong(app.getApp().getApplicationContext(),s);
                        }
                    }
                });

    }


    private void writeByPiece( final Map<String, RequestBody> bodyMap) {
        startsize = endsize;
        endsize+=UPLOADLENGTH;
        if (endsize>filesize)
            endsize = filesize;
        currentfilesize+=endsize-startsize;
        LogUtil.d(" startsize:"+startsize +"uploadSize : "+(endsize-startsize) +"currentPieces :"+currentPieces);
        app.apiService.detrixWrite(fileid, startsize + "", endsize-startsize + "", bodyMap)
                .compose(RxUtil.<String>applySchedulers())
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
                            if (currentPieces==filePieces-1){
                                DatrixDoFinish();
                            }else {
                                writeByPiece(bodyMap);
                                currentPieces+=1;
                            }
                            // DatrixDoFinish();
                        } else {
                            SomeUtil.showSnackBar(rootview, "第" + currentPieces  + "块文件写入失败!");
                            //ToastUtil.showLong(app.getApp().getApplicationContext(),"current:"+currentfilesize+"filesize : "+filesize);
                            ToastUtil.showLong(app.getApp().getApplicationContext(),s);
                        }
                    }
                });
    }


    private void DatrixCreate(final String path, final int type) {
        filesize = 0;
        filePieces = 0;
        currentPieces = 0;
        offset = 0;
        startsize = 0;
        endsize = 0;
        currentfilesize=0;
        File file = new File(path);

        app.apiService.uploadFileWithRequestBodyTest(SomeUtil.getUserId(),""+file.length())
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
                            DatrixDoWrite(path, fileid, type);

                        } else {

                            SomeUtil.showSnackBar(rootview, "创建上传图片失败!");
                        }

                    }
                });
    }

    private void DatrixDoWrite(String path, String fileid, int type) {

        Map<String, RequestBody> bodyMap = new HashMap<>();

        if (type == UPLOADPIC) {
            File file = new File(path);
            bodyMap.put("file" + 1 + "\" ; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            filesize = (int) file.length();
            app.apiService.detrixWrite(fileid, "0", filesize + "", bodyMap)
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
                            LogUtil.d("detrix_writePic_Response :" + s);
                            if (s.contains("\t\"code\":\t200")) {
                                DatrixDoFinish();
                            } else {
                                SomeUtil.showSnackBar(rootview, "写入文件失败!");
                                //ToastUtil.showLong(app.getApp().getApplicationContext()," code ："+s);
                            }
                        }
                    });
        }
        if (type == UPLOADVIDEO) {
            File file = new File(path);
            //bodyMap.put("file" + 1 + "\" ; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("video/*"), file));
            filesize = (int) file.length();
            LogUtil.d("Video file size : " + filesize);
            if (filesize > 4 * 1024 * 1024) {
                long k = filesize / 4 / 1024 / 1024;
                filePieces = (int) Math.floor(k) + 1;
                LogUtil.d("Video file Pieces  : " + filePieces);
                fileUtil.splitFile(file);
               // writeByPiece(bodyMap);
                writeByPiece2();
            }
        }
    }

    private void DatrixDoFinish() {
        app.apiService.detrixfinish(fileid, " ")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        delFolder("/storage/sdcard0/temp");
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
