package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;
import okhttp3.ResponseBody;
import rx.Subscriber;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.view.PinchImageView;
import wgz.datatom.com.utillibrary.util.LogUtil;


/**
 * 查看大图
 * Created by wgz on 2016/10/21.
 */

public class ShowBigImage2 extends BaseActivity {
    @Bind(R.id.image)
    PinchImageView image;


    @Override
    public int getLayoutId() {
        return R.layout.activity_show_big_image;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        image.setOnClickListener(v -> finish());
        LogUtil.d("showimage2 : "+url);
        app.apiService.detrixPic(url, "0", "400")
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
                        image.setImageBitmap(btp);
                        LogUtil.d("show!"+s.byteStream().toString());

                    }
                });

      /*  Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.4f)
                .dontAnimate()
                .into(image);*/
    }

}
