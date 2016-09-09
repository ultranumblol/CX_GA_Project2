package wgz.com.cx_ga_project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by wgz on 2016/9/2.
 */

public class CamsIDAdapter extends MyRecyclerArrayAdapter<String>{
    public CamsIDAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CamsIDViewHolder(parent);
    }

}
