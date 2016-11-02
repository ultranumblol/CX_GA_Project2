package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.util.SomeUtil;

public class SICActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.sic_data_input)
    CardView sicDataInput;
    @Bind(R.id.sic_pic_input)
    CardView sicPicInput;
    @Bind(R.id.sic_video_input)
    CardView sicVideoInput;
    @Bind(R.id.sic_rootview)
    LinearLayout rootview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sic;
    }

    @Override
    public void initView() {
        toolbar.setTitle("社会信息采集");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
    }

    private void initData() {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sic_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.id_sic_log:
                SomeUtil.showSnackBar(rootview, "开发ing...");
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.sic_data_input, R.id.sic_pic_input, R.id.sic_video_input})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sic_data_input:
                startActivity(new Intent(SICActivity.this, SICTypeActivity.class));


                break;
            case R.id.sic_pic_input:
                break;
            case R.id.sic_video_input:
                break;
        }
    }
}
