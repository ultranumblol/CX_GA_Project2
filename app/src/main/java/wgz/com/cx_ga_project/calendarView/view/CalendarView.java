package wgz.com.cx_ga_project.calendarView.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


import java.util.ArrayList;
import java.util.List;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.calendarView.adapter.CalendarAdapter;
import wgz.com.cx_ga_project.calendarView.utils.DateBean;
import wgz.com.cx_ga_project.calendarView.utils.OtherUtils;

/**
 * Title:日历view，只包含一个scrollview
 * Description:
 *
 * @author liu_yuwu
 * @date 2016/1/27.
 */
public class CalendarView extends GridView {

    private CalendarAdapter calendarAdapter;


    private List<String> eventDays = new ArrayList<>();

    public CalendarView(Context context, int jumpMonth, int year, int month) {
        super(context);
        initCalendarValues();
        setCalendarValues(context, jumpMonth, year, month);
    }

    private void initCalendarValues() {
        setCacheColorHint(getResources().getColor(android.R.color.transparent));
        setHorizontalSpacing(0);
        setVerticalSpacing(0);
        setNumColumns(7);
        setStretchMode(STRETCH_COLUMN_WIDTH);
        setSelector(R.color.transparent);
    }


    private void setCalendarValues(Context context, int jumpMonth, int year, int month) {
//        queryDateList(year, month, jumpMonth);
        calendarAdapter = new CalendarAdapter(context, jumpMonth, year, month);
        setAdapter(calendarAdapter);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点击任何一个item，得到这个item的日期(排除点击的是非当前月日期(点击不响应))
                DateBean dateBean = (DateBean) calendarAdapter.getItem(position);
                if (dateBean.getMonthType() == DateBean.CURRENT_MONTH) {
                    calendarAdapter.setColorDataPosition(position);
                    ((ContainerLayout) getParent().getParent()).setRowNum(position / 7);
                    if (onCalendarClickListener != null) {
                        onCalendarClickListener.onCalendarClick(position, dateBean);
                    }
                } else if (dateBean.getMonthType() == DateBean.LAST_MONTH) {
                    ViewPager viewPager = (ViewPager) getParent();
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                } else if (dateBean.getMonthType() == DateBean.NEXT_MONTH) {
                    ViewPager viewPager = (ViewPager) getParent();
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });
    }

    public void refreshView(Context context, int jumpMonth, int year, int month) {
        setCalendarValues(context, jumpMonth, year, month);
        setEventDays(eventDays);
    }

    public CalendarAdapter initFirstDayPosition(int position) {

        if (calendarAdapter != null) {
            if (position != 0) {
                calendarAdapter.setColorDataPosition(position);
            } else {
                calendarAdapter.setColorDataPosition(calendarAdapter.getFirstDatePosition());
            }
            return calendarAdapter;
        }
        return calendarAdapter;
    }

    public String getCurrentDay() {
        DateBean firstDateBean = calendarAdapter.getFirstDateBean();
        if (firstDateBean != null) {
            return OtherUtils.formatMonth(firstDateBean.getDate());
        }
        return null;
    }
    public String getCurrentDayDay() {
        DateBean firstDateBean = calendarAdapter.getFirstDateBean();
        if (firstDateBean != null) {
            return OtherUtils.formatDate(firstDateBean.getDate());
        }
        return null;
    }

    public int getColorDataPosition() {
        return calendarAdapter.getColorDataPosition();
    }

    private OnCalendarClickListener onCalendarClickListener;


    public void setOnCalendarClickListener(OnCalendarClickListener onCalendarClickListener) {
        this.onCalendarClickListener = onCalendarClickListener;
    }


    public interface OnCalendarClickListener {
        public void onCalendarClick(int position, DateBean dateBean);
    }

    /**
     * 设置含有事件的日期
     * @param eventDays
     */
    public void setEventDays( List<String> eventDays) {
        this.eventDays = eventDays;
        calendarAdapter.setDateList(eventDays);
    }

}
