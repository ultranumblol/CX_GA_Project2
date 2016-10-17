package wgz.com.cx_ga_project.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.uniview.airimos.util.MD5;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import wgz.com.cx_ga_project.API.APIservice;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.entity.UserInfo;
import wgz.com.cx_ga_project.util.SPBuild;
import wgz.com.cx_ga_project.util.SPUtils;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

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
    private String userhead;
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        RxTextView.editorActions(editPassword, new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer == EditorInfo.IME_ACTION_DONE;
            }
        }).throttleFirst(500,TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        // TODO: 2016/8/18 隐藏软键盘
                        attemptLogin();
                    }
                });



        RxView.clicks(btnLogin)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        attemptLogin();
                    }
                });
        addUsernameAutoComplete();
    }

    private void addUsernameAutoComplete() {

        // TODO: 2016/8/5  系统读入内容帮助用户输入用户名
        //系统读入内容帮助用户输入用户名
        ArrayList<String> arrayList = new ArrayList<>();
//        for (int i = 0; i < 9; i++) {
//            arrayList.add("36140137" + i + "@qq.com");
//        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(),
                android.R.layout.simple_spinner_dropdown_item, arrayList);

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
        }else {
            // TODO: 2016/8/5 登录功能还差联网
            httpLogin(username, MD5.md5(password));
        }

    }

    private void httpLogin(final String username, final String password) {

        //LogUtil.d("login :" +password );
        app.apiService.login(username,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfo>() {
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
                        showProgress(false);
                        LogUtil.d("Login error:"+e.toString());
                        SomeUtil.checkHttpException(getApplicationContext(),e,scrollLoginForm);
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        LogUtil.d("login result :" +userInfo.getRes().toString());
                        if (userInfo.getCode().equals(200)){
                            showProgress(false);

                            SomeUtil.showSnackBar(scrollLoginForm,"登录成功！").setCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                    finish();
                                }
                            });

                            getuserhead(userInfo.getRes().get(0).getUserid());
                            saveUserInfo(userInfo.getRes().get(0),password);
                        }
                        else {
                            SomeUtil.showSnackBar(scrollLoginForm,"用户名或密码错误！");
                        }
                    }
                });
    }
    private void getuserhead(String userid) {
        LogUtil.d("userid : "+userid);
        app.apiService.getUserhead(APIservice.GET_USER_HEAD,userid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("getuserhead : "+e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        //LogUtil.d("userhead : " + s);
                        String s2 = s.replaceAll("\\\\","");
                        s2.replaceAll("\"","");
                        LogUtil.d("userhead : " + s2);
                        userhead = s2;
                        saveUserHead();
                    }
                });


    }
    private void saveUserHead(){
        new SPBuild(getApplicationContext())
                .addData(Constant.USERHEAD, userhead)
                .build();

    }


    private void saveUserInfo(UserInfo.UserRes userRes,String password) {
        LogUtil.d("userres : "+ userRes.toString());
        SPUtils.clear(getApplicationContext());
        // TODO: 2016/8/5 存储用户信息
        new SPBuild(getApplicationContext())
                .addData(Constant.ISLOGIN, Boolean.TRUE)
                .addData(Constant.USERID, userRes.getUserid())//警员id
                .addData(Constant.USERNAME, userRes.getPolicename())//警员姓名
                .addData(Constant.USERPASSWORD, password)//警员密码
                //.addData(Constant.USERDATRIXID, userRes.getDatrixid())//警员Datrix id
                .addData(Constant.USERPHONENUM, userRes.getPphonenum())//警员电话
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

