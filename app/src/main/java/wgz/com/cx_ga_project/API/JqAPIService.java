package wgz.com.cx_ga_project.API;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

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


}
