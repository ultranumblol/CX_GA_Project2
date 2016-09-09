package wgz.com.cx_ga_project.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.lzp.floatingactionbuttonplus.FabTagLayout;
import com.lzp.floatingactionbuttonplus.FloatingActionButtonPlus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.TimelineAdapter;
import wgz.com.cx_ga_project.base.BaseActivity;


public class NewFightActivity extends BaseActivity {
    @Bind(R.id.timeline_rv)
    EasyRecyclerView timelineRv;
    @Bind(R.id.id_fight_upload)
    CardView idFightUpload;
    @Bind(R.id.id_fight_bulu)
    CardView idFightBulu;
    @Bind(R.id.id_fight_talk)
    CardView idFightTalk;
    @Bind(R.id.FabPlus)
    FloatingActionButtonPlus FabPlus;
    private TimelineAdapter adapter;
    private ArrayList<String> list = new ArrayList<>();
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    private String[] types = new String[]{"涉警人员信息","涉警车辆信息","警情信息"};
    @Override
    public int getLayoutId() {
        return R.layout.activity_new_fight;
    }

    @Override
    public void initView() {
        FabPlus.setOnItemClickListener(new FloatingActionButtonPlus.OnItemClickListener() {
            @Override
            public void onItemClick(FabTagLayout tagView, int position) {
                int id = tagView.getId();
                switch (id) {
                    case R.id.fabtag_bjrJQ:
                        startActivity(new Intent(NewFightActivity.this, StartNewFightActivity.class).putExtra("title", "bjr"));
                        break;
                    case R.id.fabtag_nearvideoCam:
                        //startActivity(new Intent(NewFightActivity.this, NearlyVideoCamActivity.class));
                        startActivity(new Intent(NewFightActivity.this, CamPlayerActivity.class));
                        break;
                    case R.id.fabtag_nearjq:

                        break;

                }
            }
        });
        toolbar.setTitle("接处警作战");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        timelineRv.setLayoutManager(new LinearLayoutManager(this));
        timelineRv.setAdapter(adapter = new TimelineAdapter(this));
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

    @OnClick({R.id.id_fight_upload, R.id.id_fight_bulu, R.id.id_fight_talk})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_fight_upload:
                    startActivity(new Intent(NewFightActivity.this,JQCallbackActivity.class));
                break;
            case R.id.id_fight_bulu:
                break;
            case R.id.id_fight_talk:
                startActivity(new Intent(NewFightActivity.this,ChatActivity.class));
                break;
        }
    }
}
