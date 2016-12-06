package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzp.floatingactionbuttonplus.FloatingActionButtonPlus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.MyFragmentPagerAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.bean.ChatUpProgress;
import wgz.com.cx_ga_project.fragment.SjCarFragment;
import wgz.com.cx_ga_project.fragment.SjMsgFragment;
import wgz.com.cx_ga_project.fragment.SjPhoneFragment;
import wgz.com.cx_ga_project.fragment.SjrFragment;
import wgz.com.cx_ga_project.util.DatrixUtil;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.com.cx_ga_project.util.UriUtils;
import wgz.datatom.com.utillibrary.util.LogUtil;


/**
 * 警情回告
 */
public class JQCallbackActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tab_jqcallback)
    TabLayout tabJqcallback;
    @Bind(R.id.jqcallback_vp)
    ViewPager jqcallbackVp;
    @Bind(R.id.upload_bg)
    CardView uploadBg;
    @Bind(R.id.upload_prg)
    ProgressBar uploadPrg;
    @Bind(R.id.rootview)
    LinearLayout rootview;
    List<String> videopaths = new ArrayList<>();
    @Bind(R.id.upload_protext)
    TextView uploadProtext;
    @Bind(R.id.progress_rl)
    RelativeLayout progressRl;
    private ArrayList<Fragment> fragments;
    private List<String> titles;
    private SjCarFragment sjCarFragment;
    private SjMsgFragment sjMsgFragment;
    private SjPhoneFragment sjPhoneFragment;
    private SjrFragment sjrFragment;
    private Subscription rxSubscription;


    @Override
    public int getLayoutId() {
        return R.layout.activity_jqcallback;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
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
       /* FabPlus.setOnItemClickListener((tagView, position) -> {
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
                case R.id.fabtag_addjqpic:
                    startActivity(new Intent(JQCallbackActivity.this, AddJQPicActivity.class));
                    break;
                case R.id.fabtag_addjqvideo:
                    Intent intent2 = new Intent();
                    intent2.setType("video*//*");
                    intent2.setAction(Intent.ACTION_GET_CONTENT);
                    intent2.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(intent2, 456);
                    break;
            }
        });*/

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

        rxSubscription = RxBus.getDefault().toObservable(ChatUpProgress.class)
                .subscribe(new Subscriber<ChatUpProgress>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("error :" + e.toString());
                    }

                    @Override
                    public void onNext(final ChatUpProgress chatUpProgress) {
                        if (progressRl.getVisibility() == View.VISIBLE)
                        runOnUiThread(() -> uploadProtext.setText(chatUpProgress.getPro()));

                    }
                });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            if (resultCode == RESULT_OK && requestCode == 456) {
                Uri uri = data.getData();
                videopaths.add(UriUtils.getPath(getApplicationContext(), uri));
                uploadBg.setVisibility(View.VISIBLE);
                uploadPrg.setVisibility(View.VISIBLE);
                uploadProtext.setVisibility(View.VISIBLE);
                progressRl.setVisibility(View.VISIBLE);
                SomeUtil.showSnackBarLong(rootview, "正在上传视频，请稍后！");
                DatrixUtil datrixUtil = new DatrixUtil(videopaths.get(0), rootview);
                datrixUtil.DatrixUpLoadVideo();
                datrixUtil.setOnAfterFinish((fileid, ids) -> {
                    app.jqAPIService.uploadJqMsg(SomeUtil.getJQId(), SomeUtil.getTASKId(), SomeUtil.getUserId(),
                            "", SomeUtil.getSysTime(), "", fileid, fileid, SomeUtil.getDepartId())
                            .compose(RxUtil.<String>applySchedulers())
                            .subscribe(new Subscriber<String>() {
                                @Override
                                public void onCompleted() {
                                    uploadBg.setVisibility(View.GONE);
                                    uploadPrg.setVisibility(View.GONE);
                                    uploadProtext.setVisibility(View.GONE);
                                     progressRl.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    uploadBg.setVisibility(View.GONE);
                                    uploadPrg.setVisibility(View.GONE);
                                    uploadProtext.setVisibility(View.GONE);
                                    progressRl.setVisibility(View.GONE);
                                    LogUtil.d("uploadvideo error:" + e.toString());
                                    SomeUtil.checkHttpException(JQCallbackActivity.this, e, rootview);
                                }

                                @Override
                                public void onNext(String s) {
                                    LogUtil.d("result : " + s);
                                    if (s.contains("200")) {
                                        SomeUtil.showSnackBarLong(rootview, "视频上传成功！");
                                    }
                                }
                            });


                });


            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("error : " + e);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 此时android.R.id.home即为返回箭头
        if (item.getItemId() == android.R.id.home) {
            if (progressRl.getVisibility() == View.VISIBLE) {
                SomeUtil.showSnackBarLong(rootview, "正在上传视频，请稍后！");

            } else {
                onBackPressed();
                return true;
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
