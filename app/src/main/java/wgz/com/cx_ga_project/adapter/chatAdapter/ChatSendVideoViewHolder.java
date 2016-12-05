package wgz.com.cx_ga_project.adapter.chatAdapter;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import rx.Subscription;
import rx.functions.Action1;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.activity.ShowBigImage;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.bean.ChatUpProgress;
import wgz.com.cx_ga_project.entity.ChatMsg;
import wgz.datatom.com.utillibrary.util.LogUtil;
import wgz.datatom.com.utillibrary.util.ToastUtil;


/**
 * Created by qwerr on 2016/9/8.
 */
public class ChatSendVideoViewHolder extends BaseViewHolder<ChatMsg.Re> {
    private ImageView imageView;
    private TextView timestamp,percentage;
    private ImageView userhead;
    private ProgressBar progressBar;
    private Subscription rxSubscription2;
    public ChatSendVideoViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_sent_video);
        timestamp = $(R.id.timestamp);
        userhead = $(R.id.iv_userhead);
        imageView = $(R.id.chatting_content_iv);
        progressBar = $(R.id.progressBar);
        percentage = $(R.id.percentage);
    }

    @Override
    public void setData(final ChatMsg.Re data) {




        Glide.with(getContext())
                .load(Constant.USERHEADURL)
                .placeholder(R.drawable.ic_account_circle_gray_48dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.4f)
                .dontAnimate()
                .into(userhead);


        Glide.with(getContext())
                .load(data.getVideopic())
                .dontAnimate()
                .centerCrop()
                .thumbnail(0.2f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        LogUtil.d("videoUrl: "+data.getVideopic());
        imageView.setOnClickListener(v -> ToastUtil.show(getContext(),"视频请在本地查看!", Toast.LENGTH_SHORT));


        timestamp.setText(data.getSendtime());
        if (data.getIssend().equals("2")){
            progressBar.setVisibility(View.VISIBLE);

        }else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
