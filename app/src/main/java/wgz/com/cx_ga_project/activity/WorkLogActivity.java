package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.calendarView.adapter.CalendarAdapter;
import wgz.com.cx_ga_project.calendarView.adapter.TopViewPagerAdapter;
import wgz.com.cx_ga_project.calendarView.utils.DateBean;
import wgz.com.cx_ga_project.calendarView.utils.OtherUtils;
import wgz.com.cx_ga_project.calendarView.view.CalendarView;
import wgz.com.cx_ga_project.calendarView.view.ContainerLayout;
import wgz.com.cx_ga_project.entity.WorkLog;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;


/**
 *
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
    ScrollView view_content;
    @Bind(R.id.workLog_container)
    ContainerLayout container;
    @Bind(R.id.content_workLog)
    ConstraintLayout contentWorkLog;
    @Bind(R.id.id_workLogRootview)
    CoordinatorLayout mRootview;
    @Bind(R.id.fab_addworklog)
    FloatingActionButton fabAddworklog;
    private List<View> calenderViews = new ArrayList<>();
    private List<WorkLog.Mylog> mylogs = new ArrayList<>();
    private String id = "";

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
        LogUtil.e("我的日志开始初始化");
        setSupportActionBar(toolbarWprklog);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iniData();
        initCalendar();
    }

    private void iniData() {
        final Calendar calendar = Calendar.getInstance();
        app.apiService.getLogData("checkOnceSummary","10001",OtherUtils.formatMonth(calendar.getTime()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WorkLog>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    LogUtil.e("error"+e);
                    }

                    @Override
                    public void onNext(WorkLog workLog) {
                        if (workLog.getCode().toString().contains("200")){
                            mylogs = workLog.getLogs();
                            String nowdate = OtherUtils.formatDate(calendar.getTime());
                            for (int i = 0;i<mylogs.size();i++){
                                if (mylogs.get(i).getTime().equals(nowdate)){
                                    idWorkLogText.setText(mylogs.get(i).getSummary());
                                    break;
                                }
                            }
                        }else {
                            SomeUtil.showSnackBar(mRootview,"服务器错误！");
                        }

                    }
                });
    }

    /**
     * @param calendarView
     */
    private void initEventDays(CalendarView calendarView) {


        //设置含有事件的日期 1-9号
        List<String> eventDays = new ArrayList<>();//根据实际情况调整，传入时间格式(yyyy-MM)
        for (int i =0;i<mylogs.size();i++){
           String date =  mylogs.get(i).getTime().toString();
            eventDays.add(date);
        }

        calendarView.setEventDays(eventDays);
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
        calenderLog.post(new Runnable() {
            @Override
            public void run() {
                initEventDays((CalendarView) adapter.getChildView(0));
            }
        });
    }
    @OnClick(R.id.fab_addworklog)
    public void onClick() {
        if (idWorkLogText.getText().toString().contains("没有工作记录")){
            Snackbar.make(mRootview, "是否为选中日期添加工作记录？", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("time",txToday.getText().toString());
                    intent.putExtra("worklog",idWorkLogText.getText().toString());
                    intent.putExtra("id",id);
                    intent.setClass(WorkLogActivity.this, AddWorkLogActivity.class);
                    startActivityForResult(intent,1);
                    //startActivity(intent);
                }
            }).show();
        }
        else {
            Snackbar.make(mRootview, "是否修改当前工作记录？", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("time",txToday.getText().toString());
                    intent.putExtra("worklog",idWorkLogText.getText().toString());
                    intent.putExtra("id",id);
                    intent.setClass(WorkLogActivity.this, AddWorkLogActivity.class);
                    startActivityForResult(intent,1);
                }
            }).show();
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if (data.getStringExtra("result").equals("refresh")){
                iniData();
                initCalendar();
                //idWorkLogText.setText(data.getStringExtra("text"));
            }

        }catch (Exception e){
            LogUtil.e("error :"+e);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 点击某个日期回调
     */
    class OnMyCalendarClickerListener implements CalendarView.OnCalendarClickListener {
        @Override
        public void onCalendarClick(int position, DateBean dateBean) {
            txToday.setText(OtherUtils.formatDate(dateBean.getDate()));
            //ToastUtil.showShort(SchedulingActivity.this,"poision:"+position);
            for (int i = 0; i<mylogs.size();i++){
                String summary = mylogs.get(i).getSummary();
                String logdate = mylogs.get(i).getTime();
                String id2 = mylogs.get(i).getId();
                LogUtil.e("summary:"+summary);
                String date = OtherUtils.formatDate(dateBean.getDate());
                LogUtil.e("DATE:"+date);
                LogUtil.e("id:"+id);
                if (date.equals(logdate)){
                    idWorkLogText.setText(summary);
                    id = id2;
                    return;
                }

            }
            idWorkLogText.setText("没有工作记录");
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
            CalendarView calendarView = (CalendarView) calenderViews.get(position % 3);
            txToday.setText(calendarView.getCurrentDay());
            container.setRowNum(0);
            CalendarAdapter adapter = calendarView.initFirstDayPosition(0);
            //iniData();

            initEventDays(calendarView);
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
