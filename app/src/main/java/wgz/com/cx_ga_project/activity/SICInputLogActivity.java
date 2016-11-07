package wgz.com.cx_ga_project.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.SICInputLogAdapter;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.com.cx_ga_project.view.Mylayout;

public class SICInputLogActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.sicinputlog_rv)
    EasyRecyclerView sicinputlogRv;
    @Bind(R.id.content_sicinput_log)
    RelativeLayout rootview;
    private SICInputLogAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sicinput_log;
    }

    @Override
    public void initView() {
        toolbar.setTitle("采集日志");
        sicinputlogRv.setLayoutManager(new Mylayout(this));
        sicinputlogRv.setAdapter(adapter = new SICInputLogAdapter(this));
        initData();

    }

    private void initData() {



    }

}
