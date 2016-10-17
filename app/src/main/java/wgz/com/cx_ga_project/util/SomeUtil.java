package wgz.com.cx_ga_project.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.Constant;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * Created by wgz on 2016/8/5.
 */

public class SomeUtil {
    public static Snackbar showSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
        return snackbar;
    }

    public static Snackbar showSnackBarLong(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
        return snackbar;
    }


    public static void showNetworkErrorSnackBar(final Context context, View view, String message, String action) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        context.startActivity(intent);
                    }
                })
                .show();

    }

    /**
     * 检查对象非空
     *
     * @param object
     * @param message
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    public static void checkHttpException(Context mContext, Throwable mThrowable, View mRootView) {
        String snack_action_to_setting = "设置";
        if ((mThrowable instanceof UnknownHostException)) {
            String snack_message_net_error = "网络错误，请检查网络";
            showNetworkErrorSnackBar(mContext, mRootView, snack_message_net_error, snack_action_to_setting);
        } else if (mThrowable instanceof SocketTimeoutException) {
            String snack_message_time_out = "连接超时，请检查网络";
            showNetworkErrorSnackBar(mContext, mRootView, snack_message_time_out, snack_action_to_setting);
        } else if (mThrowable instanceof ConnectException) {
            String snack_message_net_error = "网络错误，请检查网络";
            showNetworkErrorSnackBar(mContext, mRootView, snack_message_net_error, snack_action_to_setting);
        } else {
            String snack_message_unknown_error = "未知错误";
            showSnackBar(mRootView, snack_message_unknown_error);
        }
    }

    /**
     * 文件转换为multipartBody
     *
     * @param files
     * @return
     */
    public static MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            builder.addFormDataPart("file[]", file.getName(), requestBody);
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    /**
     * 字符转换为multipartBody
     *
     * @param
     * @return
     */
    public static MultipartBody StrToMultipartBody(String paraname, String paravalue) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart(paraname, paravalue);
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    /**
     * File转化成MultipartBody.Part
     *
     * @param files
     * @return
     */
    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file[]", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    public static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("wgz.com.cx_ga_project", 0);
            versionCode = packageInfo.versionCode;
            String dir = packageInfo.applicationInfo.publicSourceDir;
            int oldPackageSize = Integer.valueOf((int) new File(dir).length());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            LogUtil.d("Update:GetVersionCode:" + ex.getMessage());
        }
        return versionCode;
    }

    /**
     * yyyy-MM-dd
     *
     * @param str
     * @return
     */
    public static Date getStrToDate(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtil.d("DATE error" + e.toString());
            return null;
        }
    }

    /**
     * 用glide加载图片 有缓存和缩略图0.4f
     * @param context
     * @param view
     */
    public static void GlidePic(Context context, ImageView view,String loadurl){
        Glide.with(context)
                .load(loadurl)
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.4f)
                .dontAnimate()
                .placeholder(R.drawable.ic_photo_grey_400_48dp)
                .error(R.drawable.ic_broken_image_grey_400_48dp)
                .into(view);

    }


    public static String getSysTime(){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss ");

        Date curDate = new Date(System.currentTimeMillis());//获取当前时间

        String str = formatter.format(curDate);
        return str;

    }
    //(String) SPUtils.get(app.getApp().getApplicationContext(), Constant.USERNAME, "未知")
    //获取用户id
    public static String getUserId(){

        return (String) SPUtils.get(app.getApp().getApplicationContext(), Constant.USERID, "未知");
    }
}
