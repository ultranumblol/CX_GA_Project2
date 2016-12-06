package wgz.com.cx_ga_project.adapter;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.activity.ShowBigImage;

/**
 * Created by wgz
 */

public class CallbackPicVideoViewHolder extends BaseViewHolder<String> {
    private ImageView imageView;


    public CallbackPicVideoViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_picvideo);
        imageView =  $(R.id.picvideo_pic);
    }

    @Override
    public void setData(final String data) {

                imageView.setOnClickListener(v ->
                        getContext().startActivity(new Intent(getContext()
                                , ShowBigImage.class).putExtra("url",data)));

                Glide.with(getContext())
                        .load(data)
                        .thumbnail(0.7f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()

                        .dontAnimate()
                        .placeholder(R.drawable.ic_photo_grey_400_48dp)
                        .error(R.drawable.ic_broken_image_grey_400_48dp)
                        .into(imageView);

            }




        //imageView.setImageResource(R.drawable.ic_add_box_red_400_36dp);

}
