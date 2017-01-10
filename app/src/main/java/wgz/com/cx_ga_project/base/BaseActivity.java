package wgz.com.cx_ga_project.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wgz on 2016/7/26.
 */

public abstract class BaseActivity extends AppCompatActivity{
    //关键的是否登录 由父类提供
    public boolean isLogin=false;
    protected Context mContext;
    private CompositeSubscription msubscription;//管理所有的订阅
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutId());
        msubscription = new CompositeSubscription();
        ButterKnife.bind(this);
        this.initView();
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public void addSubscription(Subscription subscription){
        msubscription.add(subscription);

    }

    public void setToolbarBack(Toolbar toolbar){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(msubscription != null){
            this.msubscription.unsubscribe();
        }
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 此时android.R.id.home即为返回箭头
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
