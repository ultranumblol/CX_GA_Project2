package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.lzp.floatingactionbuttonplus.FabTagLayout;
import com.lzp.floatingactionbuttonplus.FloatingActionButtonPlus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.JQCallbackDetilAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.entity.JqCallBack;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * 警情回告
 */
public class JQCallbackActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_jqcallback)
    NestedScrollView rootview;
    @Bind(R.id.FabPlus)
    FloatingActionButtonPlus FabPlus;
    @Bind(R.id.sjr_rv)
    EasyRecyclerView sjrRv;
    @Bind(R.id.sjCar_rv)
    EasyRecyclerView sjCarRv;
    @Bind(R.id.sjPhone_rv)
    EasyRecyclerView sjPhoneRv;
    @Bind(R.id.callbackContent_rv)
    EasyRecyclerView callbackContentRv;
    private JQCallbackDetilAdapter adapter;
    private List<String> mdata = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_jqcallback;
    }

    @Override
    public void initView() {
        toolbar.setTitle("警情回告");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FabPlus.setOnItemClickListener(new FloatingActionButtonPlus.OnItemClickListener() {
            @Override
            public void onItemClick(FabTagLayout tagView, int position) {
                int id = tagView.getId();
                switch (id) {
                    case R.id.fabtag_addsjcar:
                        startActivity(new Intent(JQCallbackActivity.this, UpLoadSJCarActivity.class));

                        break;
                    case R.id.fabtag_addsjPeople:
                        startActivity(new Intent(JQCallbackActivity.this, SJPeopleActivity.class));

                        break;
                    case R.id.fabtag_addsjPhone:
                        startActivity(new Intent(JQCallbackActivity.this, UpLoadSJPhoneActivity.class));
                        break;
                    case R.id.fabtag_addjqMsg:
                        startActivity(new Intent(JQCallbackActivity.this, AddJQActivity.class));
                        break;
                }
            }
        });
        sjCarRv.setLayoutManager(new LinearLayoutManager(this));
        sjPhoneRv.setLayoutManager(new LinearLayoutManager(this));
        sjrRv.setLayoutManager(new LinearLayoutManager(this));
        callbackContentRv.setLayoutManager(new LinearLayoutManager(this));

        sjCarRv.setAdapter(adapter = new JQCallbackDetilAdapter(this));
        sjPhoneRv.setAdapter(adapter);
        sjrRv.setAdapter(adapter);
        callbackContentRv.setAdapter(adapter);

        adapter.addAll(initData());




    }

    private List<String> initData() {
        /*for (int i = 0 ; i<5 ; i++){
            mdata.add(i+"");

        }*/
        app.jqAPIService.GetAllJQDetil("2016072100100000060")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JqCallBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("jqcallback error : "+e.toString());
                    }

                    @Override
                    public void onNext(JqCallBack jqCallBack) {
                        LogUtil.e("jqcallback getRescar : "+jqCallBack.getRescar().toString() );
                        LogUtil.e("jqcallback getRespeople : "+jqCallBack.getRespeople().toString() );
                        LogUtil.e("jqcallback getResphone : "+jqCallBack.getResphone().toString() );
                        LogUtil.e("jqcallback getResreport : "+jqCallBack.getResreport().toString() );
                    }
                });


        return mdata;
    }


}
