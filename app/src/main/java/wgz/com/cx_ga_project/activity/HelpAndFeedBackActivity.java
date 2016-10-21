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
import rx.functions.Action1;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.util.SomeUtil;

public class HelpAndFeedBackActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_feedback_tv)
    EditText idFeedbackTv;
    @Bind(R.id.content_help_and_feed_back)
    LinearLayout rootview;
    @Bind(R.id.fab_feedback)
    FloatingActionButton fabFeedback;

    @Override
    public int getLayoutId() {
        return R.layout.activity_help_and_feed_back;
    }

    @Override
    public void initView() {
        toolbar.setTitle("帮助与反馈");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RxView.clicks(fabFeedback).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        UploadMSg();
                    }
                });

    }

    private void UploadMSg() {
        SomeUtil.showSnackBarLong(rootview,"反馈已提交!").setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                finish();
            }
        });

    }

}
