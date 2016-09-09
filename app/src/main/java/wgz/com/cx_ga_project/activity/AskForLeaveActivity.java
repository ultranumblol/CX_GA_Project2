package wgz.com.cx_ga_project.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.jakewharton.rxbinding.view.RxView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.bean.AskForLeaveBean;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * 提交请假申请
 * Created by wgz on 2016/8/5.
 */

public class AskForLeaveActivity extends BaseActivity {

    TimePickerView pvTime;
    @Bind(R.id.id_leave_type_layout)
    RelativeLayout idLeaveTypeLayout;
    @Bind(R.id.id_leave_starttime_layout)
    RelativeLayout idLeaveStarttimeLayout;
    @Bind(R.id.id_leave_endtime_layout)
    RelativeLayout idLeaveEndtimeLayout;
    @Bind(R.id.id_leave_daycount_layout)
    RelativeLayout idLeaveDaycountLayout;
    private int flag = 0;
    OptionsPickerView pvOptions;
    private ArrayList<AskForLeaveBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_leave_type)
    TextView mLeaveType;
    @Bind(R.id.id_leave_starttime)
    TextView mLeaveStarttime;
    @Bind(R.id.id_leave_endtime)
    TextView mLeaveEndtime;
    @Bind(R.id.id_leave_daycount)
    EditText mLeaveDaycount;
    @Bind(R.id.id_leave_reason)
    EditText mLeaveReason;
    @Bind(R.id.id_leave_commit)
    CardView mLeaveCommit;
    @Bind(R.id.content_ask_for_leave)
    LinearLayout rootview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ask_for_leave;
    }

    @Override
    public void initView() {
        toolbar.setTitle("请销假");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.MONTH_DAY_HOUR_MIN);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR));//要在setTime 之前才有效果
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                switch (flag) {
                    case 1:
                        mLeaveStarttime.setText(getTime(date));
                        break;
                    case 2:
                        mLeaveEndtime.setText(getTime(date));
                }
            }
        });
        RxView.clicks(mLeaveCommit).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        docommit();
                    }
                });

        //选项选择器
        pvOptions = new OptionsPickerView(this);
        //选项1
        options1Items.add(new AskForLeaveBean(1, "请假类型"));
        //选项2
        ArrayList<String> options2Items_01 = new ArrayList<>();
        options2Items_01.add("事假");
        options2Items_01.add("病假");
        options2Items_01.add("调休");
        options2Items_01.add("出差");
        options2Items_01.add("其他");
        options2Items.add(options2Items_01);
        pvOptions.setPicker(options1Items, options2Items, null, true);

        //pvOptions.setTitle("选择假别");
        pvOptions.setCyclic(false, false, false);
        //设置默认选中的项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(0, 1, 0);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String result = options2Items.get(options1).get(option2);
                mLeaveType.setText(result);
            }
        });
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pvOptions.isShowing() || pvTime.isShowing()) {
                pvOptions.dismiss();
                pvTime.dismiss();
                return true;
            }
            if (pvTime.isShowing()) {
                pvTime.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void docommit() {
        Date startdate = getStrToDate(mLeaveStarttime.getText().toString());
        Date enddate = getStrToDate(mLeaveEndtime.getText().toString());
        Date currentdate = new Date(System.currentTimeMillis());
        String curredate = AskForLeaveActivity.getTime(currentdate);
        String stime = AskForLeaveActivity.getTime(startdate);
        String etime = AskForLeaveActivity.getTime(enddate);
        if (mLeaveReason.getText().toString().equals("")) {
            Snackbar.make(rootview, "请填写请假事由!", Snackbar.LENGTH_SHORT).show();
            return;
        } else if (mLeaveStarttime.getText().toString().contains("请选择")
                || mLeaveEndtime.getText().toString().contains("请选择")) {
            Snackbar.make(rootview, "请选择日期!", Snackbar.LENGTH_SHORT).show();
            return;
        } else if (mLeaveType.getText().toString().contains("请选择")) {
            Snackbar.make(rootview, "请选择请假类型！", Snackbar.LENGTH_SHORT).show();
            return;
        } else if (!compareDate(startdate, enddate)) {
            Snackbar.make(rootview, "结束日期应该大于开始日期!", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // TODO: 2016/8/5 提交请假申请
        app.apiService.upLoadLeave("leaveApply", stime, etime, mLeaveReason.getText().toString(),
                "501", curredate, "101", mLeaveType.getText().toString(), mLeaveDaycount.getText().toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (s.contains("200")) {
                            SomeUtil.showSnackBar(rootview, "提交申请成功！");
                        } else {
                            SomeUtil.showSnackBar(rootview, "服务器错误！");
                        }
                    }
                });
    }


    public boolean compareDate(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);

        int result = c1.compareTo(c2);
        if (result < 0)
            return true;
        else
            return false;
    }

    public Date getStrToDate(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtil.e("DATE error" + e.toString());
            return null;
        }
    }

    @OnClick({R.id.id_leave_type_layout, R.id.id_leave_starttime_layout, R.id.id_leave_endtime_layout, R.id.id_leave_daycount_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_leave_type_layout:
                pvOptions.show();
                break;
            case R.id.id_leave_starttime_layout:
                flag = 1;
                pvTime.show();
                break;
            case R.id.id_leave_endtime_layout:
                flag = 2;
                pvTime.show();
                break;
            case R.id.id_leave_daycount_layout:
                mLeaveDaycount.requestFocus();
                break;
        }
    }


}
