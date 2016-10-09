package wgz.com.cx_ga_project.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.chatAdapter.ChatAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.entity.ChatMsg;
import wgz.com.cx_ga_project.entity.DatrixCreat;
import wgz.com.cx_ga_project.fragment.PhotoPickerFragment;
import wgz.com.cx_ga_project.service.GetNewMsgService;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.com.cx_ga_project.util.UriUtils;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static wgz.com.cx_ga_project.activity.PickPhotoActivity.HTTP_URL;

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
    private ChatAdapter adapter;
    private InputMethodManager manager;
    private List<ChatMsg.Re> chatData = new ArrayList<>();
    private List<ChatMsg.Re> newchatData = new ArrayList<>();
    private MsgReceiver receiver;
    //图片地址
    List<String> paths = new ArrayList<>();
    private String fileid = "";
    private String videoPath = "";
    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }
    private String datrixUrl = "http://101.231.77.242:9001/preview/getImage?fileid=";
    private String datrixurl2 = "&token=X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=";

    @Override
    public void initView() {
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        toolbar.setTitle("指挥通讯");
        toolbar.setSubtitle("警情2134323");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter = new ChatAdapter(this));

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

        app.jqAPIService.sendMsg("2016072100100000060", etSendmessage.getText().toString(),"", "213", curredate, "532301030355")
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
    private void SendPicmsg(String path) {

        DatrixCreate();


    }

    private void SendVideoMsg(){




    }

    @Override
    protected void onStop()
    {

        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        receiver = new MsgReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("service.MsgService");
        registerReceiver(receiver, filter);
        LogUtil.e("广播注册成功！");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
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

    @OnClick({ R.id.btn_set_mode_keyboard, R.id.btn_more,R.id.view_photo,R.id.view_camera,R.id.view_video})
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
                Intent intent = new Intent(ChatActivity.this, PickPhotoActivity.class);
                intent.putExtra(PhotoPickerFragment.EXTRA_SELECT_COUNT, 1);
                intent.putExtra(PhotoPickerFragment.EXTRA_DEFAULT_SELECTED_LIST, "");
                intent.putExtra(HTTP_URL, "");
                startActivityForResult(intent, 7);
                break;
            case R.id.view_camera:
                break;
            case R.id.view_video:
                Intent intent2 = new Intent();
                intent2.setType("video/*");
                intent2.setAction(Intent.ACTION_GET_CONTENT);
                intent2.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent2,
                        4);

                break;
        }
    }
    private List<File> makeFiles(){
        final List<File> files = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            File file = new File(paths.get(i));
            files.add(file);
        }
        return  files;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode==7){
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
                    String curredate = AskForLeaveActivity.getTime(currentdate);
                    pic.setPic(paths.get(0));
                    pic.setSendtime(curredate);
                    pic.setMark(0);
                    pic.setIssend("2");
                    newchatData.add(pic);
                    adapter.addAll(newchatData);
                    recyclerview.scrollToPosition(adapter.getCount() - 1);
                    uploadpic(makeFiles());
                    //DatrixCreate();
                    //adapter.addAll();
                }

            }

            if (requestCode==4){
                if (more.getVisibility() == View.VISIBLE) {
                    more.setVisibility(View.GONE);
                }
                newchatData.clear();

                Uri uri = data.getData();
                videoPath  = UriUtils.getPath(getApplicationContext(),uri);
                // 视频文件路径
                SomeUtil.showSnackBarLong(rootview,"视频地址："+videoPath);

                ChatMsg re = new ChatMsg();
                ChatMsg.Re pic = re.new Re();
                Date currentdate = new Date(System.currentTimeMillis());
                String curredate = AskForLeaveActivity.getTime(currentdate);
                pic.setVideo(videoPath);
                pic.setPic("null");
                pic.setSendtime(curredate);
                pic.setMark(0);
                pic.setIssend("2");
                newchatData.add(pic);
                adapter.addAll(newchatData);
                recyclerview.scrollToPosition(adapter.getCount() - 1);





            }


        } catch (Exception e) {
            LogUtil.e("error : " + e);

        }

    }

    private void uploadpic(List<File> files) {
        app.apiService.uploadFileWithRequestBody("saveAppPics",SomeUtil.filesToMultipartBody(files))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("uploadpic error : " + e.toString());
                        adapter.remove(adapter.getCount() - 1);
                    }
                    @Override
                    public void onNext(String s) {
                        LogUtil.e("upPic :"+s);
                        if (s.contains("\"code\":200")){

                            SendPicmsg(paths.get(0));
                           /* SomeUtil.showSnackBar(rootview,"提交成功！").setCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    finish();
                                }
                            });*/
                        }
                        else {
                            SomeUtil.showSnackBar(rootview,"网络错误，请再试！");
                        }
                    }
                });

    }


    /**
     * 获取视频缩略图
     * @param filePath
     * @return
     */
    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            try {
                retriever.release();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
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


                            app.jqAPIService.sendMsg("2016072100100000060", " ",datrixUrl+fileid+datrixurl2, "213", curredate, "532301030355")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<String>() {
                                        @Override
                                        public void onCompleted() {
                                            etSendmessage.setText("");
                                            // TODO: 2016/9/12 获取新消息 删除本地 换成服务器请求的
                                            //adapter.getHeader()
                                            //getNewmsg();
                                            //LogUtil.e("recyclerview count:"+recyclerview.getChildCount());
                                            //recyclerview.getChildCount();
                                            adapter.remove(adapter.getCount() - 1);

                                            getNewmsg();
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            //SomeUtil.showSnackBar(rootview, "error:" + e.toString());
                                            LogUtil.e("error:"+ e.toString());
                                        }

                                        @Override
                                        public void onNext(String s) {
                                            LogUtil.e("添加图片聊天记录result:" + s);


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


}
