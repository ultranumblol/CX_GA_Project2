package wgz.com.cx_ga_project.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.TimeBankAdapter;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.view.Mylayout;

public class TimeBankActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.timebank_rv)
    EasyRecyclerView recyclerView;
    @Bind(R.id.content_time_bank)
    RelativeLayout rootview;
    private TimeBankAdapter adapter;
    private List<String > list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_time_bank;
    }

    @Override
    public void initView() {
        toolbar.setTitle("时间银行");
        setToolbarBack(toolbar);
        recyclerView.setLayoutManager(new Mylayout(this));
        recyclerView.setAdapter(adapter = new TimeBankAdapter(this));

        for (int i = 0 ; i<6 ; i++){
            list.add("123");

        }
        adapter.addAll(list);


    }

}
