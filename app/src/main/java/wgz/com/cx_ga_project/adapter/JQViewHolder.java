package wgz.com.cx_ga_project.adapter;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;

/**
 * Created by wgz on 2016/8/22.
 */

public class JQViewHolder extends BaseViewHolder<String> {
    private TextView jqid,jqstate;



    public JQViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_jq);
        jqid = $(R.id.jqid);
        jqstate = $(R.id.jq_state);
    }

    @Override
    public void setData(String data) {
        super.setData(data);
    }
}
