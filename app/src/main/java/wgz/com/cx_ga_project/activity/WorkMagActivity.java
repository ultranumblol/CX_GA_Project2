package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.util.SomeUtil;

/**
 * 工作管理
 * Created by wgz on 2016/8/3.
 */
public class WorkMagActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_work_mag)
    ConstraintLayout contentWorkMag;
    @Bind(R.id.id_myscheduling)
    CardView idMyscheduling;
    @Bind(R.id.id_toWorkLog)
    CardView idToWorkLog;
    @Bind(R.id.id_myApply)
    CardView idMyShenqing;
    @Bind(R.id.id_addwork)
    CardView idAddwork;
    @Bind(R.id.id_qingjia)
    CardView idQingjia;
    @Bind(R.id.id_shenhe)
    CardView idShenhe;
    @Bind(R.id.id_xiashu)
    CardView idXiashu;

    @Override
    public int getLayoutId() {
        return R.layout.activity_work_mag;
    }

    @Override
    public void initView() {
        toolbar.setTitle("工作管理");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick({R.id.id_myApply, R.id.id_shenhe,
            R.id.id_addwork, R.id.id_qingjia,
            R.id.id_myscheduling, R.id.id_toWorkLog,R.id.id_xiashu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_myscheduling:
                startActivity(new Intent(WorkMagActivity.this, SchedulingActivity.class));
                break;
            case R.id.id_toWorkLog:
                startActivity(new Intent(WorkMagActivity.this, WorkLogActivity.class));
                break;
            case R.id.id_myApply:
                startActivity(new Intent(WorkMagActivity.this, MyWorkApplyActivity.class));
                break;
            case R.id.id_addwork:

                startActivity(new Intent(WorkMagActivity.this, AskForJiabanActivity.class));
                break;
            case R.id.id_qingjia:
                startActivity(new Intent(WorkMagActivity.this, AskForLeaveActivity.class));
                break;
            case R.id.id_shenhe:
                startActivity(new Intent(WorkMagActivity.this,MyApprovalActivity.class));

                break;
            case R.id.id_xiashu:
                startActivity(new Intent(WorkMagActivity.this, MySubordinateActivity.class));
                break;
        }
    }

}
