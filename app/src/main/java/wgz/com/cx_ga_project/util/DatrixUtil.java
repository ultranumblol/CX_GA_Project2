package wgz.com.cx_ga_project.util;

import android.view.View;

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

/**
 * Created by wgz on 2016/10/9.
 */

public class DatrixUtil {
    private static String DATRIXURL = "http://101.231.77.242:9001/preview/getImage?fileid=";
    private static String DATRIXURL2 = "&token=X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=";
    private String fileid;
    List<String> paths = new ArrayList<>();
    private View rootview;
    private AfterFinish afterFinish;
    public DatrixUtil(String fileid, List<String> paths, View rootview) {
        this.fileid = fileid;
        this.paths = paths;
        this.rootview = rootview;

    }

    public void DatrixUpLoadPic(){
        DatrixCreate();

    }
    private void DatrixCreate() {
        app.apiService.uploadFileWithRequestBodyTest("testtestwgzwgz")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DatrixCreat>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("Detrix_upPic_error:" + e.toString());
                    }

                    @Override
                    public void onNext(DatrixCreat datrixCreat) {
                        LogUtil.e("Detrix_upPic_create : code :" +datrixCreat.getCode().toString());
                        if (datrixCreat.getCode().equals(200)){
                            LogUtil.e("Detrix_upPic_create :"+datrixCreat.getResult().getFileid());
                            fileid = datrixCreat.getResult().getFileid();
                            LogUtil.e("Detrix_upPic_create :" +datrixCreat.getResult().toString());
                            DatrixDoWrite(paths,fileid);

                        }else{

                            SomeUtil.showSnackBar(rootview,"创建上传图片失败!");
                        }

                    }
                });
    }

    private void DatrixDoWrite(List<String> paths, String fileid) {
        String size = "";
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if (paths.size() > 0) {
            for (int i = 0; i < 1; i++) {
                File file = new File(paths.get(i));
                bodyMap.put("file" + i + "\" ; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                size = file.length()+"";
            }
        }
        LogUtil.e("file size : " +size);
        LogUtil.e("fileid  : " +fileid);
        app.apiService.detrixWrite(fileid,"0",size,bodyMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("detrix_write_Response_error :" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.e("detrix_write_Response :" + s);
                        if (s.contains("\t\"code\":\t200")){
                            DatrixDoFinish();
                        }else
                        {
                            SomeUtil.showSnackBar(rootview,"图片上传失败!");
                        }
                    }
                });


    }

    private void DatrixDoFinish() {
        app.apiService.detrixfinish(fileid," ")
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
                        if (s.contains("200")){
                            Date currentdate = new Date(System.currentTimeMillis());
                            String curredate = AskForLeaveActivity.getTime(currentdate);


                            app.jqAPIService.sendMsg("2016072100100000060", " ",DATRIXURL+fileid+DATRIXURL2, "213", curredate, "532301030355")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<String>() {
                                        @Override
                                        public void onCompleted() {
                                            if (afterFinish!=null){
                                                afterFinish.afterfinish();
                                            }

                                           /* etSendmessage.setText("");
                                            // TODO: 2016/9/12 获取新消息 删除本地 换成服务器请求的
                                            //adapter.getHeader()
                                            //getNewmsg();
                                            //LogUtil.e("recyclerview count:"+recyclerview.getChildCount());
                                            //recyclerview.getChildCount();
                                            adapter.remove(adapter.getCount() - 1);

                                            getNewmsg();*/
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            //SomeUtil.showSnackBar(rootview, "error:" + e.toString());
                                            LogUtil.e("error:"+ e.toString());
                                        }

                                        @Override
                                        public void onNext(String s) {
                                            LogUtil.e("Finish result:" + s);


                                            // SomeUtil.showSnackBar(rootview,"result:"+s);
                                        }
                                    });




                            //SomeUtil.showSnackBar(rootview,"上传图片成功！");
                        }
                        else {
                            SomeUtil.showSnackBar(rootview,"网络错误，请稍后！");
                        }
                    }
                });


    }
    public interface  AfterFinish{
        void afterfinish();
    }
    public void setOnAfterFinish(AfterFinish afterFinish){
        this.afterFinish = afterFinish;

    }

}
