package wgz.com.cx_ga_project.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.entity.JqCallBack;

/**
 * Created by qwerr on 2016/9/5.
 */
public class JQCallbackSJCarViewHolder extends BaseViewHolder<Object> {
    private TextView cp,jsrname,jsridnum,czname,czidnum;

    public JQCallbackSJCarViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_sjcar_callback);
        cp = $(R.id.sjcar_carplate);
        jsrname = $(R.id.sjc_jsrname);
        jsridnum=$(R.id.jsr_idnum);
        czname=$(R.id.cz_name);
        czidnum=$(R.id.cz_idnum);
    }

    @Override
    public void setData(Object data) {
        if (data instanceof JqCallBack.Rescar){
            cp.setText(((JqCallBack.Rescar) data).getInvolvecarplate());
            jsrname.setText(((JqCallBack.Rescar) data).getInvolvecardriver());
            jsridnum.setText(((JqCallBack.Rescar) data).getInvolvecardriveridcard());
            czname.setText(((JqCallBack.Rescar) data).getInvolvecarowner());
            czidnum.setText(((JqCallBack.Rescar) data).getInvolvecarowneridcard());

        }

    }
}
