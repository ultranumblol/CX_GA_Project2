package wgz.com.cx_ga_project.adapter;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.entity.SICType;

/**
 * Created by wgz on 2016/11/2.
 */

public class SICTypeViewHolder extends BaseViewHolder<SICType.Re> {
    private TextView type ;

    public SICTypeViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_type_sic);
        type = $(R.id.sic_type_name);
    }

    @Override
    public void setData(SICType.Re data) {
        type.setText(data.getMoudleclass());

    }
}
