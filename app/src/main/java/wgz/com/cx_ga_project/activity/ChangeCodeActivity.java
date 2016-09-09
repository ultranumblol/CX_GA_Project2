package wgz.com.cx_ga_project.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;

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
    ConstraintLayout rootview;

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



    }


}
