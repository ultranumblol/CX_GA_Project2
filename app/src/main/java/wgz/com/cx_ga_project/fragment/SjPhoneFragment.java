package wgz.com.cx_ga_project.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.lzp.floatingactionbuttonplus.FloatingActionButtonPlus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.activity.UpLoadSJPhoneActivity;
import wgz.com.cx_ga_project.adapter.JQCallbackDetilAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseFragment;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.entity.JqCallBack;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SjPhoneFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.id_sjphonefragment)
    EasyRecyclerView recyclerview;
    @Bind(R.id.FabPlus)
    FloatingActionButtonPlus FabPlus;
    private Handler handler = new Handler();
    private JQCallbackDetilAdapter adapter;
    List<JqCallBack.Resphone> list = new ArrayList<>();
    private Subscription rxSubscription;

    @Override
    public void initview(View view) {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapterWithProgress(adapter = new JQCallbackDetilAdapter(getActivity()));
        recyclerview.setRefreshListener(this);
        initData();
        FabPlus.setOnItemClickListener((tagView, position) -> {
            int id = tagView.getId();
            switch (id) {

                case R.id.fabtag_addsjPhone:
                    startActivity(new Intent(getActivity(), UpLoadSJPhoneActivity.class));
                    break;

            }
        });
        rxSubscription = RxBus.getDefault().toObservable(String.class)
                .subscribe(s -> {
                    if (s.equals("sjphoneflush"))
                        onRefresh();
                });
    }

    @Override
    public int getLayoutitem() {
        return R.layout.fragment_sjphone;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(() -> {
            list.clear();
            adapter.clear();
            initData();
        }, 2000);
    }

    private void initData() {
        app.jqAPIService.GetAllJQDetil(SomeUtil.getJQId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JqCallBack>() {
                    @Override
                    public void onCompleted() {
                        adapter.addAll(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("jqcallback error : " + e.toString());
                    }

                    @Override
                    public void onNext(JqCallBack jqCallBack) {
                        LogUtil.d("sjPhoneFragment : " + jqCallBack.getResphone().toString());
                        list = jqCallBack.getResphone();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
