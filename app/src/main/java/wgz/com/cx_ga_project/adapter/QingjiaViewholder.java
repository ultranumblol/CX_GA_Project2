package wgz.com.cx_ga_project.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.entity.Apply;

import static wgz.com.cx_ga_project.base.Constant.APPROVAL_PASS;
import static wgz.com.cx_ga_project.base.Constant.APPROVAL_UNPASS;
import static wgz.com.cx_ga_project.base.Constant.UNAPPROVAL;

/**
 * Created by wgz on 2016/8/4.
 */
public class QingjiaViewholder extends BaseViewHolder<Apply.Result> {
    private TextView qingjiaID,qingjiaReason,qingjiaDate,qingjiaState;
    private ImageView userface;


    public QingjiaViewholder(ViewGroup parent) {
        super(parent, R.layout.item_qingjia_apply);
        qingjiaReason =  $(R.id.qingjia_reason);
        qingjiaDate =  $(R.id.qingjia_date);
        qingjiaState =  $(R.id.qingjia_state);
        userface = $(R.id.user_face);
        qingjiaID = $(R.id.qingjia_name);
    }
    @Override
    public void setData(Apply.Result data) {
        qingjiaID.setText(data.getPolicename());
        qingjiaReason.setText(data.getReasontype());
        qingjiaDate.setText(data.getApplytime());
        if (data.getStatus().equals(UNAPPROVAL)){
            qingjiaState.setText("未审批");
        }else if (data.getStatus().equals(APPROVAL_PASS)){
            qingjiaState.setText("审批通过");
        }else if (data.getStatus().equals(APPROVAL_UNPASS)){
            qingjiaState.setText("审批未通过");
        }
        String url ="http://"+ data.getUrl().replaceAll("\\\\","");


        Glide.with(getContext())
                //.load("http://192.168.1.193:8004/avantar/10001.png")
                // .load("http://192.168.1.193:8004/avantar/030283.png")
                .load(url)
                .placeholder(R.drawable.ic_account_circle_gray_48dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.4f)
                .dontAnimate()
                .into(userface);
        //userface.setImageResource(R.drawable.ic_account_circle_gray_48dp);
    }
}
