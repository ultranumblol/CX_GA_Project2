package wgz.com.cx_ga_project.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.entity.NewJQ;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.com.cx_ga_project.view.Mylayout;
import wgz.com.cx_ga_project.view.TimeLineMarker;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * 我的工作轨迹
 */
public class MyWorkingTrackActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_my_working_track)
    ConstraintLayout rootview;
    @Bind(R.id.id_mytracrecyclerview)
    RecyclerView recyclerview;
    private HomeAdapter mAdapter;
    private List<NewJQ.NewjqRe> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_working_track;
    }

    @Override
    public void initView() {
        toolbar.setTitle("我的工作轨迹");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerview.setLayoutManager(new Mylayout(this));

        initData();

    }

    private void initData() {

        //历史警情
        app.jqAPIService.getOldJqlist(SomeUtil.getUserId())
                .compose(RxUtil.<NewJQ>applySchedulers())
                .subscribe(new Subscriber<NewJQ>() {
                    @Override
                    public void onCompleted() {
                        recyclerview.setAdapter(mAdapter = new HomeAdapter());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NewJQ newJQ) {
                        list = newJQ.getRes();
                        LogUtil.d("jqhistory result :" + newJQ.getRes().toString());


                    }
                });

    }



    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    MyWorkingTrackActivity.this).inflate(R.layout.item_timeline, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            //holder.tv.setText(mDatas.get(position));
            if (position == 0) {
                holder.timeLineMarker.setText("" + (position + 1));
                holder.timeLineMarker.setBeginLine(null);
            } else {
                holder.timeLineMarker.setText("" + (position + 1));
            }
            holder.time.setText(list.get(position).getSendtime());
            holder.desc_tv.setText(list.get(position).getTreatmentdep());


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView desc_tv, time;
            private TimeLineMarker timeLineMarker;

            public MyViewHolder(View view) {
                super(view);
                desc_tv = (TextView) view.findViewById(R.id.desc_tv);
                timeLineMarker = (TimeLineMarker) view.findViewById(R.id.timeLineMarker);
                time = (TextView) view.findViewById(R.id.id_timelineTime);
            }
        }
    }


}
