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

    private TextView cp, jsrname, jsridnum, czname,
            sjcGender, sjcDriverphone, czidnum, sjcCartype, sjcCarbrand,
            sjcCarcolor, sjcCarmodel, sjcCarnature, ownerphone, ownernature;

    public JQCallbackSJCarViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_sjcar_callback);
        cp = $(R.id.sjcar_carplate);
        jsrname = $(R.id.sjc_jsrname);
        jsridnum = $(R.id.jsr_idnum);
        czname = $(R.id.cz_name);
        czidnum = $(R.id.cz_idnum);
        sjcGender = $(R.id.sjc_gender);
        sjcDriverphone = $(R.id.sjc_driverphone);
        sjcCartype = $(R.id.sjc_cartype);
        sjcCarbrand = $(R.id.sjc_carbrand);
        sjcCarcolor = $(R.id.sjc_carcolor);
        sjcCarmodel = $(R.id.sjc_carmodel);
        sjcCarnature = $(R.id.sjc_carnature);
        ownerphone = $(R.id.ownerphone);
        ownernature = $(R.id.ownernature);
    }

    @Override
    public void setData(Object data) {
        if (data instanceof JqCallBack.Rescar) {
            cp.setText(((JqCallBack.Rescar) data).getInvolvecarplate());
            jsrname.setText(((JqCallBack.Rescar) data).getInvolvecardriver());
            jsridnum.setText(((JqCallBack.Rescar) data).getInvolvecardriveridcard());
            czname.setText(((JqCallBack.Rescar) data).getInvolvecarowner());
            czidnum.setText(((JqCallBack.Rescar) data).getInvolvecarowneridcard());

            sjcGender.setText(((JqCallBack.Rescar) data).getGender());
            sjcDriverphone.setText(((JqCallBack.Rescar) data).getDriverphone());
            sjcCartype.setText(((JqCallBack.Rescar) data).getCartype());
            sjcCarbrand.setText(((JqCallBack.Rescar) data).getCarbrand());
            sjcCarcolor.setText(((JqCallBack.Rescar) data).getCarcolor());
            sjcCarmodel.setText(((JqCallBack.Rescar) data).getCarmodel());
            sjcCarnature.setText(((JqCallBack.Rescar) data).getCarnature());
            ownerphone.setText(((JqCallBack.Rescar) data).getOwnerphone());
            ownernature.setText(((JqCallBack.Rescar) data).getOwnernature());
        }

    }
}
