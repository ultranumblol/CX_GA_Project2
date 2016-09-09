package wgz.com.cx_ga_project.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by wgz on 2016/7/25.
 */

public abstract class BaseMVPActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {
    public T presenter;
    protected abstract int getLayoutId();
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        presenter = initPresenter();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.Destroy();
    }

    public abstract T initPresenter();
}
