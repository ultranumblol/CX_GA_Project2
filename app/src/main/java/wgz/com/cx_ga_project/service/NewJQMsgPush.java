package wgz.com.cx_ga_project.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.functions.Action1;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.activity.HomeActivity;
import wgz.com.cx_ga_project.activity.StartNewFightActivity;
import wgz.com.cx_ga_project.activity.WelcomeActivity;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.entity.NewJQPush;
import wgz.com.cx_ga_project.util.SPBuild;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static wgz.com.cx_ga_project.util.SomeUtil.isActivityRunning;

/**
 * Created by wgz on 2016/10/26.
 */

public class NewJQMsgPush extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("警情推送服务启动");
        rx.Observable.interval(60, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        app.jqAPIService.jqmsgPush(SomeUtil.getUserId(),"532301000000")
                                .subscribe(new Subscriber<NewJQPush>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        LogUtil.d("msmgpush error : "+e.toString());
                                    }

                                    @Override
                                    public void onNext(NewJQPush data) {
                                        LogUtil.d("msmgpush : "+data.getRes().toString());
                                        if (data.getCode().equals(200)){
                                            new SPBuild(getApplicationContext())
                                                    .addData(Constant.NEWJQCOUNT,data.getRes().size()).build();

                                            if (isActivityRunning(app.getApp().getApplicationContext(), StartNewFightActivity.class)){
                                                Intent intent = new Intent();
                                                LogUtil.d("发送广播");
                                                intent.putExtra("jq","newjq");
                                                intent.putExtra("jqcount",data.getRes().size());
                                                intent.setAction("service.JQpush");
                                                sendBroadcast(intent);


                                            }else if(isActivityRunning(app.getApp().getApplicationContext(), HomeActivity.class)){
                                                Intent intent = new Intent();
                                                LogUtil.d("发送广播");
                                                intent.putExtra("jq","newjq");
                                                intent.setAction("service.JQpush");
                                                intent.putExtra("jqcount",data.getRes().size());
                                                sendBroadcast(intent);
                                            }else{

                                                NotificationManager manager = (NotificationManager)getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
                                                PendingIntent pendingIntent3 = PendingIntent.getActivity(getApplication(), 0,
                                                        new Intent(getApplication(), WelcomeActivity.class), 0);
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


                                            }

                                        }else {
                                            onError(new Exception("no push!"));
                                        }






                                    }
                                });
                    }
                });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }





}
