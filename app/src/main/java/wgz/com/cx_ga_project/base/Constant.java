package wgz.com.cx_ga_project.base;

import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.util.SPUtils;

/**
 * Created by wgz on 2016/8/12.
 */

public class Constant {
    public static final String TYPE_JIABAN = "2";
    public static final String TYPE_QINGJIA = "1";
    public static final String UNAPPROVAL = "0";
    public static final String APPROVAL_PASS = "1";
    public static final String APPROVAL_UNPASS = "2";
    public static final String ISLOGIN = "isLogin";
    public static final String LOGINTIME = "loginTime";
    public static final String USERID = "userID";
    public static final String USERNAME = "userName";
    public static final String USERHEAD = "userHead";
    public static final String USERDATRIXID = "userDatrixID";
    public static final String USERPASSWORD = "userPassWord";
    public static final String USERPHONENUM = "userPhoneNum";
    public static final String USEROFFICENAME = "userOfficeName";
    public static final String UPDATEURL = "updateurl";
    public static final String USERHEADURL = "http://" + SPUtils.get(app.getApp().getApplicationContext(), Constant.USERHEAD, "");
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";

}
