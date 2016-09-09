package wgz.com.cx_ga_project.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.fragment.PhotoPagerFragment;
import wgz.com.cx_ga_project.fragment.PhotoPickerFragment;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class PickPhotoActivity extends BaseActivity {
    public static final String HTTP_URL = "http_url";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.content_pick_photo)
    LinearLayout mRootview;
    /**
     * 默认图片选择数量
     */
    private int mDefaultCount;
    /**
     * 默认搜索路径
     */
    private String mDefaultSearchPath;
    /**
     * 服务器端地址
     */
    private String mServerUrl;
    /**
     * 控件
     */
    private PhotoPickerFragment mPickerFragment;
    private PhotoPagerFragment mPagerFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pick_photo;
    }

    @Override
    public void initView() {
        toolbar.setTitle("选择图片");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDefaultCount = getIntent().getIntExtra(PhotoPickerFragment.EXTRA_SELECT_COUNT, 9);
        mDefaultSearchPath = getIntent().getStringExtra(PhotoPickerFragment.EXTRA_DEFAULT_SELECTED_LIST);
        mServerUrl = getIntent().getStringExtra(HTTP_URL);
        if (mPickerFragment == null) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(outMetrics);

            mPickerFragment = PhotoPickerFragment.newInstance(outMetrics.heightPixels, mDefaultCount, mDefaultSearchPath);
            getSupportFragmentManager().beginTransaction()
                    .add(com.sleepyzzz.photo_selector.R.id.container, mPickerFragment)
                    .commit();
        }
        initEvents();
    }

    private void initEvents() {
    }


    @Override
    public void onBackPressed() {
        if (mPagerFragment != null && mPagerFragment.isVisible()) {
            mPagerFragment.runExitAnimation(new Runnable() {

                @Override
                public void run() {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        getSupportFragmentManager().popBackStack();
                    }
                }
            });
        } else {

            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.uploadpic, menu);
        return true;
    }

    /**
     * 暴露接口
     *
     * @param context
     * @param maxSelectCount-限制的最大图片选择数量
     * @param searchPath-指定图片搜索路径(若为null-表示搜索所有图片)
     * @param url-服务器地址
     */
    public static void actionStart(Context context, int maxSelectCount, String searchPath, String url) {
        Intent intent = new Intent(context, PickPhotoActivity.class);
        intent.putExtra(PhotoPickerFragment.EXTRA_SELECT_COUNT, maxSelectCount);
        intent.putExtra(PhotoPickerFragment.EXTRA_DEFAULT_SELECTED_LIST, searchPath);
        intent.putExtra(HTTP_URL, url);
        context.startActivity(intent);

    }

    public void addPhotoPagerFragment(PhotoPagerFragment photoPagerFragment) {

        this.mPagerFragment = photoPagerFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(com.sleepyzzz.photo_selector.R.id.container, this.mPagerFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.uploadpic_finish) {
            // TODO: 2016/8/16
            List<String> paths = new ArrayList<>();
            paths = mPickerFragment.getPhotoPickerAdapter().getmSelectedImage();
            setResult(0, new Intent(PickPhotoActivity.this, AddJQActivity.class).
                    putStringArrayListExtra("paths", (ArrayList<String>) paths)
                    .putExtra("result", "addpic"));

            setResult(1, new Intent(PickPhotoActivity.this, AddWorkLogActivity.class).
                    putStringArrayListExtra("paths", (ArrayList<String>) paths)
                    .putExtra("result", "addpic"));

            LogUtil.e("paths== "+paths.toString());
            finish();
            return true;
        }
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

}
