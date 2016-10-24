package wgz.com.cx_ga_project.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jauker.widget.BadgeView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.entity.AppVersion;
import wgz.com.cx_ga_project.service.UpdataService;
import wgz.com.cx_ga_project.util.SPBuild;
import wgz.com.cx_ga_project.util.SPUtils;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static wgz.com.cx_ga_project.util.SomeUtil.getFormatSize;

/**
 * 主页
 * Created by wgz on 2016/8/1.
 */
@SuppressWarnings("ResourceType")
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar_home)
    Toolbar toolbarHome;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.id_colltoollayout)
    CollapsingToolbarLayout idColltoollayout;
    @Bind(R.id.home_rootView)
    CoordinatorLayout homeRootView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.to_jiechujing)
    CardView mToJiechujing;
    @Bind(R.id.rollPagerView)
    RollPagerView rollPagerView;
    @Bind(R.id.id_appbar_home)
    AppBarLayout idAppbarHome;
    @Bind(R.id.id_toWorkLog)
    CardView idToWorkLog;
    @Bind(R.id.id_myscheduling)
    CardView idMyscheduling;
    @Bind(R.id.id_myApply)
    CardView idMyApply;
    @Bind(R.id.id_shenhe)
    CardView idShenhe;
    @Bind(R.id.id_xiashu)
    CardView idXiashu;
    @Bind(R.id.id_fighttrack)
    CardView idFighttrack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        rollPagerView.setHintView(new ColorPointHintView(this, Color.WHITE, Color.GRAY));
        rollPagerView.setAdapter(new BannerAdapter());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbarHome, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.setStatusBarBackground(Color.TRANSPARENT);
        toolbarHome.setTitle("");
        setSupportActionBar(toolbarHome);
        toolbarHome.setTitleTextColor(Color.WHITE);
        idColltoollayout.setCollapsedTitleTextColor(Color.WHITE);
        idColltoollayout.setExpandedTitleColor(Color.WHITE);
        navView.setNavigationItemSelectedListener(this);
        Resources resource = (Resources) getBaseContext().getResources();
        ColorStateList csl = (ColorStateList) resource.getColorStateList(R.drawable.navigation_menu_item_color);
        navView.setItemTextColor(csl);
        navView.setItemIconTintList(csl);
        //LogUtil.d("userhead_url: " +SPUtils.get(app.getApp().getApplicationContext(), Constant.USERHEAD, ""));
        ImageView userhead = (ImageView) navView.getHeaderView(0).findViewById(R.id.imageView);
        // TODO: 2016/9/18 获取用户头像地址
        //String userheadurl = "http://"+SPUtils.get(app.getApp().getApplicationContext(), Constant.USERHEAD, "");
        //LogUtil.d("userheadurl : "+userheadurl);
        Glide.with(this)
                .load(Constant.USERHEADURL)
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.4f)
                .dontAnimate()
                .into(userhead);

        TextView username = (TextView) navView.getHeaderView(0).findViewById(R.id.username_head);
        TextView userdepartment = (TextView) navView.getHeaderView(0).findViewById(R.id.departmant_head);
        username.setText((String) SPUtils.get(app.getApp().getApplicationContext(), Constant.USERNAME, "未知"));
        userdepartment.setText((String) SPUtils.get(app.getApp().getApplicationContext(), Constant.USEROFFICENAME, "未知"));


        BadgeView badgeView = new BadgeView(this);
        //btn是控件
        badgeView.setTargetView(mToJiechujing);
        //设置相对位置
        badgeView.setBadgeMargin(0, 22, 10, 0);
        //设置显示未读消息条数
        badgeView.setBadgeCount(3);
    }

    @OnClick({R.id.id_fighttrack, R.id.fab, R.id.to_jiechujing, R.id.id_toWorkLog, R.id.id_myscheduling, R.id.id_myApply, R.id.id_shenhe, R.id.id_xiashu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_fighttrack:
                startActivity(new Intent(HomeActivity.this, MyWorkingTrackActivity.class));
                break;
            case R.id.id_toWorkLog:
                startActivity(new Intent(HomeActivity.this, WorkLogActivity.class));
                break;
            case R.id.id_myscheduling:
                startActivity(new Intent(HomeActivity.this, SchedulingActivity.class));
                break;
            case R.id.id_myApply:
                startActivity(new Intent(HomeActivity.this, MyWorkApplyActivity.class));
                break;
            case R.id.id_shenhe:
                startActivity(new Intent(HomeActivity.this, MyApprovalActivity.class));
                break;
            case R.id.id_xiashu:
                startActivity(new Intent(HomeActivity.this, MySubordinateActivity.class));
                break;
            case R.id.fab:
                // TODO: 2016/8/3 社会信息采集功能
                app.apiService.getdatrixPic().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(String s) {
                                LogUtil.d("datrixPix : " + s.toString());
                            }
                        });
                //uploadpicsTest();
                //startActivity(new Intent(HomeActivity.this, FullscreenActivity.class));
                // Snackbar.make(homeRootView, "社会信息采集开发中。。", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.to_jiechujing:
                // TODO: 2016/8/5 接处警作战功能
                startActivity(new Intent(HomeActivity.this, StartNewFightActivity.class).putExtra("title", "new"));
                //Snackbar.make(homeRootView, "开发中。。。", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }
   /* private ArrayList<Integer> initData() {
        ArrayList<Integer> list = new ArrayList<>();

            list.add(R.drawable.ad1);
            list.add(R.drawable.ad2);
        list.add(R.drawable.welcome);
        return list;
    }*/

    private class BannerAdapter extends StaticPagerAdapter {
        // private List<Integer> list;
        int[] imageId = new int[]{R.drawable.ad11, R.drawable.ad22, R.drawable.ad33};

        public BannerAdapter() {
            int[] src = imageId;
        }

        @Override
        public View getView(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(HomeActivity.this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setImageResource(R.drawable.calendar_bg);
            //加载图片
            Glide.with(HomeActivity.this)
                    .load(imageId[position])
                    .placeholder(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.4f)
                    .dontAnimate()
                    .into(imageView);
            //点击事件
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //SomeUtil.showSnackBar(homeRootView, "维护中。。。");
                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getUrl())));
                }
            });
            return imageView;
        }

        @Override
        public int getCount() {
            return imageId.length;
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            //startActivity(new Intent(HomeActivity.this, CamPlayerActivity.class));
            //startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            startActivity(new Intent(HomeActivity.this, NewMsgActivity.class));
            return true;
        }
        if (id == android.R.id.home) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.openDrawer(GravityCompat.START);

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_mypic) {
            //我的二维码
            startActivity(new Intent(HomeActivity.this, FullscreenActivity.class));
            // Handle the camera action
        } else if (id == R.id.nav_changepass) {
            startActivity(new Intent(HomeActivity.this, ChangeCodeActivity.class));

        } else if (id == R.id.nav_help) {
            //帮助与反馈
            startActivity(new Intent(HomeActivity.this, HelpAndFeedBackActivity.class));


        } else if (id == R.id.nav_updateAPP) {
            final int versionCode = SomeUtil.getVersionCode(this);
            LogUtil.d("versionCode:" + versionCode);
            app.apiService.checkVersion().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<AppVersion>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.d("checkVersion error:  " + e.toString());
                        }

                        @Override
                        public void onNext(AppVersion appVersion) {
                            if (appVersion.getCode().equals(200)) {
                                String code = appVersion.getRes().get(0).getApkVersioncode();
                                int a = Integer.parseInt(code);
                                LogUtil.d("serviceCode : " + a);
                                if (a > versionCode) {
                                    // TODO: 2016/10/10 下载更新
                                    String url = appVersion.getRes().get(0).getApkUrl();
                                    url.replaceAll("\\\\","");
                                    new SPBuild(getApplicationContext())
                                            .addData(Constant.UPDATEURL,url).build();
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                                    alertDialog.setTitle("版本更新")
                                            .setMessage("检查到新版本，现在更新吗？")
                                            .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    startService(new Intent(HomeActivity.this, UpdataService.class));


                                                }
                                            }).setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();

                                }else{
                                    SomeUtil.showSnackBar(homeRootView,"当前已经是最新版本！");
                                }

                            }
                        }
                    });


            //startService(new Intent(HomeActivity.this, UpdataService.class));


        } else if (id == R.id.nav_about) {
            startActivity(new Intent(HomeActivity.this, AboutAppActivity.class));


        } else if (id == R.id.nav_clearcache) {
            clearImageAllCache();

        } else if (id == R.id.nav_logout) {
            SPUtils.clear(getApplicationContext());
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            HomeActivity.this.finish();


        } else if (id == R.id.nav_saoyisao) {
            // TODO: 2016/9/3 扫一扫
            startActivityForResult(new Intent(HomeActivity.this, SaoYiSaoActivity.class),9);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 清除所有图片缓存
     */
    public void clearImageAllCache() {

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("清理缓存")
                .setMessage("缓存大小：" + getCacheSize() + "是否清除?")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Observable.create(new Observable.OnSubscribe<Void>() {
                            @Override
                            public void call(Subscriber<? super Void> subscriber) {
                                subscriber.onNext(null);
                                subscriber.onCompleted();
                            }
                        }).subscribeOn(Schedulers.newThread())
                                .observeOn(Schedulers.newThread())
                                .subscribe(new Subscriber<Void>() {
                                    @Override
                                    public void onCompleted() {
                                        LogUtil.d("clearCache_oncompleted");
                                        SomeUtil.showSnackBar(homeRootView, "清理完成！");

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(Void aVoid) {
                                        LogUtil.d("clearCache_onNext");
                                        Glide.get(getApplicationContext()).clearDiskCache();
                                        //
                                    }
                                });
                    }
                }).setNegativeButton("取消", null).show();
        Glide.get(getApplicationContext()).clearMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public String getCacheSize() {
        try {
            return getFormatSize(getFolderSize(new File(getApplication().getCacheDir() + "/image_manager_disk_cache")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    public long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }




}
