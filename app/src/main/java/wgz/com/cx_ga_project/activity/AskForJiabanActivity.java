package wgz.com.cx_ga_project.activity;

import android.content.Intent;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import com.jakewharton.rxbinding.view.RxView;

import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.entity.Subordinate;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SPUtils;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;


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
    private List<Subordinate.Resup> list = new ArrayList<>();
    private Date mstartdate = new Date();
    private Date menddate = new Date();

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
                        mstartdate = date;
                        break;
                    case 2:
                        mJiabanEndtime.setText(getTime(date));
                        menddate = date;
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
        initData();
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
                        SomeUtil.checkHttpException(AskForJiabanActivity.this,e,rootview);
                    }

                    @Override
                    public void onNext(Subordinate s) {
                        LogUtil.d("Subordinate : "+s.getResup().toString());
                        list = s.getResup();

                    }
                });

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
    private void docommit() {
        boolean cancle = false;

        Date currentdate = new Date(System.currentTimeMillis());


        String curredate = getTime(currentdate);
        String stime = getTime(mstartdate);
        String etime = getTime(menddate);

        LogUtil.d("stime :"+mstartdate.getTime());
        LogUtil.d("etime :"+menddate.getTime());
        LogUtil.d("curredate :"+System.currentTimeMillis());

        if (mJiabanReason.getText().toString().equals("")) {
            Snackbar.make(rootview, "请填写加班内容!", Snackbar.LENGTH_SHORT).show();
            cancle = true;
            return;
        }else if (mJiabanStarttime.getText().toString().equals("")
                ||mJiabanEndtime.getText().toString().equals(""))
        {
            Snackbar.make(rootview, "请选择日期！", Snackbar.LENGTH_SHORT).show();
            cancle = true;
            return;
        }else if (mstartdate.getTime()-menddate.getTime()>=0){
            Snackbar.make(rootview, "结束日期应大于开始日期!", Snackbar.LENGTH_SHORT).show();
            cancle = true;
            return;
        }else if (mstartdate.getTime()-System.currentTimeMillis()>0||menddate.getTime()-System.currentTimeMillis()>0){
            Snackbar.make(rootview, "日期不能超过当前日期!", Snackbar.LENGTH_SHORT).show();
            cancle = true;
            return;
        }
        cancle = false;
        LogUtil.d("curredate:"+curredate);
        if (!cancle){
            app.apiService.upOverTime("overTimeApply",stime,
                    etime,mJiabanReason.getText().toString()
                    ,(String) SPUtils.get(app.getApp().getApplicationContext(), Constant.USERID,""),curredate,list.get(0).getPolid())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.d("result error:"+e.toString());
                            SomeUtil.checkHttpException(AskForJiabanActivity.this,e,rootview);
                        }

                        @Override
                        public void onNext(String s) {
                            LogUtil.d("result:"+s);
                            if (s.contains("200")){
                                RxBus.getDefault().post("jiabanflush");
                                SomeUtil.showSnackBar(rootview,"提交申请成功！").setCallback(new Snackbar.Callback() {
                                    @Override
                                    public void onDismissed(Snackbar snackbar, int event) {
                                       // setResult(1001,new Intent(AskForJiabanActivity.this,MyWorkApplyActivity.class).putExtra("result","refresh"));
                                        finish();
                                    }
                                });

                            }else {
                                SomeUtil.showSnackBar(rootview,"网络错误！");
                            }
                        }
                    });
        }
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





    /**
     * yyyy-MM-dd HH:mm"
     * @param str
     * @return
     */
    public  Date getStrToDate(String str)  {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date  date = null;
            date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtil.d("DATE error"+e.toString());
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