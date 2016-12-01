package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.API.APIservice;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.AddPictureAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.calendarView.adapter.TopViewPagerAdapter;
import wgz.com.cx_ga_project.calendarView.utils.DateBean;
import wgz.com.cx_ga_project.calendarView.utils.OtherUtils;
import wgz.com.cx_ga_project.calendarView.view.CalendarView;
import wgz.com.cx_ga_project.calendarView.view.ContainerLayout;
import wgz.com.cx_ga_project.entity.WorkLog;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SPUtils;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static wgz.com.cx_ga_project.base.Constant.CHECK_ONESSUMMARY;
import static wgz.com.cx_ga_project.base.Constant.CHECK_ONESSUMMARYPIC_BYDAYS;
import static wgz.com.cx_ga_project.base.Constant.CHECK_ONESSUMMARY_BYDAYS;


/**
 * 工作日志
 * Created by wgz on 2016/8/3.
 */
public class WorkLogActivity extends BaseActivity {
    @Bind(R.id.toolbar_wprklog)
    Toolbar toolbarWprklog;
    @Bind(R.id.tx_today)
    TextView txToday;
    @Bind(R.id.calender_log)
    ViewPager calenderLog;
    @Bind(R.id.id_workLogText)
    TextView idWorkLogText;
    @Bind(R.id.view_content)
    NestedScrollView view_content;
    @Bind(R.id.workLog_container)
    ContainerLayout container;
    @Bind(R.id.fab_addworklog)
    FloatingActionButton fabAddworklog;
    @Bind(R.id.worklog_pics)
    EasyRecyclerView worklogPics;
    private List<View> calenderViews = new ArrayList<>();
    private List<WorkLog.Mylog> mylogs = new ArrayList<>();
    private String id = "";
    private AddPictureAdapter adapter;
    private Date  clickdate  =new Date() ;
    List<String> paths = new ArrayList<>();
    private boolean onPagescroll = false;
    /**
     * 日历向左或向右可翻动的天数
     */
    private int INIT_PAGER_INDEX = 120;

    @Override
    public int getLayoutId() {
        return R.layout.activity_work_log;
    }

    @Override
    public void initView() {
        toolbarWprklog.setTitle("工作日志");
        setSupportActionBar(toolbarWprklog);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        worklogPics.setLayoutManager(new GridLayoutManager(this,3));
        worklogPics.setAdapter(adapter=new AddPictureAdapter(this));


        initCalendar();
      //  iniData();

    }

    private void iniData() {
        final Calendar calendar = Calendar.getInstance();
        LogUtil.d("initData : " + OtherUtils.formatMonth(calendar.getTime()).toString());
        app.apiService.getLogData(CHECK_ONESSUMMARY,SomeUtil.getUserId(), OtherUtils.formatMonth(calendar.getTime()))

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WorkLog>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("getLogData error : " + e);
                    }

                    @Override
                    public void onNext(WorkLog workLog) {
                        LogUtil.d("workLog : " + workLog.toString());
                        if (workLog.getCode().toString().contains("200")) {
                            mylogs = workLog.getLogs();
                            LogUtil.d("logs : " + mylogs.toString());
                            String nowdate = OtherUtils.formatDate(calendar.getTime());
                            app.apiService.getLogDataToDay(SomeUtil.getUserId(), nowdate)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<WorkLog>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onNext(WorkLog workLog) {
                                            if (workLog.getCode().equals(200)) {
                                                idWorkLogText.setText(workLog.getLogs().get(0).getSummary());
                                            } else {
                                                idWorkLogText.setText("没有工作记录");
                                            }
                                        }
                                    });
                           // initCalendar();
                        } else {
                            SomeUtil.showSnackBar(container, "服务器错误！请稍后再试");
                            LogUtil.d("logs : " + mylogs.toString());
                            //initCalendar();
                        }

                    }
                });
    }

    /**
     * @param calendarView
     */
    private void initEventDays(final CalendarView calendarView) {

        final Calendar calendar = Calendar.getInstance();
        adapter.clear();
        paths.clear();
        adapter.addAll(paths);
        app.apiService.getLogData(CHECK_ONESSUMMARY, SomeUtil.getUserId(), calendarView.getCurrentDay())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WorkLog>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("getLogData error : " + e);
                    }

                    @Override
                    public void onNext(WorkLog workLog) {
                       // LogUtil.d("worklog : " + workLog.toString());
                        if (workLog.getCode().toString().contains("200")) {
                            mylogs = workLog.getLogs();
                            String nowdate = OtherUtils.formatDate(calendar.getTime());
                            LogUtil.d("nowdate : "+nowdate);
                            if (OtherUtils.formatMonth(calendar.getTime()).equals(calendarView.getCurrentDay())&&!onPagescroll){

                                app.apiService.getLogDataToDay(SomeUtil.getUserId(), nowdate)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Subscriber<WorkLog>() {
                                            @Override
                                            public void onCompleted() {
                                                //设置含有事件的日期
                                                List<String> eventDays = new ArrayList<>();//根据实际情况调整，传入时间格式(yyyy-MM)
                                                for (int i = 0; i < mylogs.size(); i++) {
                                                    String date = mylogs.get(i).getTime().toString();

                                                    eventDays.add(OtherUtils.formatDate(SomeUtil.getStrToDate(date)));
                                                }
                                                // LogUtil.d("eventDays :" + eventDays.toString());
                                                calendarView.setEventDays(eventDays);
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onNext(WorkLog workLog) {
                                                if (workLog.getCode().equals(200)) {
                                                    idWorkLogText.setText(workLog.getLogs().get(0).getSummary());
                                                } else {
                                                    idWorkLogText.setText("没有工作记录");
                                                }
                                            }
                                        });

                            }else {

                                //LogUtil.d("logdateToday date : "+calendarView.getCurrentDayDay());

                                app.apiService.getLogDataToDay(SomeUtil.getUserId(),calendarView.getCurrentDayDay() )
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Subscriber<WorkLog>() {
                                            @Override
                                            public void onCompleted() {
                                                //设置含有事件的日期
                                                List<String> eventDays = new ArrayList<>();//根据实际情况调整，传入时间格式(yyyy-MM)
                                                for (int i = 0; i < mylogs.size(); i++) {
                                                    String date = mylogs.get(i).getTime().toString();

                                                    eventDays.add(OtherUtils.formatDate(SomeUtil.getStrToDate(date)));
                                                }
                                                // LogUtil.d("eventDays :" + eventDays.toString());
                                                calendarView.setEventDays(eventDays);
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onNext(WorkLog workLog) {
                                                LogUtil.d("scroll : "+workLog.getLogs().toString());
                                                LogUtil.d("scroll : "+workLog.getCode().toString());
                                                if (workLog.getCode().equals(200)) {
                                                    idWorkLogText.setText(workLog.getLogs().get(0).getSummary());
                                                } else {
                                                    idWorkLogText.setText("没有工作记录");
                                                }
                                            }
                                        });

                            }


                            // initCalendar();
                        } else {
                            SomeUtil.showSnackBar(container, "本月没有工作记录！");
                            idWorkLogText.setText("没有工作记录");
                            paths.clear();
                            adapter.clear();
                            adapter.addAll(paths);
                            //initCalendar();
                        }

                    }
                });

    }

    private void initCalendar() {
        Calendar calendar = Calendar.getInstance();
        txToday.setText(OtherUtils.formatDate(calendar.getTime()));

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < 3; i++) {
            CalendarView calendarView = new CalendarView(WorkLogActivity.this, i, year, month);

            calendarView.setOnCalendarClickListener(new OnMyCalendarClickerListener());
            if (i == 0) {
                container.setRowNum(calendarView.getColorDataPosition() / 7);
            }
            calenderViews.add(calendarView);
        }
        final TopViewPagerAdapter adapter = new TopViewPagerAdapter(this, calenderViews, INIT_PAGER_INDEX, calendar);
        calenderLog.setAdapter(adapter);
        calenderLog.setCurrentItem(INIT_PAGER_INDEX);
        calenderLog.addOnPageChangeListener(new OnMyViewPageChangeListener());
        calenderLog.post(() -> initEventDays(adapter.getChildView(0)));
    }

    @OnClick(R.id.fab_addworklog)
    public void onClick() {
            LogUtil.d("currenttime :"+SomeUtil.getSysTime2());
           if (clickdate.getTime()-System.currentTimeMillis()>0){
               SomeUtil.showSnackBar(container,"不能添加未来的工作日志！");

           }else{
               if (idWorkLogText.getText().toString().contains("没有工作记录")) {
                   Snackbar.make(container, "是否为选中日期添加工作记录？", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent();
                           intent.putExtra("time", txToday.getText().toString());
                           intent.putExtra("worklog", idWorkLogText.getText().toString());
                           intent.putExtra("id", id);
                           intent.setClass(WorkLogActivity.this, AddWorkLogActivity.class);
                           startActivityForResult(intent, 1);
                           //startActivity(intent);
                       }
                   }).show();
               } else {
                   Snackbar.make(container, "是否修改当前工作记录？", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent();
                           intent.putExtra("time", txToday.getText().toString());
                           intent.putExtra("worklog", idWorkLogText.getText().toString());
                           intent.putExtra("id", id);
                           intent.setClass(WorkLogActivity.this, AddWorkLogActivity.class);
                           startActivityForResult(intent, 1);
                       }
                   }).show();
               }

           }





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mytimebank, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.my_timebank:
                startActivity(new Intent(this,TimeBankActivity.class)
                        .putExtra("policename",(String) SPUtils.get(app.getApp().getApplicationContext(), Constant.USERNAME, "未知")).putExtra("policeid",SomeUtil.getUserId()));
                break;



        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (data.getStringExtra("result").equals("refresh")) {
                //iniData();
                initCalendar();


                //idWorkLogText.setText(data.getStringExtra("text"));
            }

        } catch (Exception e) {
            LogUtil.d("error :" + e);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 点击某个日期回调
     */
    class OnMyCalendarClickerListener implements CalendarView.OnCalendarClickListener {
        @Override
        public void onCalendarClick(int position, DateBean dateBean) {
            clickdate = dateBean.getDate();


            txToday.setText(OtherUtils.formatDate(dateBean.getDate()));
            LogUtil.d("click data :"+OtherUtils.formatDate(dateBean.getDate()));
            //查询日志图片内容
            app.apiService.getLogPicData(CHECK_ONESSUMMARYPIC_BYDAYS,SomeUtil.getUserId(),OtherUtils.formatDate(dateBean.getDate()))
                    .compose(RxUtil.applySchedulers())
                    .subscribe(new Subscriber<WorkLog>() {
                        @Override
                        public void onCompleted() {
                            adapter.clear();
                           if (paths.size()>=1){
                               adapter.addAll(paths);

                           }
                            LogUtil.d("显示图片");
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(WorkLog workLog) {
                            paths.clear();
                            if (workLog.getCode().equals(200)) {
                                for (int i = 0 ; i<workLog.getLogs().size();i++){
                                    paths.add(workLog.getLogs().get(i).getSummary_pic());
                                }
                                LogUtil.d("PIC path :"+paths.size());

                               // idWorkLogText.setText(workLog.getLogs().get(0).getSummary());
                                //id = workLog.getLogs().get(0).getId();
                            } else {
                                //idWorkLogText.setText("没有工作记录");
                                LogUtil.d("没有工作日志图片");
                            }
                        }
                    });



            //查询日志文字内容
            app.apiService.getLogDataToDay( SomeUtil.getUserId(), OtherUtils.formatDate(dateBean.getDate()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<WorkLog>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            idWorkLogText.setText("没有工作记录");
                        }

                        @Override
                        public void onNext(WorkLog workLog) {
                            LogUtil.d("worklogByday code: "+workLog.getCode().toString());
                            if (workLog.getCode().equals(200)) {
                                idWorkLogText.setText(workLog.getLogs().get(0).getSummary());
                                LogUtil.d("worklogByday : "+workLog.getLogs().toString());
                                id = workLog.getLogs().get(0).getId();
                            } else {
                                idWorkLogText.setText("没有工作记录");
                            }
                        }
                    });


        }
    }

    /**
     * 日期滚动时回调
     */
    class OnMyViewPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            final CalendarView calendarView = (CalendarView) calenderViews.get(position % 3);
            txToday.setText(calendarView.getCurrentDayDay());
            onPagescroll = true;
            LogUtil.d("当前月份 ： " + calendarView.getCurrentDay());
            initEventDays(calendarView);
            container.setRowNum(0);

            calendarView.initFirstDayPosition(0);


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
