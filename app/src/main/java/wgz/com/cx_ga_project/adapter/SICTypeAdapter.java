package wgz.com.cx_ga_project.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.entity.SICType;

/**
 * Created by wgz on 2016/11/2.
 */

public class SICTypeAdapter extends MyRecyclerArrayAdapter<SICType.Re> {
    public SICTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SICTypeViewHolder(parent);
    }

}
