package wgz.com.cx_ga_project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.entity.JqCallBack;

/**
 * Created by qwerr on 2016/9/5.
 */

public class JQCallbackDetilAdapter extends MyRecyclerArrayAdapter<Object> {
    public static final int TYPE_SJCAR = 2;
    public static final int TYPE_SJR = 1;
    public static final int TYPE_SJPHONE = 3;
    public static final int TYPE_SJMSG = 4;


    public JQCallbackDetilAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new JQCallbackSJRViewHolder(parent);
    }

    @Override
    public int getViewType(int position) {
        if (getItem(position) instanceof JqCallBack.Rescar)
            return TYPE_SJCAR;
        if (getItem(position) instanceof JqCallBack.Resperson)
            return TYPE_SJR;
        if (getItem(position) instanceof JqCallBack.Resreport)
            return TYPE_SJMSG;
        return TYPE_SJR;
    }
}
