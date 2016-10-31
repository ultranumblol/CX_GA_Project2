package wgz.com.cx_ga_project.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwerr on 2016/9/5.
 */

public class JqCallBack {
    @SerializedName("code")

    private Integer code;
    @SerializedName("rescar")

    private List<Rescar> rescar = new ArrayList<Rescar>();
    @SerializedName("resreport")

    private List<Resreport> resreport = new ArrayList<Resreport>();
    @SerializedName("respeople")

    private List<Resperson> respeople = new ArrayList<Resperson>();
    @SerializedName("resphone")

    private List<Resphone> resphone = new ArrayList<Resphone>();

    @Override
    public String toString() {
        return "JqCallBack{" +
                "code=" + code +
                ", rescar=" + rescar +
                ", resreport=" + resreport +
                ", respeople=" + respeople +
                ", resphone=" + resphone +
                '}';
    }

    /**
     * @return The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return The rescar
     */
    public List<Rescar> getRescar() {
        return rescar;
    }

    /**
     * @param rescar The rescar
     */
    public void setRescar(List<Rescar> rescar) {
        this.rescar = rescar;
    }

    /**
     * @return The resreport
     */
    public List<Resreport> getResreport() {
        return resreport;
    }

    /**
     * @param resreport The resreport
     */
    public void setResreport(List<Resreport> resreport) {
        this.resreport = resreport;
    }

    /**
     * @return The respeople
     */
    public List<Resperson> getRespeople() {
        return respeople;
    }

    /**
     * @param respeople The respeople
     */
    public void setRespeople(List<Resperson> respeople) {
        this.respeople = respeople;
    }

    /**
     * @return The resphone
     */
    public List<Resphone> getResphone() {
        return resphone;
    }

    /**
     * @param resphone The resphone
     */
    public void setResphone(List<Resphone> resphone) {
        this.resphone = resphone;
    }


    public class Rescar {

        @SerializedName("id")

        private String id;
        @SerializedName("jqid")

        private String jqid;
        @SerializedName("cj_taskid")

        private String cjTaskid;
        @SerializedName("cj_policeid")

        private String cjPoliceid;
        @SerializedName("cj_reporttime")

        private String cjReporttime;
        @SerializedName("involvecarplate")

        private String involvecarplate;
        @SerializedName("involvecarowner")

        private String involvecarowner;
        @SerializedName("involvecarowneridcard")

        private String involvecarowneridcard;
        @SerializedName("involvecardriver")

        private String involvecardriver;
        @SerializedName("involvecardriveridcard")

        private String involvecardriveridcard;

        @Override
        public String toString() {
            return "Rescar{" +
                    "id='" + id + '\'' +
                    ", jqid='" + jqid + '\'' +
                    ", cjTaskid='" + cjTaskid + '\'' +
                    ", cjPoliceid='" + cjPoliceid + '\'' +
                    ", cjReporttime='" + cjReporttime + '\'' +
                    ", involvecarplate='" + involvecarplate + '\'' +
                    ", involvecarowner='" + involvecarowner + '\'' +
                    ", involvecarowneridcard='" + involvecarowneridcard + '\'' +
                    ", involvecardriver='" + involvecardriver + '\'' +
                    ", involvecardriveridcard='" + involvecardriveridcard + '\'' +
                    ", remark=" + remark +
                    '}';
        }

        @SerializedName("remark")

        private Object remark;

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The jqid
         */
        public String getJqid() {
            return jqid;
        }

        /**
         * @param jqid The jqid
         */
        public void setJqid(String jqid) {
            this.jqid = jqid;
        }

        /**
         * @return The cjTaskid
         */
        public String getCjTaskid() {
            return cjTaskid;
        }

        /**
         * @param cjTaskid The cj_taskid
         */
        public void setCjTaskid(String cjTaskid) {
            this.cjTaskid = cjTaskid;
        }

        /**
         * @return The cjPoliceid
         */
        public String getCjPoliceid() {
            return cjPoliceid;
        }

        /**
         * @param cjPoliceid The cj_policeid
         */
        public void setCjPoliceid(String cjPoliceid) {
            this.cjPoliceid = cjPoliceid;
        }

        /**
         * @return The cjReporttime
         */
        public String getCjReporttime() {
            return cjReporttime;
        }

        /**
         * @param cjReporttime The cj_reporttime
         */
        public void setCjReporttime(String cjReporttime) {
            this.cjReporttime = cjReporttime;
        }

        /**
         * @return The involvecarplate
         */
        public String getInvolvecarplate() {
            return involvecarplate;
        }

        /**
         * @param involvecarplate The involvecarplate
         */
        public void setInvolvecarplate(String involvecarplate) {
            this.involvecarplate = involvecarplate;
        }

        /**
         * @return The involvecarowner
         */
        public String getInvolvecarowner() {
            return involvecarowner;
        }

        /**
         * @param involvecarowner The involvecarowner
         */
        public void setInvolvecarowner(String involvecarowner) {
            this.involvecarowner = involvecarowner;
        }

        /**
         * @return The involvecarowneridcard
         */
        public String getInvolvecarowneridcard() {
            return involvecarowneridcard;
        }

        /**
         * @param involvecarowneridcard The involvecarowneridcard
         */
        public void setInvolvecarowneridcard(String involvecarowneridcard) {
            this.involvecarowneridcard = involvecarowneridcard;
        }

        /**
         * @return The involvecardriver
         */
        public String getInvolvecardriver() {
            return involvecardriver;
        }

        /**
         * @param involvecardriver The involvecardriver
         */
        public void setInvolvecardriver(String involvecardriver) {
            this.involvecardriver = involvecardriver;
        }

        /**
         * @return The involvecardriveridcard
         */
        public String getInvolvecardriveridcard() {
            return involvecardriveridcard;
        }

        /**
         * @param involvecardriveridcard The involvecardriveridcard
         */
        public void setInvolvecardriveridcard(String involvecardriveridcard) {
            this.involvecardriveridcard = involvecardriveridcard;
        }

        /**
         * @return The remark
         */
        public Object getRemark() {
            return remark;
        }

        /**
         * @param remark The remark
         */
        public void setRemark(Object remark) {
            this.remark = remark;
        }

    }

    public class Resperson {

        @SerializedName("id")

        private String id;
        @SerializedName("jqid")

        private String jqid;
        @SerializedName("cj_taskid")

        private String cjTaskid;
        @SerializedName("cj_policeid")

        private String cjPoliceid;
        @SerializedName("cj_reporttime")

        private Object cjReporttime;
        @SerializedName("involvepeoplename")

        private String involvepeoplename;
        @SerializedName("involvepeopleidcard")

        private String involvepeopleidcard;
        @SerializedName("involvepeoplephone")

        private String involvepeoplephone;
        @SerializedName("involvepeoplemobilephone")

        private String involvepeoplemobilephone;
        @SerializedName("remark")

        private Object remark;
        @SerializedName("gander")

        private String gander;
        @SerializedName("mac")

        private Object mac;
        @SerializedName("imei")

        private Object imei;
        @SerializedName("sim")

        private Object sim;
        @SerializedName("wechat")

        private Object wechat;

        @Override
        public String toString() {
            return "Resperson{" +
                    "id='" + id + '\'' +
                    ", jqid='" + jqid + '\'' +
                    ", cjTaskid='" + cjTaskid + '\'' +
                    ", cjPoliceid='" + cjPoliceid + '\'' +
                    ", cjReporttime=" + cjReporttime +
                    ", involvepeoplename='" + involvepeoplename + '\'' +
                    ", involvepeopleidcard='" + involvepeopleidcard + '\'' +
                    ", involvepeoplephone='" + involvepeoplephone + '\'' +
                    ", involvepeoplemobilephone='" + involvepeoplemobilephone + '\'' +
                    ", remark=" + remark +
                    ", gander='" + gander + '\'' +
                    ", mac=" + mac +
                    ", imei=" + imei +
                    ", sim=" + sim +
                    ", wechat=" + wechat +
                    '}';
        }

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The jqid
         */
        public String getJqid() {
            return jqid;
        }

        /**
         * @param jqid The jqid
         */
        public void setJqid(String jqid) {
            this.jqid = jqid;
        }

        /**
         * @return The cjTaskid
         */
        public String getCjTaskid() {
            return cjTaskid;
        }

        /**
         * @param cjTaskid The cj_taskid
         */
        public void setCjTaskid(String cjTaskid) {
            this.cjTaskid = cjTaskid;
        }

        /**
         * @return The cjPoliceid
         */
        public String getCjPoliceid() {
            return cjPoliceid;
        }

        /**
         * @param cjPoliceid The cj_policeid
         */
        public void setCjPoliceid(String cjPoliceid) {
            this.cjPoliceid = cjPoliceid;
        }

        /**
         * @return The cjReporttime
         */
        public Object getCjReporttime() {
            return cjReporttime;
        }

        /**
         * @param cjReporttime The cj_reporttime
         */
        public void setCjReporttime(Object cjReporttime) {
            this.cjReporttime = cjReporttime;
        }

        /**
         * @return The involvepeoplename
         */
        public String getInvolvepeoplename() {
            return involvepeoplename;
        }

        /**
         * @param involvepeoplename The involvepeoplename
         */
        public void setInvolvepeoplename(String involvepeoplename) {
            this.involvepeoplename = involvepeoplename;
        }

        /**
         * @return The involvepeopleidcard
         */
        public String getInvolvepeopleidcard() {
            return involvepeopleidcard;
        }

        /**
         * @param involvepeopleidcard The involvepeopleidcard
         */
        public void setInvolvepeopleidcard(String involvepeopleidcard) {
            this.involvepeopleidcard = involvepeopleidcard;
        }

        /**
         * @return The involvepeoplephone
         */
        public String getInvolvepeoplephone() {
            return involvepeoplephone;
        }

        /**
         * @param involvepeoplephone The involvepeoplephone
         */
        public void setInvolvepeoplephone(String involvepeoplephone) {
            this.involvepeoplephone = involvepeoplephone;
        }

        /**
         * @return The involvepeoplemobilephone
         */
        public String getInvolvepeoplemobilephone() {
            return involvepeoplemobilephone;
        }

        /**
         * @param involvepeoplemobilephone The involvepeoplemobilephone
         */
        public void setInvolvepeoplemobilephone(String involvepeoplemobilephone) {
            this.involvepeoplemobilephone = involvepeoplemobilephone;
        }

        /**
         * @return The remark
         */
        public Object getRemark() {
            return remark;
        }

        /**
         * @param remark The remark
         */
        public void setRemark(Object remark) {
            this.remark = remark;
        }

        /**
         * @return The gander
         */
        public String getGander() {
            return gander;
        }

        /**
         * @param gander The gander
         */
        public void setGander(String gander) {
            this.gander = gander;
        }

        /**
         * @return The mac
         */
        public Object getMac() {
            return mac;
        }

        /**
         * @param mac The mac
         */
        public void setMac(Object mac) {
            this.mac = mac;
        }

        /**
         * @return The imei
         */
        public Object getImei() {
            return imei;
        }

        /**
         * @param imei The imei
         */
        public void setImei(Object imei) {
            this.imei = imei;
        }

        /**
         * @return The sim
         */
        public Object getSim() {
            return sim;
        }

        /**
         * @param sim The sim
         */
        public void setSim(Object sim) {
            this.sim = sim;
        }

        /**
         * @return The wechat
         */
        public Object getWechat() {
            return wechat;
        }

        /**
         * @param wechat The wechat
         */
        public void setWechat(Object wechat) {
            this.wechat = wechat;
        }

    }


    public class Resphone {

        @SerializedName("id")

        private String id;
        @SerializedName("jqid")

        private String jqid;
        @SerializedName("cj_taskid")

        private Object cjTaskid;
        @SerializedName("cj_policeid")

        private Object cjPoliceid;
        @SerializedName("cj_reporttime")

        private Object cjReporttime;
        @SerializedName("relationname")

        private String relationname;
        @SerializedName("relationidcard")

        private String relationidcard;
        @SerializedName("phone")

        private String phone;
        @SerializedName("mobilephone")

        private String mobilephone;

        @Override
        public String toString() {
            return "Resphone{" +
                    "id='" + id + '\'' +
                    ", jqid='" + jqid + '\'' +
                    ", cjTaskid=" + cjTaskid +
                    ", cjPoliceid=" + cjPoliceid +
                    ", cjReporttime=" + cjReporttime +
                    ", relationname='" + relationname + '\'' +
                    ", relationidcard=" + relationidcard +
                    ", phone='" + phone + '\'' +
                    ", mobilephone='" + mobilephone + '\'' +
                    ", remark=" + remark +
                    '}';
        }

        @SerializedName("remark")

        private Object remark;

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The jqid
         */
        public String getJqid() {
            return jqid;
        }

        /**
         * @param jqid The jqid
         */
        public void setJqid(String jqid) {
            this.jqid = jqid;
        }

        /**
         * @return The cjTaskid
         */
        public Object getCjTaskid() {
            return cjTaskid;
        }

        /**
         * @param cjTaskid The cj_taskid
         */
        public void setCjTaskid(Object cjTaskid) {
            this.cjTaskid = cjTaskid;
        }

        /**
         * @return The cjPoliceid
         */
        public Object getCjPoliceid() {
            return cjPoliceid;
        }

        /**
         * @param cjPoliceid The cj_policeid
         */
        public void setCjPoliceid(Object cjPoliceid) {
            this.cjPoliceid = cjPoliceid;
        }

        /**
         * @return The cjReporttime
         */
        public Object getCjReporttime() {
            return cjReporttime;
        }

        /**
         * @param cjReporttime The cj_reporttime
         */
        public void setCjReporttime(Object cjReporttime) {
            this.cjReporttime = cjReporttime;
        }

        /**
         * @return The relationname
         */
        public String getRelationname() {
            return relationname;
        }

        /**
         * @param relationname The relationname
         */
        public void setRelationname(String relationname) {
            this.relationname = relationname;
        }

        /**
         * @return The relationidcard
         */
        public String getRelationidcard() {
            return relationidcard;
        }

        /**
         * @param relationidcard The relationidcard
         */
        public void setRelationidcard(String relationidcard) {
            this.relationidcard = relationidcard;
        }

        /**
         * @return The phone
         */
        public String getPhone() {
            return phone;
        }

        /**
         * @param phone The phone
         */
        public void setPhone(String phone) {
            this.phone = phone;
        }

        /**
         * @return The mobilephone
         */
        public String getMobilephone() {
            return mobilephone;
        }

        /**
         * @param mobilephone The mobilephone
         */
        public void setMobilephone(String mobilephone) {
            this.mobilephone = mobilephone;
        }

        /**
         * @return The remark
         */
        public Object getRemark() {
            return remark;
        }

        /**
         * @param remark The remark
         */
        public void setRemark(Object remark) {
            this.remark = remark;
        }

    }

    public class Resreport {

        @SerializedName("id")

        private String id;
        @SerializedName("jqid")

        private String jqid;
        @SerializedName("taskid")

        private String taskid;
        @SerializedName("policeid")

        private String policeid;
        @SerializedName("txt")

        private String txt;
        @SerializedName("pic")

        private String pic;
        @SerializedName("video")

        private String video;
        @SerializedName("reporttime")

        private String reporttime;
        @SerializedName("remark")

        private Object remark;
        @SerializedName("policename")

        private String policename;

        public String getOfficecodename() {
            return officecodename;
        }

        public void setOfficecodename(String officecodename) {
            this.officecodename = officecodename;
        }

        @SerializedName("officecodename")
        private String officecodename;

        @Override
        public String toString() {
            return "Resreport{" +
                    "id='" + id + '\'' +
                    ", jqid='" + jqid + '\'' +
                    ", taskid='" + taskid + '\'' +
                    ", policeid='" + policeid + '\'' +
                    ", txt='" + txt + '\'' +
                    ", pic='" + pic + '\'' +
                    ", video='" + video + '\'' +
                    ", reporttime='" + reporttime + '\'' +
                    ", remark=" + remark +
                    ", policename='" + policename + '\'' +
                    '}';
        }

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The jqid
         */
        public String getJqid() {
            return jqid;
        }

        /**
         * @param jqid The jqid
         */
        public void setJqid(String jqid) {
            this.jqid = jqid;
        }

        /**
         * @return The taskid
         */
        public String getTaskid() {
            return taskid;
        }

        /**
         * @param taskid The taskid
         */
        public void setTaskid(String taskid) {
            this.taskid = taskid;
        }

        /**
         * @return The policeid
         */
        public String getPoliceid() {
            return policeid;
        }

        /**
         * @param policeid The policeid
         */
        public void setPoliceid(String policeid) {
            this.policeid = policeid;
        }

        /**
         * @return The txt
         */
        public String getTxt() {
            return txt;
        }

        /**
         * @param txt The txt
         */
        public void setTxt(String txt) {
            this.txt = txt;
        }

        /**
         * @return The pic
         */
        public String getPic() {
            return pic;
        }

        /**
         * @param pic The pic
         */
        public void setPic(String pic) {
            this.pic = pic;
        }

        /**
         * @return The video
         */
        public String getVideo() {
            return video;
        }

        /**
         * @param video The video
         */
        public void setVideo(String video) {
            this.video = video;
        }

        /**
         * @return The reporttime
         */
        public String getReporttime() {
            return reporttime;
        }

        /**
         * @param reporttime The reporttime
         */
        public void setReporttime(String reporttime) {
            this.reporttime = reporttime;
        }

        /**
         * @return The remark
         */
        public Object getRemark() {
            return remark;
        }

        /**
         * @param remark The remark
         */
        public void setRemark(Object remark) {
            this.remark = remark;
        }

        /**
         * @return The policename
         */
        public String getPolicename() {
            return policename;
        }

        /**
         * @param policename The policename
         */
        public void setPolicename(String policename) {
            this.policename = policename;
        }

    }

}