package wgz.com.cx_ga_project.adapter.chatAdapter;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
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
    }


    @Override
    public void setData(ChatMsg.Re data) {
        Glide.with(getContext())
                .load(data.getPic())
                .dontAnimate()
                .centerCrop()
                .thumbnail(0.4f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mSendPicture);
        timestamp.setText(data.getSendtime());

    }
}
