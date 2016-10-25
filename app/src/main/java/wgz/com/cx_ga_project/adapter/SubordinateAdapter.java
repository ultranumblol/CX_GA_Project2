package wgz.com.cx_ga_project.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.entity.Subordinate;

/**
 * Created by wgz on 2016/8/9.
 */

public class SubordinateAdapter extends MyRecyclerArrayAdapter<Subordinate.Resdown> {
    public SubordinateAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SubordinateViewHolder(parent);
    }
}
