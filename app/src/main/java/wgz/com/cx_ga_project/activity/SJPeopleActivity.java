package wgz.com.cx_ga_project.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * 涉警人信息采集
 * Created by wgz on 2016/8/15.
 */
public class SJPeopleActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_sjpeople)
    LinearLayout rootview;
    @Bind(R.id.fab_SJPeople)
    FloatingActionButton fab;
    @Bind(R.id.addsjr_name)
    EditText addsjrName;
    @Bind(R.id.addsjr_sex)
    EditText addsjrSex;
    @Bind(R.id.addsjr_idnum)
    EditText addsjrIdnum;
    @Bind(R.id.addsjr_mobilephone)
    EditText addsjrMobilephone;
    @Bind(R.id.addsjr_telphone)
    EditText addsjrTelphone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sjpeople;
    }

    @Override
    public void initView() {
        toolbar.setTitle("涉警人信息采集");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RxView.clicks(fab).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        UpLoadSjr();
                    }
                });
    }

    private void UpLoadSjr() {



        app.jqAPIService.uploadSjPerson(SomeUtil.getJQId()
                , SomeUtil.getTASKId()
                , SomeUtil.getUserId()
                , SomeUtil.getSysTime()
                , addsjrName.getText().toString()
                , addsjrIdnum.getText().toString()
                , addsjrTelphone.getText().toString()
                , addsjrMobilephone.getText().toString()
                , addsjrSex.getText().toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("error:" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("result:" + s);
                        RxBus.getDefault().post("sjrflush");

                        SomeUtil.showSnackBar(rootview, "提交成功！").setCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                finish();
                            }
                        });

                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
