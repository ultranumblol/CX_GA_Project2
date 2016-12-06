package wgz.com.cx_ga_project.adapter.chatAdapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.ResponseBody;
import rx.Subscriber;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.activity.ChatActivity;
import wgz.com.cx_ga_project.activity.ShowBigImage;
import wgz.com.cx_ga_project.activity.ShowBigImage2;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.entity.ChatMsg;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static wgz.com.cx_ga_project.app.DATRIX_BASE_URL;

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

        LogUtil.d("picurl :"+data.getPic());
        String url = data.getPic();
        String url1 = url.replace(DATRIX_BASE_URL + "preview/getImage?fileid=", "");
        String url2 = url1.replace("&token=X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=", "");
       // LogUtil.d("picfileid :"+url2);
        app.apiService.detrixPic(url2, "0", "300")
                .compose(RxUtil.applySchedulers())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("pic error: " + e.toString());
                    }

                    @Override
                    public void onNext(ResponseBody s) {
                        Bitmap btp = BitmapFactory.decodeStream(s.byteStream());
                        mSendPicture.setImageBitmap(btp);

                    }
                });
       /* Glide.with(getContext())
                .load(data.getPic())
                .dontAnimate()
                .centerCrop()
                .override(300, 300)
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mSendPicture);*/
       /* Glide.with(getContext()).load(data.getPic()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mSendPicture.setImageBitmap(resource);
                LogUtil.d("加载成功！");
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                LogUtil.d("加载失败！");

            }
        });*/


        mSendPicture.setOnClickListener(v -> getContext().startActivity(new Intent(getContext(), ShowBigImage2.class).putExtra("url",url2)));
        timestamp.setText(data.getSendtime());
        if (data.getIssend().equals("2")){
            progressBar.setVisibility(View.VISIBLE);

        }else {
            progressBar.setVisibility(View.GONE);
        }

    }

}
