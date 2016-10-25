package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.JQAdapter;
import wgz.com.cx_ga_project.adapter.MyRecyclerArrayAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.entity.CallerInfo;
import wgz.com.cx_ga_project.entity.JQDetil;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SPUtils;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class JQListActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_jq_rv)
    EasyRecyclerView recyclerView;
    @Bind(R.id.content_jqlist)
    LinearLayout rootview;
    private JQAdapter adapter;
    private int page = 1;
    private String title = "";
    private List<String> list = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_jqlist;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        if (title.equals("nearjq")){
            toolbar.setTitle("附近历史警情");
        }else if (title.equals("bjr"))
            toolbar.setTitle("报警人关联警情");
        else if (title.equals("sjr"))
            toolbar.setTitle("涉警人关联警情");
        else if (title.equals("jqhistory"))
            toolbar.setTitle("历史警情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new JQAdapter(this));
        adapter.setNoMore(R.layout.view_nomore);
        adapter.setOnItemClickListener(new MyRecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View itemView) {
                startActivity(new Intent(JQListActivity.this, NewFightActivity.class));
            }
        });
        initdata();
    }

    private void initdata() {
        String latitude = (String) SPUtils.get(app.getApp().getApplicationContext(), Constant.LATITUDE, "111");
        String longitude = (String) SPUtils.get(app.getApp().getApplicationContext(), Constant.LONGITUDE, "1111");
        if (title.equals("nearjq")){
            //toolbar.setTitle("附近历史警情");
            //附近警情查询
            app.jqAPIService.getNearHisJq(longitude,latitude,page+"","30")
                    .compose(RxUtil.<JQDetil>applySchedulers())
                    .subscribe(new Subscriber<JQDetil>() {
                        @Override
                        public void onCompleted() {
                            list.add("1");
                            list.add("2");
                            list.add("3");
                            adapter.addAll(list);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(JQDetil jqDetil) {
                           // String url = appVersion.getRes().get(0).getApkUrl();
                            //url.replaceAll("\\\\","");
                        }
                    });


        }else if (title.equals("bjr")){

            //报警人警情查询
            app.jqAPIService.getCallerInfo("秦晓梅")
                    .compose(RxUtil.<CallerInfo>applySchedulers())
                    .subscribe(new Subscriber<CallerInfo>() {
                        @Override
                        public void onCompleted() {
                            list.add("1");
                            list.add("2");
                            list.add("3");
                            adapter.addAll(list);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(CallerInfo callerInfo) {
                            LogUtil.d("callerinfo resutl : "+callerInfo.toString());
                        }
                    });
        }
            //toolbar.setTitle("报警人关联警情");
        else if (title.equals("sjr")){
            //涉警人关联警情
            app.jqAPIService.getPoliceJqInfo(SomeUtil.getUserId())
                    .compose(RxUtil.<String>applySchedulers())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            list.add("1");
                            list.add("2");
                            list.add("3");
                            adapter.addAll(list);
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.d("getPoliceJqInfo error: "+e.toString());
                        }

                        @Override
                        public void onNext(String s) {
                            LogUtil.d("getPoliceJqInfo result: "+s.toString());
                        }
                    });
        }
        else if (title.equals("jqhistory")){
            //涉警人关联警情
            app.jqAPIService.getPoliceJqInfo(SomeUtil.getUserId())
                    .compose(RxUtil.<String>applySchedulers())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            list.add("1");
                            list.add("2");
                            list.add("3");
                            adapter.addAll(list);
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.d("getPoliceJqInfo error: "+e.toString());
                        }

                        @Override
                        public void onNext(String s) {
                            LogUtil.d("getPoliceJqInfo result: "+s.toString());
                        }
                    });
        }


    }


}
