package wgz.com.cx_ga_project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.entity.JqOrbit;

/**
 * Created by wgz on 2016/8/22.
 */

public class TimelineAdapter extends MyRecyclerArrayAdapter<String> {
    public TimelineAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new TimelineViewHolder(parent);
    }


}
