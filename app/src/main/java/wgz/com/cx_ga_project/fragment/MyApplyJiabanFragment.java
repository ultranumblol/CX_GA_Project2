package wgz.com.cx_ga_project.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.activity.JiabanLeaveDetilActivity;

import wgz.com.cx_ga_project.adapter.ApplyAdapter;

import wgz.com.cx_ga_project.adapter.MyRecyclerArrayAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseFragment;

import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.entity.Apply;
import wgz.com.cx_ga_project.util.SPUtils;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static wgz.com.cx_ga_project.base.Constant.TYPE_JIABAN;


/**
 * 查看加班申请
 * A simple {@link Fragment} subclass.
 */
public class MyApplyJiabanFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.id_myapply_jiaban_lv2)
    EasyRecyclerView recyclerView;
    ApplyAdapter adapter;
    List<Apply.Result> list = new ArrayList<Apply.Result>();
    private Handler handler = new Handler();
    private Subscription rxSubscription;

    @Override
    public void initview(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapterWithProgress(adapter = new ApplyAdapter(getActivity()));
       /* adapter.setMore(R.layout.view_more, new MyRecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 2000);
            }
        });*/
        //adapter.setNoMore(R.layout.view_nomore);
        adapter.setOnItemClickListener(new MyRecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View itemView) {
                //ToastUtil.showShort(getActivity(),"cilck:"+position);
                ImageView im_face = (ImageView) itemView.findViewById(R.id.user_face);
                Intent intent = new Intent();
                intent.setClass(getActivity(), JiabanLeaveDetilActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("poiceid",adapter.getItem(position).getPoliceid());
                bundle.putString("poicename",adapter.getItem(position).getPolicename());

                bundle.putString("applytime",adapter.getItem(position).getApplytime());
                bundle.putString("starttime",adapter.getItem(position).getStart());
                bundle.putString("endtime",adapter.getItem(position).getEnd());

                //bundle.putString("days",adapter.getItem(position).getDays()+"");
                bundle.putString("content",adapter.getItem(position).getContent());
                bundle.putString("status",adapter.getItem(position).getStatus());
                bundle.putString("upperid",adapter.getItem(position).getUpperid());
                bundle.putString("head","http://"+adapter.getItem(position).getUrl());
                //bundle.putString("reasontype",adapter.getItem(position).getReasontype());
                intent.putExtra("detil",bundle);
                intent.putExtra("type",adapter.getItem(position).getType());

                ActivityCompat.startActivityForResult(getActivity(),
                        intent,1005, ActivityOptionsCompat
                                .makeSceneTransitionAnimation(getActivity(),
                                        im_face, "share_img").toBundle());

            }
        });

        recyclerView.setRefreshListener(this);
        initData();
        rxSubscription = RxBus.getDefault().toObservable(String.class)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (s.equals("jiabanflush"))
                        onRefresh();
                    }
                });

    }
    @Override
    public int getLayoutitem() {
        return R.layout.fragment_my_apply_jiaban;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                adapter.clear();
               initData();
            }
        }, 1500);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        app.apiService.getBeanData("getOverLeaveStatus",(String) SPUtils.get(app.getApp().getApplicationContext(), Constant.USERID,""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Apply, List<Apply.Result>>() {
                    @Override
                    public List<Apply.Result> call(Apply apply) {
                        LogUtil.d("map_result::"+apply.getResult().toString());
                        return apply.getResult();
                    }
                })
                .flatMap(new Func1<List<Apply.Result>, Observable<Apply.Result>>() {
                    @Override
                    public Observable<Apply.Result> call(List<Apply.Result> results) {
                        LogUtil.d("flatMap_result::"+results.size());
                        return Observable.from(results);
                    }
                })
                .filter(new Func1<Apply.Result, Boolean>() {
                    @Override
                    public Boolean call(Apply.Result result) {
                        return result.getType().equals(TYPE_JIABAN)?true:false;
                    }
                }).
                map(new Func1<Apply.Result, List<Apply.Result>>() {
                    @Override
                    public List<Apply.Result> call(Apply.Result result) {
                        list.add(result);
                        return list;
                    }
                }).subscribe(new Observer<List<Apply.Result>>() {
            @Override
            public void onCompleted() {
                adapter.addAll(list);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d("error"+e.toString());
            }

            @Override
            public void onNext(List<Apply.Result> results) {
                LogUtil.d("JiabanApplyresult:"+results.toString());
            }
        });

    }
    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        // TODO: 2016/10/21 刷新
        if (requestCode == 1001) {
            String result = data.getStringExtra("result");
            if (result.equals("refresh")){
                onRefresh();

            }
        }
    }*/
}
