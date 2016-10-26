package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bumptech.glide.util.ExceptionCatchingInputStream;
import com.jakewharton.rxbinding.view.RxView;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
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
import wgz.com.cx_ga_project.entity.DatrixCreat;
import wgz.com.cx_ga_project.entity.DatrixFinish;
import wgz.com.cx_ga_project.fragment.PhotoPickerFragment;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static wgz.com.cx_ga_project.activity.PickPhotoActivity.HTTP_URL;

/**
 * 警情信息回告
 */
public class AddJQActivity extends BaseActivity {

    @Bind(R.id.content_add_jq)
    LinearLayout rootview;
    @Bind(R.id.uploadPic_fab)
    FloatingActionButton uploadPicFab;
    private AddPictureAdapter adapter;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_callbackJQ)
    EditText contenttext;
    @Bind(R.id.addPicRV)
    EasyRecyclerView addPicRV;
    List<String> paths = new ArrayList<>();
    private String fileid = "";
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
        adapter.setOnItemClickListener(new MyRecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View itemView) {
                if (position + 1 == adapter.getCount()) {
                    //PickPhotoActivity.actionStart(AddJQActivity.this, 9, null, null);
                    Intent intent = new Intent(AddJQActivity.this, PickPhotoActivity.class);
                    intent.putExtra(PhotoPickerFragment.EXTRA_SELECT_COUNT, 9);
                    intent.putExtra(PhotoPickerFragment.EXTRA_DEFAULT_SELECTED_LIST, "");
                    intent.putExtra(HTTP_URL, "");
                    startActivityForResult(intent, 0);
                }
            }
        });
        RxView.clicks(uploadPicFab).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        // TODO: 2016/9/26 测试datrix 上传图片
                        // UpLoadPictures(paths);
                        DatrixCreate();
                    }
                });

    }
    private void uploadpics(List<File> files) {
        app.apiService.uploadFileWithRequestBody("saveAppPics", SomeUtil.filesToMultipartBody(files))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("upPic_error:" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("upPic:" + s);
                        if (s.contains("\"code\":200")) {
                            SomeUtil.showSnackBar(rootview, "提交成功！").setCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    finish();
                                }
                            });
                        } else {
                            SomeUtil.showSnackBar(rootview, "提交失败，请再试！");
                        }
                    }
                });

    }

    private void DatrixCreate() {
        app.apiService.uploadFileWithRequestBodyTest("testtestwgzwgz","0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DatrixCreat>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("Detrix_upPic_error:" + e.toString());
                    }

                    @Override
                    public void onNext(DatrixCreat datrixCreat) {
                        LogUtil.d("Detrix_upPic_create : code :" +datrixCreat.getCode().toString());
                        if (datrixCreat.getCode().equals(200)){
                            LogUtil.d("Detrix_upPic_create :"+datrixCreat.getResult().getFileid());
                            fileid = datrixCreat.getResult().getFileid();
                            LogUtil.d("Detrix_upPic_create :" +datrixCreat.getResult().toString());
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
        LogUtil.d("file size : " +size);
        app.apiService.detrixWrite(fileid,"0",size,bodyMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("detrix_write_Response_error :" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("detrix_write_Response :" + s);
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
                                SomeUtil.showSnackBar(rootview,"上传图片成功！");
                            }
                            else {
                                SomeUtil.showSnackBar(rootview,"网络错误，请稍后！");
                            }
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
            if (data.getStringExtra("result").equals("addpic")) {
                adapter.clear();
                paths.clear();
                paths = data.getStringArrayListExtra("paths");
                initdata();
                adapter.addAll(paths);
            }

        } catch (Exception e) {
            LogUtil.d("error : " + e);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
