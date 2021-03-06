package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding.view.RxView;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import me.iwf.photopicker.PhotoPicker;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.AddPictureAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.util.DatrixUtil;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;
import static wgz.com.cx_ga_project.util.SomeUtil.getUserId;


/**
 * 添加工作日志
 * Created by wgz on 2016/8/3.
 */
public class AddWorkLogActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab_addworklog)
    FloatingActionButton fabAddworklog;
    @Bind(R.id.id_addworklog_text)
    EditText worklogText;
    @Bind(R.id.addworklogRV)
    EasyRecyclerView addworklogRV;
    @Bind(R.id.content_add_worklog)
    LinearLayout rootview;
    List<String> paths = new ArrayList<>();
    @Bind(R.id.upload_progerss)
    ProgressBar uploadProgerss;
    private String time = "";
    private String worklog = "";
    private String id = "";
    private String edittext = "";
    private AddPictureAdapter adapter;
    private String fileid = "";
   // private String datrixUrl = DATRIX_BASE_URL + "preview/getImage?fileid=";
    private String datrixurl2 = "&token=X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=";

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_work_log;
    }

    @Override
    public void initView() {
        toolbar.setTitle("添加工作记录");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        worklog = intent.getStringExtra("worklog");
        id = intent.getStringExtra("id");
        addworklogRV.setLayoutManager(new GridLayoutManager(this, 4));
        addworklogRV.setAdapter(adapter = new AddPictureAdapter(this));
        adapter.addAll(initdata());
        adapter.setOnItemClickListener((position, itemView) -> {
            if (position + 1 == adapter.getCount()) {
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(true)
                        .start(this, 111);
            }

        });


        if (!worklog.contains("没有工作记录")) {
            worklogText.setText(worklog);
            RxView.clicks(fabAddworklog).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(aVoid -> {
                        edittext = worklogText.getText().toString();
                        LogUtil.d("修改工作日志");
                        LogUtil.d("edittext： "+edittext);
                        if (TextUtils.isEmpty(edittext)){

                            SomeUtil.showSnackBar(rootview,"请填写工作内容！");

                        }else {

                            ChangeWorkLog();
                        }


                    });


        } else if (worklog.contains("没有工作记录")){

            RxView.clicks(fabAddworklog).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(aVoid -> {
                        edittext = worklogText.getText().toString();
                        LogUtil.d("上传工作日志");
                        LogUtil.d("edittext： "+edittext);
                        if (TextUtils.isEmpty(edittext)){
                            SomeUtil.showSnackBar(rootview,"请填写工作内容！");
                        }else {

                            UpLoadWorkLog();
                        }

                    });

        }


    }

    private List<String> initdata() {

        paths.add("end");
       /* List<String> list = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            list.add("" + i);
        }*/
        return paths;
    }


    private void ChangeWorkLog() {

        if (paths.size() > 1) {
            DatrixUtil datrixUtil = new DatrixUtil(paths, rootview);
            datrixUtil.DatrixUpLoadPic();
            datrixUtil.setOnAfterFinish((fileid1, ids) -> addsummaryPic(fileid1, ids));
        } else


            changeOnesSummary();


    }

    private void changeOnesSummary() {
        app.apiService.changeWorkLog("changeOnceSummary", id, worklogText.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        SomeUtil.showSnackBar(rootview, "提交修改成功！").setCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                setResult(1, new Intent(AddWorkLogActivity.this, WorkLogActivity.class)
                                        .putExtra("text", edittext)
                                        .putExtra("result", "refresh"));
                                finish();
                            }
                        });

                    }

                    @Override
                    public void onError(Throwable e) {
                       // SomeUtil.checkHttpException(AddWorkLogActivity.this,e,rootview);
                        LogUtil.d("error :"+e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("Xiugairesult:" + s);
                        if (s.contains("200")) {
                            onCompleted();
                        } else onError(new Exception(s));
                    }
                });
    }

    private void UpLoadWorkLog() {
        uploadProgerss.setVisibility(View.VISIBLE);
        SomeUtil.showSnackBar(rootview,"正在上传请稍后！");
        LogUtil.d("pathsize : " + paths.size());
        if (paths.size() > 1) {
            DatrixUtil datrixUtil = new DatrixUtil(paths, rootview);
            datrixUtil.DatrixUpLoadPic();
            datrixUtil.setOnAfterFinish((fileid1, ids) -> addsummaryPic(fileid1, ids));
        } else {
            addsummary();
        }
    }

    // 2016/10/13 循环取ids值上传
    private void addsummaryPic(final String fileid, final List<String> ids) {
        LogUtil.d(" addwork log ids :" + ids.toString());
        final int k = ids.size() - 1;
        app.apiService.upWorkLog("addSummary", getUserId(), worklogText.getText().toString(), "", time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        for (int i = 0; i < ids.size(); i++) {
                            final int j = i;

                            app.apiService.upWorkLog("addSummary", getUserId(), "",  ids.get(i) , time)
                                    .compose(RxUtil.<String>applySchedulers())
                                    .subscribe(new Subscriber<String>() {
                                        @Override
                                        public void onCompleted() {
                                            if (j == k) {
                                                uploadProgerss.setVisibility(View.GONE);
                                                SomeUtil.showSnackBar(rootview, "添加成功！").setCallback(new Snackbar.Callback() {
                                                    @Override
                                                    public void onDismissed(Snackbar snackbar, int event) {
                                                        setResult(1, new Intent(AddWorkLogActivity.this, WorkLogActivity.class)
                                                                .putExtra("text", edittext)
                                                                .putExtra("result", "refresh"));
                                                        finish();
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            uploadProgerss.setVisibility(View.GONE);
                                            LogUtil.d("error :"+e.toString());
                                           // SomeUtil.checkHttpException(AddWorkLogActivity.this,e,rootview);
                                        }

                                        @Override
                                        public void onNext(String s) {
                                            LogUtil.d("result:" + s);
                                            if (s.contains("200")) {

                                            } else onError(new Exception(s));
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("error:" + e.toString());
                        SomeUtil.checkHttpException(AddWorkLogActivity.this,e,rootview);
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("result:" + s);
                        if (s.contains("200")) {
                            // onCompleted();
                            LogUtil.d("执行上传图片！！！！！！！");
                        } else onError(new Exception(s));
                    }
                });
    }

    private void addsummary() {
        app.apiService.upWorkLog("addSummary", getUserId(), worklogText.getText().toString(), "", time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        uploadProgerss.setVisibility(View.GONE);
                        SomeUtil.showSnackBar(rootview, "添加成功！").setCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                setResult(1, new Intent(AddWorkLogActivity.this, WorkLogActivity.class)
                                        .putExtra("text", edittext)
                                        .putExtra("result", "refresh"));
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("error:" + e.toString());
                       // SomeUtil.checkHttpException(AddWorkLogActivity.this,e,rootview);
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("result:" + s);
                        if (s.contains("200")) {
                            onCompleted();
                        } else onError(new Exception(s));
                    }
                });
    }

    @Override
    public void finish() {

        super.finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (resultCode == RESULT_OK && requestCode == 111){
                if (data != null) {
                    ArrayList<String> photos =
                            data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    adapter.clear();
                    paths.clear();
                    paths = photos;
                    initdata();
                    adapter.addAll(paths);
                }
            }

        } catch (Exception e) {
            LogUtil.d("error :" + e);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (worklogText.getText().toString().isEmpty()) {
                    finish();

                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(AddWorkLogActivity.this);
                    dialog.setTitle("请确认").setMessage("还没有提交记录，确认退出?")
                            .setPositiveButton("确认", (dialog1, which) -> finish()).setNegativeButton("取消", null).show();
                }


        }

        return true;

    }


}
