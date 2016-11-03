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
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
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
    ConstraintLayout rootview;
    @Bind(R.id.id_schefulingPeople)
    TextView mSchefulingPeople;
    @Bind(R.id.id_schefulingPeople1)
    TextView mSchefulingPeople1;
    @Bind(R.id.id_schefulingPeople2)
    TextView mSchefulingPeople2;
    private List<View> calenderViews = new ArrayList<>();
    private List<Scheduling.ScheduilingRe> data = new ArrayList<>();
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


    }

    /**
     * @param calendarView
     */
    private void initEventDays(final CalendarView calendarView) {

        final Calendar calendar = Calendar.getInstance();
        app.apiService.getAllScheduling(calendarView.getCurrentDay(), SomeUtil.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Scheduling>() {
                    @Override
                    public void onStart() {
                        mSchefulingPeople.setText("");
                        mSchefulingPeople1.setText("");
                        mSchefulingPeople2.setText("");
                    }
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("schedulingError : " + e.toString());
                    }

                    @Override
                    public void onNext(Scheduling scheduling) {
                        if (scheduling.getCode().equals(200)) {
                            data = scheduling.getRes();
                            LogUtil.d("scheduling data: " + data.toString());
                            app.apiService.getOneDayScheduling(calendarView.getCurrentDayDay())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<SchedulingOneDay>() {
                                        @Override
                                        public void onStart() {
                                            mSchefulingPeople.setText("");
                                            mSchefulingPeople1.setText("");
                                            mSchefulingPeople2.setText("");
                                        }

                                        @Override
                                        public void onCompleted() {
                                            List<String> eventDays = new ArrayList<>();//根据实际情况调整，传入时间格式(yyyy-MM)
                                            for (int j = 0; j < data.size(); j++) {
                                                String date = data.get(j).getStart();
                                                eventDays.add(OtherUtils.formatDate(SomeUtil.getStrToDate(date)));
                                            }
                                            calendarView.setEventDays(eventDays);


                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            LogUtil.d("schedulingOneDay error :" + e.toString());
                                        }

                                        @Override
                                        public void onNext(SchedulingOneDay schedulingOneDay) {
//                                            LogUtil.d("schedulingOneDay  :" + schedulingOneDay.getRes().toString());
//                                            LogUtil.d("schedulingOneDay  :" + schedulingOneDay.getRes1().toString());
//                                            LogUtil.d("schedulingOneDay  :" + schedulingOneDay.getRes2().toString());
                                            mSchefulingPeople.setText(schedulingOneDay.getRes().get(0).getPolicename());
                                            mSchefulingPeople1.setText(schedulingOneDay.getRes1().get(0).getPolicename());

                                            StringBuffer sb = new StringBuffer(256);
                                            for (int i = 0; i < schedulingOneDay.getRes2().size(); i++) {
                                                if (i < schedulingOneDay.getRes2().size() - 1) {
                                                    sb.append(schedulingOneDay.getRes2().get(i).getPolicename());
                                                    sb.append(" ; ");
                                                } else {
                                                    sb.append(schedulingOneDay.getRes2().get(i).getPolicename());
                                                }

                                            }
                                            mSchefulingPeople2.setText(sb.toString());

                                        }
                                    });


                        } else {
                            SomeUtil.showSnackBar(rootview, "没有值班信息！");

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
                initEventDays(adapter.getChildView(0));
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
            app.apiService.getOneDayScheduling(OtherUtils.formatDate(dateBean.getDate()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<SchedulingOneDay>() {
                        @Override
                        public void onStart() {
                            mSchefulingPeople.setText("");
                            mSchefulingPeople1.setText("");
                            mSchefulingPeople2.setText("");
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.d("schedulingOneDay error :" + e.toString());
                        }

                        @Override
                        public void onNext(SchedulingOneDay schedulingOneDay) {
                            LogUtil.d("schedulingOneDay  :" + schedulingOneDay.getRes().toString());
                            LogUtil.d("schedulingOneDay  :" + schedulingOneDay.getRes1().toString());
                            LogUtil.d("schedulingOneDay  :" + schedulingOneDay.getRes2().toString());
                            mSchefulingPeople.setText(schedulingOneDay.getRes().get(0).getPolicename());
                            mSchefulingPeople1.setText(schedulingOneDay.getRes1().get(0).getPolicename());

                            StringBuffer sb = new StringBuffer(256);
                            for (int i = 0; i < schedulingOneDay.getRes2().size(); i++) {
                                if (i < schedulingOneDay.getRes2().size() - 1) {
                                    sb.append(schedulingOneDay.getRes2().get(i).getPolicename());
                                    sb.append(" ; ");
                                } else {
                                    sb.append(schedulingOneDay.getRes2().get(i).getPolicename());
                                }

                            }
                            mSchefulingPeople2.setText(sb.toString());

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

            CalendarView calendarView = (CalendarView) calenderViews.get(position % 3);
            txToday.setText(calendarView.getCurrentDayDay());
            LogUtil.d("当前月份 ： " + calendarView.getCurrentDay());
            container.setRowNum(0);
            calendarView.initFirstDayPosition(0);
            initEventDays(calendarView);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}
