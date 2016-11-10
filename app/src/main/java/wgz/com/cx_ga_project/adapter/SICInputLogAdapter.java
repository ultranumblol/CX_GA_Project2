package wgz.com.cx_ga_project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.entity.SICList;

/**
 * Created by wgz on 2016/11/7.
 */

public class SICInputLogAdapter extends MyRecyclerArrayAdapter<SICList.SICListRe> {
    public SICInputLogAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SICInputLogViewholder(parent);
    }



}
