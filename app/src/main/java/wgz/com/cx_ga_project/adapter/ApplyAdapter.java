package wgz.com.cx_ga_project.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import wgz.com.cx_ga_project.bean.JiaBan;
import wgz.com.cx_ga_project.bean.QingJia;
import wgz.com.cx_ga_project.entity.Apply;

/**
 * 请假和加班申请的适配器
 * Created by wgz on 2016/8/4.
 */

public class ApplyAdapter extends MyRecyclerArrayAdapter<Apply.Result> {
    public static final int TYPE_INVALID = 0;
    public static final int TYPE_LEAVE = 1;
    public static final int TYPE_JIABAN = 2;



    public ApplyAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_LEAVE:
                return new QingjiaViewholder(parent);
            case TYPE_JIABAN:
                return new JiabanViewholder(parent);
            default:throw new InvalidParameterException();
        }
    }

    @Override
    public int getViewType(int position) {
        if (getItem(position).getType().equals("1"))
            return TYPE_LEAVE;
        if (getItem(position).getType().equals("0"))
            return TYPE_JIABAN;
        return TYPE_INVALID;
    }
}
