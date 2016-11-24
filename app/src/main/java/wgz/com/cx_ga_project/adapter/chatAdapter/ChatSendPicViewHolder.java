package wgz.com.cx_ga_project.adapter.chatAdapter;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.activity.ChatActivity;
import wgz.com.cx_ga_project.activity.ShowBigImage;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.entity.ChatMsg;

/**
 * Created by qwerr on 2016/9/8.
 */
public class ChatSendPicViewHolder extends BaseViewHolder<ChatMsg.Re> {
    private ImageView mSendPicture;
    private TextView timestamp;
    private ImageView userhead;
    private ProgressBar progressBar;


    public ChatSendPicViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_sent_picture);
        mSendPicture = $(R.id.iv_sendPicture);
        timestamp = $(R.id.timestamp);
        progressBar = $(R.id.progressBar);
        userhead = $(R.id.iv_userhead);
    }


    @Override
    public void setData(final ChatMsg.Re data) {
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
                .load(data.getPic())
                .dontAnimate()
                .centerCrop()
                .thumbnail(0.4f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mSendPicture);

        mSendPicture.setOnClickListener(v -> getContext().startActivity(new Intent(getContext(), ShowBigImage.class).putExtra("url",data.getPic())));
        timestamp.setText(data.getSendtime());
        if (data.getIssend().equals("2")){
            progressBar.setVisibility(View.VISIBLE);

        }else {
            progressBar.setVisibility(View.GONE);
        }

    }
}
