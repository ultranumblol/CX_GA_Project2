package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;

import static wgz.com.cx_ga_project.base.Constant.APPROVAL_PASS;
import static wgz.com.cx_ga_project.base.Constant.APPROVAL_UNPASS;
import static wgz.com.cx_ga_project.base.Constant.UNAPPROVAL;

/*
* 加班和请假明细
* */
public class JiabanLeaveDetilActivity extends BaseActivity {

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
        String type = intent.getStringExtra("type");
        Bundle bundle = intent.getBundleExtra("detil");
        switch (type) {
            case "0":
                toolbar.setTitle("加班明细");
                jiabanLeaveDetilJiaban.setVisibility(View.VISIBLE);
                ViewCompat.setTransitionName(userPicJiaban, "share_img");
                jiabanLeaveDetilQingjia.setVisibility(View.GONE);
                userNameJiaban.setText(bundle.getString("poiceid"));
                detilJiabanCommittime.setText(bundle.getString("applytime"));
                detilJiabanStarttime.setText(bundle.getString("starttime"));
                detilJiabanEndtime.setText(bundle.getString("endtime"));
                detilJiabanReason.setText(bundle.getString("content"));
                detilJiabanShenherenname.setText(bundle.getString("upperid"));
                if (bundle.getString("status").equals(UNAPPROVAL))
                    detilJiabanState.setText("未审批");
                else if (bundle.getString("status").equals(APPROVAL_PASS))
                    detilJiabanState.setText("审批通过");
                else if (bundle.getString("status").equals(APPROVAL_UNPASS))
                    detilJiabanState.setText("审批未通过");


                break;
            case "1":
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
    }

}
