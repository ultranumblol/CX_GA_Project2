package wgz.com.cx_ga_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.moxun.tagcloudlib.view.TagsAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import wgz.com.cx_ga_project.activity.WorkCloudListActivity;
import wgz.com.cx_ga_project.activity.WorkLogCloudActivity;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.entity.CloudTag;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * Created by wgz on 2016/11/2.
 */

public class TextTagsAdapter extends TagsAdapter {

    private List<CloudTag.Re> dataSet = new ArrayList<>();
    private String policeid ;

    public TextTagsAdapter(List<CloudTag.Re> data,String policeid) {
        dataSet.clear();
        this.dataSet = data;
        this.policeid =policeid;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public View getView(final Context context, final int position, ViewGroup parent) {
        final TextView tv = new TextView(context);
        tv.setText(dataSet.get(position).getKey());
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                app.getApp().startActivity(new Intent(context, WorkCloudListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS).putExtra("key",tv.getText().toString())
                .putExtra("policeid",policeid));




            }
        });


        return tv;
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return position % 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {
        view.setBackgroundColor(Color.parseColor("#00ffffff"));
        //view.setBackgroundResource(R.drawable.cloudbg);
        ((TextView) view).setTextColor(themeColor);
    }
}
