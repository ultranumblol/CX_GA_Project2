package wgz.com.cx_ga_project.adapter;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;

/**
 * Created by wgz on 2016/11/7.
 */

public class SICInputLogViewholder extends BaseViewHolder {
    private TextView title,time;


    public SICInputLogViewholder(ViewGroup parent) {
        super(parent,R.layout.item_timebank);
        title = (TextView) $(R.id.sic_input_log_title);
        time = (TextView) $(R.id.sic_input_log_time);
    }

    @Override
    public void setData(Object data) {
        super.setData(data);
    }
}
