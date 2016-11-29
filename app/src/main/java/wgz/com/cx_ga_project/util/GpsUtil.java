package wgz.com.cx_ga_project.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.List;

import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.Constant;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by wgz on 2016/11/29.
 */

public class GpsUtil {

    private LocationManager locationManager;
    private String locationProvider;
    private String locationStr;
    private LocationListener myLocationListener;

    public  void  getJingWeiDu(Context context){
        //获取地理位置管理器
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        for (String string : providers) {
            System.out.println(string+"4");
        }
// 2.2获取最佳的定位方式
        Criteria criteria = new Criteria();
// 设置可以定位海拔,true：表示可以定位海拔
        criteria.setAltitudeRequired(true);// 只有gps可以定位海拔
// criteria ： 设置定位属性
// enabledOnly ： 如果定位可以就返回
        String bestProvider = locationManager.getBestProvider(criteria, true);
        System.out.println("最佳的定位方式:" + bestProvider);
// 3.定位
        myLocationListener = new MyLocationListener();
// provider : 定位方式
// minTime ：定位的最小时间间隔
// minDistance　：　最小的定位距离
// listener ： LocationListener监听
        locationManager.requestLocationUpdates(bestProvider, 0, 0,
                new MyLocationListener());

    }

    // 4.创建一个定位的监听
    private class MyLocationListener implements LocationListener {
        // 定位位置发生变化的时候调用
// location ： 当前的位置
        @Override
        public void onLocationChanged(Location location) {
// 5.获取经纬度
            location.getAccuracy();// 获取精确的位置
            location.getAltitude();// 获取海拔
            double latitude = location.getLatitude();// 获取纬度，平行
            double longitude = location.getLongitude();// 获取经度
            String locationStr = "纬度：" + latitude +"\n"
                    + "经度：" + longitude;
            LogUtil.d("经纬度是：", locationStr);
            System.out.println("getJingWeiDu经纬度是：" + locationStr);
//保存经纬度坐标
            new SPBuild(app.getApp().getApplicationContext())
                    .addData(Constant.LONGITUDE, longitude)
                    .addData(Constant.LATITUDE, latitude).build();



// Timer timer = new Timer();
// //task : 定时执行的任务，
// //when:延迟的时间，延迟多长时间执行定时任务
// //period ： 每次执行的间隔时间
// timer.schedule(task, when, 1000*60*30)
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }


        // 定位可用的时候调用
        @Override
        public void onProviderEnabled(String provider) {
// TODO Auto-generated method stub


        }


        // 定位不可用的时候调用
        @Override
        public void onProviderDisabled(String provider) {
// TODO Auto-generated method stub


        }
    }
    /**
     * 显示地理位置经度和纬度信息
     * @param location
     */
    public static String  showLocation(Location location){
        String locationStr = "纬度：" + location.getLatitude() +"\n"
                + "经度：" + location.getLongitude();
        LogUtil.d("经纬度是：", locationStr);

        return locationStr;
    }


}
