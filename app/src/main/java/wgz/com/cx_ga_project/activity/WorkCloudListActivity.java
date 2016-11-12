package wgz.com.cx_ga_project.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.MyRecyclerArrayAdapter;
import wgz.com.cx_ga_project.adapter.SICInputLogAdapter;
import wgz.com.cx_ga_project.adapter.WorkCloudListAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.entity.WorkCloudList;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.com.cx_ga_project.view.Mylayout;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class WorkCloudListActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.workcloud_rv)
    EasyRecyclerView workcloudRv;
    @Bind(R.id.content_work_cloud_list)
    RelativeLayout rootview;
    private WorkCloudListAdapter adapter;
    private String key = "";
    private String policeid = "";
    private List<WorkCloudList.Re> list = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_work_cloud_list;
    }

    @Override
    public void initView() {

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        policeid = intent.getStringExtra("policeid");
        toolbar.setTitle("工作云标签");
        toolbar.setSubtitle(key);
        setToolbarBack(toolbar);
        workcloudRv.setLayoutManager(new Mylayout(this));
        workcloudRv.setAdapter(adapter = new WorkCloudListAdapter(this));



        initData();
        adapter.setOnItemClickListener(new MyRecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View itemView) {
                startActivity(new Intent(WorkCloudListActivity.this,OnesLogActivity.class)
                        .putExtra("log",list.get(position).getSummary()));
            }
        });


    }

    private void initData() {
        app.apiService.getWorkLogKeyWords(policeid,key)
                .compose(RxUtil.<WorkCloudList>applySchedulers())
                .subscribe(new Subscriber<WorkCloudList>() {
                    @Override
                    public void onCompleted() {
                    adapter.addAll(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        SomeUtil.checkHttpException(WorkCloudListActivity.this,e,rootview);
                    }

                    @Override
                    public void onNext(WorkCloudList s) {
                        list = s.getRes();
                        LogUtil.d("result :"+s.getRes().toString());
                    }
                });


    }


}
