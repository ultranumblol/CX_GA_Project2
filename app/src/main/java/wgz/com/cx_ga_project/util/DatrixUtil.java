package wgz.com.cx_ga_project.util;

import android.view.View;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.DefaultProgressListener;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.base.UploadFileRequestBody;
import wgz.com.cx_ga_project.bean.progress;
import wgz.com.cx_ga_project.entity.DatrixCreat;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static wgz.com.cx_ga_project.util.fileUtil.delFolder;

/**
 * Created by wgz on 2016/10/9.
 */

public class DatrixUtil {
    private static final int UPLOADPIC = 1;
    private static final int UPLOADVIDEO = 2;
    private static final int UPLOADLENGTH = 4 * 1024 * 1024;
    List<String> paths = new ArrayList<>();
    List<String> ids = new ArrayList<>();
    //private String fileid;
    private View rootview;
    private AfterFinish afterFinish;
    private String path = "";
    private int filesize = 0;
    private int filePieceSize = 0;
    private int filePieces = 0;
    private int currentPieces = 0;
    private int offset = 0;
    private int startsize = 0;
    private int endsize = 0;
    private int currentfilesize = 0;
    private boolean flag = false;
    private int videofile = 0;
    private int videofilecount = 0;
    private Subscription rxSubscription;
    private CompositeSubscription msubscription;//管理所有的订阅

    /**
     * 文件地址集合
     *
     * @param paths
     * @param rootview
     */
    public DatrixUtil(List<String> paths, View rootview) {
        ids.clear();
        this.paths.clear();
        videofile = 0;
        videofilecount = 0;
        flag = true;
        this.paths = paths;
        this.rootview = rootview;
        msubscription= new CompositeSubscription();
        rxSubscription = RxBus.getDefault().toObservable(String.class)
                .subscribe(s -> {
                    if (s.equals("stopSubscription"))
                    StopSubscription();
                });

    }

    public void StopSubscription(){

        if(msubscription != null){
            this.msubscription.unsubscribe();
        }

    }
    /**
     * 单个文件地址
     *
     * @param path
     * @param rootview
     */
    public DatrixUtil(String path, View rootview) {
        this.path = path;
        paths.clear();
        ids.clear();
        flag = true;
        this.rootview = rootview;
        msubscription= new CompositeSubscription();
        rxSubscription = RxBus.getDefault().toObservable(String.class)
                .subscribe(s -> {
                    if (s.equals("stopSubscription"))
                    StopSubscription();
                });
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
     * 全部图片上传
     */
    public void DatrixUpLoadPic2() {
        LogUtil.d("datrixUtil pathssize:" + paths.size());
        for (int i = 0; i < paths.size(); i++) {

            DatrixCreate(paths.get(i), UPLOADPIC);
        }


    }

    /**
     * 上传单个视频
     */
    public void DatrixUpLoadVideo() {
        LogUtil.d("datrixUtil path:" + path);

        DatrixCreate(path, UPLOADVIDEO);
    }

    /**
     * 上传多个视频
     */
    public void DatrixUpLoadVideos() {
        videofile = 0;
        videofilecount = paths.size();
        DatrixVideosCreate((paths.get(videofile)));

    }

    private void DatrixVideosCreate(final String path) {
        filesize = 0;
        filePieces = 0;
        currentPieces = 0;
        offset = 0;
        startsize = 0;
        endsize = 0;
        currentfilesize = 0;
        File file = new File(path);

        app.apiService.uploadFileWithRequestBodyTest(file.getName(), "" + file.length())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DatrixCreat>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //LogUtil.d("Detrix_upPic_error:" + e.toString());
                       // SomeUtil.checkHttpException(app.getApp().getApplicationContext(), e, rootview);
                    }

                    @Override
                    public void onNext(DatrixCreat datrixCreat) {
                        //LogUtil.d("Detrix_upPic_create : code :" + datrixCreat.getCode().toString());
                        if (datrixCreat.getCode().equals(200)) {
                            String fileid = datrixCreat.getResult().getFileid();
                            ids.add(fileid);
                            //LogUtil.d("Detrix_upPic_create :" + datrixCreat.getResult().toString());
                            DatrixVideosDoWrite(path, fileid);

                        } else {

                            SomeUtil.showSnackBar(rootview, "创建上传图片失败!");
                        }

                    }
                });


    }

    private void DatrixVideosDoWrite(String path, final String fileid) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        final File file = new File(path);
        filesize = (int) file.length();
        // LogUtil.d("Video file size : " + filesize);
        if (filesize > 4 * 1024 * 1024) {
            long k = filesize / 4 / 1024 / 1024;
            filePieces = (int) Math.floor(k) + 1;
            // LogUtil.d("Video file Pieces  : " + filePieces);
            rx.Observable.just("split").subscribeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            writeVideosByPiece2(fileid);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(String s) {
                            fileUtil.splitFile(file);
                        }
                    });



        }

    }

    private void writeVideosByPiece2(final String fileid) {
        final Map<String, RequestBody> piecebodyMap = new HashMap<>();
        File file = new File("/storage/sdcard0/temp/" + currentPieces + ".3gp");

        UploadFileRequestBody fileRequestBody = new UploadFileRequestBody(file, new DefaultProgressListener(currentPieces, filePieces));

        //piecebodyMap.put("file" + currentPieces + "\" ; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("video/*"), file));


        piecebodyMap.put("file" + currentPieces + "\" ; filename=\"" + file.getName(), fileRequestBody);

        filePieceSize = (int) file.length();
        currentPieces += 1;
        //发送进度
        float num = (float) currentPieces / filePieces;
        DecimalFormat df = new DecimalFormat("0.00");

        RxBus.getDefault().post(new progress(df.format(num * 100)));

        LogUtil.d("current:" + currentPieces + "filepiece:" + filePieces);
        Subscription i = app.apiService.detrixWrite(fileid, startsize + "", filePieceSize + "", piecebodyMap)
                .compose(RxUtil.applySchedulers())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //SomeUtil.checkHttpException(app.getApp().getApplicationContext(), e, rootview);
                        LogUtil.d("detrix_write_Response_error :" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("detrix_write_Response :" + s);
                        if (s.contains("\t\"code\":\t200")) {
                            if (currentPieces == filePieces) {
                                DatrixVideosDoFinish(fileid);
                            } else {
                                startsize += UPLOADLENGTH;
                                writeVideosByPiece2(fileid);
                            }

                        } else {
                            SomeUtil.showSnackBar(rootview, "第" + currentPieces + "块文件写入失败!");

                        }
                    }
                });
            msubscription.add(i);
    }


    private void writeByPiece2(final String fileid) {
        final Map<String, RequestBody> piecebodyMap = new HashMap<>();
        File file = new File("/storage/sdcard0/temp/" + currentPieces + ".3gp");

        UploadFileRequestBody fileRequestBody = new UploadFileRequestBody(file, new DefaultProgressListener(currentPieces, filePieces));

        //piecebodyMap.put("file" + currentPieces + "\" ; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("video/*"), file));


        piecebodyMap.put("file" + currentPieces + "\" ; filename=\"" + file.getName(), fileRequestBody);

        filePieceSize = (int) file.length();
        currentPieces += 1;
        //发送进度
        float num = (float) currentPieces / filePieces;
        DecimalFormat df = new DecimalFormat("0.00");

        RxBus.getDefault().post(new progress(df.format(num * 100)));

        LogUtil.d("current:" + currentPieces + "filepiece:" + filePieces);
        Subscription i =app.apiService.detrixWrite(fileid, startsize + "", filePieceSize + "", piecebodyMap)
                .compose(RxUtil.<String>applySchedulers())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                       // SomeUtil.checkHttpException(app.getApp().getApplicationContext(), e, rootview);
                        LogUtil.d("detrix_write_Response_error :" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("detrix_write_Response :" + s);
                        if (s.contains("\t\"code\":\t200")) {
                            if (currentPieces == filePieces) {
                                DatrixDoFinish(fileid);
                            } else {
                                startsize += UPLOADLENGTH;
                                writeByPiece2(fileid);
                            }

                        } else {
                            SomeUtil.showSnackBar(rootview, "第" + currentPieces + "块文件写入失败!");

                        }
                    }
                });
        msubscription.add(i);

    }


    private void DatrixCreate(final String path, final int type) {
        filesize = 0;
        filePieces = 0;
        currentPieces = 0;
        offset = 0;
        startsize = 0;
        endsize = 0;
        currentfilesize = 0;
        File file = new File(path);

        app.apiService.uploadFileWithRequestBodyTest(file.getName(), "" + file.length())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DatrixCreat>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("Detrix_upPic_error:" + e.toString());
                       // SomeUtil.checkHttpException(app.getApp().getApplicationContext(), e, rootview);
                    }

                    @Override
                    public void onNext(DatrixCreat datrixCreat) {
                        LogUtil.d("Detrix_upPic_create : code :" + datrixCreat.getCode().toString());
                        if (datrixCreat.getCode().equals(200)) {
                            LogUtil.d("Detrix_upPic_create :" + datrixCreat.getResult().getFileid());
                            String fileid = datrixCreat.getResult().getFileid();

                            ids.add(fileid);
                            LogUtil.d("Detrix_upPic_create :" + datrixCreat.getResult().toString());
                            DatrixDoWrite(path, fileid, type);

                        } else {

                            SomeUtil.showSnackBar(rootview, "创建上传图片失败!");
                        }

                    }
                });
    }

    private void DatrixDoWrite(String path, final String fileid, int type) {

        Map<String, RequestBody> bodyMap = new HashMap<>();

        if (type == UPLOADPIC) {
            File file = new File(path);


            bodyMap.put("file" + 1 + "\" ; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            filesize = (int) file.length();
            Subscription i = app.apiService.detrixWrite(fileid, "0", filesize + "", bodyMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.d("detrix_write_Response_error :" + e.toString());
                           // SomeUtil.checkHttpException(app.getApp().getApplicationContext(), e, rootview);
                        }

                        @Override
                        public void onNext(String s) {
                            LogUtil.d("detrix_writePic_Response :" + s);
                            if (s.contains("200")) {
                                DatrixDoFinish(fileid);
                            } else {
                                SomeUtil.showSnackBar(rootview, "写入文件失败!");
                                //ToastUtil.showLong(app.getApp().getApplicationContext()," code ："+s);
                            }
                        }
                    });
            msubscription.add(i);
        }
        if (type == UPLOADVIDEO) {
            File file = new File(path);
            filesize = (int) file.length();
            // LogUtil.d("Video file size : " + filesize);
            if (filesize > 4 * 1024 * 1024) {
                long k = filesize / 4 / 1024 / 1024;
                filePieces = (int) Math.floor(k) + 1;
                // LogUtil.d("Video file Pieces  : " + filePieces);
                fileUtil.splitFile(file);
                writeByPiece2(fileid);
            }
        }
    }

    private void DatrixDoFinish(final String fildid) {
        app.apiService.detrixfinish(fildid, " ")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        delFolder("/storage/sdcard0/temp");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("DatrixDoFinish error:"+e.toString());
                        //SomeUtil.checkHttpException(app.getApp().getApplicationContext(), e, rootview);
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("DatrixDoFinish_Response:"+s.toString());
                        if (s.contains("200")) {
                            if (afterFinish != null) {

                                if (flag) {
                                    afterFinish.afterfinish(fildid, ids);
                                    flag = false;
                                }

                            }


                        } else {
                            SomeUtil.showSnackBar(rootview, "网络错误，请稍后！");
                        }
                    }
                });


    }

    private void DatrixVideosDoFinish(final String fileid) {
        app.apiService.detrixfinish(fileid, " ")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        delFolder("/storage/sdcard0/temp");

                        videofile += 1;
                        if (videofile == videofilecount) {
                            LogUtil.d(videofilecount + "个视频上传完毕！");
                        } else {
                            DatrixVideosCreate((paths.get(videofile)));
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                       // SomeUtil.checkHttpException(app.getApp().getApplicationContext(), e, rootview);
                    }

                    @Override
                    public void onNext(String s) {
                        if (s.contains("200")) {
                            if (afterFinish != null) {

                                if (videofile == videofilecount - 1) {
                                    afterFinish.afterfinish(fileid, ids);
                                }

                            }

                        } else {
                            SomeUtil.showSnackBar(rootview, "网络错误，请稍后！");
                        }
                    }
                });


    }

    public void setOnAfterFinish(AfterFinish afterFinish) {
        this.afterFinish = afterFinish;

    }

    public interface AfterFinish {
        void afterfinish(String fileid, List<String> ids);
    }

}
