package wgz.com.cx_ga_project.adapter;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.activity.JQListActivity;
import wgz.com.cx_ga_project.entity.JqCallBack;

/**
 * Created by qwerr on 2016/9/5.
 */
public class JQCallbackSJRViewHolder extends BaseViewHolder<Object> {
    private TextView name, sex, phone, mobilephone, idnum, addr, mac, serialnumber, sim;
    private FloatingActionButton fab ;

    public JQCallbackSJRViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_sjr_callback);
        name = $(R.id.sjr_name);
        sex = $(R.id.sjr_sex);
        phone = $(R.id.sjr_telphone);
        mobilephone = $(R.id.sjr_mobilephone);
        idnum = $(R.id.sjr_idcard);
        addr = $(R.id.sjr_addr);
        mac = $(R.id.sjr_mac);
        serialnumber = $(R.id.sjr_serialnumber);
        sim = $(R.id.sjr_sim);
        fab = $(R.id.sjr_fab);

    }

    @Override
    public void setData(Object data) {
        if (data instanceof JqCallBack.Resperson) {
            name.setText(((JqCallBack.Resperson) data).getInvolvepeoplename());
            sex.setText(((JqCallBack.Resperson) data).getGander());
            phone.setText(((JqCallBack.Resperson) data).getInvolvepeoplephone());
            mobilephone.setText(((JqCallBack.Resperson) data).getInvolvepeoplemobilephone());
            idnum.setText(((JqCallBack.Resperson) data).getInvolvepeopleidcard());
            addr.setText(((JqCallBack.Resperson) data).getAddr());
            try {
                mac.setText(((JqCallBack.Resperson) data).getMac().toString());
            } catch (Exception e) {
                e.printStackTrace();
                mac.setText("");
            }
            serialnumber.setText(((JqCallBack.Resperson) data).getSerialnumber());
            sim.setText(((JqCallBack.Resperson) data).getSimi());
            fab.setOnClickListener(v -> getContext().startActivity(new Intent(getContext(), JQListActivity.class).putExtra("title", "sjr")
                    .putExtra("name",((JqCallBack.Resperson) data).getInvolvepeoplename()).putExtra("idcard",((JqCallBack.Resperson) data).getInvolvepeopleidcard())));


        }
        }
}
