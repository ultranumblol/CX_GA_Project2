package wgz.com.cx_ga_project.fragment;


import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.ApplyAdapter;
import wgz.com.cx_ga_project.adapter.JQCallbackDetilAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseFragment;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.entity.Apply;
import wgz.com.cx_ga_project.entity.JqCallBack;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SjCarFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.id_sjcarfragment)
    EasyRecyclerView recyclerview;
    private Handler handler = new Handler();
    private JQCallbackDetilAdapter adapter;
    List<JqCallBack.Rescar> list = new ArrayList<>();
    private Subscription rxSubscription;

    @Override
    public void initview(View view) {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapterWithProgress(adapter = new JQCallbackDetilAdapter(getActivity()));
        recyclerview.setRefreshListener(this);


        initData();

        rxSubscription = RxBus.getDefault().toObservable(String.class)
                .subscribe(s -> {
                    if (s.equals("sjcarflush"))
                        onRefresh();
                });
    }

    @Override
    public int getLayoutitem() {
        return R.layout.fragment_sjcar;
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
                        LogUtil.d("sjCarFragment : "+jqCallBack.getRescar().toString());
                        list = jqCallBack.getRescar();
                    }
                });
    }

}
