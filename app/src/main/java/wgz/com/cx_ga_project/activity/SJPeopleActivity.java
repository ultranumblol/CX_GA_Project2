package wgz.com.cx_ga_project.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
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
import wgz.datatom.com.utillibrary.util.LogUtil;

public class SJPeopleActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_sjpeople)
    LinearLayout rootview;
    @Bind(R.id.fab_SJPeople)
    FloatingActionButton fab;

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
        app.jqAPIService.uploadSjPerson("123","123","10001","2016-9-1","詹姆斯","4324234324324234",
                "3123213","1864343443","男").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("error:"+e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.e("result:"+s);

                    }
                });

    }

}
