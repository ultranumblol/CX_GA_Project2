package wgz.com.cx_ga_project.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.activity.ChatActivity;
import wgz.com.cx_ga_project.activity.HomeActivity;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.entity.ChatMsg;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * Created by wgz on 2016/9/11.
 */

public class GetNewMsgService extends Service {
    private List<ChatMsg.Re> newchatData = new ArrayList<>();
    private boolean ifHasNew = false;
    private boolean ifHasNotify = false;
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("获取新消息服务启动！");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        Thread.sleep(5000);
                        checkNew();
                        if (ifHasNew){
                            if (isActivityRunning(getApplicationContext())){
                                Intent intent = new Intent();
                                intent.putExtra("msg","newmsg");
                                intent.setAction("service.MsgService");
                                sendBroadcast(intent);
                                LogUtil.d("发送广播");
                                newchatData.clear();
                            }else {
                                if (ifHasNotify){
                                    LogUtil.d("nothing");
                                }else {

                                    NotificationManager manager = (NotificationManager)getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
                                    PendingIntent pendingIntent3 = PendingIntent.getActivity(getApplication(), 0,
                                            new Intent(getApplication(), HomeActivity.class), 0);
                                    Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                    // 通过Notification.Builder来创建通知，注意API Level
                                    // API16之后才支持
                                    Notification notify3 = null; // 需要注意build()是在API
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                        notify3 = new Notification.Builder(getApplication())
                                                .setSmallIcon(R.mipmap.ic_launcher)
                                                .setTicker("智慧警务：" + "您收到了新的警情消息！")
                                                .setContentTitle("智慧警务")
                                                .setContentText("您收到了新的警情消息！")
                                                .setContentIntent(pendingIntent3).setSound(ringUri).build();
                                    }
                                    // level16及之后增加的，API11可以使用getNotificatin()来替代
                                    notify3.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
                                    manager.notify(1, notify3);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示

                                    ifHasNotify = true;
                                }
                                newchatData.clear();
                            }

                        }

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();



    }
    private void checkNew(){

        GetNewMsg();


    }
    private void GetNewMsg() {
        app.jqAPIService.GetNewMsg2("3242342","223432432").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChatMsg>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    LogUtil.d("error: " + e.toString());
                    }

                    @Override
                    public void onNext(ChatMsg chatMsg) {
                        newchatData = chatMsg.getRes();
                        LogUtil.d("newchatData  ：" +chatMsg.getRes().size()+" 条");
                        if (newchatData.size()>0){
                            ifHasNew = true;

                        }else ifHasNew = false;
                    }
                });
    }
    //判断一个activity是否在运行
    public  static  boolean isActivityRunning(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> info = activityManager.getRunningTasks(1);
        if (info!=null&&info.size()>0){
            ComponentName component = info.get(0).topActivity;
            if (ChatActivity.class.getName().equals(component.getClassName())){
                return true;
            }
        }

        return false;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
