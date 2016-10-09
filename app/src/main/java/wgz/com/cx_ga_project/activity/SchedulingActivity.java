package wgz.com.cx_ga_project.activity;

import android.os.Bundle;
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
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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
import wgz.com.cx_ga_project.entity.Scheduling;
import wgz.com.cx_ga_project.entity.SchedulingOneDay;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
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
    @Bind(R.id.id_schefulingPeople1)
    TextView mSchefulingPeople1;
    @Bind(R.id.id_schefulingPeople2)
    TextView mSchefulingPeople2;
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
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCalendar();
        app.apiService.getAllScheduling("2016-09-20", "2016-11-03", "030689")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Scheduling>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("schedulingError : " + e.toString());
                    }

                    @Override
                    public void onNext(Scheduling scheduling) {
                        LogUtil.e("scheduling : " + scheduling.getRes().toString());
                    }
                });

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
            //ToastUtil.showShort(SchedulingActivity.this, "poision:" + position);
            app.apiService.getOneDayScheduling(OtherUtils.formatDate(dateBean.getDate()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<SchedulingOneDay>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.e("schedulingOneDay error :" + e.toString());
                        }

                        @Override
                        public void onNext(SchedulingOneDay schedulingOneDay) {
                            LogUtil.e("schedulingOneDay  :" + schedulingOneDay.getRes().toString());
                            LogUtil.e("schedulingOneDay  :" + schedulingOneDay.getRes1().toString());
                            LogUtil.e("schedulingOneDay  :" + schedulingOneDay.getRes2().toString());
                            mSchefulingPeople.setText(schedulingOneDay.getRes().get(0).getPolicename());
                            mSchefulingPeople1.setText(schedulingOneDay.getRes1().get(0).getPolicename());

                            StringBuffer sb = new StringBuffer(256);
                            for (int i = 0 ; i <schedulingOneDay.getRes2().size(); i++){
                                if (i<schedulingOneDay.getRes2().size()-1){
                                    sb.append(schedulingOneDay.getRes2().get(i).getPolicename());
                                    sb.append(" ; ");
                                }
                                else {
                                    sb.append(schedulingOneDay.getRes2().get(i).getPolicename());
                                }

                            }
                            mSchefulingPeople2.setText(sb.toString());

                        }
                    });

            /*if (dateBean.getTag()) {
                mSchefulingPeople.setText("值班人员：" + "张三，李三，王三");

            } else {
                mSchefulingPeople.setText("今日无值班人员");
            }*/

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

            for (int i = 0; i < 42; i++) {
                DateBean dateBean = (DateBean) adapter.getItem(i);
                if (dateBean.getTag()) {
                    mSchefulingPeople.setText("值班人员：\" + \"张三，李三，王三");
                    return;
                } else {
                    mSchefulingPeople.setText("今日无值班人员");
                }

            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}
