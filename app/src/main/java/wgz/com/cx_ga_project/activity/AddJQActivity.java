package wgz.com.cx_ga_project.activity;

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
import android.widget.ProgressBar;

import com.jakewharton.rxbinding.view.RxView;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import me.iwf.photopicker.PhotoPicker;
import rx.Subscriber;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.AddPictureAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.util.DatrixUtil;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;
import static wgz.com.cx_ga_project.app.DATRIX_BASE_URL;

/**
 * 警情信息回告
 */
public class AddJQActivity extends BaseActivity {

    @Bind(R.id.content_add_jq)
    LinearLayout rootview;
    @Bind(R.id.uploadPic_fab)
    FloatingActionButton uploadPicFab;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_callbackJQ)
    EditText contenttext;
    @Bind(R.id.addPicRV)
    EasyRecyclerView addPicRV;
    List<String> paths = new ArrayList<>();
    @Bind(R.id.up_jq_progress)
    ProgressBar upJqProgress;
    private AddPictureAdapter adapter;
    private String fileid = "";
    private String datrixUrl = DATRIX_BASE_URL + "preview/getImage?fileid=";
    private String datrixurl2 = "&token=X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=";

    @Override
    public int getLayoutId() {
        return R.layout.activity_addjq;
    }

    @Override
    public void initView() {
        toolbar.setTitle("警情回告");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addPicRV.setLayoutManager(new GridLayoutManager(this, 4));
        addPicRV.setAdapter(adapter = new AddPictureAdapter(this));
        adapter.addAll(initdata());
        adapter.setOnItemClickListener((position, itemView) -> {
            if (position + 1 == adapter.getCount()) {
                //PickPhotoActivity.actionStart(AddJQActivity.this, 9, null, null);
                PhotoPicker.builder()
                        .setPhotoCount(3)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(true)
                        .start(this, 333);

            }
        });
        RxView.clicks(uploadPicFab).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {uploadjq();});

    }

    private void uploadjq() {
        SomeUtil.showSnackBar(rootview,"正在上传请稍后！");
        LogUtil.d("pathsize : " + paths.size());
        if (paths.size() > 1) {
            upJqProgress.setVisibility(View.VISIBLE);
            DatrixUtil datrixUtil = new DatrixUtil(paths, rootview);
            datrixUtil.DatrixUpLoadPic();
            datrixUtil.setOnAfterFinish((fileid1, ids) -> addjqAndpic(fileid1, ids));
        } else {
            addjq();
        }

    }

    private void addjq() {
        app.jqAPIService.uploadJqMsg(SomeUtil.getJQId(), SomeUtil.getTASKId(), SomeUtil.getUserId(),
                contenttext.getText().toString(), SomeUtil.getSysTime(), "", "", "", SomeUtil.getDepartId())
                .compose(RxUtil.<String>applySchedulers())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        upJqProgress.setVisibility(View.GONE);
                        SomeUtil.checkHttpException(AddJQActivity.this,e,rootview);

                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("addjq result : " + s);
                        if (s.contains("\"code\":200")) {
                            upJqProgress.setVisibility(View.GONE);
                            RxBus.getDefault().post("sjmsgflush");
                            SomeUtil.showSnackBar(rootview, "警情回传成功！").setCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    finish();
                                }
                            });
                        }
                    }
                });



    }

    private void addjqAndpic(String fileid, final List<String> ids) {

        final int k = ids.size() - 1;
        app.jqAPIService.uploadJqMsg(SomeUtil.getJQId(), SomeUtil.getTASKId(), SomeUtil.getUserId(),
                contenttext.getText().toString(), SomeUtil.getSysTime(), "", "", "", SomeUtil.getDepartId())
                .compose(RxUtil.<String>applySchedulers())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        for (int i = 0; i < ids.size(); i++) {
                            final int j = i;
                            app.jqAPIService.uploadJqMsg(SomeUtil.getJQId(), SomeUtil.getTASKId(), SomeUtil.getUserId(),
                                    "", SomeUtil.getSysTime(), datrixUrl + ids.get(i) + datrixurl2, "", "", "532301000000")
                                    .compose(RxUtil.<String>applySchedulers())
                                    .subscribe(new Subscriber<String>() {
                                        @Override
                                        public void onCompleted() {
                                            if (j == k) {
                                                upJqProgress.setVisibility(View.GONE);
                                                RxBus.getDefault().post("sjmsgflush");
                                                SomeUtil.showSnackBar(rootview, "警情回传成功！").setCallback(new Snackbar.Callback() {
                                                    @Override
                                                    public void onDismissed(Snackbar snackbar, int event) {

                                                        finish();
                                                    }
                                                });

                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            upJqProgress.setVisibility(View.GONE);
                                            SomeUtil.checkHttpException(AddJQActivity.this,e,rootview);
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
                        SomeUtil.checkHttpException(AddJQActivity.this,e,rootview);
                    }

                    @Override
                    public void onNext(String s) {
                        if (s.contains("200")) {

                        } else onError(new Exception(s));
                    }
                });


    }

    private List<String> initdata() {

        paths.add("end");
        return paths;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == RESULT_OK && requestCode == 333){
                if (data != null) {
                    ArrayList<String> photos =
                            data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    adapter.clear();
                    paths.clear();
                    paths =photos ;
                    initdata();
                    adapter.addAll(paths);

                }

            }

         /*
            if (data.getStringExtra("result").equals("addpic")) {
                adapter.clear();
                paths.clear();
                paths = data.getStringArrayListExtra("paths");
                initdata();
                adapter.addAll(paths);
            }*/

        } catch (Exception e) {
            LogUtil.d("error : " + e);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (contenttext.getText().toString().isEmpty()) {
                    finish();

                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(AddJQActivity.this);
                    dialog.setTitle("请确认").setMessage("还没有提交记录，确认退出?")
                            .setPositiveButton("确认", (dialog1, which) -> finish()).setNegativeButton("取消", null).show();
                }


        }

        return true;

    }

}
