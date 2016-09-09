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
public class JiabanViewholder extends BaseViewHolder<Apply.Result> {
    private TextView jiabanID,jiabanState,jiabanDate;
    private ImageView userface;



    public JiabanViewholder(ViewGroup parent) {
        super(parent, R.layout.item_jiaban_apply);
        jiabanID =  $(R.id.jiaban_ID);
        jiabanDate =  $(R.id.jiaban_date);
        jiabanState =  $(R.id.jiaban_state);
        userface = $(R.id.user_face);

    }

    @Override
    public void setData(Apply.Result data) {
       jiabanID.setText(data.getPoliceid());
        jiabanDate.setText(data.getApplytime());
        if (data.getStatus().equals(UNAPPROVAL)){
            jiabanState.setText("未审批");
        }else if (data.getStatus().equals(APPROVAL_PASS)){
            jiabanState.setText("审批通过");
        }else if (data.getStatus().equals(APPROVAL_UNPASS)){
            jiabanState.setText("审批未通过");
        }
        userface.setImageResource(R.drawable.ic_account_circle_gray_48dp);
    }
}
