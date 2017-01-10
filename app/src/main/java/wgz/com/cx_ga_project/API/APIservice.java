package wgz.com.cx_ga_project.API;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;
import wgz.com.cx_ga_project.entity.AppVersion;
import wgz.com.cx_ga_project.entity.Apply;
import wgz.com.cx_ga_project.entity.CloudTag;
import wgz.com.cx_ga_project.entity.DatrixCreat;
import wgz.com.cx_ga_project.entity.DepPeople;
import wgz.com.cx_ga_project.entity.Deps;
import wgz.com.cx_ga_project.entity.LeaveType;
import wgz.com.cx_ga_project.entity.SICDetil;
import wgz.com.cx_ga_project.entity.SICList;
import wgz.com.cx_ga_project.entity.SICType;
import wgz.com.cx_ga_project.entity.Scheduling;
import wgz.com.cx_ga_project.entity.SchedulingOneDay;
import wgz.com.cx_ga_project.entity.Subordinate;
import wgz.com.cx_ga_project.entity.TypeOfAuth;
import wgz.com.cx_ga_project.entity.UserInfo;
import wgz.com.cx_ga_project.entity.WorkCloudList;
import wgz.com.cx_ga_project.entity.WorkLog;

import static wgz.com.cx_ga_project.app.DATRIX_BASE_URL;
import static wgz.com.cx_ga_project.base.Constant.CHECK_ONESSUMMARY_BYDAYS;
import static wgz.com.cx_ga_project.base.Constant.DATRIXUID;

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
    @POST(DATRIX_BASE_URL + "api/cluster/tracker/file/create" + DATRIXUID)
   // @POST(DATRIX_BASE_URL + "api/cluster/tracker/file/create")
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
    @POST(DATRIX_BASE_URL + "api/cluster/storage/file/write")
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
    @POST(DATRIX_BASE_URL + "upload/finish")
    @Headers("ACCESS-TOKEN:X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=")
    Observable<String> detrixfinish(
            @Field("fileid") String fileid,
            @Field("customjson") String customjson
    );


    /**
     * 获取datrix上的图片缩略图
     * @param fileid
     * @param height 缩略图的高度，单位为像素，默认为 0, 表示等比缩放图片
     * @param width 缩略图的宽度，单位为像素, 默认为 0, 表示大小不变
     * @return
     */
    @FormUrlEncoded
    @POST(DATRIX_BASE_URL + "api/media/image/thumbnail/preview")
    @Headers("ACCESS-TOKEN:X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=")
    Observable<ResponseBody> detrixPic(
            @Field("fileid") String fileid,
            @Field("height") String height,
            @Field("width") String width
    );

    /**
     * 删除datrix文件
     * @param fileid 要删除的文件的文件ID
     * @param removesubs 删除文件时是否删除其子文件，默认为true(删除), false表示不删除
     * @return
     */
    @FormUrlEncoded
    @POST(DATRIX_BASE_URL + "api/v2/core/file/remove")
    @Headers("ACCESS-TOKEN:X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=")
    Observable<String> DetrixRemove( @Field("fileid") String fileid,
                                     @Field("removesubs") String removesubs);

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
     *
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
    @POST("appworkmanager/" + CHECK_ONESSUMMARY_BYDAYS)
    Observable<WorkLog> getLogDataToDay(@Field("loginid") String loginid,
                                        @Field("time") String time
    );


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
     *
     * @return
     */
    @FormUrlEncoded
    @POST("apponduty/getAppAllSch")
    Observable<Scheduling> getAllScheduling(
            @Field("month") String month,
            @Field("policeid") String policeid
    );

    /**
     * 查询某一天的排班记录
     *
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
     *
     * @return
     */
    @GET(DATRIX_BASE_URL + "preview/getImage?fileid=c1b09c7c-6e9f-43cc-8f16-780713066cc0&token=X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=")
    Observable<String> getdatrixPic();

    /**
     * 登录
     *
     * @param username
     * @return
     */
    @FormUrlEncoded
    @POST("applogin/appLogin")
    Observable<UserInfo> login(@Field("username") String username);


    /**
     * 登录2
     *
     * @param username
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("sysadmin/panel/newlogin.php")
    Observable<String> login2(@Field("user_name") String username,
                              @Field("password") String pwd,
                              @Field("syscode") String syscode);

    /**
     * 修改用户密码
     *
     * @param
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("sysadmin/panel/changePwd.php")
    Observable<String> changePass(@Field("user_id") String user_id,
                                  @Field("syscode") String syscode,
                                  @Field("old_passwd") String old_passwd,
                                  @Field("new_passwd") String new_passwd



    );

    /**
     * 检查版本
     *
     * @return
     */
    @GET("appupload/checkAppVersion")
    Observable<AppVersion> checkVersion();


    /**
     * 获取请假类型
     *
     * @return
     */
    @GET("appworkmanager/getLeaveType")
    Observable<LeaveType> getLeaveType();

    /**
     * 获取某人的上下级关系
     *
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

    /**
     * 获取社会信息采集类型
     *
     * @return
     */

    @GET("appinfocollect/getTypeOfSocialInfo")
    Observable<SICType> getTypeOfSocialInfo();


    /**
     * 获取某人的时间银行
     *
     * @param policeid
     * @param year
     * @return
     */
    @FormUrlEncoded
    @POST("appworkmanager/getOnceTimeBankDetail")
    Observable<String> getOnceTimeBankDetail(@Field("policeid") String policeid,
                                             @Field("year") String year);

    /**
     * 社会信息采集内容上传
     *
     * @param policeid
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("")
    Observable<String> SICUpload(
            @Field("policeid") String policeid,
            @FieldMap Map<String, String> map);

    /**
     * 获取社会信息采集类型
     *
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("appinfocollect/getFieldNameByType")
    Observable<String> getFieldNameByType(
            @Field("type") String type);

    /**
     * 上传信息采集input信息
     *
     * @param type
     * @param txt
     * @return
     */
    @FormUrlEncoded
    @POST("appinfocollect/addSocialInfoTxt")
    Observable<String> addSocialInfoTxt(
            @Field("type") String type
            , @Field("txt") String txt,
            @Field("policeid") String policeid,
            @Field("pic") String pic,
            @Field("video") String video);

    /**
     * 获取社会信息权限
     *
     * @return
     */
    @GET("appinfocollect/getTypeOfAuth")
    Observable<TypeOfAuth> getTypeOfAuth();

    /**
     * 获取社会信息采集历史
     *
     * @param policeid
     * @return
     */
    @FormUrlEncoded
    @POST("appinfocollect/getSocialInfoList")
    Observable<SICList> getSocialInfoList(@Field("policeid") String policeid);


    /**
     * 修改采集信息
     *
     * @param type
     * @param docid
     * @return
     */
    @FormUrlEncoded
    @POST("appinfocollect/updateSocialInfo")
    Observable<String> updateSocialInfo(@Field("docid") String docid,
                                        @Field("type") String type,
                                        @Field("txt") String txt,
                                        @Field("pic") String pic,
                                        @Field("video") String video,
                                        @Field("picdocid") String picdocid,
                                        @Field("videodocid") String videodocid,
                                        @Field("policeid") String policeid);


    /**
     * 获取历史社会信息采集内容
     *
     * @param type
     * @param docid
     * @return
     */
    @FormUrlEncoded
    @POST("appinfocollect/getTxtDetail")
    Observable<String> getTxtDetail(
            @Field("type") String type,
            @Field("docid") String docid);


    /**
     * 获取信息详情
     *
     * @param type
     * @param docid
     * @return
     */
    @FormUrlEncoded
    @POST("appinfocollect/getSocialInfoDetail")
    Observable<SICDetil> getSocialInfoDetil(
            @Field("type") String type,
            @Field("docid") String docid);


    /**
     * 获取工作云标签
     * @param policeid
     * @param wordtype
     * @param month
     * @return
     */
    @FormUrlEncoded
    @POST("appworkmanager/getWorkLogKeyWords")
    Observable<CloudTag> getWorkLogKeyWords(@Field("policeid") String policeid,
                                            @Field("wordtype") String wordtype,
                                            @Field("month") String month
                                         );

    /**
     * 根据标签词来查日志
     * @param policeid
     * @param key
     * @return
     */
    @FormUrlEncoded
    @POST("appworkmanager/getWordInfo")
    Observable<WorkCloudList> getWorkLogKeyWords(@Field("policeid") String policeid,
                                                 @Field("key") String key

    );

    /**
     * 获取所有部门
     * @return
     */
    @GET("appworkmanager/getAllDep")
    Observable<Deps> getAllDep();

    /**
     * 获取部门人员
     * @param depid
     * @return
     */
    @FormUrlEncoded
    @POST("appworkmanager/getDepMember")
    Observable<DepPeople> getDepMember(@Field("depid") String depid);

    /**
     * 设置上级
     * @param policeid
     * @param upperid
     * @return
     */
    @FormUrlEncoded
    @POST("appworkmanager/setUpper")
    Observable<String> setUpper(@Field("policeid") String policeid,
            @Field("upperid") String upperid);


    /**
     * 下载
     * @param fileUrl
     * @return
     */
    @GET
    Observable<ResponseBody> downloadFileWithFixedUrl(@Url String fileUrl);

}
