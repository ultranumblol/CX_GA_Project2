package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.moxun.tagcloudlib.view.TagCloudView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.TextTagsAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.entity.CloudTag;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class WorkLogCloudActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tag_cloud)
    TagCloudView tagCloud;
    @Bind(R.id.content_work_log_cloud)
    RelativeLayout rootview;
    @Bind(R.id.shoudong_tag)
    CardView shoudongTag;
    @Bind(R.id.peizhi_tag)
    CardView peizhiTag;
    private TextTagsAdapter textTagsAdapter;
    private List<CloudTag.Re> list = new ArrayList<>();
    private String policeid = "";
    private String wordtype = "1";

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
        Intent intent = getIntent();
        policeid = intent.getStringExtra("policeid");


        initData();

    }

    private void initData() {
        getdata("6");


    }

    private void getdata(String mounth) {
        app.apiService.getWorkLogKeyWords(policeid, wordtype, mounth)
                .compose(RxUtil.<CloudTag>applySchedulers())
                .subscribe(new Subscriber<CloudTag>() {
                    @Override
                    public void onStart() {
                        list.clear();
                    }

                    @Override
                    public void onCompleted() {
                        textTagsAdapter = new TextTagsAdapter(list, policeid);
                        tagCloud.setAdapter(textTagsAdapter);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CloudTag s) {
                        LogUtil.d("reuslt : " + s.toString());
                        list = s.getRes();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cloudtag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.cloudtag_week:
                getdata("3");
                break;
            case R.id.cloudtag_month:
                getdata("6");

                break;
            case R.id.cloudtag_year:
                getdata("12");

                break;
          /*  case R.id.cloudtag_selfs:
                SomeUtil.showSnackBar(rootview, "查看自定义");
*/

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.shoudong_tag, R.id.peizhi_tag})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shoudong_tag:
                wordtype="1";
                getdata("6");
                break;
            case R.id.peizhi_tag:
                wordtype="2";
                getdata("6");
                break;
        }
    }
}
