package wgz.com.cx_ga_project.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.entity.NewJQ;

/**
 * Created by wgz on 2016/8/22.
 */

public class JQViewHolder extends BaseViewHolder<Object> {
    private TextView jqid,taskid,stateid,jqdate;
    private ImageView jqstate;



    public JQViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_jq);
        jqid = $(R.id.jqid);
        jqstate = $(R.id.jq_state);
        taskid = $(R.id.taskid);
        stateid = $(R.id.jqstate_id);
        jqdate = $(R.id.jq_bjdate);
    }

    @Override
    public void setData(Object data) {
        if (data instanceof String ){
            if (data.equals("1")){
                Glide.with(getContext())
                        //.load("http://192.168.1.193:8004/avantar/10001.png")
                        // .load("http://192.168.1.193:8004/avantar/030283.png")
                        .load(R.drawable.jqfinish)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .thumbnail(0.4f)
                        .dontAnimate()
                        .into(jqstate);
            }else {
                Glide.with(getContext())
                        //.load("http://192.168.1.193:8004/avantar/10001.png")
                        // .load("http://192.168.1.193:8004/avantar/030283.png")
                        .load(R.drawable.jqing)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .thumbnail(0.4f)
                        .dontAnimate()
                        .into(jqstate);

            }
        }

        if (data instanceof NewJQ.NewjqRe){
            if (((NewJQ.NewjqRe) data).getStatus().equals(3)){
                Glide.with(getContext())
                        //.load("http://192.168.1.193:8004/avantar/10001.png")
                        // .load("http://192.168.1.193:8004/avantar/030283.png")
                        .load(R.drawable.jqfinish)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .thumbnail(0.4f)
                        .dontAnimate()
                        .into(jqstate);

            }else {
                Glide.with(getContext())
                        //.load("http://192.168.1.193:8004/avantar/10001.png")
                        // .load("http://192.168.1.193:8004/avantar/030283.png")
                        .load(R.drawable.jqing)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .thumbnail(0.4f)
                        .dontAnimate()
                        .into(jqstate);
            }
            taskid.setText(((NewJQ.NewjqRe) data).getTaskid());
            stateid.setText(((NewJQ.NewjqRe) data).getStatus());
            jqid.setText(((NewJQ.NewjqRe) data).getJqid());
            jqdate.setText(((NewJQ.NewjqRe) data).getSendtime());

        }
      /*  Glide.with(getContext())
                //.load("http://192.168.1.193:8004/avantar/10001.png")
                // .load("http://192.168.1.193:8004/avantar/030283.png")
                .load(R.drawable.jqfinish)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.4f)
                .dontAnimate()
                .into(jqstate);*/




    }
}
