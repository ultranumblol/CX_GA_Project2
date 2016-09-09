package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.uniview.airimos.listener.OnLoginListener;
import com.uniview.airimos.manager.ServiceManager;
import com.uniview.airimos.parameter.LoginParam;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;

public class CamLoginActivity extends BaseActivity  implements OnLoginListener {


    @Bind(R.id.edittext_server)
    EditText edittextServer;
    @Bind(R.id.edittext_port)
    EditText edittextPort;
    @Bind(R.id.edittext_username)
    EditText edittextUsername;
    @Bind(R.id.edittext_password)
    EditText edittextPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;

    @Override
    public int getLayoutId() {
        return R.layout.camvideologin;
    }

    @Override
    public void initView() {
        RxView.clicks(btnLogin).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        dologin();
                    }
                });
    }

    private void dologin() {
        // 设置登录参数
        LoginParam params = new LoginParam();
        params.setServer(edittextServer.getText().toString());
        params.setPort(Integer.parseInt(edittextPort.getText().toString()));
        params.setUserName(edittextUsername.getText().toString());
        params.setPassword(edittextPassword.getText().toString());

        //调用登录接口
        ServiceManager.login(params, CamLoginActivity.this);

    }


    @Override
    public void onLoginResult(long errorCode, String errorDesc) {
        //成功为0，其余为失败错误码
        if (errorCode == 0)
        {
            Intent intent = new Intent();
            intent.setClass(CamLoginActivity.this, CamPlayerActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(CamLoginActivity.this, "登录失败：" + errorCode + "," + errorDesc, Toast.LENGTH_LONG).show();

        }
    }
}
