package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.AddPictureAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.calendarView.adapter.TopViewPagerAdapter;
import wgz.com.cx_ga_project.calendarView.utils.DateBean;
import wgz.com.cx_ga_project.calendarView.utils.OtherUtils;
import wgz.com.cx_ga_project.calendarView.view.CalendarView;
import wgz.com.cx_ga_project.calendarView.view.ContainerLayout;
import wgz.com.cx_ga_project.entity.WorkLog;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static wgz.com.cx_ga_project.base.Constant.CHECK_ONESSUMMARY;
import static wgz.com.cx_ga_project.base.Constant.CHECK_ONESSUMMARYPIC_BYDAYS;

/**
 * Created by wgz on 2016/8/9.
 */

public class MySubordinateLogAcitvity extends BaseActivity {
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
    @Bind(R.id.id_workLogRootview)
    CoordinatorLayout mRootview;
    @Bind(R.id.fab_addworklog)
    FloatingActionButton fabAddworklog;
    @Bind(R.id.worklog_pics)
    EasyRecyclerView worklogPics;
    private List<View> calenderViews = new ArrayList<>();
    private List<WorkLog.Mylog> mylogs = new ArrayList<>();
    private String policeid = "";
    private AddPictureAdapter adapter;
    List<String> paths = new ArrayList<>();
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
        Intent intent = getIntent();
        String name = intent.getStringExtra("policename");
        policeid = intent.getStringExtra("policeid");

        LogUtil.d("subordiante policeid: "+policeid);
        toolbarWprklog.setTitle(name + "的工作日志");
        fabAddworklog.setVisibility(View.GONE);
        setSupportActionBar(toolbarWprklog);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        worklogPics.setLayoutManager(new GridLayoutManager(this,3));
        worklogPics.setAdapter(adapter=new AddPictureAdapter(this));


        initCalendar();
       // initData();
    }

    /**
     * @param calendarView
     */
    private void initEventDays(final CalendarView calendarView) {
      final Calendar calendar = Calendar.getInstance();
        app.apiService.getLogData(CHECK_ONESSUMMARY, policeid, calendarView.getCurrentDay())
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
                        LogUtil.d("worklog : " + workLog.toString());
                        if (workLog.getCode().toString().contains("200")) {
                            mylogs = workLog.getLogs();
                            LogUtil.d("logs : " + mylogs.toString());
                            String nowdate = OtherUtils.formatDate(calendar.getTime());
                            app.apiService.getLogDataToDay("10001", nowdate)
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
                           // initCalendar();
                        } else {
                            SomeUtil.showSnackBar(container, "没有工作记录！");
                            idWorkLogText.setText("没有工作记录");
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
            CalendarView calendarView = new CalendarView(MySubordinateLogAcitvity.this, i, year, month);

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
        calenderLog.post(new Runnable() {
            @Override
            public void run() {
                initEventDays( adapter.getChildView(0));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 点击某个日期回调
     */
    class OnMyCalendarClickerListener implements CalendarView.OnCalendarClickListener {
        @Override
        public void onCalendarClick(int position, DateBean dateBean) {
            txToday.setText(OtherUtils.formatDate(dateBean.getDate()));

            //查询日志图片内容
            app.apiService.getLogPicData(CHECK_ONESSUMMARYPIC_BYDAYS, policeid, OtherUtils.formatDate(dateBean.getDate()))
                    .compose(RxUtil.<WorkLog>applySchedulers())
                    .subscribe(new Subscriber<WorkLog>() {
                        @Override
                        public void onCompleted() {
                            adapter.clear();
                            adapter.addAll(paths);
                            LogUtil.d("显示图片");
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(WorkLog workLog) {
                            paths.clear();
                            if (workLog.getCode().equals(200)) {
                                for (int i = 0; i < workLog.getLogs().size(); i++) {
                                    paths.add(workLog.getLogs().get(i).getSummary_pic());
                                }
                                LogUtil.d("PIC path :" + paths.toString());

                                // idWorkLogText.setText(workLog.getLogs().get(0).getSummary());
                                //id = workLog.getLogs().get(0).getId();
                            } else {
                                //idWorkLogText.setText("没有工作记录");
                                LogUtil.d("没有工作日志图片");
                            }
                        }
                    });


            //查询日志文字内容
            app.apiService.getLogDataToDay(policeid, OtherUtils.formatDate(dateBean.getDate()))
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
                            LogUtil.d("worklogByday code: " + workLog.getCode().toString());
                            if (workLog.getCode().equals(200)) {
                                idWorkLogText.setText(workLog.getLogs().get(0).getSummary());
                                LogUtil.d("worklogByday : " + workLog.getLogs().toString());
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
            txToday.setText(calendarView.getCurrentDay());
            LogUtil.d("当前月份 ： " + calendarView.getCurrentDay());
            initEventDays(calendarView);
            container.setRowNum(0);
            calendarView.initFirstDayPosition(0);

            //iniData();


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.subordinate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.Statistics) {
            // TODO: 2016/8/9 统计详情功能
            SomeUtil.showSnackBar(mRootview, "开发中。。。");
            return true;
        }
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
