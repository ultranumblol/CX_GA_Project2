package wgz.com.cx_ga_project.adapter;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;

/**
 * Created by wgz on 2016/11/2.
 */

public class SICInputViewHolder extends BaseViewHolder<Object> {
    private TextView title;
    private EditText content;
    public SICInputViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_input_sic);
        title=$(R.id.sic_input_title);
    }

    @Override
    public void setData(Object data)
    {
       if (data instanceof String ){
           title.setText(data.toString());


       }
    }
}
