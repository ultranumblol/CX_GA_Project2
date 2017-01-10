package wgz.com.cx_ga_project.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;

public class AboutAppActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_tv_aboutApp)
    TextView idTvAboutApp;
    @Bind(R.id.content_about_app)
    LinearLayout rootview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_app;
    }

    @Override
    public void initView() {
        toolbar.setTitle("关于智慧警务");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        idTvAboutApp.setText("警务APP将为每位干警提供Andriod下的APP应用，是维稳大数据平台的移动端扩展，与大数据平台紧密结合,和大数据平台的功能模块互联互通，也可以作为大数据平台的补充，将某些更适合在移动端使用的功能独立出来。");

    }

}
