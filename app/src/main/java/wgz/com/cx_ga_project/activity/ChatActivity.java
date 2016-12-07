package wgz.com.cx_ga_project.activity;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;

import me.iwf.photopicker.PhotoPicker;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.chatAdapter.ChatAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.bean.ChatUpProgress;
import wgz.com.cx_ga_project.entity.ChatMsg;
import wgz.com.cx_ga_project.entity.ChatPic;
import wgz.com.cx_ga_project.service.GetNewMsgService;
import wgz.com.cx_ga_project.util.DatrixUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.com.cx_ga_project.util.UriUtils;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static wgz.com.cx_ga_project.app.DATRIX_BASE_URL;
import static wgz.com.cx_ga_project.util.fileUtil.delFolder;

/**
 * 指挥通讯
 */
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
    @Bind(R.id.sicupload_bg)
    CardView uploadBg;
    @Bind(R.id.sicupload_prg)
    ProgressBar uploadPrg;
    @Bind(R.id.sicupload_protext)
    TextView uploadProtext;
    //图片地址
    List<String> paths = new ArrayList<>();
    private ChatAdapter adapter;
    private InputMethodManager manager;
    private List<ChatMsg.Re> chatData = new ArrayList<>();
    private List<ChatMsg.Re> newchatData = new ArrayList<>();
    private String fileid = "";
    private String videoPath = "";
    private Subscription rxSubscription;
    private Subscription rxSubscription2;
    private Subscription rxSubscription3;
   // private String datrixUrl = DATRIX_BASE_URL + "preview/getImage?fileid=";
   private GetNewMsgService mService = new GetNewMsgService();
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            GetNewMsgService.MyBind myBind = (GetNewMsgService.MyBind) service;
            mService = myBind.getMyService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public void initView() {
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        toolbar.setTitle("指挥通讯");
        toolbar.setSubtitle(getIntent().getStringExtra("jqid"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter = new ChatAdapter(this));
        // startService(new Intent(this, GetNewMsgService.class));
        bindService(new Intent(this, GetNewMsgService.class), connection, BIND_AUTO_CREATE);

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
        RxView.clicks(etSendmessage).throttleFirst(500, TimeUnit.MICROSECONDS)
                .subscribe(aVoid -> {
                    recyclerview.scrollToPosition(adapter.getCount() - 1);
                    if (more.getVisibility() == View.VISIBLE) {
                        more.setVisibility(View.GONE);
                    }
                });
        etSendmessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        RxView.clicks(btnSend).throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    if (etSendmessage.getText().toString().equals("")) {

                    } else {
                        Sendmsg();
                        hideKeyboard();
                    }

                });

        getmsg();

        rxSubscription2 = RxBus.getDefault().toObservable(ChatUpProgress.class)
                .subscribe(new Subscriber<ChatUpProgress>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("rxSubscription2 error :"+e.toString());
                      //  SomeUtil.checkHttpException(ChatActivity.this,e,rootview);
                    }

                    @Override
                    public void onNext(final ChatUpProgress progress) {

                        runOnUiThread(() -> uploadProtext.setText(progress.getPro()));

                    }
                });
        rxSubscription3 = RxBus.getDefault().toObservable(ChatPic.class)
                .subscribe(chatPic -> {
                    if (more.getVisibility() == View.VISIBLE) {
                        more.setVisibility(View.GONE);
                    }
                    LogUtil.d("选择图片回掉");
                    newchatData.clear();
                    paths.clear();
                    paths = chatPic.getPaths();
                    ChatMsg re = new ChatMsg();
                    ChatMsg.Re pic = re.new Re();
                    Date currentdate = new Date(System.currentTimeMillis());
                    String curredate = getTime(currentdate);
                    pic.setPic(paths.get(0));
                    pic.setSendtime(curredate);
                    pic.setMark(0);
                    pic.setIssend("2");
                    newchatData.add(pic);
                    adapter.addAll(newchatData);
                    recyclerview.scrollToPosition(adapter.getCount() - 1);
                    //uploadpic(makeFiles());
                   // SomeUtil.showSnackBar(rootview,"发送图片！");
                    SendPicmsg();
                });

        rxSubscription = RxBus.getDefault().toObservable(String.class)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                       // SomeUtil.checkHttpException(ChatActivity.this,e,rootview);
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("test : " + s);
                        if (s.equals("flush")) {
                            NotificationManager manager = (NotificationManager) ChatActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                            Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            // 通过Notification.Builder来创建通知，注意API Level
                            // API16之后才支持
                            Notification notify3 = null; // 需要注意build()是在APIlevel16及之后增加的，API11可以使用getNotificatin()来替代
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                notify3 = new Notification.Builder(ChatActivity.this)
                                        .setSound(ringUri).build();
                            }

                            notify3.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
                            manager.notify(1, notify3);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
                            getNewmsg();


                        }
                        if (s.equals("addchatpic")){





                        }


                    }
                });
    }

    private void getmsg() {
        adapter.clear();
        chatData.clear();
        app.jqAPIService.GetMsg(SomeUtil.getJQId(), SomeUtil.getUserId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChatMsg>() {
                    @Override
                    public void onCompleted() {

                        hideKeyboard();
                    }

                    @Override
                    public void onError(Throwable e) {
                   }

                    @Override
                    public void onNext(ChatMsg chatMsg) {
                        LogUtil.d("chatmsg:" + chatMsg.getRes().size());
                        chatData = chatMsg.getRes();
                        adapter.addAll(chatData);
                        recyclerview.scrollToPosition(adapter.getCount() - 1);


                    }
                });
    }

    private void getNewmsg() {
        LogUtil.d("获取新消息");
        chatData.clear();
        app.jqAPIService.GetNewMsg(SomeUtil.getJQId(), SomeUtil.getUserId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChatMsg>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                       // SomeUtil.checkHttpException(ChatActivity.this,e,rootview);
                    }

                    @Override
                    public void onNext(ChatMsg chatMsg) {
                        LogUtil.d("chatNewmsg:" + chatMsg.getRes().size());
                        chatData = chatMsg.getRes();
                        adapter.addAll(chatData);
                        recyclerview.scrollToPosition(adapter.getCount() - 1);
                        hideKeyboard();

                    }
                });
    }

    private void getPicVideoNewmsg() {
        LogUtil.d("获取新消息");
        chatData.clear();
        app.jqAPIService.GetNewMsg(SomeUtil.getJQId(), SomeUtil.getUserId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChatMsg>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                       // SomeUtil.checkHttpException(ChatActivity.this,e,rootview);
                    }

                    @Override
                    public void onNext(ChatMsg chatMsg) {

                        adapter.remove(adapter.getCount() - 1);
                        LogUtil.d("chatNewmsg:" + chatMsg.getRes().size());
                        chatData = chatMsg.getRes();
                        adapter.addAll(chatData);
                        recyclerview.scrollToPosition(adapter.getCount() - 1);
                        hideKeyboard();

                    }
                });
    }

    public  String getTime(Date date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    private void Sendmsg() {
        Date currentdate = new Date(System.currentTimeMillis());
        String curredate = getTime(currentdate);
        app.jqAPIService.sendMsg(SomeUtil.getJQId(), etSendmessage.getText().toString(), "", "", "", SomeUtil.getTASKId(), curredate, SomeUtil.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        SomeUtil.showSnackBar(rootview, "error:" + e.toString());
                       // SomeUtil.checkHttpException(ChatActivity.this,e,rootview);
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("result:" + s);
                        etSendmessage.setText("");
                        getNewmsg();
                        // SomeUtil.showSnackBar(rootview,"result:"+s);
                    }
                });

    }

    private void SendPicmsg() {

        //DatrixCreate();
        DatrixUtil datrixUtil = new DatrixUtil(paths, rootview);
        datrixUtil.DatrixUpLoadPic2();
        datrixUtil.setOnAfterFinish((fileid1, ids) -> {
            Date currentdate = new Date(System.currentTimeMillis());
            String curredate = getTime(currentdate);

           // datrixUrl + fileid1 + datrixurl2
            app.jqAPIService.sendMsg(SomeUtil.getJQId(), "", fileid1 , "", "", SomeUtil.getTASKId(), curredate, SomeUtil.getUserId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            etSendmessage.setText("");

                            getPicVideoNewmsg();
                        }

                        @Override
                        public void onError(Throwable e) {
                            //SomeUtil.showSnackBar(rootview, "error:" + e.toString());
                            LogUtil.d("error:" + e.toString());
                           // SomeUtil.checkHttpException(ChatActivity.this,e,rootview);
                        }

                        @Override
                        public void onNext(String s) {
                            LogUtil.d("Finish result:" + s);
                            // SomeUtil.showSnackBar(rootview,"result:"+s);
                        }
                    });
        });

    }

    private void SendVideoMsg() {
        uploadPrg.setVisibility(View.VISIBLE);
        uploadBg.setVisibility(View.VISIBLE);
        uploadProtext.setVisibility(View.VISIBLE);
        DatrixUtil datrixUtil = new DatrixUtil(videoPath, rootview);
        datrixUtil.DatrixUpLoadVideo();
        datrixUtil.setOnAfterFinish((fileid1, ids) -> {
            LogUtil.d("UpLoad Video finish  id : " + fileid1);
            Date currentdate = new Date(System.currentTimeMillis());
            String curredate = getTime(currentdate);


            app.jqAPIService.sendMsg(SomeUtil.getJQId(), "", "",  fileid1,  fileid1 , SomeUtil.getTASKId(), curredate, SomeUtil.getUserId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {

                        @Override
                        public void onCompleted() {
                            etSendmessage.setText("");
                            uploadPrg.setVisibility(View.GONE);
                            uploadBg.setVisibility(View.GONE);
                            uploadProtext.setVisibility(View.GONE);
                            //2016/9/12 获取新消息 删除本地 换成服务器请求的
                            //adapter.getHeader()
                            //getNewmsg();
                            //LogUtil.d("recyclerview count:"+recyclerview.getChildCount());
                            //recyclerview.getChildCount();
                            //adapter.remove(adapter.getCount() - 1);
                            getPicVideoNewmsg();
                        }

                        @Override
                        public void onError(Throwable e) {
                            //SomeUtil.showSnackBar(rootview, "error:" + e.toString());
                            LogUtil.d("error:" + e.toString());
                           // SomeUtil.checkHttpException(ChatActivity.this,e,rootview);
                        }

                        @Override
                        public void onNext(String s) {
                            LogUtil.d("Finish result:" + s);
                            // SomeUtil.showSnackBar(rootview,"result:"+s);
                        }
                    });

            //SomeUtil.showSnackBarLong(rootview, "视频id：" + fileid);

        });


    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        // unregisterReceiver(receiver);
    }


   /* private class MsgReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String msg = bundle.getString("msg");
            if (msg.equals("newmsg")) {
               NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
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
                getNewmsg();

            }
        }
    }*/


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

    @OnClick({R.id.btn_set_mode_keyboard, R.id.btn_more, R.id.view_photo, R.id.view_camera, R.id.view_video})
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
                    llBtnContainer.setVisibility(View.VISIBLE);

                } else {
                    more.setVisibility(View.GONE);
                }
                break;
            case R.id.view_photo:
               /* Intent intent = new Intent(ChatActivity.this, PickPhotoActivity2.class);
                intent.putExtra(PhotoPickerFragment2.EXTRA_SELECT_COUNT, 1);
                intent.putExtra(PhotoPickerFragment2.EXTRA_DEFAULT_SELECTED_LIST, "");
                intent.putExtra(HTTP_URL, "");
                startActivityForResult(intent, 7);*/
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(true)
                        .start(this, 777);




                break;
            case R.id.view_camera:
                break;
            case R.id.view_video:
                delFolder("/storage/sdcard0/temp");
                Intent intent2 = new Intent();
                intent2.setType("video/*");
                intent2.setAction(Intent.ACTION_GET_CONTENT);
                intent2.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent2, 4);

                break;
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
        if (!rxSubscription2.isUnsubscribed()) {
            rxSubscription2.unsubscribe();
        }
        unbindService(connection);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (resultCode == RESULT_OK && requestCode == 777){
                if (data != null) {
                    ArrayList<String> photos =
                            data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    if (more.getVisibility() == View.VISIBLE) {
                        more.setVisibility(View.GONE);
                    }
                    LogUtil.d("选择图片回掉");
                    newchatData.clear();
                    paths.clear();
                    paths = photos;
                    ChatMsg re = new ChatMsg();
                    ChatMsg.Re pic = re.new Re();
                    Date currentdate = new Date(System.currentTimeMillis());
                    String curredate = getTime(currentdate);
                    pic.setPic(paths.get(0));
                    pic.setSendtime(curredate);
                    pic.setMark(0);
                    pic.setIssend("2");
                    newchatData.add(pic);
                    adapter.addAll(newchatData);
                    recyclerview.scrollToPosition(adapter.getCount() - 1);
                    //uploadpic(makeFiles());
                    // SomeUtil.showSnackBar(rootview,"发送图片！");
                    SendPicmsg();
                }

            }



            if (resultCode == 7) {
                if (data.getStringExtra("result").equals("addpic")) {
                    if (more.getVisibility() == View.VISIBLE) {
                        more.setVisibility(View.GONE);
                    }
                    newchatData.clear();
                    paths.clear();
                    paths = data.getStringArrayListExtra("paths");
                    ChatMsg re = new ChatMsg();
                    ChatMsg.Re pic = re.new Re();
                    Date currentdate = new Date(System.currentTimeMillis());
                    String curredate = getTime(currentdate);
                    pic.setPic(paths.get(0));
                    pic.setSendtime(curredate);
                    pic.setMark(0);
                    pic.setIssend("2");
                    newchatData.add(pic);
                    adapter.addAll(newchatData);
                    recyclerview.scrollToPosition(adapter.getCount() - 1);
                    //uploadpic(makeFiles());
                    SomeUtil.showSnackBar(rootview,"发送图片！");
                    SendPicmsg();
                    //DatrixCreate();
                    //adapter.addAll();
                }

            }

            if (requestCode == 4) {
                if (more.getVisibility() == View.VISIBLE) {
                    more.setVisibility(View.GONE);
                }
                newchatData.clear();

                Uri uri = data.getData();
                videoPath = UriUtils.getPath(getApplicationContext(), uri);
                // 视频文件路径


                ChatMsg re = new ChatMsg();
                ChatMsg.Re pic = re.new Re();
                Date currentdate = new Date(System.currentTimeMillis());
                String curredate = getTime(currentdate);
                pic.setVideo(videoPath);
                pic.setPic("null");
                pic.setSendtime(curredate);
                pic.setMark(0);
                pic.setIssend("2");
                newchatData.add(pic);
                adapter.addAll(newchatData);
                recyclerview.scrollToPosition(adapter.getCount() - 1);

                SendVideoMsg();

            }
        } catch (Exception e) {
            LogUtil.d("error : " + e);

        }
    }


}
