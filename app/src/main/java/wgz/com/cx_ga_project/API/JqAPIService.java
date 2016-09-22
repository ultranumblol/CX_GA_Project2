package wgz.com.cx_ga_project.API;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import wgz.com.cx_ga_project.entity.ChatMsg;
import wgz.com.cx_ga_project.entity.JQDetil;

/**
 * Created by wgz on 2016/8/31.
 */

public interface JqAPIService {
    /**
     * 上传警情详情
     * 通过 MultipartBody和@body作为参数来上传
     * @return 状态信息
     */
    @FormUrlEncoded
    @POST("http://192.168.1.193:8004/appjqreport/addJqReport")
    Observable<String> uploadJqMsg(
                                   @Field("taskid") String taskid,
                                   @Field("policeid") String policeid,
                                   @Field("content") String content,
                                   @Field("time") String time
                                  );

    /**
     * 添加涉警 车辆信息回传
     * @param jqid  警情id
     * @param taskid taskid
     * @param cj_policeid policeid
     * @param cj_reporttime 回告时间
     * @param involvecarplate  涉警车辆车牌号
     * @param involvecarowner 车主名
     * @param involvecarowneridcard  车主身份证号
     * @param involvecardriver 驾驶人名
     * @param involvecardriveridcard 驾驶人身份证号
     * @return
     */
    @FormUrlEncoded
    @POST("http://192.168.1.193:8004/appjqreport/addJqCar")
    Observable<String> uploadSjCar(
            @Field("jqid") String jqid,
            @Field("taskid") String taskid,
            @Field("cj_policeid") String cj_policeid,
            @Field("cj_reporttime") String cj_reporttime,
            @Field("involvecarplate") String involvecarplate,
            @Field("involvecarowner") String involvecarowner,
            @Field("involvecarowneridcard") String involvecarowneridcard,
            @Field("involvecardriver") String involvecardriver,
            @Field("involvecardriveridcard") String involvecardriveridcard
    );

    /**
     * 添加涉警人信息
     * @param jqid 警情id
     * @param taskid taskid
     * @param cj_policeid  policeid
     * @param cj_reporttime 回告时间
     * @param involvepeoplename 涉警人
     * @param involvepeopleidcard 涉警人idcard
     * @param involvepeoplephone 涉警人座机
     * @param involvepeoplemobilephone 涉警人手机
     * @param gander 涉警人性别
     * @return
     */
    @FormUrlEncoded
    @POST("http://192.168.1.193:8004/appjqreport/addJqPerson")
    Observable<String> uploadSjPerson(
            @Field("jqid") String jqid,
            @Field("taskid") String taskid,
            @Field("cj_policeid") String cj_policeid,
            @Field("cj_reporttime") String cj_reporttime,
            @Field("involvepeoplename") String involvepeoplename,
            @Field("involvepeopleidcard") String involvepeopleidcard,
            @Field("involvepeoplephone") String involvepeoplephone,
            @Field("involvepeoplemobilephone") String involvepeoplemobilephone,
            @Field("gander") String gander
    );

    /**
     * 发送消息
     * @param jqid
     * @param msg
     * @param taskid
     * @param policeid
     * @return
     */
    @FormUrlEncoded
    @POST("http://192.168.1.193:8004/liveshow/sendMsgToPC")
    Observable<String> sendMsg(@Field("jqid") String jqid,
                               @Field("msg") String msg,
                               @Field("pic") String pic,
                               @Field("taskid") String taskid,
                               @Field("time") String time,
                               @Field("policeid") String policeid);



    @FormUrlEncoded
    @POST("http://192.168.1.193:8004/liveshow/getAppMsgList")
    Observable<ChatMsg> GetMsg(@Field("jqid") String jqid,
                               @Field("policeid") String msg);

    /**
     * 获取新消息
     * @param jqid
     * @param msg
     * @return
     */
    @FormUrlEncoded
    @POST("http://192.168.1.193:8004/liveshow/getAppNewMsg")
    Observable<ChatMsg> GetNewMsg(@Field("jqid") String jqid,
                               @Field("policeid") String msg);

    /**
     * 获取新消息不修改数据库
     * @param jqid
     * @param msg
     * @return
     */
    @FormUrlEncoded
    @POST("http://192.168.1.193:8004/liveshow/getAppNewMsgs")
    Observable<ChatMsg> GetNewMsg2(@Field("jqid") String jqid,
                                  @Field("policeid") String msg);



    //http://192.168.1.193:8004/nearbyresources/getDetailjqInfo

    /**
     * 获取警情详情
     * @param key
     * @return
     */
    @FormUrlEncoded
    @POST("http://192.168.1.193:8004/nearbyresources/getDetailjqInfo")
    Observable<JQDetil> GetJQDetil(@Field("key") String key);

}
