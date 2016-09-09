package wgz.com.cx_ga_project.adapter;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.view.TimeLineMarker;

/**
 * Created by wgz on 2016/8/22.
 */

public class TimelineViewHolder extends BaseViewHolder<String> {
    private TextView desc_tv;
    private TimeLineMarker timeLineMarker;



    public TimelineViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_timeline);
        desc_tv = (TextView) $(R.id.desc_tv);
        timeLineMarker = (TimeLineMarker) $(R.id.timeLineMarker);
    }

    @Override
    public void setData(String data) {
        if (getAdapterPosition()==0){
            timeLineMarker.setText(""+(getAdapterPosition()+1));
            timeLineMarker.setBeginLine(null);
        }else {
            timeLineMarker.setText(""+(getAdapterPosition()+1));
        }


      desc_tv.setText(data);
    }
}
