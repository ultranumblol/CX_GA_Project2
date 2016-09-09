package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.MyRecyclerArrayAdapter;
import wgz.com.cx_ga_project.adapter.SubordinateAdapter;
import wgz.com.cx_ga_project.base.BaseActivity;

public class MySubordinateActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_recycView)
    EasyRecyclerView recyclerView;
    @Bind(R.id.content_my_subordinate)
    ConstraintLayout contentMySubordinate;
    private SubordinateAdapter  adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_subordinate;
    }

    @Override
    public void initView() {
        toolbar.setTitle("我的下属");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubordinateAdapter(this);
        recyclerView.setAdapter(adapter);
        //adapter.setNoMore(R.layout.view_nomore);
        adapter.setOnItemClickListener(new MyRecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View itemView) {
                startActivity(new Intent(MySubordinateActivity.this,MySubordinateLogAcitvity.class));

            }
        });

        adapter.addAll(initData());
    }
    private ArrayList<String> initData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add("i");
        }
        return list;
    }

}
