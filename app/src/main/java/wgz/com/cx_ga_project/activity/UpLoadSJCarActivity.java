package wgz.com.cx_ga_project.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
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
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class UpLoadSJCarActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_up_load_sjcar)
    LinearLayout rootview;
    @Bind(R.id.fab_addSJCar)
    FloatingActionButton fab;

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
        app.jqAPIService.uploadSjCar("123","123","10001","2016-9-1","云E18898","马加爵","422187198818181818",
                "马健绝","45688761818999817").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("error:"+e.toString());
                        SomeUtil.showSnackBar(rootview,"提交失败！");
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.e("result:"+s);
                        SomeUtil.showSnackBar(rootview,"提交成功！");
                    }
                });
    }


}
