package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.calendarView.adapter.CalendarAdapter;
import wgz.com.cx_ga_project.calendarView.adapter.TopViewPagerAdapter;
import wgz.com.cx_ga_project.calendarView.utils.DateBean;
import wgz.com.cx_ga_project.calendarView.utils.OtherUtils;
import wgz.com.cx_ga_project.calendarView.view.CalendarView;
import wgz.com.cx_ga_project.calendarView.view.ContainerLayout;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

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
        toolbarWprklog.setTitle("xxx的工作日志");
        fabAddworklog.setVisibility(View.GONE);
        LogUtil.e("我的日志开始初始化");
        setSupportActionBar(toolbarWprklog);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initCalendar();
    }
    /**
     * @param calendarView
     */
    private void initEventDays(CalendarView calendarView) {
        //设置含有事件的日期 1-9号
        List<String> eventDays = new ArrayList<>();//根据实际情况调整，传入时间格式(yyyy-MM)
        for (int j = 0; j < 10; j++) {
            //eventDays.add(calendarView.getCurrentDay() + "-0" + j);
            eventDays.add("2016-08" + "-0" + j);
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
            CalendarView calendarView = new CalendarView(MySubordinateLogAcitvity.this, i, year, month);

            calendarView.setOnCalendarClickListener(new MySubordinateLogAcitvity.OnMyCalendarClickerListener());
            if (i == 0) {
                container.setRowNum(calendarView.getColorDataPosition() / 7);
            }
            calenderViews.add(calendarView);
        }
        final TopViewPagerAdapter adapter = new TopViewPagerAdapter(this, calenderViews, INIT_PAGER_INDEX, calendar);
        calenderLog.setAdapter(adapter);
        calenderLog.setCurrentItem(INIT_PAGER_INDEX);
        calenderLog.addOnPageChangeListener(new MySubordinateLogAcitvity.OnMyViewPageChangeListener());
        calenderLog.post(new Runnable() {
            @Override
            public void run() {
                initEventDays((CalendarView) adapter.getChildView(0));
            }
        });
    }
  /*  @OnClick(R.id.fab_addworklog)
    public void onClick() {
        Snackbar.make(mRootview, "是否为选中日期添加工作记录？", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MySubordinateLogAcitvity.this, AddWorkLogActivity.class));
            }
        }).show();
    }*/
    /**
     * 点击某个日期回调
     */
    class OnMyCalendarClickerListener implements CalendarView.OnCalendarClickListener {
        @Override
        public void onCalendarClick(int position, DateBean dateBean) {
            txToday.setText(OtherUtils.formatDate(dateBean.getDate()));
            //ToastUtil.showShort(SchedulingActivity.this,"poision:"+position);
            if (dateBean.getTag()) {
                idWorkLogText.setText("日志23123213123");

            } else {
                idWorkLogText.setText("没有工作记录");
            }

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

            for (int i = 0; i < 42; i++) {
                DateBean dateBean = (DateBean) adapter.getItem(i);
                if (dateBean.getTag()) {
                    idWorkLogText.setText("日志23123213123");
                    return;
                } else {
                    idWorkLogText.setText("没有工作记录");
                }

            }
            //设置含有事件的日期 1-9号
            initEventDays(calendarView);
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
            SomeUtil.showSnackBar(mRootview,"开发中。。。");
            return true;
        }
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
