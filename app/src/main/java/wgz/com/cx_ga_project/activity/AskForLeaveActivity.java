package wgz.com.cx_ga_project.activity;

import android.content.Intent;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.bean.AskForLeaveBean;
import wgz.com.cx_ga_project.entity.LeaveType;
import wgz.com.cx_ga_project.entity.Subordinate;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SPUtils;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.com.cx_ga_project.util.TimeUtils;
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
    private List<LeaveType.LeaveTypeRe> leaveTypeRes = new ArrayList<>();
    private String valueCode = "0";


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
    private List<Subordinate.Resup> list = new ArrayList<>();
    private Date mstartdate = new Date();
    private Date menddate = new Date();

    @Override
    public int getLayoutId() {
        return R.layout.activity_ask_for_leave;
    }

    @Override
    public void initView() {
        toolbar.setTitle("请假申请");
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
                        mstartdate = date;
                        if (menddate!=null){
                            mLeaveDaycount.setText(TimeUtils.getGapCount(mstartdate,menddate)+"");
                        }
                        break;
                    case 2:
                        mLeaveEndtime.setText(getTime(date));
                        menddate = date;
                        if (mstartdate!=null){
                            mLeaveDaycount.setText(TimeUtils.getGapCount(mstartdate,menddate)+"");

                        }
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

        app.apiService.getLeaveType()
                .compose(RxUtil.<LeaveType>applySchedulers())
                .subscribe(new Subscriber<LeaveType>() {
                    @Override
                    public void onCompleted() {
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
                                //LogUtil.d("leaveTypeRes : "+leaveTypeRes.toString());
                              for (int i = 0 ; i <leaveTypeRes.size();i++)
                                  if (leaveTypeRes.get(i).getValname().equals(result)){

                                      valueCode = leaveTypeRes.get(i).getValcode();
                                  }

                                LogUtil.d("valuecode : "+valueCode);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("leavetype  error: "+e.toString());
                    }

                    @Override
                    public void onNext(LeaveType leaveType) {
                        leaveTypeRes.clear();
                        leaveTypeRes = leaveType.getRes();
                        LogUtil.d("leavetype : "+leaveType.getRes().toString());
                        ArrayList<String> options2Items_01 = new ArrayList<>();
                        for (int i = 0 ; i <leaveType.getRes().size(); i++){
                            options2Items_01.add(leaveType.getRes().get(i).getValname());
                        }
                        options2Items.add(options2Items_01);
                    }
                });
        initData();
    }

    public  String getTime(Date date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void initData() {
        app.apiService.getSupAndSub(SomeUtil.getUserId())
                .compose(RxUtil.<Subordinate>applySchedulers())
                .subscribe(new Subscriber<Subordinate>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("Subordinate : "+e.toString());
                    }

                    @Override
                    public void onNext(Subordinate s) {
                        LogUtil.d("Subordinate : "+s.getResup().toString());
                        list = s.getResup();

                    }
                });

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

        String curredate = getTime(currentdate);
        String stime = getTime(startdate);
        String etime = getTime(enddate);
        if (mLeaveReason.getText().toString().equals("")) {
            Snackbar.make(rootview, "请填写请假事由!", Snackbar.LENGTH_SHORT).show();
            return;
        } else if (mLeaveStarttime.getText().toString().equals("")
                || mLeaveEndtime.getText().toString().equals("")) {
            Snackbar.make(rootview, "请选择日期!", Snackbar.LENGTH_SHORT).show();
            return;
        } else if (mLeaveType.getText().toString().equals("")) {
            Snackbar.make(rootview, "请选择请假类型！", Snackbar.LENGTH_SHORT).show();
            return;
        } else if (SomeUtil.DateCompare(stime, etime)) {
            Snackbar.make(rootview, "结束日期应该大于开始日期!", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // TODO: 2016/8/5 提交请假申请
        app.apiService.upLoadLeave("leaveApply", stime, etime, mLeaveReason.getText().toString(),
                SomeUtil.getUserId(), curredate, list.get(0).getPolid(),valueCode, mLeaveDaycount.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.d("leaveApply :" +s);
                        if (s.contains("200")) {
                            RxBus.getDefault().post("qingjiaflush");
                            SomeUtil.showSnackBar(rootview, "提交申请成功！").setCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    //setResult(1001,new Intent(AskForLeaveActivity.this,MyWorkApplyActivity.class).putExtra("result","refresh"));
                                    finish();
                                }
                            });

                         } else {
                            SomeUtil.showSnackBar(rootview, "服务器错误！");
                        }
                    }
                });
    }



    public Date getStrToDate(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtil.d("DATE error" + e.toString());
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
