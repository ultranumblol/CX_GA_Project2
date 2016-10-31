package wgz.com.cx_ga_project.adapter;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.entity.JqCallBack;

/**
 * Created by qwerr on 2016/9/5.
 */
public class JQCallbackSJRViewHolder extends BaseViewHolder<Object> {
    private TextView name,sex,phone,mobilephone,idnum;

    public JQCallbackSJRViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_sjr_callback);
        name = $(R.id.sjr_name);
        sex = $(R.id.sjr_sex);
        phone = $(R.id.sjr_telphone);
        mobilephone = $(R.id.sjr_mobilephone);
        idnum = $(R.id.sjr_idcard);
    }

    @Override
    public void setData(Object data)
    {
       if (data instanceof JqCallBack.Resperson){
           name.setText(((JqCallBack.Resperson) data).getInvolvepeoplename());
           if (((JqCallBack.Resperson) data).getGander().equals("1")){
               sex.setText("男");

           }else
               sex.setText("nv");
           phone.setText(((JqCallBack.Resperson) data).getInvolvepeoplephone());
           mobilephone.setText(((JqCallBack.Resperson) data).getInvolvepeoplemobilephone());
           idnum.setText(((JqCallBack.Resperson) data).getInvolvepeopleidcard());


       }
    }
}
