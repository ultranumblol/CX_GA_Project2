package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.MyRecyclerArrayAdapter;
import wgz.com.cx_ga_project.adapter.SICInputLogAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.bean.progress;
import wgz.com.cx_ga_project.entity.SICList;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.com.cx_ga_project.view.Mylayout;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class SICInputLogActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.sicinputlog_rv)
    EasyRecyclerView sicinputlogRv;
    @Bind(R.id.content_sicinput_log)
    RelativeLayout rootview;
    private SICInputLogAdapter adapter;
    private List<SICList.SICListRe> list = new ArrayList<>();
    private Subscription rxSubscription;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sicinput_log;
    }

    @Override
    public void initView() {
        toolbar.setTitle("采集历史");
        setToolbarBack(toolbar);
        sicinputlogRv.setLayoutManager(new Mylayout(this));
        sicinputlogRv.setAdapter(adapter = new SICInputLogAdapter(this));
        initData();
        adapter.setOnItemClickListener((position, itemView) ->
                startActivity(new Intent(SICInputLogActivity.this,SICLogDetilActivity.class)
        .putExtra("type",adapter.getItem(position).getType())
                .putExtra("docid",adapter.getItem(position).getDocid())
        ));

        rxSubscription = RxBus.getDefault().toObservable(String.class)
                .subscribe(s -> {
                 if (s.equals("flushsiclog")){
                     initData();

                 }

                });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }
    private void initData() {
        app.apiService.getSocialInfoList(SomeUtil.getUserId())
        .compose(RxUtil.applySchedulers())
        .subscribe(new Subscriber<SICList>() {
            @Override
            public void onStart() {
                adapter.clear();
                list.clear();
            }

            @Override
            public void onCompleted() {
                    adapter.addAll(list);
            }

            @Override
            public void onError(Throwable e) {
               // SomeUtil.checkHttpException(SICInputLogActivity.this,e,rootview);
            }

            @Override
            public void onNext(SICList s) {
                LogUtil.d("result : "+s.getRes().toString());
                list = s.getRes();
            }
        });


    }

}
