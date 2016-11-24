package wgz.com.cx_ga_project.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.uniview.airimos.util.MD5;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.entity.Subordinate;
import wgz.com.cx_ga_project.entity.UserInfo;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SPBuild;
import wgz.com.cx_ga_project.util.SPUtils;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;
import wgz.datatom.com.utillibrary.util.ToastUtil;

import static wgz.com.cx_ga_project.base.Constant.DATAFUSION;

/**
 * 登陆
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.actv_username)
    AutoCompleteTextView actvUsername;
    @Bind(R.id.edit_password)
    EditText editPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.progress_login)
    ProgressBar progressLogin;
    @Bind(R.id.scroll_login_form)
    ScrollView scrollLoginForm;
    @Bind(R.id.login_rootview)
    FrameLayout rootview;
    private String userhead;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        RxTextView.editorActions(editPassword, integer -> integer == EditorInfo.IME_ACTION_DONE).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(integer -> {
                    // 2016/8/18 隐藏软键盘
                    attemptLogin();
                });


        RxView.clicks(btnLogin)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    attemptLogin();
                });
        addUsernameAutoComplete();
    }

    private void addUsernameAutoComplete() {

        //  系统读入内容帮助用户输入用户名
        //系统读入内容帮助用户输入用户名
        ArrayList<String> arrayList = new ArrayList<>();
       /* for (int i = 0; i < 9; i++) {
            arrayList.add("03028"+i);
        }*/


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(),
                android.R.layout.simple_list_item_1, arrayList);

        actvUsername.setAdapter(adapter);
    }

    private void attemptLogin() {
        actvUsername.setError(null);
        editPassword.setError(null);
        String username = actvUsername.getText().toString();
        String password = editPassword.getText().toString();


        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(password)) {
            editPassword.setError("请输入密码！");
            focusView = editPassword;
            cancel = true;
        }
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            editPassword.setError("密码太短了！");
            focusView = editPassword;
            cancel = true;
        }
        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            actvUsername.setError("请输入警员编号！");
            focusView = actvUsername;
            cancel = true;
        }
        //所有的检查完成 判断是否能开始联网 还是弹出提示
        if (cancel) {
            focusView.requestFocus();
        } else {
            httpLogin(username, MD5.md5(password));
            //LogUtil.d("md5 : " + MD5.md5(password));
        }

    }

    private void httpLogin(final String username, final String password) {
        app.apiService.login2(username, password, DATAFUSION).compose(RxUtil.<String>applySchedulers())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showProgress(true);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        SomeUtil.checkHttpException(getApplicationContext(), e, scrollLoginForm);
                        showProgress(false);
                        LogUtil.d("login : " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("login : " + s);
                       // ToastUtil.showLong(LoginActivity.this,s);
                           // SomeUtil.showSnackBar(rootview,s);
                        if (s.contains("\"code\":200")) {
                            app.apiService.login(username)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<UserInfo>() {

                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            showProgress(false);
                                            LogUtil.d("Login error:" + e.toString());
                                            //SomeUtil.checkHttpException(getApplicationContext(),e,scrollLoginForm);
                                        }

                                        @Override
                                        public void onNext(UserInfo userInfo) {
                                            LogUtil.d("login result code:" + userInfo.getCode().toString());
                                            LogUtil.d("login result :" + userInfo.getRes().toString());
                                            if (userInfo.getCode().equals(200)) {
                                                showProgress(false);
                                                saveUserInfo(userInfo.getRes().get(0), password);
                                                getuserhead(userInfo.getRes().get(0).getUserid());
                                                getsub();
                                                SomeUtil.showSnackBar(scrollLoginForm, "登录成功！").setCallback(new Snackbar.Callback() {
                                                    @Override
                                                    public void onDismissed(Snackbar snackbar, int event) {
                                                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                                        finish();
                                                    }
                                                });





                                            } else {
                                                SomeUtil.showSnackBar(scrollLoginForm, "用户名或密码错误！");
                                                showProgress(false);
                                            }
                                        }
                                    });


                        } else {
                            SomeUtil.showSnackBar(scrollLoginForm, "用户名或密码错误！"+s);
                            showProgress(false);

                        }
                    }
                });
    }

    private void getsub() {
        app.apiService.getSupAndSub(actvUsername.getText().toString())
                .compose(RxUtil.<Subordinate>applySchedulers())
                .subscribe(new Subscriber<Subordinate>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        SomeUtil.checkHttpException(app.getApp().getApplicationContext(), e, scrollLoginForm);
                    }

                    @Override
                    public void onNext(Subordinate subordinate) {
                        LogUtil.d("subordinate: "+subordinate);
                        LogUtil.d("subord size :" + subordinate.getResdown().size());
                        LogUtil.d("upper :" + subordinate.getResup().get(0).toString());
                       if (subordinate.getResdown().size() > 0) {
                            new SPBuild(getApplicationContext())
                                    .addData(Constant.ISLEADER, Boolean.TRUE)
                                    .build();

                        } else {
                            new SPBuild(getApplicationContext())
                                    .addData(Constant.ISLEADER, Boolean.FALSE)
                                    .build();
                        }
                        try {
                            if (subordinate.getResup().get(0).getPolid()==null&&subordinate.getResup().get(0).getPolid().equals("null")){
                                new SPBuild(getApplicationContext())
                                        .addData(Constant.HASUPPER, Boolean.FALSE)
                                        .build();

                            }else {
                                new SPBuild(getApplicationContext())
                                        .addData(Constant.HASUPPER, Boolean.TRUE)
                                        .build();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            new SPBuild(getApplicationContext())
                                    .addData(Constant.HASUPPER, Boolean.FALSE)
                                    .build();
                        }


                    }
                });


    }

    private void getuserhead(String userid) {
        LogUtil.d("userid : " + userid);
        app.apiService.getUserhead(userid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("getuserhead : " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        //LogUtil.d("userhead : " + s);
                        String s2 = s.replaceAll("\\\\", "");
                        s2.replaceAll("\"", "");
                        LogUtil.d("userhead : " + s2);
                        userhead = s2;
                        saveUserHead();
                    }
                });


    }

    private void saveUserHead() {
        new SPBuild(getApplicationContext())
                .addData(Constant.USERHEAD, userhead)
                .build();

    }


    private void saveUserInfo(UserInfo.UserRes userRes, String password) {
        LogUtil.d("userres : " + userRes.toString());
        SPUtils.clear(getApplicationContext());
        // 存储用户信息
        new SPBuild(getApplicationContext())
                .addData(Constant.ISLOGIN, Boolean.TRUE)
                .addData(Constant.USERID, userRes.getUserid())//警员id
                .addData(Constant.USERNAME, userRes.getPolicename())//警员姓名
                .addData(Constant.USERPASSWORD, password)//警员密码
                .addData(Constant.DEPARTID,userRes.getOfficecode())//部门id
                .addData(Constant.DEPARTNAME,userRes.getOfficecodename())//部门名称
                //.addData(Constant.USERDATRIXID, userRes.getDatrixid())//警员Datrix id
                .addData(Constant.USEROFFICENAME, userRes.getOfficecodename())//警员办公地点
                .addData(Constant.LOGINTIME, System.currentTimeMillis())//登陆时间
                .build();
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            progressLogin.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            progressLogin.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressLogin.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progressLogin.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        }
    }

}

