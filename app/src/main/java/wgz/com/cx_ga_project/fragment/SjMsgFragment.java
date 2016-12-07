package wgz.com.cx_ga_project.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import wgz.com.cx_ga_project.activity.AddJQActivity;
import wgz.com.cx_ga_project.activity.AddJQPicActivity;
import wgz.com.cx_ga_project.adapter.AddPictureAdapter;
import wgz.com.cx_ga_project.adapter.JQCallbackDetilAdapter;
import wgz.com.cx_ga_project.adapter.chatAdapter.CallBackPicVideoAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseFragment;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.entity.JqCallBack;
import wgz.com.cx_ga_project.entity.PicAndVideo;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SjMsgFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.id_sjmsgfragment)
    EasyRecyclerView recyclerview;
    @Bind(R.id.FabPlus)
    FloatingActionButtonPlus FabPlus;
    @Bind(R.id.id_sjmsgpic_rv)
    EasyRecyclerView idSjmsgpicRv;
    @Bind(R.id.id_sjmsgvideo_rv)
    EasyRecyclerView idSjmsgvideoRv;
    private Handler handler = new Handler();
    private JQCallbackDetilAdapter adapter;
    List<JqCallBack.Resreport> list = new ArrayList<>();
    private Subscription rxSubscription;
    private CallBackPicVideoAdapter picadapter,videoadapter;
    private List<String> piclist = new ArrayList<>();
    private List<String> videolist = new ArrayList<>();

    @Override
    public void initview(View view) {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapterWithProgress(adapter = new JQCallbackDetilAdapter(getActivity()));
        recyclerview.setRefreshListener(this);
        initData();

        //idSjmsgpicRv.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        idSjmsgpicRv.setLayoutManager(manager);


        idSjmsgpicRv.setAdapter(picadapter = new CallBackPicVideoAdapter(getActivity()));

        LinearLayoutManager manager2 = new LinearLayoutManager(getActivity());
        manager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        idSjmsgvideoRv.setLayoutManager(manager2);
        idSjmsgvideoRv.setAdapter(videoadapter = new CallBackPicVideoAdapter(getActivity()));

        FabPlus.setOnItemClickListener((tagView, position) -> {
            int id = tagView.getId();
            switch (id) {

                case R.id.fabtag_addjqMsg:
                    startActivity(new Intent(getActivity(), AddJQActivity.class));
                    break;
                case R.id.fabtag_addjqpic:
                    startActivity(new Intent(getActivity(), AddJQPicActivity.class));
                    break;
                case R.id.fabtag_addjqvideo:
                    Intent intent2 = new Intent();
                    intent2.setType("video/*");
                    intent2.setAction(Intent.ACTION_GET_CONTENT);
                    //intent2.addCategory(Intent.CATEGORY_OPENABLE);
                    getActivity().startActivityForResult(intent2, 456);
                    break;
            }
        });
        rxSubscription = RxBus.getDefault().toObservable(String.class)
                .subscribe(s -> {
                    if (s.equals("sjmsgflush"))
                        onRefresh();
                });
    }

    @Override
    public int getLayoutitem() {
        return R.layout.fragment_sjmsg;
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
                        LogUtil.d("sjMsgFragment : " + jqCallBack.getResreport().toString());
                        list = jqCallBack.getResreport();
                    }
                });
        app.jqAPIService.getJqPicAndVideo(SomeUtil.getJQId())
                .compose(RxUtil.applySchedulers())
                .subscribe(new Subscriber<PicAndVideo>() {
                    @Override
                    public void onCompleted() {
                        picadapter.addAll(piclist);
                        videoadapter.addAll(videolist);

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("result error: " + e.toString());
                    }

                    @Override
                    public void onNext(PicAndVideo s) {
                        LogUtil.d("result code:"+s.getCode());

                        LogUtil.d("piclist: "+s.toString());
                        if (s.getRespic()!=null){
                            for (PicAndVideo.Respic pic :s.getRespic())
                                piclist.add(pic.getPic());
                        }
                        if (s.getResvideo()!=null){
                            for (PicAndVideo.Resvideo video :s.getResvideo())
                                videolist.add(video.getVideopic());
                        }

                        LogUtil.d("piclist : " + piclist.toString());
                        LogUtil.d("videolist : " + videolist.toString());

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
