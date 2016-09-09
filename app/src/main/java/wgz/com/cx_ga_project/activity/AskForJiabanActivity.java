package wgz.com.cx_ga_project.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.AndroidCharacter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.jakewharton.rxbinding.view.RxView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import com.jakewharton.rxbinding.view.RxView;

import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static wgz.com.cx_ga_project.activity.AskForLeaveActivity.getTime;

/**
 * 提交加班申请
 * Created by wgz on 2016/8/5.
 */
public class AskForJiabanActivity extends BaseActivity {

    TimePickerView pvTime;
    @Bind(R.id.id_jiaban_starttime_layout)
    RelativeLayout idJiabanStarttimeLayout;
    @Bind(R.id.id_jiaban_endtime_layout)
    RelativeLayout idJiabanEndtimeLayout;
    private int flag = 0;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_jiaban_starttime)
    TextView mJiabanStarttime;
    @Bind(R.id.id_jiaban_endtime)
    TextView mJiabanEndtime;
    @Bind(R.id.id_jiaban_reason)
    EditText mJiabanReason;
    @Bind(R.id.id_jiaban_commit)
    CardView mJiabanCommit;
    @Bind(R.id.content_ask_for_jiaban)
    LinearLayout rootview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ask_for_jiaban;
    }

    @Override
    public void initView() {


        toolbar.setTitle("加班申请");
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
                        mJiabanStarttime.setText(getTime(date));
                        break;
                    case 2:
                        mJiabanEndtime.setText(getTime(date));
                }
            }
        });
        RxView.clicks(mJiabanCommit).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        docommit();
                    }
                });
    }
    private void docommit() {
        boolean cancle = true;
        Date startdate = getStrToDate(mJiabanStarttime.getText().toString());
        Date enddate = getStrToDate(mJiabanEndtime.getText().toString());
        Date currentdate = new Date(System.currentTimeMillis());
        String curredate = AskForLeaveActivity.getTime(currentdate);
        Date CUDDATE = getStrToDate(curredate);
        String stime = AskForLeaveActivity.getTime(startdate);
        String etime = AskForLeaveActivity.getTime(enddate);
        /*if (mJiabanReason.getText().toString().equals("")) {
            Snackbar.make(rootview, "请填写加班内容!", Snackbar.LENGTH_SHORT).show();
            cancle = true;
            return;
        }else if (mJiabanStarttime.getText().toString().contains("请选择")
                ||mJiabanEndtime.getText().toString().contains("请选择")) {
            Snackbar.make(rootview, "请选择日期!", Snackbar.LENGTH_SHORT).show();
            cancle = true;
            return;
        }
        else if (!compareDate(startdate, enddate)) {
            Snackbar.make(rootview, "结束日期应该大于开始日期!", Snackbar.LENGTH_SHORT).show();
            cancle = true;
            return;
        } else if (!DateCompare(CUDDATE, enddate)) {
            Snackbar.make(rootview, "日期不能超过当前日期!", Snackbar.LENGTH_SHORT).show();
            cancle = true;
            return;
        }*/
        cancle = false;
        LogUtil.e("curredate:"+curredate);
        if (!cancle){
            app.apiService.upOverTime("overTimeApply",stime,
                    etime,"content"
                    ,"007",curredate,"11")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(String s) {
                            LogUtil.e("result:"+s);
                            if (s.contains("200")){
                                SomeUtil.showSnackBar(rootview,"提交申请成功！");
                            }else {
                                SomeUtil.showSnackBar(rootview,"网络错误！");
                            }
                        }
                    });




        }


        // TODO: 2016/8/5 提交加班内容！
        Snackbar.make(rootview, "正在提交!", Snackbar.LENGTH_SHORT).show();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pvTime.isShowing()) {
                pvTime.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public static boolean DateCompare(Date startdate, Date enddate) {
        if (Math.abs(((startdate.getTime() - enddate.getTime())/(24*3600*1000)))<0){
            return true;
        }
        else {
            return false;
        }
    }


    public  boolean compareDate(Date d1, Date d2) {
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
    public  Date getStrToDate(String str)  {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date  date = null;
            date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtil.e("DATE error"+e.toString());
            return null;
        }
    }


    @OnClick({R.id.id_jiaban_starttime_layout, R.id.id_jiaban_endtime_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_jiaban_starttime_layout:
                pvTime.show();
                flag = 1;
                break;
            case R.id.id_jiaban_endtime_layout:
                pvTime.show();
                flag = 2;
                break;

        }
    }

}