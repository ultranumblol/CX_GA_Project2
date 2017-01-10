package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;

public class OnesLogActivity extends BaseActivity {


    @Bind(R.id.id_tv_oneslog)
    TextView idTvOneslog;
    @Bind(R.id.activity_ones_log)
    RelativeLayout rootview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ones_log;
    }

    @Override
    public void initView() {
        setTitle("工作日志");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String log = intent.getStringExtra("log");
        idTvOneslog.setText(log);

    }


}
