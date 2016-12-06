package wgz.com.cx_ga_project.adapter.chatAdapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import wgz.com.cx_ga_project.adapter.AddPictureViewHolder;
import wgz.com.cx_ga_project.adapter.CallbackPicVideoViewHolder;
import wgz.com.cx_ga_project.adapter.MyRecyclerArrayAdapter;

/**
 * Created by wgz on 2016/8/16.
 */

public class CallBackPicVideoAdapter extends MyRecyclerArrayAdapter<String> {
    public CallBackPicVideoAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CallbackPicVideoViewHolder(parent);
    }
}
