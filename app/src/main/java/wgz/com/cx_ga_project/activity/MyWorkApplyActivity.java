package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.lzp.floatingactionbuttonplus.FabTagLayout;
import com.lzp.floatingactionbuttonplus.FloatingActionButtonPlus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.MyFragmentPagerAdapter;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.fragment.MyApplyJiabanFragment;
import wgz.com.cx_ga_project.fragment.MyApplyQingjiaFragment;

/**
 * 我的申请
 * Created by wgz on 2016/8/4.
 */

public class MyWorkApplyActivity extends BaseActivity {

    @Bind(R.id.FabPlus)
    FloatingActionButtonPlus FabPlus;
    private ArrayList<Fragment> fragments;
    private List<String> titles;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tab_myapply)
    TabLayout tabMyapply;
    @Bind(R.id.myapple_vp)
    ViewPager myappleVp;

    private MyApplyJiabanFragment jiabanFragment;
    private MyApplyQingjiaFragment qingjiaFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_work_apply;
    }

    @Override
    public void initView() {
        toolbar.setTitle("我的申请");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        jiabanFragment = new MyApplyJiabanFragment();
        qingjiaFragment = new MyApplyQingjiaFragment();
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add("加班申请");
        titles.add("请假申请");
        fragments.add(jiabanFragment);
        fragments.add(qingjiaFragment);
        myappleVp.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        myappleVp.setCurrentItem(0);
        tabMyapply.setupWithViewPager(myappleVp);
        FabPlus.setOnItemClickListener(new FloatingActionButtonPlus.OnItemClickListener() {
            @Override
            public void onItemClick(FabTagLayout tagView, int position) {
                int id = tagView.getId();
                switch (id){
                    case R.id.fabtag_addJiaban:
                        startActivity(new Intent(MyWorkApplyActivity.this,AskForJiabanActivity.class));
                        break;
                    case R.id.fabtag_addQj:
                        startActivity(new Intent(MyWorkApplyActivity.this,AskForLeaveActivity.class));
                        break;
                }
            }
        });


        tabMyapply.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        myappleVp.setCurrentItem(0);
                        break;
                    case 1:
                        myappleVp.setCurrentItem(1);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        initData();
    }

    private void initData() {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
