package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.functions.Action1;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.SICInputAdapter;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.view.Mylayout;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class SICInputActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.sic_input_recyclerview)
    EasyRecyclerView recyclerview;
    @Bind(R.id.content_sicinput2)
    RelativeLayout rootview;
    @Bind(R.id.upload_sic)
    FloatingActionButton uploadSic;
    private SICInputAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sicinput2;
    }

    @Override
    public void initView() {
        toolbar.setTitle("社会信息采集");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String typename = intent.getStringExtra("typename");
        String typeid = intent.getStringExtra("typeid");
        toolbar.setSubtitle(typename);
        LogUtil.d("typename: " + typename);
        LogUtil.d("typeid: " + typeid);
        recyclerview.setLayoutManager(new Mylayout(this));
        recyclerview.setAdapter(adapter = new SICInputAdapter(this));
        initData();
        RxView.clicks(uploadSic).throttleFirst(500, TimeUnit.MICROSECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        UploadInfo();
                    }
                });

    }

    private void UploadInfo() {


    }

    private void initData() {
        List<String> list = new ArrayList<>();
        list.add("仓库名称");
        list.add("地址");
        list.add("储存物品名称");
        list.add("所属单位");
        list.add("领导电话");;
        list.add("值班电话");
        adapter.addAll(list);


    }


}
