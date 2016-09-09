package wgz.com.cx_ga_project.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.MyFragmentPagerAdapter;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.fragment.MyapprovalFragment;
import wgz.com.cx_ga_project.fragment.MyapprovalHistoryFragment;

public class MyApprovalActivity extends BaseActivity {
    private ArrayList<Fragment> fragments;
    private List<String> titles;
    private MyapprovalFragment myapprovalFragment;
    private MyapprovalHistoryFragment myapprovalHistoryFragment;
    @Bind(R.id.tab_myapproval)
    TabLayout tabMyapproval;
    @Bind(R.id.myapproval_vp)
    ViewPager viewpager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_approval;
    }

    @Override
    public void initView() {
        toolbar.setTitle("我的审批");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myapprovalFragment = new MyapprovalFragment();
        myapprovalHistoryFragment = new MyapprovalHistoryFragment();
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add("待审批");
        titles.add("已审批");
        fragments.add(myapprovalFragment);
        fragments.add(myapprovalHistoryFragment);
        viewpager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()
                ,fragments,titles));
        viewpager.setCurrentItem(0);
        tabMyapproval.setupWithViewPager(viewpager);
        tabMyapproval.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        viewpager.setCurrentItem(0);
                        break;
                    case 1:
                        viewpager.setCurrentItem(1);
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

    }

}
