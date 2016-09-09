package wgz.com.cx_ga_project.adapter;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;

/**
 * Created by wgz on 2016/9/2.
 */

public class CamsIDViewHolder extends BaseViewHolder<String> {
    private TextView camsID;

    public CamsIDViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_cam_id);
        camsID = $(R.id.id_item_camID);
    }

    @Override
    public void setData(String data) {
        camsID.setText(data);
    }
}
