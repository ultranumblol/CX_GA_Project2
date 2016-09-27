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
import wgz.com.cx_ga_project.entity.Apply;
import wgz.com.cx_ga_project.entity.DatrixCreat;
import wgz.com.cx_ga_project.entity.DatrixFinish;
import wgz.com.cx_ga_project.entity.WorkLog;

/**
 * Created by wgz on 2016/8/1.
 */

public interface APIservice {

    public static final String GET_USER_HEAD = "getAvantar";
    public static final String CHECK_ONESSUMMARY_BYDAYS = "checkOnceSummaryBydays";
    public static final String CHECK_ONESSUMMARY = "checkOnceSummary";
    public static final String UPLOAD_PICS = "saveAppPics";


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
    @POST("{type}")
    Observable<String> uploadFileWithRequestBody(@Path("type") String type,
                                                 @Body MultipartBody multipartBody);


    @POST("http://222.85.131.142:3007/apps/task/saveattachment")
    @Headers("ACCESS-TOKEN:X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=")
    Observable<String> testdatrix(@Body MultipartBody multipartBody);


    /**
     * detrix 创建文件creat方法(1)
     *
     * @param filename
     * @return
     */
    @FormUrlEncoded
    @POST("http://101.231.77.242:9001/api/cluster/tracker/file/create?uid=10098")
    @Headers("ACCESS-TOKEN:X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=")
    Observable<DatrixCreat> uploadFileWithRequestBodyTest(
            @Field("filename") String filename);


    /**
     * detrix 上传写入文件方法(2)
     *
     * @return
     */
    @Multipart
    @POST("http://101.231.77.242:9001/api/cluster/storage/file/write")
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
    @POST("http://101.231.77.242:9001/upload/finish")
    @Headers("ACCESS-TOKEN:X7yABwjE20sUJLefATUFqU0iUs8mJPqEJo6iRnV63mI=")
    Observable<String> detrixfinish(
            @Field("fileid") String fileid,
            @Field("customjson") String customjson
    );

/*
    @Multipart
    @POST("{type}")
    Observable<ResponseBody> uploadPic(
            @Path("type") String type,
            @Part MultipartBody.Part file);*/


    /**
     * 修改工作日志
     *
     * @param type
     * @param id
     * @param summary
     * @return
     */
    @FormUrlEncoded
    @POST("{type}")
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
    @POST("{type}")
    Observable<String> approvalApply(@Path("type") String type,
                                     @Field("id") String id,
                                     @Field("status") String status);

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
    @POST("{type}")
    Observable<String> upWorkLog(@Path("type") String type,
                                 @Field("loginid") String loginid,
                                 @Field("summary") String summary,
                                 @Field("time") String time);

    /**
     * 提交加班申请
     */
    @FormUrlEncoded
    @POST("{type}")
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
    @POST("{type}")
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
    @POST("{type}")
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
    @POST("{type}")
    Observable<WorkLog> getLogData(@Path("type") String type,
                                   @Field("loginid") String loginid,
                                   @Field("time") String time
    );


    /**
     * 获取某天工作日志
     *
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("{type}")
    Observable<WorkLog> getLogDataToDay(@Path("type") String type,
                                        @Field("loginid") String loginid,
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
     * @param type
     * @param policeid
     * @return
     */
    @FormUrlEncoded
    @POST("{type}")
    Observable<String> getUserhead(@Path("type") String type,
                                   @Field("policeid") String policeid
    );




    /*@FormUrlEncoded
    @POST("http://192.168.1.193:8004/appworkmanager/getAvantar")
    Observable<String> getUserHead(@Field("policeid") String policeid);*/
}
