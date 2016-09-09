package wgz.com.cx_ga_project.API;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;
import wgz.com.cx_ga_project.bean.JiaBan;
import wgz.com.cx_ga_project.bean.UserBean;
import wgz.com.cx_ga_project.entity.Apply;
import wgz.com.cx_ga_project.entity.WorkLog;

/**
 * Created by wgz on 2016/8/1.
 */

public interface APIservice {
    /**
     * 通过 List<MultipartBody.Part> 传入多个part实现多文件上传
     * @param parts 每个part代表一个
     * @return 状态信息
     */
    @Multipart
    @POST("{type}")
    Observable<String> uploadFilesWithParts( @Path("type") String type,
                                             @Part() List<MultipartBody.Part> parts);


    /**
     * 通过 MultipartBody和@body作为参数来上传
     * @param multipartBody MultipartBody包含多个Part
     * @return 状态信息
     */
    @POST("{type}")
    Observable<String> uploadFileWithRequestBody( @Path("type") String type,
                                                  @Body MultipartBody multipartBody);






    @Multipart
    @POST("{type}")
    Observable<ResponseBody> uploadPic(
            @Path("type") String type,
            @Part MultipartBody.Part file);


    /**
     * 修改工作日志
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
    @GET("{type}")
    Observable<Apply> getBeanData(@Path("type") String type);


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


}
