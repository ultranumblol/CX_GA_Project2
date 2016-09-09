package wgz.com.cx_ga_project.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.TimelineAdapter;
import wgz.com.cx_ga_project.base.BaseActivity;

public class MyWorkingTrackActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tracktimeline_rv)
    EasyRecyclerView recyclerView;
    @Bind(R.id.content_my_working_track)
    ConstraintLayout rootview;
    private TimelineAdapter adapter;
    private ArrayList<String> list = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_working_track;
    }
    @Override
    public void initView() {
        toolbar.setTitle("我的工作轨迹");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new TimelineAdapter(this));
        initData();
        adapter.addAll(list);
    }
    private void initData() {
        list.add("东城派出所\n将该情况通告各派出所值班室、警务室");
        list.add("西城派出所\n将该情况通告各派出所值班室、警务室");
        list.add("开发区派出所\n将该情况通告各派出所值班室、警务室");
        list.add("东城派出所\n将该情况通告各派出所值班室、警务室");
        list.add("西城派出所\n将该情况通告各派出所值班室、警务室");
        list.add("开发区派出所\n将该情况通告各派出所值班室、警务室");
    }
}
