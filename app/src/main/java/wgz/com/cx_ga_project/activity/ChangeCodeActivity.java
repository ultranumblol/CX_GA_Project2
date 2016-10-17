package wgz.com.cx_ga_project.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.uniview.airimos.util.MD5;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.util.SPUtils;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * 修改密码
 */
public class ChangeCodeActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.newpass2)
    EditText newpass2;
    @Bind(R.id.newpass)
    EditText newpass;
    @Bind(R.id.oldpass)
    EditText oldpass;
    @Bind(R.id.button)
    Button commit;
    @Bind(R.id.content_change_code)
    LinearLayout rootview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_code;
    }

    @Override
    public void initView() {
        toolbar.setTitle("修改密码");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RxView.clicks(commit)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        commitPass();
                    }
                });

    }

    private void commitPass() {
        String oldpwd = oldpass.getText().toString();
        String newpwd = newpass.getText().toString();
        String newpwd2 = newpass2.getText().toString();
        if (MD5.md5(oldpwd) .equals((String)SPUtils.get(app.getApp().getApplicationContext(), Constant.USERPASSWORD,""))){
            if (newpwd.equals(newpwd2)){

                app.apiService.changePass((String)SPUtils.get(app.getApp().getApplicationContext(), Constant.USERID,""), MD5.md5(newpwd))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtil.d("changepass result error: "+e.toString());
                            }

                            @Override
                            public void onNext(String s) {
                                LogUtil.d("changepass result : "+s);
                                if (s.contains("200")){
                                    SomeUtil.showSnackBar(rootview,"修改密码成功！");

                                }else SomeUtil.showSnackBar(rootview,"修改密码失败！");
                            }
                        });
            }
            else SomeUtil.showSnackBar(rootview,"两次密码不一致！");
        }else SomeUtil.showSnackBar(rootview,"原密码错误！");







    }


}
