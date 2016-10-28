package wgz.com.cx_ga_project.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.AddPictureAdapter;
import wgz.com.cx_ga_project.adapter.MyRecyclerArrayAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.fragment.PhotoPickerFragment;
import wgz.com.cx_ga_project.util.DatrixUtil;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static wgz.com.cx_ga_project.activity.PickPhotoActivity.HTTP_URL;
import static wgz.com.cx_ga_project.app.DATRIX_BASE_URL;
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
    private String time = "";
    private String worklog = "";
    private String id = "";
    private String edittext = "";
    private AddPictureAdapter adapter;
    private String fileid = "";
    private String datrixUrl = DATRIX_BASE_URL + "preview/getImage?fileid=";
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
        adapter.setOnItemClickListener(new MyRecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View itemView) {
                if (position + 1 == adapter.getCount()) {
                    Intent intent = new Intent(AddWorkLogActivity.this, PickPhotoActivity.class);
                    intent.putExtra(PhotoPickerFragment.EXTRA_SELECT_COUNT, 9);
                    intent.putExtra(PhotoPickerFragment.EXTRA_DEFAULT_SELECTED_LIST, "");
                    intent.putExtra(HTTP_URL, "");
                    startActivityForResult(intent, 1);
                }

            }
        });


        if (!worklog.contains("没有工作记录")) {
            worklogText.setText(worklog);
            RxView.clicks(fabAddworklog).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            edittext = worklogText.getText().toString();
                            ChangeWorkLog();
                        }
                    });


        } else {


            RxView.clicks(fabAddworklog).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            edittext = worklogText.getText().toString();
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
            datrixUtil.setOnAfterFinish(new DatrixUtil.AfterFinish() {
                @Override
                public void afterfinish(String fileid, List<String> ids) {

                }
            });
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
        LogUtil.d("pathsize : " + paths.size());
        if (paths.size() > 1) {
            DatrixUtil datrixUtil = new DatrixUtil(paths, rootview);
            datrixUtil.DatrixUpLoadPic();
            datrixUtil.setOnAfterFinish(new DatrixUtil.AfterFinish() {
                @Override
                public void afterfinish(String fileid, List<String> ids) {
                    addsummaryPic(fileid, ids);
                }
            });
        } else {
            addsummary();
        }
    }

    // TODO: 2016/10/13 循环取ids值上传
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

                            app.apiService.upWorkLog("addSummary", getUserId(), "", datrixUrl + ids.get(i) + datrixurl2, time)
                                    .compose(RxUtil.<String>applySchedulers())
                                    .subscribe(new Subscriber<String>() {
                                        @Override
                                        public void onCompleted() {
                                            if (j == k) {
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

                                        }

                                        @Override
                                        public void onNext(String s) {
                                            LogUtil.d("result:" + s);
                                            if (s.contains("200")) {
                                                // onCompleted();

                                            } else onError(new Exception(s));
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("error:" + e.toString());
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
            if (data.getStringExtra("result").equals("addpic")) {
                adapter.clear();
                paths.clear();
                paths = data.getStringArrayListExtra("paths");
                initdata();
                adapter.addAll(paths);

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
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).setNegativeButton("取消", null).show();
                }


        }

        return true;

    }

}
