package wgz.com.cx_ga_project.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.entity.JqCallBack;

/**
 * Created by qwerr on 2016/9/5.
 */
public class JQCallbackMsgViewHolder extends BaseViewHolder<Object> {
    private TextView  name,time,office,txt;

    public JQCallbackMsgViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_sjmsg_callback);
        name = $(R.id.sjmsg_name);
        time = $(R.id.sjmsg_time);
        office = $(R.id.sjmsg_depart);
        txt = $(R.id.sjmsg_txt);

    }

    @Override
    public void setData(Object data)
    {

        if (data instanceof JqCallBack.Resreport){
            name.setText(((JqCallBack.Resreport) data).getPolicename());
            time.setText(((JqCallBack.Resreport) data).getReporttime());
            office.setText(((JqCallBack.Resreport) data).getOfficecodename());
            txt.setText(((JqCallBack.Resreport) data).getTxt());


        }
    }
}
