package wgz.com.cx_ga_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.activity.ApprovalDetilActivity;
import wgz.com.cx_ga_project.adapter.ApplyAdapter;
import wgz.com.cx_ga_project.adapter.MyApprovalAdapter;
import wgz.com.cx_ga_project.adapter.MyRecyclerArrayAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseFragment;
import wgz.com.cx_ga_project.entity.Apply;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static wgz.com.cx_ga_project.base.Constant.APPROVAL_PASS;
import static wgz.com.cx_ga_project.base.Constant.APPROVAL_UNPASS;
import static wgz.com.cx_ga_project.base.Constant.UNAPPROVAL;

/**
 * Created by wgz on 2016/8/9.
 */

public class MyapprovalHistoryFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.id_myapproval_Lv_his)
    EasyRecyclerView recyclerview;
    private Handler handler = new Handler();
    private ApplyAdapter adapter;
    List<Apply.Result> list = new ArrayList<Apply.Result>();

    @Override
    public void initview(View view) {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(adapter = new ApplyAdapter(getActivity()));

      /*  adapter.setMore(R.layout.view_more, new MyRecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //adapter.addAll(initData2());
                    }
                }, 2000);
            }
        });*/
        recyclerview.setRefreshListener(this);
        adapter.setOnItemClickListener(new MyRecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View itemView) {
                ImageView im_face = (ImageView) itemView.findViewById(R.id.user_face);
                Intent intent = new Intent();
                intent.setClass(getActivity(), ApprovalDetilActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("poiceid", adapter.getItem(position).getPoliceid());
                bundle.putString("poicename",adapter.getItem(position).getPolicename());
                bundle.putString("applytime", adapter.getItem(position).getApplytime());
                bundle.putString("starttime", adapter.getItem(position).getStart());
                bundle.putString("endtime", adapter.getItem(position).getEnd());
                bundle.putString("days", adapter.getItem(position).getDays() + "");
                bundle.putString("content", adapter.getItem(position).getContent());
                bundle.putString("status", adapter.getItem(position).getStatus());
                bundle.putString("upperid", adapter.getItem(position).getUpperid());
                bundle.putString("reasontype", adapter.getItem(position).getReasontype());
                bundle.putString("head","http://"+adapter.getItem(position).getUrl());
                intent.putExtra("detil", bundle);
                //intent.putExtra("type","qingjia");


                intent.putExtra("type", adapter.getItem(position).getType());
                intent.putExtra("ifhis",true);
                ActivityCompat.startActivity(getActivity(),
                        intent, ActivityOptionsCompat
                                .makeSceneTransitionAnimation(getActivity(),
                                        im_face, "share_img").toBundle());


            }
        });
        initdata();
    }

    /**
     * 初始化数据
     */
    private void initdata() {
        app.apiService.getBeanData("getDepLeaveOverApply", SomeUtil.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Apply, List<Apply.Result>>() {
                    @Override
                    public List<Apply.Result> call(Apply apply) {
                        //LogUtil.d("map_result::" + apply.getResult().toString());
                        return apply.getResult();
                    }
                })
                .flatMap(new Func1<List<Apply.Result>, Observable<Apply.Result>>() {
                    @Override
                    public Observable<Apply.Result> call(List<Apply.Result> results) {
                        //LogUtil.d("flatMap_result::" + results.size());
                        return Observable.from(results);
                    }
                })
                .filter(new Func1<Apply.Result, Boolean>() {
                    @Override
                    public Boolean call(Apply.Result result) {
                        if (result.getStatus().equals(APPROVAL_PASS)||result.getStatus().equals(APPROVAL_UNPASS)){
                            return  true;
                        }
                        else return false;
                    }
                })
                .map(new Func1<Apply.Result, List<Apply.Result>>() {
                            @Override
                            public List<Apply.Result> call(Apply.Result result) {
                                list.add(result);

                                return list;
                            }
                        }).subscribe(new Subscriber<List<Apply.Result>>() {
            @Override
            public void onCompleted() {
                //LogUtil.d("approvalHistory list : "+list.toString());
                //LogUtil.d("approvalHistory list  size : "+list.size());
                if (list.size()>1){
                    adapter.setNoMore(R.layout.view_nomore);
                }


                adapter.addAll(list);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d("ApprovalhistoryERROR:" + e.toString());
            }

            @Override
            public void onNext(List<Apply.Result> results) {
                //LogUtil.d("approvalHistory list : "+list.toString());
                LogUtil.d("approvalHistory results : "+results.toString());
            }
        });







                /*.subscribe(new Observer<Apply>() {
                    @Override
                    public void onCompleted() {
                    adapter.addAll(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("ApprovalhistoryERROR:"+e.toString());
                    }

                    @Override
                    public void onNext(Apply apply) {
                        list =  apply.getResult();
                        LogUtil.d("ApprovalhistoryRESULT:"+apply.getResult().size());
                    }
                });*/
    }

    @Override
    public int getLayoutitem() {
        return R.layout.fragment_my_approval_history;
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
}
