package wgz.com.cx_ga_project.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.calendarView.adapter.CalendarAdapter;
import wgz.com.cx_ga_project.calendarView.adapter.TopViewPagerAdapter;
import wgz.com.cx_ga_project.calendarView.utils.DateBean;
import wgz.com.cx_ga_project.calendarView.utils.OtherUtils;
import wgz.com.cx_ga_project.calendarView.view.CalendarView;
import wgz.com.cx_ga_project.calendarView.view.ContainerLayout;
import wgz.com.cx_ga_project.util.httpUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 *
 * 查看排班
 * Created by wgz on 2016/8/1.
 */
public class SchedulingActivity extends BaseActivity {


    @Bind(R.id.toolbar_scheduling)
    Toolbar mtoolbar;
    @Bind(R.id.tx_today)
    TextView txToday;
    @Bind(R.id.vp_calender)
    ViewPager vpCalender;
    @Bind(R.id.view_content)
    ScrollView viewContent;
    @Bind(R.id.scheduling_container)
    ContainerLayout container;
    @Bind(R.id.content_scheduling)
    ConstraintLayout contentScheduling;
    @Bind(R.id.id_schefulingPeople)
    TextView mSchefulingPeople;
    private List<View> calenderViews = new ArrayList<>();
    /**
     * 日历向左或向右可翻动的天数
     */
    private int INIT_PAGER_INDEX = 120;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scheduling;
    }

    @Override
    public void initView() {
        mtoolbar.setTitle("我的值班");
        LogUtil.e("ContainerLayout开始初始化");
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCalendar();
            new Thread(new Runnable() {
                @Override
                public void run() {
                   String result =  httpUtil.getStr("http://192.168.1.88/demojob/getAppAllSch","utf_8");
                    LogUtil.e("jsonStr:"+result);
                }
            }).start();
      /*  Call<String> call = app.apiService.getZhiBan();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String jsonStr = response.body();
                LogUtil.e("jsonStr:"+jsonStr);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                LogUtil.e("jsonStr===error");
                LogUtil.e("error:"+t.toString());
            }
        });*/

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
            CalendarView calendarView = new CalendarView(SchedulingActivity.this, i, year, month);

            calendarView.setOnCalendarClickListener(new OnMyCalendarClickerListener());
            if (i == 0) {
                container.setRowNum(calendarView.getColorDataPosition() / 7);
            }
            calenderViews.add(calendarView);
        }
        final TopViewPagerAdapter adapter = new TopViewPagerAdapter(this, calenderViews, INIT_PAGER_INDEX, calendar);
        vpCalender.setAdapter(adapter);
        vpCalender.setCurrentItem(INIT_PAGER_INDEX);
        vpCalender.addOnPageChangeListener(new OnMyViewPageChangeListener());


        vpCalender.post(new Runnable() {
            @Override
            public void run() {
                initEventDays((CalendarView) adapter.getChildView(0));
            }
        });

    }


    /**
     * 点击某个日期回调
     */
    class OnMyCalendarClickerListener implements CalendarView.OnCalendarClickListener {
        @Override
        public void onCalendarClick(int position, DateBean dateBean) {
            txToday.setText(OtherUtils.formatDate(dateBean.getDate()));
            //ToastUtil.showShort(SchedulingActivity.this, "poision:" + position);
            if (dateBean.getTag()) {
                mSchefulingPeople.setText("值班人员：" + "张三，李三，王三");

            } else {
                mSchefulingPeople.setText("今日无值班人员");
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
            calendarView.initFirstDayPosition(0);


            //设置含有事件的日期 1-9号
            CalendarAdapter adapter = calendarView.initFirstDayPosition(0);

            for (int i=0;i<42;i++){
                DateBean dateBean = (DateBean) adapter.getItem(i);
                if (dateBean.getTag()){
                    mSchefulingPeople.setText("值班人员：\" + \"张三，李三，王三");
                    return;
                }else {
                    mSchefulingPeople.setText("今日无值班人员");
                }

            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}
