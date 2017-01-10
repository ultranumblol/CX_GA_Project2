package wgz.com.cx_ga_project.adapter;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import okhttp3.ResponseBody;
import rx.Subscriber;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.activity.ShowBigImage;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * Created by wgz
 */

public class CallbackPicVideoViewHolder extends BaseViewHolder<String> {
    private ImageView imageView;


    public CallbackPicVideoViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_picvideo);
        imageView = $(R.id.picvideo_pic);
    }

    @Override
    public void setData(final String data) {

        imageView.setOnClickListener(v ->
                getContext().startActivity(new Intent(getContext()
                        , ShowBigImage.class).putExtra("url", data)));

        app.apiService.detrixPic(data, "0", "400")
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
                        Glide.with(getContext())
                                .load(SomeUtil.input2byte(s.byteStream()))
                                .dontAnimate()
                                .centerCrop()
                                .override(300, 300)
                                .thumbnail(0.1f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);

                    }
                });

               /* Glide.with(getContext())
                        .load(data)
                        .thumbnail(0.7f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()

                        .dontAnimate()
                        .placeholder(R.drawable.ic_photo_grey_400_48dp)
                        .error(R.drawable.ic_broken_image_grey_400_48dp)
                        .into(imageView);
*/
    }


    //imageView.setImageResource(R.drawable.ic_add_box_red_400_36dp);

}
