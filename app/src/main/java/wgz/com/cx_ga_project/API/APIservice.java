package wgz.com.cx_ga_project.API;

import android.net.http.HttpResponseCache;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import rx.Observable;
import wgz.com.cx_ga_project.bean.JiaBan;
import wgz.com.cx_ga_project.bean.UserBean;
import wgz.com.cx_ga_project.entity.AppVersion;
import wgz.com.cx_ga_project.entity.Apply;
import wgz.com.cx_ga_project.entity.DatrixCreat;
import wgz.com.cx_ga_project.entity.DatrixFinish;
import wgz.com.cx_ga_project.entity.LeaveType;
import wgz.com.cx_ga_project.entity.Scheduling;
import wgz.com.cx_ga_project.entity.SchedulingOneDay;
import wgz.com.cx_ga_project.entity.Subordinate;
import wgz.com.cx_ga_project.entity.UserInfo;
import wgz.com.cx_ga_project.entity.WorkLog;

import static wgz.com.cx_ga_project.app.DATRIX_BASE_URL;
import static wgz.com.cx_ga_project.base.Constant.CHECK_ONESSUMMARY_BYDAYS;
import static wgz.com.cx_ga_project.base.Constant.DATRIXUID;
import static wgz.com.cx_ga_project.base.Constant.GET_USER_HEAD;

/**
 * Created by wgz on 2016/8/1.
 */

public interface APIservice {

    /**
     * 通过 List<MultipartBody.Part> 传入多个part实现多文件上传
     *
     * @param parts 每个part代表一个
     * @return 状态信息
     */
    @Multipart
    @POST("{type}")
    Observable<String> uploadFilesWithParts(@Path("type") String type,
                                            @Part() List<MultipartBody.Part> parts);


    /**
     * 通过 MultipartBody和@body作为参数来上传
     *
     * @param multipartBody MultipartBody包含多个Part
     * @return 状态信息
     */
    @POST("appworkmanager/{type}")
    Observable<String> uploadFileWithRequestBody(@Path("type") String type,
                                                 @Body MultipartBody multipartBody);




    /**
     * detrix 创建文件creat方法(1)
     *
     * @param filename
     * @return
     */
    @FormUrlEncoded
    @POST(DATRIX_BASE_URL+"api/cluster/tracker/file/create"+DATRIXUID)
    @Headers("ACCESS-TOKEN:X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=")
    Observable<DatrixCreat> uploadFileWithRequestBodyTest(
            @Field("filename") String filename,
            @Field("filesize") String filesize
    );


    /**
     * detrix 上传写入文件方法(2)
     *
     * @return
     */
    @Multipart
    @POST(DATRIX_BASE_URL+"api/cluster/storage/file/write")
    @Headers("ACCESS-TOKEN:X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=")
    Observable<String> detrixWrite(
            @Part("fileid") String fileid,
            @Part("offset") String offset,
            @Part("length") String length,
            @PartMap Map<String, RequestBody> params);


    /**
     * detrix 上传结束方法(3)
     *
     * @param fileid
     * @param customjson
     * @return
     */
    @FormUrlEncoded
    @POST(DATRIX_BASE_URL+"upload/finish")
    @Headers("ACCESS-TOKEN:X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=")
    Observable<String> detrixfinish(
            @Field("fileid") String fileid,
            @Field("customjson") String customjson
    );

    /**
     * 修改工作日志
     *
     * @param type
     * @param id
     * @param summary
     * @return
     */
    @FormUrlEncoded
    @POST("appworkmanager/{type}")
    Observable<String> changeWorkLog(@Path("type") String type,
                                     @Field("id") String id,
                                     @Field("summary") String summary);

    /**
     * 审核申请
     *
     * @param type
     * @param id
     * @param status 1通过 ，2未通过
     * @return
     */
    @FormUrlEncoded
    @POST("appworkmanager/{type}")
    Observable<String> approvalApply(@Path("type") String type,
                                     @Field("id") String id,
                                     @Field("status") String status,
                                     @Field("nopassreason") String nopassreason

    );

    /**
     * 提交工作日志
     *
     * @param type
     * @param loginid
     * @param summary
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("appworkmanager/{type}")
    Observable<String> upWorkLog(@Path("type") String type,
                                 @Field("loginid") String loginid,
                                 @Field("summary") String summary,
                                 @Field("pic") String pic,
                                 @Field("time") String time);

    /**
     * 提交加班申请
     */
    @FormUrlEncoded
    @POST("appworkmanager/{type}")
    Observable<String> upOverTime(@Path("type") String type,
                                  @Field("starttime") String starttime,
                                  @Field("endtime") String endtime,
                                  @Field("content") String content,
                                  @Field("policeid") String policeid,
                                  @Field("applytime") String applytime,
                                  @Field("upperid") String upperid
    );

    /**
     * 提交请假申请
     *
     * @param type
     * @param starttime
     * @param endtime
     * @param content
     * @param policeid
     * @param applytime
     * @param upperid
     * @return
     */
    @FormUrlEncoded
    @POST("appworkmanager/{type}")
    Observable<String> upLoadLeave(@Path("type") String type,
                                   @Field("starttime") String starttime,
                                   @Field("endtime") String endtime,
                                   @Field("content") String content,
                                   @Field("policeid") String policeid,
                                   @Field("applytime") String applytime,
                                   @Field("upperid") String upperid,
                                   @Field("reasontype") String reasontype,
                                   @Field("days") String days
    );


    /**
     * 获取加班请假申请信息
     *
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("appworkmanager/{type}")
    Observable<Apply> getBeanData(@Path("type") String type,
                                  @Field("policeid") String policeid

    );


    /**
     * 获取工作日志
     *
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("appworkmanager/{type}")
    Observable<WorkLog> getLogData(@Path("type") String type,
                                   @Field("loginid") String loginid,
                                   @Field("time") String time
    );

    /**
     * 获取工作日志图片
     * @param type
     * @param loginid
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("appworkmanager/{type}")
    Observable<WorkLog> getLogPicData(@Path("type") String type,
                                   @Field("loginid") String loginid,
                                   @Field("time") String time
    );



    /**
     * 获取某天工作日志
     *
     * @return
     */
    @FormUrlEncoded
    @POST("appworkmanager/"+CHECK_ONESSUMMARY_BYDAYS)
    Observable<WorkLog> getLogDataToDay(@Field("loginid") String loginid,
                                        @Field("time") String time
    );


    /**
     * 登陆
     *
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("ceshi/denglu")
    Observable<UserBean> UserLogin(@Field("username") String username,
                                   @Field("password") String password);


    /**
     * 获取用户头像
     *
     * @param policeid
     * @return
     */
    @FormUrlEncoded
    @POST("appworkmanager/getAvantar")
    Observable<String> getUserhead(@Field("policeid") String policeid
    );


    /**
     * 根据时间段获取值班信息
     * @param start
     * @param end
     * @param policenum
     * @return
     */
    @FormUrlEncoded
    @POST("demojob/getAppAllSch")
    Observable<Scheduling> getAllScheduling(
            @Field("start") String start,
            @Field("end") String end,
            @Field("policenum") String policenum
    );

    /**
     * 查询某一天的排班记录
     * @param day
     * @return
     */
    @FormUrlEncoded
    @POST("apponduty/getWeekLeaderByDay")
    Observable<SchedulingOneDay> getOneDayScheduling(
            @Field("day") String day
    );

    /**
     * 显示datrix上的图片
     * @return
     */
    @GET(DATRIX_BASE_URL+"preview/getImage?fileid=c1b09c7c-6e9f-43cc-8f16-780713066cc0&token=X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=")
    Observable<String> getdatrixPic();

    /**
     * 登录
     * @param username
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("applogin/appLogin")
    Observable<UserInfo> login(@Field("username") String username,
                               @Field("pwd") String pwd);

    /**
     * 修改用户密码
     * @param username
     * @param newpwd
     * @return
     */
    @FormUrlEncoded
    @POST("applogin/appChangePwd")
    Observable<String> changePass(@Field("username") String username,
                                  @Field("newpwd") String newpwd);

    /**
     * 检查版本
     * @return
     */
    @GET("appupload/checkAppVersion")
    Observable<AppVersion> checkVersion();


    /**
     * 获取请假类型
     * @return
     */
    @GET("appworkmanager/getLeaveType")
    Observable<LeaveType> getLeaveType();

    /**
     * 获取某人的上下级关系
     * @param policeid
     * @return
     */
    @FormUrlEncoded
    @POST("apponduty/getSupAndSub")
    Observable<Subordinate> getSupAndSub(@Field("policeid") String policeid);
    //获取某个人的时间银行
   /* appworkmanager/getOnceTimeBankDetail
            $policeid = $this ->param('policeid');
    $year = $this ->param('year');*/


}
