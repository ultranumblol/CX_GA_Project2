package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.lzp.floatingactionbuttonplus.FabTagLayout;
import com.lzp.floatingactionbuttonplus.FloatingActionButtonPlus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.TimelineAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.entity.JQDetil;
import wgz.com.cx_ga_project.entity.JqOrbit;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * 接处警作战
 * Created by wgz on 2016/8/15.
 */
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
    @Bind(R.id.detil_jq_bjtime)
    TextView detilJqBjtime;
    @Bind(R.id.detil_jq_address)
    TextView detilJqAddress;
    @Bind(R.id.detil_jq_bjrName)
    TextView detilJqBjrName;
    @Bind(R.id.detil_jq_bjrPhone)
    TextView detilJqBjrPhone;
    @Bind(R.id.detil_jq_nature)
    TextView detilJqNature;
    @Bind(R.id.detil_jq_type)
    TextView detilJqType;
    @Bind(R.id.fab_newfight)
    FloatingActionButton fabNewfight;
    @Bind(R.id.id_fight_rootview)
    CoordinatorLayout rootview;
    private TimelineAdapter adapter;
    private List<JqOrbit.Re> list = new ArrayList<>();
    private ArrayList<String> list2 = new ArrayList<>();
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    private String[] types = new String[]{"涉警人员信息", "涉警车辆信息", "警情信息"};

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

        RxView.clicks(fabNewfight).throttleFirst(500, TimeUnit.MICROSECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        StartFight();
                    }
                });

    }

    private void StartFight() {
        LogUtil.e("systime : "+SomeUtil.getSysTime());
        String time = SomeUtil.getSysTime();
        app.jqAPIService.StartNewFight("2016072100100000060", "1", time, "111", "1111")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("newfight result error: " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.e("newfight result : " + s);
                        if (s.contains("199")) {
                            SomeUtil.showSnackBarLong(rootview,"已经开始作战！");
                            fabNewfight.setImageResource(R.drawable.ic_stop_white_48dp);

                        }if (s.contains("200")){
                            SomeUtil.showSnackBar(rootview,"开始作战！");

                        }if (s.contains("300")){
                            SomeUtil.showSnackBar(rootview,"服务器错误！请稍后再试！");

                        }
                    }
                });

    }

    private void initData() {
        list2.add("东城派出所\n将该情况通告各派出所值班室、警务室");
        list2.add("西城派出所\n将该情况通告各派出所值班室、警务室");
        list2.add("开发区派出所\n将该情况通告各派出所值班室、警务室");
        list2.add("东城派出所\n将该情况通告各派出所值班室、警务室");
        list2.add("西城派出所\n将该情况通告各派出所值班室、警务室");
        list2.add("开发区派出所\n将该情况通告各派出所值班室、警务室");
        LogUtil.e("currentThread : "+Thread.currentThread().getName());
        adapter.addAll(list2);
        app.jqAPIService.getJqOrbit("2016072100100000060")
                .compose(RxUtil.<JqOrbit>applySchedulers())
                .subscribe(new Subscriber<JqOrbit>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.e("Rxjava currentThread : "+Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JqOrbit jqOrbit) {
                       // LogUtil.e("JqOrbit result : "+jqOrbit.getRes().toString());
                        list=jqOrbit.getRes();
                        LogUtil.e("JqOrbit result : "+list.size());
                    }
                });



        app.jqAPIService.GetJQDetil("2016072100100000060")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<JQDetil>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("jqDetil_error:" + e.toString());
                    }

                    @Override
                    public void onNext(JQDetil jqDetil) {
                        //LogUtil.e("jqDetil : " + jqDetil.getCode().toString());

                        if (jqDetil.getCode().equals(200)) {
                            //LogUtil.e("jqDetil :" + jqDetil.getResult().toString());
                            detilJqAddress.setText(jqDetil.getResult().get(0).getJqaddr());
                            detilJqBjrName.setText(jqDetil.getResult().get(0).getAlarmperson());
                            detilJqBjrPhone.setText(jqDetil.getResult().get(0).getCallingnumber());
                            detilJqNature.setText(jqDetil.getResult().get(0).getJqnature());
                            detilJqType.setText(jqDetil.getResult().get(0).getJqtype());
                            detilJqBjtime.setText(jqDetil.getResult().get(0).getCallpolicetime());
                        }

                    }
                });

    }

    @OnClick({R.id.id_fight_upload, R.id.id_fight_bulu, R.id.id_fight_talk})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_fight_upload:
                startActivity(new Intent(NewFightActivity.this, JQCallbackActivity.class));
                break;
            case R.id.id_fight_bulu:
                break;
            case R.id.id_fight_talk:
                startActivity(new Intent(NewFightActivity.this, ChatActivity.class));
                break;
        }
    }


}
