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

public class FightActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_fight)
    ConstraintLayout rootView;
    @Bind(R.id.id_fight_upload)
    CardView idFightUpload;
    @Bind(R.id.id_fight_bulu)
    CardView idFightBulu;
    @Bind(R.id.id_fight_nearlyJQ)
    CardView idFightNearlyJQ;
    @Bind(R.id.id_fight_BJR)
    CardView idFightBJR;
    @Bind(R.id.id_fight_SJR)
    CardView idFightSJR;
    @Bind(R.id.id_fight_talk)
    CardView idFightTalk;
    @Bind(R.id.id_fight_nearlyCamera)
    CardView idFightNearlyCamera;
    @Bind(R.id.id_fight_way)
    CardView idFightWay;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fight;
    }

    @Override
    public void initView() {
        toolbar.setTitle("接处警作战");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick({R.id.id_fight_upload, R.id.id_fight_bulu, R.id.id_fight_nearlyJQ, R.id.id_fight_BJR, R.id.id_fight_SJR, R.id.id_fight_talk, R.id.id_fight_nearlyCamera, R.id.id_fight_way})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_fight_upload:
                startActivity(new Intent(FightActivity.this,AddJQActivity.class));

                break;
            case R.id.id_fight_bulu:
                // TODO: 2016/8/16 警情补录功能
                startActivity(new Intent(FightActivity.this,AddJQActivity.class));
                break;
            case R.id.id_fight_nearlyJQ:
                startActivity(new Intent(FightActivity.this,NewFightActivity.class));
                // TODO: 2016/8/16 附近警情
                break;
            case R.id.id_fight_BJR:
                // TODO: 2016/8/16 报警人关联警情
                startActivity(new Intent(FightActivity.this,ScrollingActivity.class));

                break;
            case R.id.id_fight_SJR:
                // TODO: 2016/8/16 涉警人关联警情
                break;
            case R.id.id_fight_talk:
                // TODO: 2016/8/16 指挥通讯
                break;
            case R.id.id_fight_nearlyCamera:
                // TODO: 2016/8/16  附近监控
                break;
            case R.id.id_fight_way:
                // TODO: 2016/8/16 作战轨迹
                break;
        }
    }
}
