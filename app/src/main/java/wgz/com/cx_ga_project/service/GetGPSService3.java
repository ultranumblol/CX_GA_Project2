package wgz.com.cx_ga_project.service;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import wgz.com.cx_ga_project.base.Constant;
import wgz.com.cx_ga_project.base.RxBus;
import wgz.com.cx_ga_project.util.SPBuild;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * Created by qwerr on 2016/8/2.
 */

public class GetGPSService3 extends Service {
    LocationManager locationManager;
    Location mlocation;


    private Location updateToNewLocation(Location location) {
        System.out.println("--------zhixing--2--------");
        String latLongString;
        double lat = 0;
        double lng=0;

        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            latLongString = "纬度:" + lat + "\n经度:" + lng;
           // System.out.println("经度："+lng+"纬度："+lat);
        } else {
            latLongString = "无法获取地理信息，请稍后...";
        }
        if(lat!=0){
            System.out.println("--------反馈信息----------"+ String.valueOf(lat));
        }
            LogUtil.d(latLongString);
        RxBus.getDefault().post(latLongString);

        return location;

    }

    // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
    public final LocationListener mLocationListener01 = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mlocation = location;
            //updateToNewLocation(mlocation);
        }

        @Override
        public void onProviderDisabled(String provider) {
            updateToNewLocation(null);
        }
        @Override
        public void onProviderEnabled(String provider) {}
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //locationManager.setTestProviderEnabled("gps", true);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, mLocationListener01);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, mLocationListener01);





        new Thread(() -> {
            Looper.prepare();
            LogUtil.d("定位服务333启动！");
            while (true) {

                try {
                    updateToNewLocation(mlocation);
                  /*  LogUtil.d("longitude: "+longitude);
                    LogUtil.d("latitude: "+latitude);*/
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();


    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}
