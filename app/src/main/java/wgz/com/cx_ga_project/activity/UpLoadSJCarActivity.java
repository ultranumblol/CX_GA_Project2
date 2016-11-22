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

/**
 * 上传涉警车辆信息
 * Created by wgz on 2016/8/25.
 */
public class UpLoadSJCarActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_up_load_sjcar)
    LinearLayout rootview;
    @Bind(R.id.fab_addSJCar)
    FloatingActionButton fab;
    @Bind(R.id.addsjcar_carplate)
    EditText addsjcarCarplate;
    @Bind(R.id.addsjcar_owner)
    EditText addsjcarOwner;
    @Bind(R.id.addsjcar_owneridnum)
    EditText addsjcarOwneridnum;
    @Bind(R.id.addsjcar_driver)
    EditText addsjcarDriver;
    @Bind(R.id.addsjcar_driveridnum)
    EditText addsjcarDriveridnum;

    @Override
    public int getLayoutId() {
        return R.layout.activity_up_load_sjcar;
    }

    @Override
    public void initView() {
        toolbar.setTitle("涉警车辆信息采集");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RxView.clicks(fab).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        UpLoadcarMsg();
                    }
                });
    }

    private void UpLoadcarMsg() {
        app.jqAPIService.uploadSjCar(SomeUtil.getJQId(), SomeUtil.getTASKId()
                , SomeUtil.getUserId(), SomeUtil.getSysTime()
                , addsjcarCarplate.getText().toString()
                , addsjcarOwner.getText().toString()
                , addsjcarOwneridnum.getText().toString()
                , addsjcarDriver.getText().toString()
                , addsjcarDriveridnum.getText().toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("error:" + e.toString());
                        SomeUtil.showSnackBar(rootview, "提交失败！");
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("result:" + s);
                        RxBus.getDefault().post("sjcarflush");

                        SomeUtil.showSnackBar(rootview, "提交成功！").setCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                finish();
                            }
                        });
                    }
                });
    }



}
