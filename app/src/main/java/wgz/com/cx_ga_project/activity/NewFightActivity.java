package wgz.com.cx_ga_project.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.calendarView.utils.OtherUtils;
import wgz.com.cx_ga_project.entity.AllDep;
import wgz.com.cx_ga_project.entity.JQDetil;
import wgz.com.cx_ga_project.entity.JQOnDutyPeople;
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
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.fabtag_zyJQ)
    FabTagLayout fabtagZyJQ;
    @Bind(R.id.fabtag_zyJQ_fab)
    FloatingActionButton fabtagZyJQFab;

    private List<JqOrbit.Re> list = new ArrayList<>();
    private ArrayList<String> list2 = new ArrayList<>();
    private HomeAdapter mAdapter;
    private String JQid = "";
    private List<JQOnDutyPeople.peoRe> mDutyPeodata = new ArrayList<>();
    private String taskid = "";
    private String jqstate = "";
    private int partNum = -1;
    private String departmentName = "";
    private String departmentID = "";
    private String stopid = "";
    private String stopstate = "3";
    private String baojingName = "";
    private String sendtime = "";
    private boolean ifFromJQlist = false;


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
                        startActivity(new Intent(NewFightActivity.this, JQListActivity.class).putExtra("title", "bjr").putExtra("bjrname", baojingName));
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
                    case R.id.fabtag_zyJQ:
                        if(!ifFromJQlist){
                            transferDialog();
                        }

                        break;

                }
            }
        });
        toolbar.setTitle("接处警作战");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idRecyclerview.setLayoutManager(new Mylayout(this));
        //idRecyclerview.setAdapter(mAdapter = new HomeAdapter());


        Intent intent = getIntent();
        taskid = intent.getStringExtra("taskid");
        jqstate = intent.getStringExtra("jqstate");
        JQid = intent.getStringExtra("jqid");
        stopid = intent.getStringExtra("jqstopid");
        sendtime = intent.getStringExtra("sendtime");
        ifFromJQlist = intent.getBooleanExtra("jqlist", false);
        LogUtil.d("taskid : " + taskid);
        LogUtil.d("jqstate : " + jqstate);
        LogUtil.d("jqid : " + JQid);
        LogUtil.d("sendtime : " + sendtime);
        LogUtil.d("ifFromJQlist : " + ifFromJQlist);
        if (jqstate == null) {
            jqstate = "-1";
        }
        if (ifFromJQlist) {
            fabNewfight.setVisibility(View.GONE);
            idFightBulu.setVisibility(View.GONE);
            idFightTalk.setVisibility(View.GONE);
            idFightUpload.setVisibility(View.GONE);



        }
        if (jqstate.equals("1")) {

            RxView.clicks(fabNewfight).throttleFirst(500, TimeUnit.MICROSECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            ShowDialog();
                        }
                    });
        }

        if (jqstate.equals("2")) {
            fabNewfight.setImageResource(R.drawable.ic_stop_white_48dp);
            RxView.clicks(fabNewfight).throttleFirst(500, TimeUnit.MICROSECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            stopjq();
                        }
                    });
            idFightBulu.setVisibility(View.GONE);
            idFightUpload.setVisibility(View.VISIBLE);

        }
        if (jqstate.equals("4")) {
            fabNewfight.setImageResource(R.drawable.ic_stop_white_48dp);
            RxView.clicks(fabNewfight).throttleFirst(500, TimeUnit.MICROSECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            stopjq2();
                        }
                    });
            idFightBulu.setVisibility(View.VISIBLE);
            idFightUpload.setVisibility(View.GONE);
            idFightTalk.setVisibility(View.GONE);
        }

        // LogUtil.d("jqid : "+JQid);

        initData();
    }

    private void stopjq2() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(NewFightActivity.this);
        builder.setTitle("是否结束警情补录?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        app.jqAPIService.stopTaskJq("3", stopid, taskid)
                                .compose(RxUtil.<String>applySchedulers())
                                .subscribe(new Subscriber<String>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        LogUtil.d("jqstop result error:" + e.toString());
                                    }

                                    @Override
                                    public void onNext(String s) {
                                        LogUtil.d("jqstop result :" + s);
                                        SomeUtil.showSnackBar(rootview, "出警任务已经结束！").setCallback(new Snackbar.Callback() {
                                            @Override
                                            public void onDismissed(Snackbar snackbar, int event) {
                                                RxBus.getDefault().post("newjqflush");
                                                finish();

                                            }
                                        });
                                    }
                                });
                    }
                })
                .setNegativeButton("取消", null).show();


    }

    private void stopjq() {
        final String[] choice = new String[]{"已完成警情回告并结束此次出警任务", "结束任务，后期补录警情内容"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(NewFightActivity.this);
        builder.setTitle("是否结束此次出警？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setTitle("请选择：")
                                .setSingleChoiceItems(choice, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                stopstate = "3";
                                                break;
                                            case 1:
                                                stopstate = "4";

                                        }

                                    }
                                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LogUtil.d("taskid:" + taskid);
                                app.jqAPIService.stopTaskJq(stopstate, stopid, taskid)
                                        .compose(RxUtil.<String>applySchedulers())
                                        .subscribe(new Subscriber<String>() {
                                            @Override
                                            public void onCompleted() {

                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                LogUtil.d("jqstop result error:" + e.toString());
                                            }

                                            @Override
                                            public void onNext(String s) {
                                                LogUtil.d("jqstop result :" + s);
                                                SomeUtil.showSnackBar(rootview, "出警任务已经结束！").setCallback(new Snackbar.Callback() {
                                                    @Override
                                                    public void onDismissed(Snackbar snackbar, int event) {
                                                        RxBus.getDefault().post("newjqflush");
                                                        finish();
                                                    }
                                                });
                                            }
                                        });
                            }
                        }).setNegativeButton("取消", null).show();

                        /**/
                    }
                }).setNegativeButton("取消", null)
                .show();


    }

    private void transferDialog() {
        final String[] parts = new String[]{"楚雄市公安局", "楚雄市森林公安局"};
        final String[] choice = new String[]{"已完成警情回告并结束此次出警任务", "结束任务，后期补录警情内容"};

        app.jqAPIService.getAllDep()
                .compose(RxUtil.<AllDep>applySchedulers())
                .subscribe(new Subscriber<AllDep>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AllDep result) {
                        LogUtil.d("alldep : " + result.getRes().toString());
                        final List<AllDep.AlldepRe> data1 = new ArrayList<>();
                        final List<AllDep.AlldepRe> dataSL = new ArrayList<>();
                        for (int i = 0; i < result.getRes().size(); i++) {
                            if (result.getRes().get(i).getDepartParentid().equals("532301000000")) {
                                data1.add(result.getRes().get(i));
                            }
                        }
                        for (int i = 0; i < result.getRes().size(); i++) {
                            if (result.getRes().get(i).getDepartParentid().equals("532301390000")) {
                                dataSL.add(result.getRes().get(i));
                            }
                        }
                        final String[] parts1 = new String[data1.size()];
                        final String[] partsSL = new String[dataSL.size()];
                        for (int i = 0; i < data1.size(); i++) {
                            parts1[i] = data1.get(i).getDepartSimplename();
                        }
                        for (int i = 0; i < dataSL.size(); i++) {
                            partsSL[i] = dataSL.get(i).getDepartSimplename();
                        }

                        final AlertDialog.Builder tbuilder = new AlertDialog.Builder(NewFightActivity.this);
                        tbuilder.setTitle("请确认")
                                .setMessage("是否结束当前出警任务并转移该任务？")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        tbuilder.setTitle("请选择：")
                                                .setMessage(null)
                                                .setSingleChoiceItems(choice, -1, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        switch (which) {
                                                            case 0:
                                                                stopstate = "3";
                                                                break;
                                                            case 1:
                                                                stopstate = "4";

                                                        }

                                                    }
                                                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //停止警情
                                                app.jqAPIService.stopTaskJq(stopstate, stopid, taskid)
                                                        .compose(RxUtil.<String>applySchedulers())
                                                        .subscribe(new Subscriber<String>() {
                                                            @Override
                                                            public void onCompleted() {

                                                            }

                                                            @Override
                                                            public void onError(Throwable e) {
                                                                LogUtil.d("jqstop result error:" + e.toString());
                                                            }

                                                            @Override
                                                            public void onNext(String s) {
                                                                tbuilder.setTitle("请选择要转移的单位")
                                                                        .setMessage(null)
                                                                        .setSingleChoiceItems(parts, -1, new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                partNum = which;
                                                                            }
                                                                        })
                                                                        .setNegativeButton("取消", null)
                                                                        .setPositiveButton("下一步", new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                LogUtil.d("which : " + which);
                                                                                switch (partNum) {
                                                                                    case 0:
                                                                                        tbuilder.setTitle(parts[partNum])
                                                                                                .setMessage(null)
                                                                                                .setSingleChoiceItems(parts1, -1, new DialogInterface.OnClickListener() {
                                                                                                    @Override
                                                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                                                        departmentName = parts1[which];
                                                                                                        for (int i = 0; i < data1.size(); i++) {
                                                                                                            if (departmentName.equals(data1.get(i).getDepartSimplename())) {
                                                                                                                departmentID = data1.get(i).getDepartmentid();
                                                                                                            }
                                                                                                        }

                                                                                                    }
                                                                                                }).setNegativeButton("取消", null)
                                                                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                                                                    @Override
                                                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                                                        LogUtil.d("departmentName : " + departmentName);
                                                                                                        LogUtil.d("departmentid : " + departmentID);
                                                                                                        Jqzhuanyi();
                                                                                                    }
                                                                                                }).show();

                                                                                        break;
                                                                                    case 1:
                                                                                        tbuilder.setTitle(parts[partNum])
                                                                                                .setMessage(null)
                                                                                                .setSingleChoiceItems(partsSL, -1, new DialogInterface.OnClickListener() {
                                                                                                    @Override
                                                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                                                        departmentName = partsSL[which];
                                                                                                        for (int i = 0; i < dataSL.size(); i++) {
                                                                                                            if (departmentName.equals(dataSL.get(i).getDepartSimplename())) {
                                                                                                                departmentID = dataSL.get(i).getDepartmentid();
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }).setNegativeButton("取消", null)
                                                                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                                                                    @Override
                                                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                                                        Jqzhuanyi();
                                                                                                    }
                                                                                                }).show();
                                                                                        break;

                                                                                }

                                                                            }
                                                                        }).show();

                                                            }
                                                        });

                                            }
                                        }).setNegativeButton("取消", null).show();

                                        /**/
                                    }
                                }).show();


                    }
                });


    }

    private void Jqzhuanyi() {
        app.jqAPIService.JqTransfer(JQid, taskid, SomeUtil.getSysTime(), departmentName, departmentID)
                .compose(RxUtil.<String>applySchedulers())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("JqTransfer : " + s);
                        RxBus.getDefault().post("newjqflush");
                        SomeUtil.showSnackBar(rootview, "警情转移成功！").setCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                finish();
                            }
                        });

                    }
                });


    }

    private void ShowDialog() {
        // TODO: 2016/11/1 部门id替换 日期替换sendtime
        app.jqAPIService.getPeoOnduty("532301000000", OtherUtils.formatDate(SomeUtil.getStrToDate(sendtime)))
                .compose(RxUtil.<JQOnDutyPeople>applySchedulers())
                .subscribe(new Subscriber<JQOnDutyPeople>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        SomeUtil.checkHttpException(getApplicationContext(), e, rootview);
                        LogUtil.d("JQOnDutyPeople error : " + e.toString());
                    }

                    @Override
                    public void onNext(JQOnDutyPeople result) {
                        LogUtil.d("JQOnDutyPeople code : " + result.getCode().toString());
                        LogUtil.d("JQOnDutyPeople code : " + result.getRes().toString());
                        mDutyPeodata = result.getRes();
                        String[] lname = new String[mDutyPeodata.size()];
                        final String[] lid = new String[mDutyPeodata.size()];
                        final boolean[] ifchaeck = new boolean[mDutyPeodata.size()];
                        for (int i = 0; i < mDutyPeodata.size(); i++) {
                            lname[i] = mDutyPeodata.get(i).getPolicename();
                            lid[i] = mDutyPeodata.get(i).getPoliceid();
                        }

                        for (int i = 0; i < lname.length; i++) {
                            if (SomeUtil.getUserId().equals(lid[i])) {
                                ifchaeck[i] = true;
                            }

                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(NewFightActivity.this);
                        builder.setTitle("请选择出警人员:")
                                .setMultiChoiceItems(lname, ifchaeck, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        ifchaeck[which] = isChecked;
                                    }
                                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                int j = 0;
                                for (int i = 0; i < ifchaeck.length; i++) {
                                    if (ifchaeck[i]) {
                                        //sb.append(lid[i] + "&");
                                        j += 1;
                                    }
                                }
                                String[] ids = new String[j];
                                int k = 0;
                                for (int i = 0; i < ifchaeck.length; i++) {
                                    if (ifchaeck[i]) {
                                        ids[k] = lid[i];
                                        k += 1;
                                    }
                                }


                                addCjPerson(ids);
                                StartFight();


                                // StartFight();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                    }
                });
    }

    private void addCjPerson(String[] lid) {
        // TODO: 2016/11/1 部门id替换
        app.jqAPIService.addCjPerson(taskid, "532301000000", lid)
                .compose(RxUtil.<String>applySchedulers())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.d("addcjperson result : " + s);
                    }
                });


    }


    private void StartFight() {
        //adapter.addAll(list2);
        //LogUtil.d("systime : "+SomeUtil.getSysTime());
        String time = SomeUtil.getSysTime();
        String latitude = (String) SPUtils.get(app.getApp().getApplicationContext(), Constant.LATITUDE, "111");
        String longitude = (String) SPUtils.get(app.getApp().getApplicationContext(), Constant.LONGITUDE, "1111");
        LogUtil.d("fight latitude:" + latitude);
        LogUtil.d("fight longitude:" + longitude);
        app.jqAPIService.StartNewFight(JQid, taskid, time, longitude, latitude)
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


                        }
                        if (s.contains("200")) {
                            SomeUtil.showSnackBar(rootview, "开始作战！");
                            fabNewfight.setImageResource(R.drawable.ic_stop_white_48dp);
                            RxBus.getDefault().post("newjqflush");

                        }
                        if (s.contains("300")) {
                            SomeUtil.showSnackBar(rootview, "服务器错误！请稍后再试！");

                        }
                    }
                });

    }

    private void initData() {

        app.jqAPIService.getJqOrbit(JQid)
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


        app.jqAPIService.GetJQDetil(JQid)
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
                        LogUtil.d("jqDetil : " + jqDetil.getResult().toString());

                        if (jqDetil.getCode().equals(200)) {
                            // LogUtil.d("jqDetil :" + jqDetil.getResult().toString());
                            detilJqAddress.setText(jqDetil.getResult().get(0).getJqaddr());
                            detilJqBjrName.setText(jqDetil.getResult().get(0).getAlarmperson());
                            detilJqBjrPhone.setText(jqDetil.getResult().get(0).getCallingnumber());
                            detilJqNature.setText(jqDetil.getResult().get(0).getJqnature());
                            detilJqType.setText(jqDetil.getResult().get(0).getJqtype());
                            detilJqBjtime.setText(jqDetil.getResult().get(0).getCallpolicetime());
                            baojingName = jqDetil.getResult().get(0).getAlarmperson();
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
                startActivity(new Intent(NewFightActivity.this, JQCallbackActivity.class));
                break;
            case R.id.id_fight_talk:
                startActivity(new Intent(NewFightActivity.this, ChatActivity.class).putExtra("jqid", JQid));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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


}
