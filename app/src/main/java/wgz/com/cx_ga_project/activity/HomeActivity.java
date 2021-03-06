package wgz.com.cx_ga_project.activity;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
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
import android.util.Log;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.entity.AllDep;
import wgz.com.cx_ga_project.entity.AppVersion;
import wgz.com.cx_ga_project.entity.DepPeople;
import wgz.com.cx_ga_project.entity.Deps;
import wgz.com.cx_ga_project.service.NewJQMsgPush;
import wgz.com.cx_ga_project.service.UpdataService;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SPBuild;
import wgz.com.cx_ga_project.util.SPUtils;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;
import wgz.datatom.com.utillibrary.util.ToastUtil;

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
    @Bind(R.id.id_tagcloudlog)
    CardView idTagcloudlog;
    private JQReceiver receiver;
    private BadgeView badgeView;
    private boolean isleader = false;
    private boolean hasupper = true;
    private List<Deps.Re> deplist = new ArrayList<>();
    private List<DepPeople.Re> deppeopleplist = new ArrayList<>();
    private Subscription rxSubscription;
    private CompositeSubscription msubscription;//管理所有的订阅
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        badgeView = new BadgeView(HomeActivity.this);
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
        Resources resource = getBaseContext().getResources();
        ColorStateList csl = resource.getColorStateList(R.drawable.navigation_menu_item_color);
        navView.setItemTextColor(csl);
        navView.setItemIconTintList(csl);
        //LogUtil.d("userhead_url: " +SPUtils.get(app.getApp().getApplicationContext(), Constant.USERHEAD, ""));
        ImageView userhead = (ImageView) navView.getHeaderView(0).findViewById(R.id.imageView);
        // 2016/9/18 获取用户头像地址
        //String userheadurl = "http://"+SPUtils.get(app.getApp().getApplicationContext(), Constant.USERHEAD, "");
        //LogUtil.d("userheadurl : "+userheadurl);
        Glide.with(this)
                .load(Constant.USERHEADURL)
                .placeholder(R.drawable.ic_account_circle_gray_48dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.4f)
                .dontAnimate()
                .into(userhead);
        isleader = (boolean) SPUtils.get(app.getApp().getApplicationContext(), Constant.ISLEADER, false);
        TextView username = (TextView) navView.getHeaderView(0).findViewById(R.id.username_head);
        TextView userdepartment = (TextView) navView.getHeaderView(0).findViewById(R.id.departmant_head);
        username.setText((String) SPUtils.get(app.getApp().getApplicationContext(), Constant.USERNAME, "未知"));
        userdepartment.setText(SomeUtil.getDepartName());

        // startService(new Intent(this, GetNewMsgService.class));

        if (isleader){
            idShenhe.setVisibility(View.VISIBLE);
            idXiashu.setVisibility(View.VISIBLE);

        }else{
            idXiashu.setVisibility(View.GONE);
            idShenhe.setVisibility(View.GONE);
        }
        startService(new Intent(this, NewJQMsgPush.class));

        hasupper = (boolean) SPUtils.get(app.getApp().getApplicationContext(), Constant.HASUPPER, true);
        LogUtil.d("hasupper "+hasupper);
        if (!hasupper){
            showSetUpper();
        }
        // showSetUpper();
       /* rxSubscription = RxBus.getDefault().toObservable(String.class)
                .subscribe(s -> {
                        SomeUtil.showSnackBar(homeRootView,s);

                });*/

    }

    private void showSetUpper() {
        app.apiService.getAllDep()
        .compose(RxUtil.applySchedulers())
        .subscribe(new Subscriber<Deps>() {
            @Override
            public void onCompleted() {
                final String[] parts = new String[deplist.size()];
                final String[] depid = {""};
                for (int i = 0; i < deplist.size(); i++) {
                    parts[i] = deplist.get(i).getDepartSimplename();
                }

                final android.support.v7.app.AlertDialog.Builder builder =
                        new android.support.v7.app.AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("还没有设置您的上级，是否现在设置？")
                        .setPositiveButton("确认", (dialog, which) -> builder.setTitle("请选择上级所在部门")
                                .setItems(parts, (dialog12, which12) -> {
                                    for (int i = 0 ; i<deplist.size() ; i++){
                                        if (parts[which12].equals(deplist.get(i).getDepartSimplename())){
                                            depid[0] = deplist.get(i).getDepartmentid();
                                        }
                                    }
                                    LogUtil.d("depid:"+depid[0]);
                                    app.apiService.getDepMember(depid[0])
                                            .compose(RxUtil.applySchedulers())
                                            .subscribe(new Subscriber<DepPeople>() {
                                                @Override
                                                public void onCompleted() {
                                                    final String[] peoples = new String[deppeopleplist.size()];
                                                    final String[] upperid = {""};
                                                    for (int i = 0; i < deppeopleplist.size(); i++) {
                                                        peoples[i] = deppeopleplist.get(i).getPolicename();
                                                    }
                                                    builder.setTitle("请选择上级")
                                                            .setItems(peoples, (dialog1, which1) -> {
                                                            for (int i = 0 ; i<deppeopleplist.size(); i++){
                                                                if (peoples[which1].equals(deppeopleplist.get(i).getPolicename()))
                                                                {
                                                                    upperid[0] = deppeopleplist.get(i).getPolid();
                                                                }
                                                            } LogUtil.d("upperid: "+upperid[0]);
                                                                app.apiService.setUpper(SomeUtil.getUserId(),upperid[0])
                                                                        .compose(RxUtil.applySchedulers())
                                                                        .subscribe(new Subscriber<String>() {
                                                                            @Override
                                                                            public void onCompleted() {

                                                                            }

                                                                            @Override
                                                                            public void onError(Throwable e) {

                                                                            }

                                                                            @Override
                                                                            public void onNext(String s) {
                                                                                LogUtil.d("result:"+s);
                                                                                if (s.contains("200")){
                                                                                    SomeUtil.showSnackBar(homeRootView,"设置上级成功！");

                                                                                }else {
                                                                                    SomeUtil.showSnackBar(homeRootView,"设置上级失败！");
                                                                                }
                                                                            }
                                                                        });

                                                            }).setNegativeButton("取消",null).show();



                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    LogUtil.d("depPeople error:"+e.toString());
                                                }

                                                @Override
                                                public void onNext(DepPeople s) {
                                                    deppeopleplist = s.getRes();
                                                }
                                            });
                                }).setNegativeButton("取消",null).setPositiveButton(null,null)
                                .show())
                        .setNegativeButton("稍后设置",null)
                        .show();

            }

            @Override
            public void onError(Throwable e) {
               // SomeUtil.checkHttpException(HomeActivity.this,e,homeRootView);
            }

            @Override
            public void onNext(Deps s) {


                deplist = s.getRes();
                LogUtil.d("deplist :"+deplist.toString());
            }
        });



        //app.apiService.getDepMember();




    }

    @OnClick({R.id.id_fighttrack,R.id.id_tagcloudlog, R.id.fab, R.id.to_jiechujing, R.id.id_toWorkLog, R.id.id_myscheduling, R.id.id_myApply, R.id.id_shenhe, R.id.id_xiashu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_fighttrack:
                startActivity(new Intent(HomeActivity.this, MyWorkingTrackActivity.class));
                break;
            case R.id.id_toWorkLog:
                startActivity(new Intent(HomeActivity.this, WorkLogActivity.class));
                break;
            case R.id.id_tagcloudlog:
                startActivity(new Intent(HomeActivity.this, WorkLogCloudActivity.class).putExtra("policeid",SomeUtil.getUserId()));
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
                //  社会信息采集功能
                startActivity(new Intent(HomeActivity.this, SICTypeActivity.class));
                break;
            case R.id.to_jiechujing:
                //接处警作战功能
                LogUtil.d("count :" + SomeUtil.getNewJQMSgCount());
                new SPBuild(getApplicationContext())
                        .addData(Constant.NEWJQCOUNT, 0).build();
                LogUtil.d("count :" + SomeUtil.getNewJQMSgCount());
                BadgeViewCount(0);
                startActivity(new Intent(HomeActivity.this, StartNewFightActivity.class).putExtra("title", "new"));

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
                    .thumbnail(0.3f)
                    .dontAnimate()
                    .into(imageView);
            //点击事件
            imageView.setOnClickListener(v -> {
                //SomeUtil.showSnackBar(homeRootView, "维护中。。。");
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getUrl())));
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
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        BadgeViewCount(SomeUtil.getNewJQMSgCount());
        receiver = new JQReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("service.JQpush");
        registerReceiver(receiver, filter);
        // LogUtil.d("广播注册成功！");
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

            startActivity(new Intent(HomeActivity.this, TestPlayerActivity.class));
            //startActivity(new Intent(HomeActivity.this, LoginActivity.class));
           //startActivity(new Intent(HomeActivity.this, NewMsgActivity.class));


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
            Subscription i  =app.apiService.checkVersion().subscribeOn(Schedulers.io())
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
                                    //  下载更新
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                                    alertDialog.setTitle("检查到新版本，是否更新？")
                                            .setMessage(appVersion.getRes().get(0).getApkUpdatelog())
                                            .setPositiveButton("立即更新", (dialog, which) -> update(appVersion)).setNegativeButton("稍后更新", (dialog, which) -> {

                                    }).show();

/*
                                    new SPBuild(getApplicationContext())
                                            .addData(Constant.UPDATEURL, url).build();
                                  */

                                } else {
                                    SomeUtil.showSnackBar(homeRootView, "当前已经是最新版本！");
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
            //  扫一扫
            startActivityForResult(new Intent(HomeActivity.this, SaoYiSaoActivity.class), 9);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void update(AppVersion appVersion) {

        String url = appVersion.getRes().get(0).getApkUrl();
        url.replaceAll("\\\\", "");
        String  filename = appVersion.getRes().get(0).getApkName();
        LogUtil.d("url : " + url);
        app.apiService.downloadFileWithFixedUrl(url)
                .compose(RxUtil.applySchedulers())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onStart() {
                        SomeUtil.showSnackBarLong(homeRootView,"正在后台下载，稍后提示安装");
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        SomeUtil.showSnackBar(homeRootView,"下载错误！");
                        LogUtil.d("error:"+e.toString());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        LogUtil.d("size :"+responseBody.contentLength() );
                        writeResponseBodyToDisk(responseBody,filename);
                        installAPK(filename);
                    }
                });



    }

    /**
     * 安装apk文件
     */
    private void installAPK(String  filename) {

        // 通过Intent安装APK文件
        Intent intents = new Intent();

        intents.setAction("android.intent.action.VIEW");
        intents.addCategory("android.intent.category.DEFAULT");
        intents.setType("application/vnd.android.package-archive");
        intents.setData(Uri.fromFile(new File(getExternalFilesDir(null) + File.separator + filename)));
        intents.setDataAndType(Uri.fromFile(new File(getExternalFilesDir(null) + File.separator + filename)), "application/vnd.android.package-archive");
        intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //android.os.Process.killProcess(android.os.Process.myPid());
        // 如果不加上这句的话在apk安装完成之后点击单开会崩溃

        startActivity(intents);

    }


    private boolean writeResponseBodyToDisk(ResponseBody body,String filename) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + filename);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                   // Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
    /**
     * 清除所有图片缓存
     */
    public void clearImageAllCache() {

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("清理缓存")
                .setMessage("缓存大小：" + getCacheSize() + "是否清除?")
                .setPositiveButton("确认", (dialog, which) -> Observable.create(new Observable.OnSubscribe<Void>() {
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
                        })).setNegativeButton("取消", null).show();
        Glide.get(getApplicationContext()).clearMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(msubscription != null){
            this.msubscription.unsubscribe();
        }
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

    private class JQReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String msg = bundle.getString("jq");
            int count = bundle.getInt("jqcount");
            if (msg.equals("newjq")) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                // 通过Notification.Builder来创建通知，注意API Level
                // API16之后才支持
                Notification notify3 = null; // 需要注意build()是在APIlevel16及之后增加的，API11可以使用getNotificatin()来替代
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    notify3 = new Notification.Builder(context)
                            .setSound(ringUri).build();
                }

                notify3.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
                manager.notify(1, notify3);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
                BadgeViewCount(count);
            }
        }
    }

    private void BadgeViewCount(int count) {
        if (count == 0) {

            badgeView.setVisibility(View.GONE);
        } else {
            badgeView.setVisibility(View.VISIBLE);
            //btn是控件
            badgeView.setTargetView(mToJiechujing);
            //设置相对位置
            badgeView.setBadgeMargin(0, 22, 10, 0);
            //设置显示未读消息条数
            badgeView.setBadgeCount(count);
        }


    }


}
