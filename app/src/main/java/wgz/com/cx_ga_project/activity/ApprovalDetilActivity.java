package wgz.com.cx_ga_project.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.util.SomeUtil;

import static wgz.com.cx_ga_project.base.Constant.APPROVAL_PASS;
import static wgz.com.cx_ga_project.base.Constant.APPROVAL_UNPASS;
import static wgz.com.cx_ga_project.base.Constant.UNAPPROVAL;

/**
 * 申请明细
 * Created by wgz on 2016/8/9.
 */

public class ApprovalDetilActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.detil_leave_committime)
    TextView detilLeaveCommittime;
    @Bind(R.id.detil_leave_starttime)
    TextView detilLeaveStarttime;
    @Bind(R.id.detil_leave_endtime)
    TextView detilLeaveEndtime;
    @Bind(R.id.detil_leave_dayscount)
    TextView detilLeaveDayscount;
    @Bind(R.id.detil_leave_type)
    TextView detilLeaveType;
    @Bind(R.id.detil_leave_reason)
    TextView detilLeaveReason;
    @Bind(R.id.detil_jiaban_committime)
    TextView detilJiabanCommittime;
    @Bind(R.id.detil_jiaban_starttime)
    TextView detilJiabanStarttime;
    @Bind(R.id.detil_jiaban_endtime)
    TextView detilJiabanEndtime;
    @Bind(R.id.detil_jiaban_reason)
    TextView detilJiabanReason;
    @Bind(R.id.detil_jiaban_shenherenname)
    TextView detilJiabanShenherenname;
    @Bind(R.id.content_jiaban_leave_detil)
    LinearLayout rootview;
    @Bind(R.id.jiabanLeave_detil_qingjia)
    LinearLayout jiabanLeaveDetilQingjia;
    @Bind(R.id.jiabanLeave_detil_jiaban)
    LinearLayout jiabanLeaveDetilJiaban;
    @Bind(R.id.detil_qingjia_shenherenname)
    TextView detilQingjiaShenherenname;
    @Bind(R.id.userPic)
    ImageView userPic;
    @Bind(R.id.userPic_jiaban)
    ImageView userPicJiaban;
    @Bind(R.id.detil_qingjia_state)
    TextView detilQingjiaState;
    @Bind(R.id.detil_jiaban_state)
    TextView detilJiabanState;
    @Bind(R.id.approval_makesrue)
    CardView approvalMakesrue;
    @Bind(R.id.detil_root)
    CoordinatorLayout detilRoot;
    @Bind(R.id.userName)
    TextView userName;
    @Bind(R.id.userName_jiaban)
    TextView userNameJiaban;

    @Override
    public int getLayoutId() {
        return R.layout.activity_jiaban_leave_detil;
    }

    @Override
    public void initView() {

        Intent intent = getIntent();
        int type = intent.getIntExtra("type",-1);
        Bundle bundle = intent.getBundleExtra("detil");
        approvalMakesrue.setVisibility(View.VISIBLE);
        switch (type) {
            case 0:
                toolbar.setTitle("加班明细");
                jiabanLeaveDetilJiaban.setVisibility(View.VISIBLE);
                ViewCompat.setTransitionName(userPicJiaban, "share_img");
                jiabanLeaveDetilQingjia.setVisibility(View.GONE);
                jiabanLeaveDetilQingjia.setVisibility(View.GONE);
                userNameJiaban.setText(bundle.getString("poiceid"));
                detilJiabanCommittime.setText(bundle.getString("applytime"));
                detilJiabanStarttime.setText(bundle.getString("starttime"));
                detilJiabanEndtime.setText(bundle.getString("endtime"));
                detilJiabanReason.setText(bundle.getString("content"));
                detilJiabanShenherenname.setText(bundle.getString("upperid"));
                detilJiabanState.setText(bundle.getString("status"));
                if (bundle.getString("status").equals(UNAPPROVAL))
                    detilJiabanState.setText("未审批");
                else if (bundle.getString("status").equals(APPROVAL_PASS))
                    detilJiabanState.setText("审批通过");
                else if (bundle.getString("status").equals(APPROVAL_UNPASS))
                    detilJiabanState.setText("审批未通过");

                break;
            case 1:
                toolbar.setTitle("请假明细");
                jiabanLeaveDetilJiaban.setVisibility(View.GONE);
                ViewCompat.setTransitionName(userPic, "share_img");
                jiabanLeaveDetilQingjia.setVisibility(View.VISIBLE);
                userName.setText(bundle.getString("poiceid"));
                detilLeaveCommittime.setText(bundle.getString("applytime"));
                detilLeaveStarttime.setText(bundle.getString("starttime"));
                detilLeaveEndtime.setText(bundle.getString("endtime"));
                detilLeaveDayscount.setText(bundle.getString("days"));
                detilLeaveReason.setText(bundle.getString("content"));
                detilLeaveType.setText(bundle.getString("reasontype"));
                detilQingjiaShenherenname.setText(bundle.getString("upperid"));
                detilQingjiaState.setText(bundle.getString("status"));
                if (bundle.getString("status").equals(UNAPPROVAL))
                    detilQingjiaState.setText("未审批");
                else if (bundle.getString("status").equals(APPROVAL_PASS))
                    detilQingjiaState.setText("审批通过");
                else if (bundle.getString("status").equals(APPROVAL_UNPASS))
                    detilQingjiaState.setText("审批未通过");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RxView.clicks(approvalMakesrue)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        showAlert();
                    }
                });


    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请确认")
                .setPositiveButton("审核通过", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SomeUtil.showSnackBar(detilRoot, "审批通过！").setCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                finish();
                            }
                        }).show();
                    }
                }).setNegativeButton("拒绝申请", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                whyRefuse();
            }
        }).show();


    }

    private void whyRefuse() {
        EditText input = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入未通过原因")
                .setView(input)
                .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SomeUtil.showSnackBar(detilRoot, "提交成功").show();
                    }
                }).setNegativeButton("取消", null).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
