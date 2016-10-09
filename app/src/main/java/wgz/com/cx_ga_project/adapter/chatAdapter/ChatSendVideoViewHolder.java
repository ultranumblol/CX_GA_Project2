package wgz.com.cx_ga_project.adapter.chatAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.entity.ChatMsg;


/**
 * Created by qwerr on 2016/9/8.
 */
public class ChatSendVideoViewHolder extends BaseViewHolder<ChatMsg.Re> {
    private ImageView imageView;
    private TextView timestamp;
    private ImageView userhead;
    private ProgressBar progressBar;
    public ChatSendVideoViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_sent_video);
        timestamp = $(R.id.timestamp);
        userhead = $(R.id.iv_userhead);
        imageView = $(R.id.chatting_content_iv);
        progressBar = $(R.id.progressBar);
    }

    @Override
    public void setData(ChatMsg.Re data) {
        Glide.with(getContext())
                //.load("http://192.168.1.193:8004/avantar/10001.png")
                // .load("http://192.168.1.193:8004/avantar/030283.png")
                .load(Constant.USERHEADURL)
                .placeholder(R.drawable.ic_account_circle_gray_48dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.4f)
                .dontAnimate()
                .into(userhead);


        Glide.with(getContext())
                .load(data.getVideo())
                .dontAnimate()
                .centerCrop()
                .thumbnail(0.2f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        timestamp.setText(data.getSendtime());
        if (data.getIssend().equals("2")){
            progressBar.setVisibility(View.VISIBLE);

        }else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
