package wgz.com.cx_ga_project.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.lzp.floatingactionbuttonplus.FabTagLayout;
import com.lzp.floatingactionbuttonplus.FloatingActionButtonPlus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.TimelineAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.entity.JQDetil;
import wgz.com.cx_ga_project.entity.JqOrbit;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SPUtils;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.com.cx_ga_project.view.Mylayout;
import wgz.com.cx_ga_project.view.TimeLineMarker;
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
    @Bind(R.id.id_recyclerview)
    RecyclerView idRecyclerview;
    private TimelineAdapter adapter;
    private List<JqOrbit.Re> list = new ArrayList<>();
    private ArrayList<String> list2 = new ArrayList<>();
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    private HomeAdapter mAdapter;
    private String JQid = "";


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
                        startActivity(new Intent(NewFightActivity.this, JQListActivity.class).putExtra("title", "bjr"));
                        break;
                    case R.id.fabtag_nearvideoCam:
                        startActivity(new Intent(NewFightActivity.this, CamMapHtmlActivity.class));
                        break;
                    case R.id.fabtag_nearjq:
                        startActivity(new Intent(NewFightActivity.this, JQListActivity.class).putExtra("title", "nearjq"));
                        break;
                    case R.id.fabtag_sjrJQ:
                        startActivity(new Intent(NewFightActivity.this, JQListActivity.class).putExtra("title", "sjr"));
                        break;

                }
            }
        });
        toolbar.setTitle("接处警作战");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idRecyclerview.setLayoutManager(new Mylayout(this));
        //idRecyclerview.setAdapter(mAdapter = new HomeAdapter());


        timelineRv.setLayoutManager(new Mylayout(this));
        timelineRv.setAdapter(adapter = new TimelineAdapter(this));
        initData();


        RxView.clicks(fabNewfight).throttleFirst(500, TimeUnit.MICROSECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        ShowDialog();
                        //StartFight();

                    }
                });

    }

    private void ShowDialog() {
        final StringBuilder sb = new StringBuilder();
        final String[] names = new String[]{"213","31212","4324","111","213","31212","4324"};
        final boolean[] ifchacken = new boolean[]{false,false,false,false,false,false,false};
        final String[] ids = new String[]{"030021","030022","030023","030283","030025","030026","030027"};
        for (int i = 0 ; i <names.length ; i++){
            if (SomeUtil.getUserId().equals(ids[i])){
                ifchacken[i] = true;
            }

        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择出警人员:")
                .setMultiChoiceItems(names, ifchacken, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        ifchacken[which] = isChecked;
                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0 ; i <ifchacken.length; i ++){
                    if (ifchacken[i]){
                        sb.append(ids[i]+"&");
                    }
                }
                LogUtil.d("sb : "+sb.toString());
               // StartFight();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();


    }


    private void StartFight() {
        //adapter.addAll(list2);
        //LogUtil.d("systime : "+SomeUtil.getSysTime());
        String time = SomeUtil.getSysTime();
        String latitude = (String) SPUtils.get(app.getApp().getApplicationContext(), Constant.LATITUDE, "111");
        String longitude = (String) SPUtils.get(app.getApp().getApplicationContext(), Constant.LONGITUDE, "1111");
        LogUtil.d("fight latitude:" + latitude);
        LogUtil.d("fight longitude:" + longitude);
        app.jqAPIService.StartNewFight("2016072100100000060", "1", time, longitude, latitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("newfight result error: " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("newfight result : " + s);
                        if (s.contains("199")) {
                            SomeUtil.showSnackBarLong(rootview, "已经开始作战！");
                            fabNewfight.setImageResource(R.drawable.ic_stop_white_48dp);

                        }
                        if (s.contains("200")) {
                            SomeUtil.showSnackBar(rootview, "开始作战！");

                        }
                        if (s.contains("300")) {
                            SomeUtil.showSnackBar(rootview, "服务器错误！请稍后再试！");

                        }
                    }
                });

    }



    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    NewFightActivity.this).inflate(R.layout.item_timeline, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            //holder.tv.setText(mDatas.get(position));
            if (position == 0) {
                holder.timeLineMarker.setText("" + (position + 1));
                holder.timeLineMarker.setBeginLine(null);
            } else {
                holder.timeLineMarker.setText("" + (position + 1));
            }
            holder.time.setText(list.get(position).getSendtime());
            holder.desc_tv.setText(list.get(position).getTreatmentdep());


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView desc_tv, time;
            private TimeLineMarker timeLineMarker;

            public MyViewHolder(View view) {
                super(view);
                desc_tv = (TextView) view.findViewById(R.id.desc_tv);
                timeLineMarker = (TimeLineMarker) view.findViewById(R.id.timeLineMarker);
                time = (TextView) view.findViewById(R.id.id_timelineTime);
            }
        }
    }

    private void initData() {

        app.jqAPIService.getJqOrbit("2016072100100000060")
                .compose(RxUtil.<JqOrbit>applySchedulers())
                .subscribe(new Subscriber<JqOrbit>() {
                    @Override
                    public void onCompleted() {
                        idRecyclerview.setAdapter(mAdapter = new HomeAdapter());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JqOrbit jqOrbit) {
                        // LogUtil.d("JqOrbit result : "+jqOrbit.getRes().toString());
                        list = jqOrbit.getRes();
                        LogUtil.d("JqOrbit result : " + list.toString());
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
                        LogUtil.d("jqDetil_error:" + e.toString());
                    }

                    @Override
                    public void onNext(JQDetil jqDetil) {
                        //LogUtil.d("jqDetil : " + jqDetil.getCode().toString());

                        if (jqDetil.getCode().equals(200)) {
                            LogUtil.d("jqDetil :" + jqDetil.getResult().toString());
                            detilJqAddress.setText(jqDetil.getResult().get(0).getJqaddr());
                            detilJqBjrName.setText(jqDetil.getResult().get(0).getAlarmperson());
                            detilJqBjrPhone.setText(jqDetil.getResult().get(0).getCallingnumber());
                            detilJqNature.setText(jqDetil.getResult().get(0).getJqnature());
                            detilJqType.setText(jqDetil.getResult().get(0).getJqtype());
                            detilJqBjtime.setText(jqDetil.getResult().get(0).getCallpolicetime());

                            JQid = jqDetil.getResult().get(0).getJqid();

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
                startActivity(new Intent(NewFightActivity.this, ChatActivity.class).putExtra("jqid",JQid));
                break;
        }
    }


}
