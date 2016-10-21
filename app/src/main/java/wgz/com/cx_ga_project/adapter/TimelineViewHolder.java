package wgz.com.cx_ga_project.adapter;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.entity.JqOrbit;
import wgz.com.cx_ga_project.view.TimeLineMarker;

/**
 * Created by wgz on 2016/8/22.
 */

public class TimelineViewHolder extends BaseViewHolder<String> {
    private TextView desc_tv,time;
    private TimeLineMarker timeLineMarker;



    public TimelineViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_timeline);
        desc_tv =  $(R.id.desc_tv);
        timeLineMarker =  $(R.id.timeLineMarker);
        time = $(R.id.id_timelineTime);
    }

    @Override
    public void setData(String data) {
        if (getAdapterPosition()==0){
            timeLineMarker.setText(""+(getAdapterPosition()+1));
            timeLineMarker.setBeginLine(null);
        }else {
            timeLineMarker.setText(""+(getAdapterPosition()+1));
        }
        //time.setText("2016-9-18 11:43:22");
        desc_tv.setText(data);
    }

   /* @Override
    public void setData(JqOrbit.Re data) {


        if (getAdapterPosition()==0){
            timeLineMarker.setText(""+(getAdapterPosition()+1));
            timeLineMarker.setBeginLine(null);
        }else {
            timeLineMarker.setText(""+(getAdapterPosition()+1));
        }
        time.setText(data.getSendtime());
        desc_tv.setText(data.getTreatmentdep());
    }*/

}
