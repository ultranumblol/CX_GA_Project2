package wgz.com.cx_ga_project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by wgz on 2016/11/3.
 */

public class TimeBankAdapter extends MyRecyclerArrayAdapter {
    public TimeBankAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new TimeBankViewHolder(parent);
    }


}
