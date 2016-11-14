package wgz.com.cx_ga_project.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.entity.WorkCloudList;
import wgz.com.cx_ga_project.util.SomeUtil;

/**
 * Created by wgz on 2016/11/12.
 */

public class WorkCloudListViewHolder extends BaseViewHolder<WorkCloudList.Re> {
    private TextView title, time;

    public WorkCloudListViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_timebank);
        title =  $(R.id.sic_input_log_title);
        time =  $(R.id.sic_input_log_time);
    }

    @Override
    public void setData(WorkCloudList.Re data) {
        title.setText(data.getSummary());

        time.setText(data.getInserttime().substring(0,10));
    }
}
