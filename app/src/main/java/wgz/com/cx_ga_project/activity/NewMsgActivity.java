package wgz.com.cx_ga_project.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;

public class NewMsgActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_newmsg_lv)
    EasyRecyclerView idNewmsgLv;
    @Bind(R.id.content_new_msg)
    LinearLayout rootview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_msg;
    }

    @Override
    public void initView() {

    }

}
