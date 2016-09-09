package wgz.com.cx_ga_project.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.security.InvalidParameterException;

import wgz.com.cx_ga_project.bean.JiaBan;
import wgz.com.cx_ga_project.bean.QingJia;

/**
 * Created by wgz on 2016/8/9.
 */

public class MyApprovalAdapter extends MyRecyclerArrayAdapter<Object> {
    public static final int TYPE_INVALID = 0;
    public static final int TYPE_LEAVE = 1;
    public static final int TYPE_JIABAN = 2;

    public MyApprovalAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewType(int position) {
        if (getItem(position) instanceof JiaBan)
            return TYPE_LEAVE;
        if (getItem(position) instanceof QingJia)
            return TYPE_JIABAN;
        return TYPE_INVALID;
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_JIABAN:
                return new JiabanViewholder(parent);
            case TYPE_LEAVE:
                return new QingjiaViewholder(parent);
            default:throw new InvalidParameterException();
        }
    }
}
