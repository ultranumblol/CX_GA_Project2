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
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.JQCallbackDetilAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseFragment;
import wgz.com.cx_ga_project.entity.JqCallBack;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SjMsgFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.id_sjmsgfragment)
    EasyRecyclerView recyclerview;
    private Handler handler = new Handler();
    private JQCallbackDetilAdapter adapter;
    List<JqCallBack.Resreport> list = new ArrayList<>();

    @Override
    public void initview(View view) {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapterWithProgress(adapter = new JQCallbackDetilAdapter(getActivity()));
        recyclerview.setRefreshListener(this);
        initData();
    }

    @Override
    public int getLayoutitem() {
        return R.layout.fragment_sjmsg;
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
        }, 2000);
    }

    private void initData() {
        app.jqAPIService.GetAllJQDetil("2016072100100000060")
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
                        LogUtil.d("sjMsgFragment : " + jqCallBack.getResreport().toString());
                        list = jqCallBack.getResreport();
                    }
                });
    }

}
