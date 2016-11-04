package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;

import org.w3c.dom.Text;

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
import wgz.com.cx_ga_project.entity.NearJQ;
import wgz.com.cx_ga_project.entity.NewJQ;
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
    private List<CallerInfo.Resjq> bjrdata = new ArrayList<>();
    private List<NearJQ.Re> sjrdata = new ArrayList<>();
    private List<NearJQ.Re> nearjqdata = new ArrayList<>();
    private List<NewJQ.NewjqRe> jqhisdate = new ArrayList<>();
    private String baojingrenname = "";
    @Override
    public int getLayoutId() {
        return R.layout.activity_jqlist;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        baojingrenname = intent.getStringExtra("bjrname");
        if (title.equals("nearjq")){
            toolbar.setTitle("附近历史警情");
        }else if (title.equals("bjr"))
            toolbar.setTitle(baojingrenname+"的关联警情");
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
                TextView jqidview = (TextView) itemView.findViewById(R.id.jqid);
                String jqid = jqidview.getText().toString();
                startActivity(new Intent(JQListActivity.this, NewFightActivity.class)
                        .putExtra("jqid",jqid)
                        .putExtra("jqlist",true));
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
                    .compose(RxUtil.<NearJQ>applySchedulers())
                    .subscribe(new Subscriber<NearJQ>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(NearJQ jqDetil) {
                            LogUtil.d("nearjq : "+jqDetil);
                            nearjqdata  = jqDetil.getRes();
                            adapter.addAll(nearjqdata);
                           /*// String url = appVersion.getRes().get(0).getApkUrl();
                            //url.replaceAll("\\\\","");
                            nearjqdata  = jqDetil.getResult();
                            adapter.addAll(nearjqdata);
                            LogUtil.d("nearjq : "+jqDetil.getResult().toString());
                            LogUtil.d("nearjq code: "+jqDetil.getCode());*/
                        }
                    });


        }else if (title.equals("bjr")){

            //报警人警情查询
            app.jqAPIService.getCallerInfo(baojingrenname)
                    .compose(RxUtil.<CallerInfo>applySchedulers())
                    .subscribe(new Subscriber<CallerInfo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(CallerInfo callerInfo) {
                            LogUtil.d("callerinfo resutl : "+callerInfo.getCode());
                            bjrdata = callerInfo.getResjq();
                            adapter.addAll(bjrdata);
                        }
                    });
        }

        else if (title.equals("sjr")){
            //涉警人关联警情
            app.jqAPIService.getPoliceJqInfo(SomeUtil.getUserId())
                    .compose(RxUtil.<NearJQ>applySchedulers())
                    .subscribe(new Subscriber<NearJQ>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.d("getPoliceJqInfo error: "+e.toString());
                        }

                        @Override
                        public void onNext(NearJQ s) {
                            sjrdata = s.getRes();
                            adapter.addAll(sjrdata);

                        }
                    });
        }
        else if (title.equals("jqhistory")){
            //历史警情
            app.jqAPIService.getOldJqlist(SomeUtil.getUserId())
            .compose(RxUtil.<NewJQ>applySchedulers())
            .subscribe(new Subscriber<NewJQ>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(NewJQ newJQ) {
                    jqhisdate = newJQ.getRes();
                    LogUtil.d("jqhistory result :"+newJQ.getRes().toString());
                    adapter.addAll(jqhisdate);
                }
            });
        }


    }


}
