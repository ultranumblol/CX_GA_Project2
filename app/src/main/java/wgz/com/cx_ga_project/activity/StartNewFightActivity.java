package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.JQAdapter;
import wgz.com.cx_ga_project.adapter.MyRecyclerArrayAdapter;
import wgz.com.cx_ga_project.base.BaseActivity;

/**
 * 开始新作战任务
 * Created by wgz on 2016/8/15.
 */
public class StartNewFightActivity extends BaseActivity {


    @Bind(R.id.id_newjqRv)
    EasyRecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_start_new_fight)
    ConstraintLayout rootview;
    private JQAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_start_new_fight;
    }

    @Override
    public void initView() {

        toolbar.setTitle("警情信息");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new JQAdapter(this));
        adapter.setNoMore(R.layout.view_nomore);

        adapter.setOnItemClickListener(new MyRecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View itemView) {
                startActivity(new Intent(StartNewFightActivity.this, NewFightActivity.class));
            }
        });
        initdata();
        adapter.addAll(list);


    }

    private void initdata() {
        list.add("1");
        list.add("2");
        list.add("3");

    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.jqhistory, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.jq_history) {
            SomeUtil.showSnackBar(rootview, "开发中。。。");
            return true;
        }*/
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
