package wgz.com.cx_ga_project.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.moxun.tagcloudlib.view.TagsAdapter;

import java.util.ArrayList;
import java.util.List;

import wgz.com.cx_ga_project.entity.CloudTag;

/**
 * Created by wgz on 2016/11/2.
 */

public class TextTagsAdapter extends TagsAdapter {

    private List<CloudTag.cTag> dataSet = new ArrayList<>();


    public TextTagsAdapter(List<CloudTag.cTag> data) {
        dataSet.clear();
        this.dataSet = data;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public View getView(final Context context, final int position, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setText(dataSet.get(position).getTagText());
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, dataSet.get(position).getTagText(), Toast.LENGTH_SHORT).show();
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
