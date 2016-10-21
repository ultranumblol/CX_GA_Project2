package wgz.com.cx_ga_project.adapter;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.activity.ShowBigImage;

/**
 * Created by wgz on 2016/8/16.
 */

public class AddPictureViewHolder extends BaseViewHolder<String> {
    private ImageView imageView;


    public AddPictureViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_addpic);
        imageView = (ImageView) $(R.id.addpic);
    }

    @Override
    public void setData(final String data) {
        if (data.equals("end")){
            //imageView.setImageResource(R.drawable.ic_add_box_red_400_36dp);
            Glide.with(getContext())
                    .load(R.drawable.ic_add_box_red_400_48dp)
                    .override(78,78)
                    .dontAnimate()
                    .into(imageView);
        }else{

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getContext().startActivity(new Intent(getContext(), ShowBigImage.class).putExtra("url",data));
                }
            });

            Glide.with(getContext())
                    .load(data)
                    .thumbnail(0.7f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()

                    .dontAnimate()
                    .placeholder(R.drawable.ic_photo_grey_400_48dp)
                    .error(R.drawable.ic_broken_image_grey_400_48dp)
                    .override(80,80)
                    .into(imageView);
        }

        //imageView.setImageResource(R.drawable.ic_add_box_red_400_36dp);
    }
}
