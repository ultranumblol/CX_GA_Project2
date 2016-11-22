package wgz.com.cx_ga_project.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.JQAdapter;
import wgz.com.cx_ga_project.adapter.MyRecyclerArrayAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.entity.NewJQ;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SPBuild;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * 开始新作战任务
 * Created by wgz on 2016/8/15.
 */
public class StartNewFightActivity extends BaseActivity {


    @Bind(R.id.id_newjqRv)
    EasyRecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_start_new_fight)
    ConstraintLayout rootview;
    private JQAdapter adapter;
    private List<String> list = new ArrayList<>();
    private JQReceiver receiver;
    private List<NewJQ.NewjqRe> data = new ArrayList<>();
    private Subscription rxSubscription;
    @Override
    public int getLayoutId() {
        return R.layout.activity_start_new_fight;
    }

    @Override
    public void initView() {

        toolbar.setTitle("警情信息");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new JQAdapter(this));
        adapter.setNoMore(R.layout.view_nomore);

        adapter.setOnItemClickListener(new MyRecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View itemView) {
                TextView taskidview = (TextView) itemView.findViewById(R.id.taskid);
                String taskid =taskidview.getText().toString();
                TextView jqstateview = (TextView) itemView.findViewById(R.id.jqstate_id);
                String jqstate = jqstateview.getText().toString();

                TextView jqidview = (TextView) itemView.findViewById(R.id.jqid);
                String jqid = jqidview.getText().toString();

                TextView jqstopidview = (TextView) itemView.findViewById(R.id.jqstop_id);
                String jqstopid = jqstopidview.getText().toString();

                TextView sendtimeview = (TextView) itemView.findViewById(R.id.jqsendtime);
                String sendtime = sendtimeview.getText().toString();

                startActivity(new Intent(StartNewFightActivity.this, NewFightActivity.class)
                        .putExtra("taskid",taskid)
                        .putExtra("jqstate",jqstate)
                        .putExtra("jqid",jqid)
                        .putExtra("jqstopid",jqstopid)
                        .putExtra("sendtime",sendtime)
                );
                new SPBuild(getApplicationContext())
                        .addData(Constant.JQID, jqid)
                        .addData(Constant.TASKID,taskid)
                        .build();


            }
        });
        initdata();
        rxSubscription = RxBus.getDefault().toObservable(String.class)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (s.equals("newjqflush"))
                            initdata();
                    }
                });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }
    private void initdata() {
        adapter.clear();
        data.clear();
        //  部门id
        app.jqAPIService.getNewJqlist(SomeUtil.getUserId(),SomeUtil.getDepartId())
                .compose(RxUtil.<NewJQ>applySchedulers())
                .subscribe(new Subscriber<NewJQ>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("newjq error:" +e.toString());
                    }

                    @Override
                    public void onNext(NewJQ newJQ) {
                        LogUtil.d("newjq1 :" +newJQ.getRes().toString());
                       if (newJQ.getCode().equals(200)) {
                            data = newJQ.getRes();
                            adapter.addAll(data);


                        } else {
                            //SomeUtil.showSnackBar(rootview, "没有新警情!");
                        }
                    }
                });
        app.jqAPIService.getNewJqlist1(SomeUtil.getUserId())
                .compose(RxUtil.<NewJQ>applySchedulers())
                .subscribe(new Subscriber<NewJQ>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("newjq2 error:" +e.toString());
                    }

                    @Override
                    public void onNext(NewJQ newJQ) {
                        LogUtil.d("newjq2 :" +newJQ.getRes().toString());
                        if (newJQ.getCode().equals(200)) {
                            data = newJQ.getRes();
                            adapter.addAll(data);



                        } else {
                           // SomeUtil.showSnackBar(rootview, "没有新警情!");
                        }
                    }
                });



    }


    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        receiver = new StartNewFightActivity.JQReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("service.JQpush");
        registerReceiver(receiver, filter);
        // LogUtil.d("广播注册成功！");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.jqhistory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.jq_history) {
            // SomeUtil.showSnackBar(rootview, "开发中。。。");
            startActivity(new Intent(StartNewFightActivity.this, JQListActivity.class).putExtra("title", "jqhistory"));
            return true;
        }
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void getNewJq() {


    }

    private class JQReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String msg = bundle.getString("jq");
            if (msg.equals("newjq")) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                // 通过Notification.Builder来创建通知，注意API Level
                // API16之后才支持
                Notification notify3 = null; // 需要注意build()是在APIlevel16及之后增加的，API11可以使用getNotificatin()来替代
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    notify3 = new Notification.Builder(context)
                            .setSound(ringUri).build();
                }

                notify3.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
                manager.notify(1, notify3);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
                getNewJq();

            }
        }
    }

}
