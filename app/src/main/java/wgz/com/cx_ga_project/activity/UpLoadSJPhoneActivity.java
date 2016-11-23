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
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class UpLoadSJPhoneActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_sjphone)
    LinearLayout rootview;
    @Bind(R.id.fab_addSJPhone)
    FloatingActionButton fab;
    @Bind(R.id.addphone_mibilephone)
    EditText addphoneMibilephone;
    @Bind(R.id.addphone_name)
    EditText addphoneName;
    @Bind(R.id.addphone_idnum)
    EditText addphoneIdnum;
    @Bind(R.id.addphone_telphone)
    EditText addphoneTelphone;
    @Bind(R.id.addphone_mac)
    EditText addphoneMac;
    @Bind(R.id.serialnumber)
    EditText addserialnumber;
    @Bind(R.id.addphone_simi)
    EditText addphoneSimi;

    @Override
    public int getLayoutId() {
        return R.layout.activity_up_load_sjphone;
    }

    @Override
    public void initView() {
        toolbar.setTitle("涉警电话信息采集");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RxView.clicks(fab).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        UpLoadPhone();
                    }
                });

    }

    private void UpLoadPhone() {
        app.jqAPIService.uploadSjPhone(SomeUtil.getJQId(), SomeUtil.getTASKId()
                , SomeUtil.getUserId(), SomeUtil.getSysTime(), addphoneName.getText().toString()
                , addphoneIdnum.getText().toString(), addphoneMibilephone.getText().toString()
                , addphoneTelphone.getText().toString()
                ,addphoneMac.getText().toString()
                ,addserialnumber.getText().toString()
                ,addphoneSimi.getText().toString()
        )
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
                        LogUtil.d("result:" + s);
                        RxBus.getDefault().post("sjphoneflush");

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
