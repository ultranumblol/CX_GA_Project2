package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.MyRecyclerArrayAdapter;
import wgz.com.cx_ga_project.adapter.SICTypeAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.entity.SICType;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.com.cx_ga_project.view.Mylayout;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class SICTypeActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.sic_input_rv)
    EasyRecyclerView recyclerview;
    @Bind(R.id.content_sicinput)
    RelativeLayout rootview;
    @Bind(R.id.sic_type_progressbar)
    ProgressBar sicTypeProgressbar;

    private List<SICType.Re> typelist = new ArrayList<>();
    private SICTypeAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sictype;
    }

    @Override
    public void initView() {
        toolbar.setTitle("社会信息录入");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerview.setLayoutManager(new Mylayout(this));
        recyclerview.setAdapter(adapter = new SICTypeAdapter(this));


        app.apiService.getTypeOfSocialInfo().compose(RxUtil.<SICType>applySchedulers())
                .subscribe(new Subscriber<SICType>() {
                    @Override
                    public void onStart() {
                        sicTypeProgressbar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCompleted() {
                        sicTypeProgressbar.setVisibility(View.GONE);
                        adapter.addAll(typelist);
                    }

                    @Override
                    public void onError(Throwable e) {

                        LogUtil.d("SICType error: " + e.toString());
                        SomeUtil.checkHttpException(SICTypeActivity.this,e,rootview);
                    }

                    @Override
                    public void onNext(SICType sicType) {
                        LogUtil.d("SICType : " + sicType.getCode().toString());
                        LogUtil.d("SICType : " + sicType.getRes().toString());
                        typelist = sicType.getRes();

                    }
                });

        adapter.setOnItemClickListener(new MyRecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View itemView) {
                startActivity(new Intent(SICTypeActivity.this, SICInputActivity.class)
                        .putExtra("typeid", adapter.getItem(position).getMoudleclasscode())
                        .putExtra("typename", adapter.getItem(position).getMoudleclass())
                );


            }
        });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
