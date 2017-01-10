package wgz.com.cx_ga_project.activity;

import android.content.Intent;

import android.view.View;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.view.PinchImageView;


/**
 * 查看大图
 * Created by wgz on 2016/10/21.
 */

public class ShowBigImage extends BaseActivity {
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


        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.4f)
                .dontAnimate()
                .into(image);
    }

}
