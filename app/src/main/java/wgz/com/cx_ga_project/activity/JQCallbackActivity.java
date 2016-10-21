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
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.MyFragmentPagerAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.entity.Apply;
import wgz.com.cx_ga_project.entity.JqCallBack;
import wgz.com.cx_ga_project.fragment.SjCarFragment;
import wgz.com.cx_ga_project.fragment.SjMsgFragment;
import wgz.com.cx_ga_project.fragment.SjPhoneFragment;
import wgz.com.cx_ga_project.fragment.SjrFragment;


/**
 * 警情回告
 */
public class JQCallbackActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.FabPlus)
    FloatingActionButtonPlus FabPlus;
    @Bind(R.id.tab_jqcallback)
    TabLayout tabJqcallback;
    @Bind(R.id.jqcallback_vp)
    ViewPager jqcallbackVp;
    private ArrayList<Fragment> fragments;
    private List<String> titles;
    private SjCarFragment sjCarFragment;
    private SjMsgFragment sjMsgFragment;
    private SjPhoneFragment sjPhoneFragment;
    private SjrFragment sjrFragment;


    @Override
    public int getLayoutId() {
        return R.layout.activity_jqcallback;
    }

    @Override
    public void initView() {
        toolbar.setTitle("警情回告");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add("涉警人员");
        titles.add("涉警车辆");
        titles.add("涉警电话");
        titles.add("警情回告");
        sjCarFragment = new SjCarFragment();
        sjMsgFragment = new SjMsgFragment();
        sjPhoneFragment = new SjPhoneFragment();
        sjrFragment = new SjrFragment();
        fragments.add(sjrFragment);
        fragments.add(sjCarFragment);
        fragments.add(sjPhoneFragment);
        fragments.add(sjMsgFragment);


        jqcallbackVp.setOffscreenPageLimit(4);
        jqcallbackVp.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        jqcallbackVp.setCurrentItem(0);
        tabJqcallback.setupWithViewPager(jqcallbackVp);
        FabPlus.setOnItemClickListener(new FloatingActionButtonPlus.OnItemClickListener() {
            @Override
            public void onItemClick(FabTagLayout tagView, int position) {
                int id = tagView.getId();
                switch (id) {
                    case R.id.fabtag_addsjcar:
                        startActivity(new Intent(JQCallbackActivity.this, UpLoadSJCarActivity.class));

                        break;
                    case R.id.fabtag_addsjPeople:
                        startActivity(new Intent(JQCallbackActivity.this, SJPeopleActivity.class));

                        break;
                    case R.id.fabtag_addsjPhone:
                        startActivity(new Intent(JQCallbackActivity.this, UpLoadSJPhoneActivity.class));
                        break;
                    case R.id.fabtag_addjqMsg:
                        startActivity(new Intent(JQCallbackActivity.this, AddJQActivity.class));
                        break;
                }
            }
        });

        tabJqcallback.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        jqcallbackVp.setCurrentItem(0);
                        break;
                    case 1:
                        jqcallbackVp.setCurrentItem(1);
                        break;
                    case 2:
                        jqcallbackVp.setCurrentItem(2);
                        break;
                    case 3:
                        jqcallbackVp.setCurrentItem(3);
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
