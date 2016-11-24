package wgz.com.cx_ga_project.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.MyRecyclerArrayAdapter;
import wgz.com.cx_ga_project.adapter.SubordinateAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.entity.Subordinate;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * 我的下属
 */
public class MySubordinateActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_recycView)
    EasyRecyclerView recyclerView;
    @Bind(R.id.content_my_subordinate)
    ConstraintLayout contentMySubordinate;
    private SubordinateAdapter adapter;
    private List<Subordinate.Resdown> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_subordinate;
    }

    @Override
    public void initView() {
        toolbar.setTitle("我的下属");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubordinateAdapter(this);
        recyclerView.setAdapter(adapter);
        //adapter.setNoMore(R.layout.view_nomore);
        adapter.setOnItemClickListener((position, itemView) -> {
            final TextView policeidview = (TextView) itemView.findViewById(R.id.subordinate_zhiwu);
            final TextView policeidname = (TextView) itemView.findViewById(R.id.subordinate_name);
            String[] titles = new String[]{"时间银行", "工作日志", "工作云标签"};
            int item = -1;
            final AlertDialog.Builder builder = new AlertDialog.Builder(MySubordinateActivity.this);

            builder.setTitle("请选择要查看下属的内容")
                    .setItems(titles, (dialog, which) -> {
                        switch (which) {
                            case 0:

                                startActivity(new Intent(MySubordinateActivity.this, TimeBankActivity.class)
                                        .putExtra("policeid", policeidview.getText().toString())
                                        .putExtra("policename", policeidname.getText().toString()));


                                break;
                            case 1:

                                startActivity(new Intent(MySubordinateActivity.this, MySubordinateLogAcitvity.class)
                                        .putExtra("policeid", policeidview.getText().toString())
                                        .putExtra("policename", policeidname.getText().toString()));

                                break;
                            case 2:
                                startActivity(new Intent(MySubordinateActivity.this, WorkLogCloudActivity.class)
                                        .putExtra("policeid", policeidview.getText().toString())
                                        .putExtra("policename", policeidname.getText().toString()));

                                break;

                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();


        });
        initData();

    }

    private void initData() {

        app.apiService.getSupAndSub(SomeUtil.getUserId())
                .compose(RxUtil.<Subordinate>applySchedulers())
                .subscribe(new Subscriber<Subordinate>() {
                    @Override
                    public void onCompleted() {
                        adapter.addAll(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("Subordinate : " + e.toString());
                    }

                    @Override
                    public void onNext(Subordinate s) {
                        LogUtil.d("Subordinate : " + s.toString());
                        list = s.getResdown();

                    }
                });

    }

}
