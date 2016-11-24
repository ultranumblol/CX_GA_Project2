package wgz.com.cx_ga_project.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.TimeBankAdapter;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.util.SPUtils;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.com.cx_ga_project.view.Mylayout;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class TimeBankActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_time_bank)
    RelativeLayout rootview;
    @Bind(R.id.timebank_webview)
    WebView timebankWebview;
    private TimeBankAdapter adapter;
    private List<String> list = new ArrayList<>();
    private String username = "";
    private String userid = "";
    @Override
    public int getLayoutId() {
        return R.layout.activity_time_bank;
    }

    @Override
    public void initView() {
        toolbar.setTitle("时间银行");
        setToolbarBack(toolbar);
        Intent intent = getIntent();
        userid = intent.getStringExtra("policeid");
        username = intent.getStringExtra("policename");
        timebankWebview.requestFocus();
        timebankWebview.setWebChromeClient(new WebChromeClient(){


        });
        timebankWebview.setWebChromeClient(new WebChromeClient());
        timebankWebview.setWebViewClient(new WebViewClient() {

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                timebankWebview.loadUrl("file:///android_asset/page404.html");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                timebankWebview.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }

        });
        timebankWebview.setOnKeyListener((v, keyCode, event) -> {
            if (timebankWebview.canGoBack() && event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                timebankWebview.goBack();
                return true;
            }
            return false;
        });
        WebSettings webSettings = timebankWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        timebankWebview.setInitialScale(100);
        //webSettings.setBuiltInZoomControls(true);
        timebankWebview.addJavascriptInterface(getHtmlInterface(), "android");
        timebankWebview.loadUrl("http://192.168.1.187:8888/timebank?flag_menu=JWXN-sjyh&appflag=1&apptoken=58c93a51-3e95-43b6-8a11-e33c96bb78cf");
        timebankWebview.loadUrl("javascript:timebankapp()");
        //timebankWebview.loadUrl("http://192.168.1.187:8888/timebank?flag_menu=JWXN-sjyh&appflag=1&apptoken=58c93a51-3e95-43b6-8a11-e33c96bb78cf&uid=030247&uname=%E9%99%88%E6%9B%99%E5%9B%BD");


    }
    private Object getHtmlInterface() {
        return new Object() {

            @JavascriptInterface
            public void copyText(String html) {
                LogUtil.d(html);

            }


            @JavascriptInterface
            public String getuserid() {

                return userid;
            }

            @JavascriptInterface
            public String getusername() {

                return username;
            }


        };

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (timebankWebview.canGoBack()) timebankWebview.goBack();
                else {
                    onBackPressed();
                }

        }
        return super.onOptionsItemSelected(item);
    }


}
