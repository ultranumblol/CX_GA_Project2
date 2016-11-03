package wgz.com.cx_ga_project.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.moxun.tagcloudlib.view.TagCloud;
import com.moxun.tagcloudlib.view.TagCloudView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.TextTagsAdapter;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.entity.CloudTag;
import wgz.com.cx_ga_project.util.SomeUtil;

public class WorkLogCloudActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tag_cloud)
    TagCloudView tagCloud;
    @Bind(R.id.content_work_log_cloud)
    RelativeLayout rootview;
    private TextTagsAdapter textTagsAdapter;
    private List<CloudTag.cTag> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_work_log_cloud;
    }

    @Override
    public void initView() {
        toolbar.setTitle("工作云标签");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tagCloud.setBackgroundColor(Color.WHITE);



        initData();

    }

    private void initData() {
        for (int i = 0; i<26 ; i++){

            CloudTag.cTag tag = new CloudTag().new cTag();
            tag.setTagText("加班" +(new Random().nextInt(7)+1)+"小时！");
            list.add(tag);
        }
        textTagsAdapter = new TextTagsAdapter(list);
        tagCloud.setAdapter(textTagsAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cloudtag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.cloudtag_week:
                SomeUtil.showSnackBar(rootview,"查看1周");
                break;
            case R.id.cloudtag_month:
                SomeUtil.showSnackBar(rootview,"查看1月");

                break;
            case R.id.cloudtag_year:
                SomeUtil.showSnackBar(rootview,"查看1年");

                break;
            case R.id.cloudtag_selfs:
                SomeUtil.showSnackBar(rootview,"查看1自定义");

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
