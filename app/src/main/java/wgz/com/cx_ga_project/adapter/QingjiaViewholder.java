package wgz.com.cx_ga_project.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
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
        qingjiaID.setText(data.getPoliceid());
        qingjiaReason.setText(data.getContent());
        qingjiaDate.setText(data.getApplytime());
        if (data.getStatus().equals(UNAPPROVAL)){
            qingjiaState.setText("未审批");
        }else if (data.getStatus().equals(APPROVAL_PASS)){
            qingjiaState.setText("审批通过");
        }else if (data.getStatus().equals(APPROVAL_UNPASS)){
            qingjiaState.setText("审批未通过");
        }

        userface.setImageResource(R.drawable.ic_account_circle_gray_48dp);
    }
}
