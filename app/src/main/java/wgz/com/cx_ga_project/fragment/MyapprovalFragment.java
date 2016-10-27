package wgz.com.cx_ga_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
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
import wgz.com.cx_ga_project.activity.ApprovalDetilActivity;
import wgz.com.cx_ga_project.adapter.ApplyAdapter;
import wgz.com.cx_ga_project.adapter.MyRecyclerArrayAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseFragment;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.entity.Apply;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static wgz.com.cx_ga_project.base.Constant.UNAPPROVAL;

/**
 * Created by wgz on 2016/8/9.
 */

public class MyapprovalFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.id_myapproval_lv)
    EasyRecyclerView recyclerview;
    ApplyAdapter adapter;
    List<Apply.Result> list = new ArrayList<Apply.Result>();
    private Handler handler = new Handler();
    private Subscription rxSubscription;
    @Override
    public void initview(View view) {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(adapter = new ApplyAdapter(getActivity()));
        recyclerview.setRefreshListener(this);
        adapter.setOnItemClickListener(new MyRecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View itemView) {
                ImageView im_face = (ImageView) itemView.findViewById(R.id.user_face);
                Intent intent = new Intent();
                intent.setClass(getActivity(), ApprovalDetilActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",adapter.getItem(position).getId());
                bundle.putString("poiceid",adapter.getItem(position).getPoliceid());
                bundle.putString("poicename",adapter.getItem(position).getPolicename());
                bundle.putString("applytime",adapter.getItem(position).getApplytime());
                bundle.putString("starttime",adapter.getItem(position).getStart());
                bundle.putString("endtime",adapter.getItem(position).getEnd());
                bundle.putString("days",adapter.getItem(position).getDays()+"");
                bundle.putString("content",adapter.getItem(position).getContent());
                bundle.putString("status",adapter.getItem(position).getStatus());
                bundle.putString("upperid",adapter.getItem(position).getUpperid());
                bundle.putString("reasontype",adapter.getItem(position).getReasontype());
                bundle.putString("head","http://"+adapter.getItem(position).getUrl());
                intent.putExtra("detil",bundle);
                //intent.putExtra("type","qingjia");



                intent.putExtra("type",adapter.getItem(position).getType());
                intent.putExtra("ifhis",false);
                ActivityCompat.startActivityForResult(getActivity(),
                        intent,1002,ActivityOptionsCompat
                                .makeSceneTransitionAnimation(getActivity(),
                                        im_face, "share_img").toBundle());
            }
        });

        //adapter.addAll(initData());
        initdata();
        rxSubscription = RxBus.getDefault().toObservable(String.class)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (s.equals("myspflush"))
                            onRefresh();
                    }
                });

    }

    private void initdata(){
        app.apiService.getBeanData("getDepLeaveOverApply", SomeUtil.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Apply, List<Apply.Result>>() {
                    @Override
                    public List<Apply.Result> call(Apply apply) {
                       // LogUtil.d("approval map_result::"+apply.getResult().toString());
                        return apply.getResult();
                    }
                })
                .flatMap(new Func1<List<Apply.Result>, Observable<Apply.Result>>() {
                    @Override
                    public Observable<Apply.Result> call(List<Apply.Result> results) {
                        //LogUtil.d("approval flatMap_result::"+results.size());
                        return Observable.from(results);
                    }
                })
                .filter(new Func1<Apply.Result, Boolean>() {
                    @Override
                    public Boolean call(Apply.Result result) {
                        return result.getStatus().equals(UNAPPROVAL)?true:false;
                    }
                }).
                map(new Func1<Apply.Result, List<Apply.Result>>() {
                    @Override
                    public List<Apply.Result> call(Apply.Result result) {
                        list.add(result);
                        //LogUtil.d("approval result list :"+list.toString());
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
                LogUtil.d("approval result:"+results.toString());
            }
        });



    }
    @Override
    public int getLayoutitem() {
        return R.layout.fragment_my_approval;
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
                initdata();
            }
        }, 2000);
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;

        if (requestCode == 1002) {
            String result = data.getStringExtra("result");
            if (result.equals("refresh")){
                onRefresh();

            }
        }
    }*/
}
