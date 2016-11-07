package wgz.com.cx_ga_project.adapter;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.util.Map;

import wgz.com.cx_ga_project.R;

/**
 * Created by wgz on 2016/11/2.
 */

public class SICInputViewHolder extends BaseViewHolder<Map<String, Object>> {
    private TextView title, id;
    private EditText content;

    public SICInputViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_input_sic);
        title = $(R.id.sic_input_title);
        content = $(R.id.sic_input_content);
        id = $(R.id.sic_input_titleid);
    }

    @Override
    public void setData(Map<String, Object> data) {
        title.setText(data.get("zh_name").toString());
        id.setText(data.get("en_name").toString());
    }
}
