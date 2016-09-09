package wgz.com.cx_ga_project.observable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import rx.Observable;
import rx.Subscriber;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.util.SPUtils;
import wgz.com.cx_ga_project.util.TimeUtils;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static com.jakewharton.rxbinding.internal.Preconditions.checkUiThread;

/**
 * Created by wgz on 2016/8/8.
 */

public class AnimatorOnSubscribe implements Observable.OnSubscribe<String> {
    final Animator animator;
    private static final int mTimeDifference = TimeUtils.HOUR;

    //构造器传入Animator
    public AnimatorOnSubscribe(Animator animator) {
        this.animator = animator;
    }


    @Override
    public void call(final Subscriber<? super String> subscriber) {
        checkUiThread();
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                subscriber.onNext("save");

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                boolean isLogin = (Boolean) SPUtils.get(app.getApp().getApplicationContext(), Constant.ISLOGIN, false);
                Long lastTime = (Long) SPUtils.get(app.getApp().getApplicationContext(), Constant.LOGINTIME, 0L);
                long dTime = System.currentTimeMillis() - lastTime;
                LogUtil.e("dTime= " + dTime + " default: " + mTimeDifference);
                LogUtil.e("isLogin: "+isLogin);
                // TODO: 2016/8/19 是否登陆的判断先写为true
                if (true) {
                    if (dTime < mTimeDifference) {
                        subscriber.onCompleted();
                    }
                } else subscriber.onError(new Exception("error"));
            }
        };

        animator.addListener(animatorListenerAdapter);
        animator.start();//先绑定监听器再开始

    }
}
