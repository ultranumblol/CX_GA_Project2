package wgz.com.cx_ga_project.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.entity.JqCallBack;

/**
 * Created by qwerr on 2016/9/5.
 */
public class JQCallbackSJPhoneViewHolder extends BaseViewHolder<Object> {
    private TextView name,phone,idnum,mobilephone;

    public JQCallbackSJPhoneViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_sjphone_callback);
        name = $(R.id.sjphone_name);
        phone = $(R.id.sjphone_telphone);
        idnum = $(R.id.sjphone_idnum);
        mobilephone = $(R.id.sjphone_mobilephone);
    }

    @Override
    public void setData(Object data) {
       if (data instanceof JqCallBack.Resphone){
           name.setText(((JqCallBack.Resphone) data).getRelationname());
           phone.setText(((JqCallBack.Resphone) data).getPhone());
           mobilephone.setText(((JqCallBack.Resphone) data).getMobilephone());
           idnum.setText(((JqCallBack.Resphone) data).getRelationidcard());


       }
    }
}
