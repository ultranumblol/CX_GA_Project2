package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;

public class NearlyVideoCamActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_nearlycam)
    WebView webView;
    @Bind(R.id.content_nearly_video_cam)
    ConstraintLayout rootview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_nearly_video_cam;
    }
    @Override
    public void initView() {
        toolbar.setTitle("附近监控");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView.requestFocus();
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                webView.loadUrl(url);
                return true;
            }




        });
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (webView.canGoBack() && event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });
        webView.loadUrl("http://192.168.1.100/nearbyresources?flag_menu=ZHDD-jqcl");

    }
}
