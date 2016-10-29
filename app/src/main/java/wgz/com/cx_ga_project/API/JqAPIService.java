package wgz.com.cx_ga_project.API;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;
import wgz.com.cx_ga_project.entity.CallerInfo;
import wgz.com.cx_ga_project.entity.ChatMsg;
import wgz.com.cx_ga_project.entity.ChuJingRen;
import wgz.com.cx_ga_project.entity.JQDetil;
import wgz.com.cx_ga_project.entity.JQOnDutyPeople;
import wgz.com.cx_ga_project.entity.JqCallBack;
import wgz.com.cx_ga_project.entity.JqOrbit;
import wgz.com.cx_ga_project.entity.NewJQ;
import wgz.com.cx_ga_project.entity.NewJQPush;

/**
 * Created by wgz on 2016/8/31.
 */

public interface JqAPIService {
    /**
     * 上传警情详情
     *
     * @param taskid
     * @param policeid
     * @param content
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/addJqReport")
    Observable<String> uploadJqMsg(
            @Field("taskid") String taskid,
            @Field("policeid") String policeid,
            @Field("content") String content,
            @Field("time") String time
    );

    /**
     * 添加涉警车辆信息回传
     *
     * @param jqid                   警情id
     * @param taskid                 taskid
     * @param cj_policeid            policeid
     * @param cj_reporttime          回告时间
     * @param involvecarplate        涉警车辆车牌号
     * @param involvecarowner        车主名
     * @param involvecarowneridcard  车主身份证号
     * @param involvecardriver       驾驶人名
     * @param involvecardriveridcard 驾驶人身份证号
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/addJqCar")
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
     *
     * @param jqid                     警情id
     * @param taskid                   taskid
     * @param cj_policeid              policeid
     * @param cj_reporttime            回告时间
     * @param involvepeoplename        涉警人
     * @param involvepeopleidcard      涉警人idcard
     * @param involvepeoplephone       涉警人座机
     * @param involvepeoplemobilephone 涉警人手机
     * @param gander                   涉警人性别
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/addJqPerson")
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
     * 添加涉警电话信息
     *
     * @param jqid
     * @param taskid
     * @param cj_policeid
     * @param cj_reporttime
     * @param involvepeoplename
     * @param involvepeopleidcard
     * @param involvepeoplephone
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/addJqPhone")
    Observable<String> uploadSjPhone(
            @Field("jqid") String jqid,
            @Field("taskid") String taskid,
            @Field("cj_policeid") String cj_policeid,
            @Field("cj_reporttime") String cj_reporttime,
            @Field("relationname") String involvepeoplename,
            @Field("relationidcard") String involvepeopleidcard,
            @Field("mobilephone") String involvepeoplephone
    );


    /**
     * 发送消息
     *
     * @param jqid
     * @param msg
     * @param taskid
     * @param policeid
     * @return
     */
    @FormUrlEncoded
    @POST("liveshow/sendMsgToPC")
    Observable<String> sendMsg(@Field("jqid") String jqid,
                               @Field("msg") String msg,
                               @Field("pic") String pic,
                               @Field("video") String video,
                               @Field("videopic") String videopic,
                               @Field("taskid") String taskid,
                               @Field("time") String time,
                               @Field("policeid") String policeid);


    /**
     * 查询聊天记录
     *
     * @param jqid
     * @param msg
     * @return
     */
    @FormUrlEncoded
    @POST("liveshow/getAppMsgList")
    Observable<ChatMsg> GetMsg(@Field("jqid") String jqid,
                               @Field("policeid") String msg);

    /**
     * 获取新消息
     *
     * @param jqid
     * @param msg
     * @return
     */
    @FormUrlEncoded
    @POST("liveshow/getAppNewMsg")
    Observable<ChatMsg> GetNewMsg(@Field("jqid") String jqid,
                                  @Field("policeid") String msg);

    /**
     * 获取新消息不修改数据库
     *
     * @param jqid
     * @param msg
     * @return
     */
    @FormUrlEncoded
    @POST("liveshow/getAppNewMsgs")
    Observable<ChatMsg> GetNewMsg2(@Field("jqid") String jqid,
                                   @Field("policeid") String msg);


    //http://192.168.1.193:8004/nearbyresources/getDetailjqInfo

    /**
     * 获取警情详情
     *
     * @param key
     * @return
     */
    @FormUrlEncoded
    @POST("nearbyresources/getDetailjqInfo")
    Observable<JQDetil> GetJQDetil(@Field("key") String key);


    /**
     * 获取警情回告页面的所有涉警信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/getAllDetail")
    Observable<JqCallBack> GetAllJQDetil(@Field("jqid") String jqid);


    /**
     * 获取一个警情的出警人名单
     *
     * @param taskid
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/getTaskPeople")
    Observable<ChuJingRen> GetCJRList(@Field("taskid") String taskid);


    /**
     * 开始新警情
     *
     * @param jqid
     * @param taskid
     * @param arrivetime
     * @param gps_e
     * @param gps_n
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/locationBack")
    Observable<String> StartNewFight(@Field("jqid") String jqid,
                                     @Field("taskid") String taskid,
                                     @Field("arrivetime") String arrivetime,
                                     @Field("gps_e") String gps_e,
                                     @Field("gps_n") String gps_n);

    /**
     * 获取警情处理轨迹
     *
     * @param jqid
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/getJqOrbit")
    Observable<JqOrbit> getJqOrbit(@Field("jqid") String jqid);

    /**
     * 获取附近警情列表
     *
     * @param gps_e   //   longitude
     * @param gps_n   //   latitude
     * @param page
     * @param perpage
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/getNearHisJq")
    Observable<JQDetil> getNearHisJq(@Field("gps_e") String gps_e,
                                     @Field("gps_n") String gps_n,
                                     @Field("page") String page,
                                     @Field("perpage") String perpage);

    /**
     * 获取报警人警情列表
     *
     * @param caller
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/getCallerInfo")
    Observable<CallerInfo> getCallerInfo(@Field("caller") String caller);

    /**
     * 获取涉警人警情列表
     *
     * @param policeid
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/getPoliceJqInfo")
    Observable<JQDetil> getPoliceJqInfo(@Field("policeid") String policeid);


    /**
     * 获取某人的上下级关系
     *
     * @param policeid
     * @return
     */
    @FormUrlEncoded
    @POST("apponduty/getSupAndSub")
    Observable<String> getSupAndSub(@Field("policeid") String policeid);


    /**
     * 获取新警情消息
     *
     * @param policeid
     * @param depid    部门id 532301590000
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/msgPush")
    Observable<NewJQPush> jqmsgPush(@Field("policeid") String policeid,
                                    @Field("depid") String depid);


    /**
     * 获取某警情值班人员
     *
     * @param depid 部门id
     * @param stime 发送时间
     * @return
     */
    @FormUrlEncoded
    @POST("apponduty/getDepOnduty")
    Observable<JQOnDutyPeople> getPeoOnduty(@Field("depid") String depid,
                                            @Field("stime") String stime);

    /**
     * 选择添加出警人
     *
     * @param taskid
     * @param policeid
     * @param depid
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/addCjPerson")
    Observable<String> addCjPerson(@Field("taskid") String taskid,
                                   //@Field("policeid") String policeid,
                                   @Field("depid") String depid,
                                   @Field("policeid[]") String... policeid
    );

    /**
     * 查询新警情还没有出警人的
     *
     * @param policeid
     * @param depid
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/getNewJqlist")
    Observable<NewJQ> getNewJqlist(@Field("policeid") String policeid,
                                    @Field("depid") String depid);


    /**
     * 查询新警情还没有出警人的
     *
     * @param policeid
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/getNewJqlist1")
    Observable<NewJQ> getNewJqlist1(@Field("policeid") String policeid);


    /**
     * 获取警员的历史警情
     * @param policeid
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/getOldJqlist")
    Observable<NewJQ> getOldJqlist(@Field("policeid") String policeid);


    /**
     * 停止某警情
     * @param status
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("appjqreport/stopTaskJq")
    Observable<String> stopTaskJq(@Field("status") String status,
                                 @Field("id") String id);



    @GET("appjqreport/getAllDep")
    Observable<String> getAllDep();
}
