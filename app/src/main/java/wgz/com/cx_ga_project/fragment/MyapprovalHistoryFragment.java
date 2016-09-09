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
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * Created by wgz on 2016/8/9.
 */

public class MyapprovalHistoryFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.id_myapproval_Lv_his)
    EasyRecyclerView recyclerview;
    private Handler handler = new Handler();
    private ApplyAdapter adapter;
    List<Apply.Result> list = new ArrayList<Apply.Result>();
    @Override
    public void initview(View view) {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter( adapter = new ApplyAdapter(getActivity()));
        adapter.setNoMore(R.layout.view_nomore);
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
                bundle.putString("poiceid",adapter.getItem(position).getPoliceid());
                bundle.putString("applytime",adapter.getItem(position).getApplytime());
                bundle.putString("starttime",adapter.getItem(position).getStart());
                bundle.putString("endtime",adapter.getItem(position).getEnd());
                bundle.putString("days",adapter.getItem(position).getDays()+"");
                bundle.putString("content",adapter.getItem(position).getContent());
                bundle.putString("status",adapter.getItem(position).getStatus());
                bundle.putString("upperid",adapter.getItem(position).getUpperid());
                bundle.putString("reasontype",adapter.getItem(position).getReasontype());
                intent.putExtra("detil",bundle);
                //intent.putExtra("type","qingjia");



                intent.putExtra("type",adapter.getItem(position).getType());
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
    private void initdata(){
        app.apiService.getBeanData("getDepLeaveOverApply")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Apply>() {
                    @Override
                    public void onCompleted() {
                    adapter.addAll(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("ApprovalhistoryERROR:"+e.toString());
                    }

                    @Override
                    public void onNext(Apply apply) {
                        list =  apply.getResult();
                        LogUtil.e("ApprovalhistoryRESULT:"+apply.getResult().size());
                    }
                });
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
