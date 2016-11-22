package wgz.com.cx_ga_project.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func2;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.AddPictureAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.bean.ChatUpProgress;
import wgz.com.cx_ga_project.bean.progress;
import wgz.com.cx_ga_project.entity.SICDetil;
import wgz.com.cx_ga_project.fragment.PhotoPickerFragment;
import wgz.com.cx_ga_project.util.DatrixUtil;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.com.cx_ga_project.util.UriUtils;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static wgz.com.cx_ga_project.activity.PickPhotoActivity.HTTP_URL;

public class SICLogDetilActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.log_tesllv)
    LinearLayout logTesllv;
    @Bind(R.id.sicloginput_pics)
    EasyRecyclerView sicloginputPics;
    @Bind(R.id.sicupload_prg)
    ProgressBar sicuploadPrg;
    @Bind(R.id.content_siclog_detil)
    RelativeLayout rootview;
    @Bind(R.id.sicupload_bg)
    CardView sicuploadBg;
    @Bind(R.id.sicupload_protext)
    TextView sicuploadProtext;
    @Bind(R.id.siclog)
    FloatingActionButton siclog;
    List<String> paths = new ArrayList<>();
    List<String> videopaths = new ArrayList<>();
    List<String> NewPicpaths = new ArrayList<>();
    private AddPictureAdapter adapter;
    private String type = "";
    private String docid = "";
    private List<Map<String, Object>> viewdata = new ArrayList<>();
    private List<View> viewlist = new ArrayList<>();
    private SICDetil detildata = new SICDetil();
    private Subscription rxSubscription;
    private String picids = "";
    private String videoids = "";
    private String videoPath = "";


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_siclog_detil;
    }

    @Override
    public void initView() {
        toolbar.setTitle("社会信息采集");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        docid = intent.getStringExtra("docid");
        LogUtil.d("type :" + type);
        LogUtil.d("docid :" + docid);
        sicloginputPics.setLayoutManager(new GridLayoutManager(this, 4));
        sicloginputPics.setAdapter(adapter = new AddPictureAdapter(this));
        initdata();
        rxSubscription = RxBus.getDefault().toObservable(ChatUpProgress.class)
                .subscribe(new Action1<ChatUpProgress>() {
                    @Override
                    public void call(final ChatUpProgress s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sicuploadProtext.setText(s.getPro());
                            }
                        });


                    }
                });
    }

    private void initdata() {

        Observable<SicDetilAndStr> com =
                Observable.zip(app.apiService.getTxtDetail(type, docid)
                                .compose(RxUtil.<String>applySchedulers()),
                        app.apiService.getSocialInfoDetil(type, docid)
                                .compose(RxUtil.<SICDetil>applySchedulers())
                        , new Func2<String, SICDetil, SicDetilAndStr>() {
                            @Override
                            public SicDetilAndStr call(String s, SICDetil sicDetil) {
                                return new SicDetilAndStr(s, sicDetil);
                            }
                        });
        com.subscribe(new Subscriber<SicDetilAndStr>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SicDetilAndStr sicDetilAndStr) {
                LogUtil.d("s : " + sicDetilAndStr.getStr());
                viewdata = SomeUtil.JsonStrToListMap(sicDetilAndStr.getStr());
                for (int i = 0; i < viewdata.size(); i++) {
                    View view = LayoutInflater.from(SICLogDetilActivity.this).inflate(R.layout.item_input_sic, null);
                    TextView tvname = (TextView) view.findViewById(R.id.sic_input_title);
                    TextView tvid = (TextView) view.findViewById(R.id.sic_input_titleid);
                    EditText ev = (EditText) view.findViewById(R.id.sic_input_content);
                    tvname.setText(viewdata.get(i).get("zh_name").toString());
                    tvid.setText(viewdata.get(i).get("en_name").toString());

                    try {
                        ev.setText(viewdata.get(i).get("val").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        ev.setText("");
                    }

                    logTesllv.addView(view);
                    viewlist.add(view);
                }
                LogUtil.d("sicDetil: " + sicDetilAndStr.getDetil().toString());
                detildata = sicDetilAndStr.getDetil();
                paths = detildata.getPicurl();
                adapter.addAll(paths);


            }
        });


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

    private void UploadInfo() {

        Map<String, Object> paraValue = new HashMap<>();


        for (int i = 0; i < viewlist.size(); i++) {
            try {

                View itemview = viewlist.get(i);
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

        String txt = SomeUtil.mapTojsonStr(paraValue);
        LogUtil.d("sicinput json : " + SomeUtil.mapTojsonStr(paraValue));
        Map<String,Object> test = new HashMap<>();
        test.put("doc",txt);

        String newtxt = "{\"doc\": "+txt+"}";

        LogUtil.d("sicinput json : " + newtxt);

        app.apiService.updateSocialInfo(docid, type, newtxt, picids, videoids, detildata.getPicdocid(), detildata.getVideodocid())
                .compose(RxUtil.<String>applySchedulers())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        sicuploadPrg.setVisibility(View.GONE);
                        sicuploadBg.setVisibility(View.GONE);
                        sicuploadProtext.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        sicuploadPrg.setVisibility(View.GONE);
                        sicuploadBg.setVisibility(View.GONE);
                        sicuploadProtext.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(String s) {

                        LogUtil.d("sicinputresult : " + s);
                        SomeUtil.showSnackBar(rootview, "修改成功！").setCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                RxBus.getDefault().post("flushsiclog");
                                finish();
                            }
                        });

                    }
                });

    }

    @OnClick(R.id.siclog)
    public void onClick() {
        //  添加图片视频
        String[] titles = new String[]{"添加图片", "添加视频"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SICLogDetilActivity.this);
        builder.setTitle("请选择要添加的附件")
                .setItems(titles, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(SICLogDetilActivity.this, PickPhotoActivity.class);
                                intent.putExtra(PhotoPickerFragment.EXTRA_SELECT_COUNT, 6);
                                intent.putExtra(PhotoPickerFragment.EXTRA_DEFAULT_SELECTED_LIST, "");
                                intent.putExtra(HTTP_URL, "");
                                startActivityForResult(intent, 2);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            LogUtil.d("resultCode :" + resultCode);
            if (resultCode == 2) {
                if (data.getStringExtra("result").equals("addpic")) {

                    //  设置高度
                    if (NewPicpaths.size()+paths.size()>3){

                        ViewGroup.LayoutParams lp;
                        lp = sicloginputPics.getLayoutParams();
                        lp.height = 260;
                        sicloginputPics.setLayoutParams(lp);
                    }



                    NewPicpaths.clear();
                    NewPicpaths = data.getStringArrayListExtra("paths");
                    LogUtil.d("NewPicpaths :" + NewPicpaths.toString());
                    adapter.addAll(NewPicpaths);
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

    private void commit() {
        SomeUtil.showSnackBar(rootview, "正在上传请稍等！");
        Observable.just("go").compose(RxUtil.<String>applySchedulers())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        sicuploadPrg.setVisibility(View.VISIBLE);
                        sicuploadBg.setVisibility(View.VISIBLE);
                        sicuploadProtext.setVisibility(View.VISIBLE);
                    }
                });
        if (NewPicpaths.size() > 0 && videopaths.size() <= 0) {
            DatrixUtil datrixUtil = new DatrixUtil(NewPicpaths, rootview);
            datrixUtil.DatrixUpLoadPic2();
            datrixUtil.setOnAfterFinish(new DatrixUtil.AfterFinish() {
                @Override
                public void afterfinish(String fileid, List<String> ids) {
                    UploadPic(ids);
                    UploadInfo();
                }
            });
        }
        if (NewPicpaths.size() > 0 && videopaths.size() > 0) {
            DatrixUtil datrixUtil = new DatrixUtil(NewPicpaths, rootview);
            datrixUtil.DatrixUpLoadPic2();
            datrixUtil.setOnAfterFinish(new DatrixUtil.AfterFinish() {
                @Override
                public void afterfinish(String fileid, List<String> ids) {
                    UploadPic(ids);
                    UpLoadVideo();

                }
            });


        }
        if (NewPicpaths.size() <= 0 && videopaths.size() > 0) {
            UpLoadVideo();
        }
        if (NewPicpaths.size() <= 0 && videopaths.size() <= 0) {
            UploadInfo();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sic_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.id_sic_input_menu:

                AlertDialog.Builder builder = new AlertDialog.Builder(SICLogDetilActivity.this);
                builder.setTitle("确认提交修改？")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                commit();
                            }
                        }).setNegativeButton("取消", null).show();

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public class SicDetilAndStr {
        private String str;
        private SICDetil detil;

        public SicDetilAndStr(String str, SICDetil detil) {
            this.str = str;
            this.detil = detil;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public SICDetil getDetil() {
            return detil;
        }

        public void setDetil(SICDetil detil) {
            this.detil = detil;
        }
    }
}
