package wgz.com.cx_ga_project.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.AddPictureAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.bean.ChatUpProgress;
import wgz.com.cx_ga_project.bean.progress;
import wgz.com.cx_ga_project.entity.TypeOfAuth;
import wgz.com.cx_ga_project.fragment.PhotoPickerFragment;
import wgz.com.cx_ga_project.util.DatrixUtil;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.com.cx_ga_project.util.UriUtils;
import wgz.com.cx_ga_project.view.Mylayout;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static wgz.com.cx_ga_project.activity.PickPhotoActivity.HTTP_URL;
import static wgz.com.cx_ga_project.app.DATRIX_BASE_URL;

public class SICInputActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.content_sicinput2)
    RelativeLayout rootview;
    @Bind(R.id.upload_sic)
    FloatingActionButton uploadSic;


    @Bind(R.id.tesllv)
    LinearLayout tesllv;
    @Bind(R.id.sicinput_pics)
    EasyRecyclerView sicinputPics;
    List<String> paths = new ArrayList<>();
    List<String> videopaths = new ArrayList<>();
    @Bind(R.id.sicupload_prg)
    ProgressBar sicuploadPrg;
    @Bind(R.id.sicupload_bg)
    CardView sicuploadBg;
    @Bind(R.id.sicupload_protext)
    TextView sicuploadProtext;
    private List<Map<String, Object>> mdata = new ArrayList<>();
    private Mylayout manager;
    private String typeid = "";
    private String typename = "";
    private List<TypeOfAuth.AuthRe> typedata = new ArrayList();
    private String authid = "";
    private String picids = "";
    private String videoids = "";
    private HashMap<Integer, View> lmap = new HashMap<Integer, View>();
    private List<View> list = new ArrayList<>();
    private AddPictureAdapter adapter;
    //private String videoPath = "";
    private String videoPicPath = "";
    private String datrixurl2 = "&token=X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=";
    private String datrixVideoPicdurl1 = DATRIX_BASE_URL + "preview/coverMedium?fileid=";
    private Subscription rxSubscription;
    private boolean isupload = false;


    @Override
    public int getLayoutId() {
        return R.layout.activity_sicinput2;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }

    @Override
    public void initView() {
        toolbar.setTitle("社会信息采集");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        typename = intent.getStringExtra("typename");
        typeid = intent.getStringExtra("typeid");
        toolbar.setSubtitle(typename);
        LogUtil.d("typename: " + typename);
        LogUtil.d("typeid: " + typeid);
        sicinputPics.setLayoutManager(new GridLayoutManager(this, 4));
        sicinputPics.setAdapter(adapter = new AddPictureAdapter(this));
        initData();
        rxSubscription = RxBus.getDefault().toObservable(ChatUpProgress.class)
                .subscribe(new Subscriber<ChatUpProgress>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("error :" +e.toString());
                    }

                    @Override
                    public void onNext(final ChatUpProgress chatUpProgress) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sicuploadProtext.setText(chatUpProgress.getPro());
                            }
                        });

                    }
                });


        RxView.clicks(uploadSic).throttleFirst(500, TimeUnit.MICROSECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        // TODO: 2016/11/8 添加图片视频
                        String[] titles = new String[]{"添加图片", "添加视频"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(SICInputActivity.this);
                        builder.setTitle("请选择要添加的附件")
                                .setItems(titles, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                Intent intent = new Intent(SICInputActivity.this, PickPhotoActivity.class);
                                                intent.putExtra(PhotoPickerFragment.EXTRA_SELECT_COUNT, 6);
                                                intent.putExtra(PhotoPickerFragment.EXTRA_DEFAULT_SELECTED_LIST, "");
                                                intent.putExtra(HTTP_URL, "");
                                                startActivityForResult(intent, 9);

                                                break;
                                            case 1:
                                                Intent intent2 = new Intent();
                                                intent2.setType("video/*");
                                                intent2.setAction(Intent.ACTION_GET_CONTENT);
                                                intent2.addCategory(Intent.CATEGORY_OPENABLE);
                                                startActivityForResult(intent2, 3);

                                                break;


                                        }
                                    }
                                }).show();

                    }
                });

    }

    private void UploadInfo() {

        Map<String, Object> paraValue = new HashMap<>();
        LogUtil.d("mdata.size :" + mdata.size());
        LogUtil.d("lmap.size :" + lmap.size());

        for (int i = 0; i < list.size(); i++) {
            try {

                View itemview = list.get(i);
                TextView titleview = (TextView) itemview.findViewById(R.id.sic_input_titleid);
                EditText contentview = (EditText) itemview.findViewById(R.id.sic_input_content);
                String key = titleview.getText().toString();
                String value = contentview.getText().toString();
                paraValue.put(key, value);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        paraValue.put("policeid", SomeUtil.getUserId());
        paraValue.put("addtime", SomeUtil.getSysTime());
        paraValue.put("authid", authid);


        LogUtil.d("sicinput json : " + SomeUtil.mapTojsonStr(paraValue));
        app.apiService.addSocialInfoTxt(typeid, SomeUtil.mapTojsonStr(paraValue), SomeUtil.getUserId()
                , picids, videoids)
                .compose(RxUtil.<String>applySchedulers())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        sicuploadPrg.setVisibility(View.GONE);
                        sicuploadBg.setVisibility(View.GONE);
                        sicuploadProtext.setVisibility(View.GONE);
                        isupload = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        sicuploadPrg.setVisibility(View.GONE);
                        sicuploadBg.setVisibility(View.GONE);
                        isupload = false;
                        sicuploadProtext.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("sicinputresult : " + s);
                        if (s.contains("200")){
                            SomeUtil.showSnackBar(rootview, "提交成功！").setCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    finish();
                                }
                            });

                        }else {

                            SomeUtil.showSnackBar(rootview,"网络错误！");
                        }



                    }
                });

    }

    private void initData() {
        app.apiService.getFieldNameByType(typeid)
                .compose(RxUtil.<String>applySchedulers())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("sicinput error : " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("s : " + s);
                        mdata = SomeUtil.JsonStrToListMap(s);
                        for (int i = 0; i < mdata.size(); i++) {
                            View view = LayoutInflater.from(SICInputActivity.this).inflate(R.layout.item_input_sic, null);
                            TextView tvname = (TextView) view.findViewById(R.id.sic_input_title);
                            TextView tvid = (TextView) view.findViewById(R.id.sic_input_titleid);
                            tvname.setText(mdata.get(i).get("zh_name").toString());
                            tvid.setText(mdata.get(i).get("en_name").toString());
                            tesllv.addView(view);
                            list.add(view);
                        }


                        LogUtil.d("listmap : " + mdata.toString());

                    }
                });

        app.apiService.getTypeOfAuth()
                .compose(RxUtil.<TypeOfAuth>applySchedulers())
                .subscribe(new Subscriber<TypeOfAuth>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        SomeUtil.checkHttpException(getApplicationContext(), e, rootview);
                    }

                    @Override
                    public void onNext(TypeOfAuth typeOfAuth) {
                        typedata = typeOfAuth.getRes();
                        LogUtil.d("typeOfAuth : " + typedata.toString());
                    }
                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sic_input, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            LogUtil.d("resultCode :" + resultCode);
            if (resultCode == 9) {
                if (data.getStringExtra("result").equals("addpic")) {

                    // TODO: 2016/11/8 设置高度
                    ViewGroup.LayoutParams lp;
                    lp = sicinputPics.getLayoutParams();
                    lp.height = 240;
                    sicinputPics.setLayoutParams(lp);

                    paths.clear();
                    paths = data.getStringArrayListExtra("paths");
                    LogUtil.d("paths :" + paths.toString());
                    adapter.addAll(paths);
                }

            } else {
                Uri uri = data.getData();
                videopaths.add( UriUtils.getPath(getApplicationContext(), uri));
                //videoPath = UriUtils.getPath(getApplicationContext(), uri);
               /* paths.clear();
                paths.add(testvideo);*/
                adapter.add("testvideo");
                // SomeUtil.showSnackBar(rootview,"videoPath: "+videoPath);
                // 视频文件路径


            }


        } catch (Exception e) {
            LogUtil.d("error : " + e);

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:




                break;
            case R.id.id_sic_input_menu:
                String[] typename = new String[typedata.size()];
                final String[] typeid = new String[typedata.size()];
                for (int i = 0; i < typedata.size(); i++) {
                    typename[i] = typedata.get(i).getValname();
                    typeid[i] = typedata.get(i).getValcode();

                }
                AlertDialog.Builder builder = new AlertDialog.Builder(SICInputActivity.this);
                builder.setTitle("请选择数据权限")
                        .setSingleChoiceItems(typename, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                authid = typeid[which];
                                LogUtil.d("authID : " + authid);
                            }
                        }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        commit();
                    }
                }).setNegativeButton("取消", null).show();


                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void commit() {
        SomeUtil.showSnackBar(rootview,"正在上传请稍等！");
        Observable.just("go").compose(RxUtil.<String>applySchedulers())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        sicuploadPrg.setVisibility(View.VISIBLE);
                        sicuploadBg.setVisibility(View.VISIBLE);
                        sicuploadProtext.setVisibility(View.VISIBLE);
                        isupload = true;
                    }
                });

        LogUtil.d("paths.size :"+paths.size());
        LogUtil.d("videopaths.size :"+videopaths.size());
        if (paths.size() > 0 && videopaths.size() <= 0) {

            LogUtil.d("上传图片和文字");

            DatrixUtil datrixUtil = new DatrixUtil(paths, rootview);
            datrixUtil.DatrixUpLoadPic2();
            datrixUtil.setOnAfterFinish(new DatrixUtil.AfterFinish() {
                @Override
                public void afterfinish(String fileid, List<String> ids) {
                    UploadPic(ids);
                    UploadInfo();
                }
            });
        }
        if (paths.size() > 0 && videopaths.size() > 0) {
            LogUtil.d("上传图片和文字和视频");
            DatrixUtil datrixUtil = new DatrixUtil(paths, rootview);
            datrixUtil.DatrixUpLoadPic2();
            datrixUtil.setOnAfterFinish(new DatrixUtil.AfterFinish() {
                @Override
                public void afterfinish(String fileid, List<String> ids) {
                    UploadPic(ids);
                    UpLoadVideo();

                }
            });


        }
        if (paths.size() <= 0 && videopaths.size() > 0) {
            LogUtil.d("上传视频");
            UpLoadVideo();
        }
        if (paths.size() <= 0 && videopaths.size() <= 0) {
            LogUtil.d("上传文字");
            UploadInfo();
        }

    }


    private void UpLoadVideo() {
        DatrixUtil datrixUtil = new DatrixUtil(videopaths, rootview);
        //datrixUtil.DatrixUpLoadVideo();
        datrixUtil.DatrixUpLoadVideos();
        datrixUtil.setOnAfterFinish(new DatrixUtil.AfterFinish() {
            @Override
            public void afterfinish(String fileid, List<String> ids) {
                StringBuffer sf = new StringBuffer();
                for (int i = 0; i < ids.size(); i++) {
                    if (i == ids.size() - 1) {
                        sf.append(ids.get(i));
                    } else {
                        sf.append(ids.get(i));
                        sf.append(",");
                    }
                }
                videoids = sf.toString();
                LogUtil.d("videoids : " + videoids);


                UploadInfo();
            }
        });


    }

    private void UploadPic(List<String> ids) {
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < ids.size(); i++) {
            if (i == ids.size() - 1) {
                sf.append(ids.get(i));
            } else {
                sf.append(ids.get(i));
                sf.append(",");
            }


        }
        picids = sf.toString();
        LogUtil.d("picids : " + picids);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
