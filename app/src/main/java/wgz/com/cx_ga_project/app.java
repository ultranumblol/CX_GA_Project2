package wgz.com.cx_ga_project;

import android.app.Application;
import android.content.Intent;
import android.os.Looper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import wgz.com.cx_ga_project.API.APIservice;
import wgz.com.cx_ga_project.API.JqAPIService;
import wgz.com.cx_ga_project.entity.ChatMsg;
import wgz.com.cx_ga_project.service.GetGPSService;
import wgz.com.cx_ga_project.util.ProgressListener;
import wgz.com.cx_ga_project.util.ProgressResponseBody;
import wgz.datatom.com.utillibrary.util.LogUtil;
import wgz.datatom.com.utillibrary.util.ToastUtil;

/**
 * Created by wgz on 2016/7/26.
 */

public class app extends Application {
    private static app mApp;
    //public static final String BASE_URL = "http://192.168.1.193:8004/";
    public static final String BASE_URL = "http://53.20.31.31:8788/";
    public static final String DATRIX_BASE_URL = "http://101.231.77.242:9001/";

    public static APIservice apiService;
    public static JqAPIService jqAPIService;


    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        ToastUtil.isShow =true;
        //开启定位服务
        startService(new Intent(getApplicationContext(), GetGPSService.class));
        LogUtil.isDebug=true;

        //配置程序异常退出处理
        //Thread.setDefaultUncaughtExceptionHandler(new LocalFileHandler(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(defaultOkHttpClient())
                .build();


        apiService = retrofit.create(APIservice.class);
        jqAPIService = retrofit.create(JqAPIService.class);

    }
    public static OkHttpClient defaultOkHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .writeTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Response orginalResponse = chain.proceed(chain.request());

                        return orginalResponse.newBuilder()
                                .body(new ProgressResponseBody(orginalResponse.body(), new ProgressListener() {
                                    @Override
                                    public void onProgress(long progress, long total, boolean done) {
                                       // LogUtil.d(Looper.myLooper()+"");
                                       // LogUtil.d("onProgress: " + "total ---->" + total + "done ---->" + progress);
                                    }
                                }))
                                .build();
                    }
                })
                .build();
        return client;
    }
    public static app getApp(){
        return mApp;
    }
}
