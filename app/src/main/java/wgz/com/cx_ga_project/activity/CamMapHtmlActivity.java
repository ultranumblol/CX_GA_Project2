package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.util.SPUtils;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class CamMapHtmlActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.mapview)
    WebView webView;
    @Bind(R.id.content_cam_map_html)
    RelativeLayout rootview;
    @Bind(R.id.camfab)
    FloatingActionButton fab;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cam_map_html;
    }

    @Override
    public void initView() {
        toolbar.setTitle("附近监控查询");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView.requestFocus();
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.loadUrl("file:///android_asset/page404.html");
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

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {

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
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setBuiltInZoomControls(true);
        webView.addJavascriptInterface(getHtmlInterface(), "android");
        webView.loadUrl("http://192.168.1.184:12345/appmap");

        RxView.clicks(fab).throttleFirst(500, TimeUnit.MICROSECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        //获取body标签下的详情内容
                       // webView.loadUrl("javascript:window.android.copyText(document.getElementsByTagName('body')[0].innerHTML);");
                        webView.loadUrl("javascript:getposition()");
                    }
                });


    }

    private Object getHtmlInterface() {
        return new Object() {

            @JavascriptInterface
            public void copyText(String html) {
                LogUtil.d(html);

            }


            @JavascriptInterface
            public String getlongitude() {
                String longitude = (String) SPUtils.get(app.getApp().getApplicationContext(), Constant.LONGITUDE, "1111");
                LogUtil.d("html longitude "+longitude);
                return longitude;
            }

            @JavascriptInterface
            public String getlatitude() {
                String latitude = (String) SPUtils.get(app.getApp().getApplicationContext(), Constant.LATITUDE, "111");
                LogUtil.d("html latitude "+latitude);
                return latitude;
            }

            @JavascriptInterface
            public void goPlay(String camID) {
                LogUtil.d("camid : "+camID);
                startActivity(new Intent(CamMapHtmlActivity.this, CamPlayerActivity.class).putExtra("camid", camID));

            }


        };

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (webView.canGoBack()) webView.goBack();
                else {
                    onBackPressed();
                }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
