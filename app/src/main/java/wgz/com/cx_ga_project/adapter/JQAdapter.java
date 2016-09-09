package wgz.com.cx_ga_project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by wgz on 2016/8/22.
 */

public class JQAdapter extends MyRecyclerArrayAdapter<String> {
    public JQAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new JQViewHolder(parent);
    }


}
