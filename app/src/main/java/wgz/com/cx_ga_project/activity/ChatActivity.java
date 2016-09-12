package wgz.com.cx_ga_project.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.chatAdapter.ChatAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.entity.ChatMsg;
import wgz.com.cx_ga_project.service.GetNewMsgService;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class ChatActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_set_mode_keyboard)
    Button btnSetModeKeyboard;
    @Bind(R.id.et_sendmessage)
    EditText etSendmessage;
    @Bind(R.id.edittext_layout)
    RelativeLayout edittextLayout;
    @Bind(R.id.btn_more)
    Button btnMore;
    @Bind(R.id.btn_send)
    Button btnSend;
    @Bind(R.id.rl_bottom)
    LinearLayout rlBottom;
    @Bind(R.id.view_photo)
    LinearLayout viewPhoto;
    @Bind(R.id.view_camera)
    LinearLayout viewCamera;
    @Bind(R.id.view_location)
    LinearLayout viewLocation;
    @Bind(R.id.custom_botton)
    LinearLayout customBotton;
    @Bind(R.id.view_file)
    LinearLayout viewFile;
    @Bind(R.id.view_audio)
    LinearLayout viewAudio;
    @Bind(R.id.view_video)
    LinearLayout viewVideo;
    @Bind(R.id.view_location_video)
    LinearLayout viewLocationVideo;
    @Bind(R.id.ll_btn_container)
    LinearLayout llBtnContainer;
    @Bind(R.id.more)
    LinearLayout more;
    @Bind(R.id.bar_bottom)
    LinearLayout barBottom;
    @Bind(R.id.pb_load_more)
    ProgressBar pbLoadMore;
    @Bind(R.id.list)
    EasyRecyclerView recyclerview;
    @Bind(R.id.root_layout)
    RelativeLayout rootview;
    private ChatAdapter adapter;
    private InputMethodManager manager;
    private List<ChatMsg.Re> chatData = new ArrayList<>();
    private List<ChatMsg.Re> newchatData = new ArrayList<>();
    private MsgReceiver receiver;
    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public void initView() {
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        toolbar.setTitle("指挥通讯");
        toolbar.setSubtitle("警情2134323");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter = new ChatAdapter(this));
        //注册广播
        receiver = new MsgReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("service.MsgService");
        registerReceiver(receiver, filter);
        LogUtil.e("广播注册成功！");
        startService(new Intent(this, GetNewMsgService.class));

        recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (more.getVisibility() == View.VISIBLE) {
                    more.setVisibility(View.GONE);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        recyclerview.scrollToPosition(adapter.getCount() - 1);
        RxView.clicks(etSendmessage).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                recyclerview.scrollToPosition(adapter.getCount() - 1);
                if (more.getVisibility() == View.VISIBLE) {
                    more.setVisibility(View.GONE);
                }
            }
        });
        etSendmessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
             /*   if (s.length() > 1) {
                    btnMore.setVisibility(View.GONE);
                    btnSend.setVisibility(View.VISIBLE);
                } else {
                    btnMore.setVisibility(View.VISIBLE);
                    btnSend.setVisibility(View.GONE);
                }*/
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        RxView.clicks(btnSend).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Sendmsg();
                    }
                });

       getmsg();


    }

    private void getmsg() {
        adapter.clear();
        chatData.clear();
        app.jqAPIService.GetMsg("213213123", "1231231233").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChatMsg>() {
                    @Override
                    public void onCompleted() {
                        recyclerview.scrollToPosition(adapter.getCount() - 1);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ChatMsg chatMsg) {
                        LogUtil.e("chatmsg:" + chatMsg.getRes().size());
                        chatData = chatMsg.getRes();
                        adapter.addAll(chatData);


                    }
                });
    }

    private void getNewmsg() {
        chatData.clear();
        app.jqAPIService.GetNewMsg("213213123", "1231231233").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChatMsg>() {
                    @Override
                    public void onCompleted() {
                        recyclerview.scrollToPosition(adapter.getCount() - 1);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ChatMsg chatMsg) {
                        LogUtil.e("chatNewmsg:" + chatMsg.getRes().size());
                        chatData = chatMsg.getRes();


                        adapter.addAll(chatData);


                    }
                });
    }


    private void Sendmsg() {
        Date currentdate = new Date(System.currentTimeMillis());
        String curredate = AskForLeaveActivity.getTime(currentdate);

        app.jqAPIService.sendMsg("2016072100100000060", etSendmessage.getText().toString(), "213", curredate, "10001")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        etSendmessage.setText("");
                       getNewmsg();
                    }

                    @Override
                    public void onError(Throwable e) {
                        SomeUtil.showSnackBar(rootview, "error:" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.e("result:" + s);
                        // SomeUtil.showSnackBar(rootview,"result:"+s);
                    }
                });

    }
    @Override
    protected void onStop()
    {
        unregisterReceiver(receiver);
        super.onStop();
    }

    private class MsgReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle  = intent.getExtras();
            String msg = bundle.getString("msg");
            if (msg.equals("newmsg")){
               /* NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                PendingIntent pendingIntent3 = PendingIntent.getActivity(context, 0,
                        new Intent(context, ChatActivity.class), 0);
                Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                // 通过Notification.Builder来创建通知，注意API Level
                // API16之后才支持
                Notification notify3 = null; // 需要注意build()是在APIlevel16及之后增加的，API11可以使用getNotificatin()来替代
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    notify3 = new Notification.Builder(context)
                            .setSound(ringUri).build();
                }

                notify3.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
                manager.notify(1, notify3);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示*/
                 getNewmsg();

            }
        }
    }


    /**
     * 隐藏软键盘
     */
    private void hideKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @OnClick({ R.id.btn_set_mode_keyboard, R.id.btn_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set_mode_keyboard:
                break;
            case R.id.btn_more:
                //llBtnContainer.setVisibility(llBtnContainer.getVisibility()==View.GONE?View.VISIBLE:View.GONE);
                if (more.getVisibility() == View.GONE) {
                    System.out.println("more gone");
                    hideKeyboard();
                    more.setVisibility(View.VISIBLE);
                   // llBtnContainer.setVisibility(View.VISIBLE);

                } else {
                    more.setVisibility(View.GONE);
                }
                break;
        }
    }
}
