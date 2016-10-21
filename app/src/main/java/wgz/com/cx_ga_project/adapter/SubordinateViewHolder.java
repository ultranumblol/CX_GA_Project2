package wgz.com.cx_ga_project.adapter;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.Constant;

/**
 * Created by wgz on 2016/8/9.
 */

public class SubordinateViewHolder extends BaseViewHolder {
    private TextView name,zhiwu;
    private ImageView face;

    public SubordinateViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_subordiante);
        name = (TextView) $(R.id.subordinate_name);
        zhiwu = (TextView) $(R.id.subordinate_zhiwu);
        face = (ImageView) $(R.id.subordiante_face);

    }

    @Override
    public void setData(Object data) {
        super.setData(data);
        name.setText("007");
        zhiwu.setText("特警");
        //face.setImageResource(R.drawable.ic_account_circle_gray_48dp);
        Glide.with(getContext())
                //.load("http://192.168.1.193:8004/avantar/10001.png")
                // .load("http://192.168.1.193:8004/avantar/030283.png")
                .load(Constant.USERHEADURL)
                .placeholder(R.drawable.ic_account_circle_gray_48dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.4f)
                .dontAnimate()
                .into(face);
    }
}
