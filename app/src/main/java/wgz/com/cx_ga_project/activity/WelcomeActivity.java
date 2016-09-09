package wgz.com.cx_ga_project.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Build;

import android.view.WindowManager;
import android.widget.ImageView;

import butterknife.Bind;
import rx.Observable;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.observable.AnimatorOnSubscribe;
import wgz.com.cx_ga_project.util.SPBuild;

import wgz.datatom.com.utillibrary.util.LogUtil;


import static rx.schedulers.Schedulers.io;

/**
 * Created by wgz on 2016/8/8.
 */

public class WelcomeActivity extends BaseActivity {
    @Bind(R.id.img_welcome)
    ImageView imgWelcome;

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Animator animation = AnimatorInflater.loadAnimator(this, R.animator.welcome_animator);
        animation.setTarget(imgWelcome);
        Observable<String> observable =  Observable.create(new AnimatorOnSubscribe(animation));
        observable
                .subscribeOn(AndroidSchedulers.mainThread())//指定订阅的Observable对象的call方法运行在ui线程中
                .observeOn(AndroidSchedulers.mainThread())//最后统一回到UI线程中处理
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.e("onCompleted!");
                        startActivity(new Intent(WelcomeActivity.this,HomeActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("error:"+e);
                        startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                        finish();
                    }

                    @Override
                    public void onNext(String str) {
                        LogUtil.e(str);
                        saveinfo();
                    }
                });
    }



    @Override
    public void initView() {
        //StatusBarUtil.setTransparent(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    private void saveinfo() {
        new SPBuild(getApplicationContext())
                .addData(Constant.LOGINTIME, System.currentTimeMillis())
                .build();
    }
}
