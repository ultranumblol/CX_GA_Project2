package wgz.com.cx_ga_project.entity;


import com.google.gson.annotations.Expose;
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
        @Expose
        private String id;
        @SerializedName("jqid")
        @Expose
        private String jqid;
        @SerializedName("cj_taskid")
        @Expose
        private String cjTaskid;
        @SerializedName("cj_policeid")
        @Expose
        private String cjPoliceid;
        @SerializedName("cj_reporttime")
        @Expose
        private String cjReporttime;
        @SerializedName("involvecarplate")
        @Expose
        private String involvecarplate;
        @SerializedName("involvecarowner")
        @Expose
        private String involvecarowner;
        @SerializedName("involvecarowneridcard")
        @Expose
        private String involvecarowneridcard;
        @SerializedName("involvecardriver")
        @Expose
        private String involvecardriver;
        @SerializedName("involvecardriveridcard")
        @Expose
        private String involvecardriveridcard;
        @SerializedName("remark")
        @Expose
        private Object remark;
        @SerializedName("cartype")
        @Expose
        private String cartype;
        @SerializedName("carbrand")
        @Expose
        private String carbrand;
        @SerializedName("carcolor")
        @Expose
        private String carcolor;
        @SerializedName("carmodel")
        @Expose
        private String carmodel;
        @SerializedName("carnature")
        @Expose
        private String carnature;
        @SerializedName("driverphone")
        @Expose
        private String driverphone;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("ownerphone")
        @Expose
        private String ownerphone;
        @SerializedName("ownernature")
        @Expose
        private String ownernature;

        /**
         *
         * @return
         * The id
         */
        public String getId() {
            return id;
        }

        /**
         *
         * @param id
         * The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         *
         * @return
         * The jqid
         */
        public String getJqid() {
            return jqid;
        }

        /**
         *
         * @param jqid
         * The jqid
         */
        public void setJqid(String jqid) {
            this.jqid = jqid;
        }

        /**
         *
         * @return
         * The cjTaskid
         */
        public String getCjTaskid() {
            return cjTaskid;
        }

        /**
         *
         * @param cjTaskid
         * The cj_taskid
         */
        public void setCjTaskid(String cjTaskid) {
            this.cjTaskid = cjTaskid;
        }

        /**
         *
         * @return
         * The cjPoliceid
         */
        public String getCjPoliceid() {
            return cjPoliceid;
        }

        /**
         *
         * @param cjPoliceid
         * The cj_policeid
         */
        public void setCjPoliceid(String cjPoliceid) {
            this.cjPoliceid = cjPoliceid;
        }

        /**
         *
         * @return
         * The cjReporttime
         */
        public String getCjReporttime() {
            return cjReporttime;
        }

        /**
         *
         * @param cjReporttime
         * The cj_reporttime
         */
        public void setCjReporttime(String cjReporttime) {
            this.cjReporttime = cjReporttime;
        }

        /**
         *
         * @return
         * The involvecarplate
         */
        public String getInvolvecarplate() {
            return involvecarplate;
        }

        /**
         *
         * @param involvecarplate
         * The involvecarplate
         */
        public void setInvolvecarplate(String involvecarplate) {
            this.involvecarplate = involvecarplate;
        }

        /**
         *
         * @return
         * The involvecarowner
         */
        public String getInvolvecarowner() {
            return involvecarowner;
        }

        /**
         *
         * @param involvecarowner
         * The involvecarowner
         */
        public void setInvolvecarowner(String involvecarowner) {
            this.involvecarowner = involvecarowner;
        }

        /**
         *
         * @return
         * The involvecarowneridcard
         */
        public String getInvolvecarowneridcard() {
            return involvecarowneridcard;
        }

        /**
         *
         * @param involvecarowneridcard
         * The involvecarowneridcard
         */
        public void setInvolvecarowneridcard(String involvecarowneridcard) {
            this.involvecarowneridcard = involvecarowneridcard;
        }

        /**
         *
         * @return
         * The involvecardriver
         */
        public String getInvolvecardriver() {
            return involvecardriver;
        }

        /**
         *
         * @param involvecardriver
         * The involvecardriver
         */
        public void setInvolvecardriver(String involvecardriver) {
            this.involvecardriver = involvecardriver;
        }

        /**
         *
         * @return
         * The involvecardriveridcard
         */
        public String getInvolvecardriveridcard() {
            return involvecardriveridcard;
        }

        /**
         *
         * @param involvecardriveridcard
         * The involvecardriveridcard
         */
        public void setInvolvecardriveridcard(String involvecardriveridcard) {
            this.involvecardriveridcard = involvecardriveridcard;
        }

        /**
         *
         * @return
         * The remark
         */
        public Object getRemark() {
            return remark;
        }

        /**
         *
         * @param remark
         * The remark
         */
        public void setRemark(Object remark) {
            this.remark = remark;
        }

        /**
         *
         * @return
         * The cartype
         */
        public String getCartype() {
            return cartype;
        }

        /**
         *
         * @param cartype
         * The cartype
         */
        public void setCartype(String cartype) {
            this.cartype = cartype;
        }

        /**
         *
         * @return
         * The carbrand
         */
        public String getCarbrand() {
            return carbrand;
        }

        /**
         *
         * @param carbrand
         * The carbrand
         */
        public void setCarbrand(String carbrand) {
            this.carbrand = carbrand;
        }

        /**
         *
         * @return
         * The carcolor
         */
        public String getCarcolor() {
            return carcolor;
        }

        /**
         *
         * @param carcolor
         * The carcolor
         */
        public void setCarcolor(String carcolor) {
            this.carcolor = carcolor;
        }

        /**
         *
         * @return
         * The carmodel
         */
        public String getCarmodel() {
            return carmodel;
        }

        /**
         *
         * @param carmodel
         * The carmodel
         */
        public void setCarmodel(String carmodel) {
            this.carmodel = carmodel;
        }

        /**
         *
         * @return
         * The carnature
         */
        public String getCarnature() {
            return carnature;
        }

        /**
         *
         * @param carnature
         * The carnature
         */
        public void setCarnature(String carnature) {
            this.carnature = carnature;
        }

        /**
         *
         * @return
         * The driverphone
         */
        public String getDriverphone() {
            return driverphone;
        }

        /**
         *
         * @param driverphone
         * The driverphone
         */
        public void setDriverphone(String driverphone) {
            this.driverphone = driverphone;
        }

        /**
         *
         * @return
         * The gender
         */
        public String getGender() {
            return gender;
        }

        /**
         *
         * @param gender
         * The gender
         */
        public void setGender(String gender) {
            this.gender = gender;
        }

        /**
         *
         * @return
         * The ownerphone
         */
        public String getOwnerphone() {
            return ownerphone;
        }

        /**
         *
         * @param ownerphone
         * The ownerphone
         */
        public void setOwnerphone(String ownerphone) {
            this.ownerphone = ownerphone;
        }

        /**
         *
         * @return
         * The ownernature
         */
        public String getOwnernature() {
            return ownernature;
        }

        /**
         *
         * @param ownernature
         * The ownernature
         */
        public void setOwnernature(String ownernature) {
            this.ownernature = ownernature;
        }

    }

    public class Resperson {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("jqid")
        @Expose
        private String jqid;
        @SerializedName("cj_taskid")
        @Expose
        private String cjTaskid;
        @SerializedName("cj_policeid")
        @Expose
        private String cjPoliceid;
        @SerializedName("cj_reporttime")
        @Expose
        private String cjReporttime;
        @SerializedName("involvepeoplename")
        @Expose
        private String involvepeoplename;
        @SerializedName("involvepeopleidcard")
        @Expose
        private String involvepeopleidcard;
        @SerializedName("involvepeoplephone")
        @Expose
        private String involvepeoplephone;
        @SerializedName("involvepeoplemobilephone")
        @Expose
        private String involvepeoplemobilephone;
        @SerializedName("remark")
        @Expose
        private Object remark;
        @SerializedName("gander")
        @Expose
        private String gander;
        @SerializedName("addr")
        @Expose
        private String addr;
        @SerializedName("mac")
        @Expose
        private Object mac;
        @SerializedName("serialnumber")
        @Expose
        private String serialnumber;
        @SerializedName("simi")
        @Expose
        private String simi;
        @SerializedName("imei")
        @Expose
        private Object imei;
        @SerializedName("sim")
        @Expose
        private Object sim;
        @SerializedName("wechat")
        @Expose
        private Object wechat;

        /**
         *
         * @return
         * The id
         */
        public String getId() {
            return id;
        }

        /**
         *
         * @param id
         * The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         *
         * @return
         * The jqid
         */
        public String getJqid() {
            return jqid;
        }

        /**
         *
         * @param jqid
         * The jqid
         */
        public void setJqid(String jqid) {
            this.jqid = jqid;
        }

        /**
         *
         * @return
         * The cjTaskid
         */
        public String getCjTaskid() {
            return cjTaskid;
        }

        /**
         *
         * @param cjTaskid
         * The cj_taskid
         */
        public void setCjTaskid(String cjTaskid) {
            this.cjTaskid = cjTaskid;
        }

        /**
         *
         * @return
         * The cjPoliceid
         */
        public String getCjPoliceid() {
            return cjPoliceid;
        }

        /**
         *
         * @param cjPoliceid
         * The cj_policeid
         */
        public void setCjPoliceid(String cjPoliceid) {
            this.cjPoliceid = cjPoliceid;
        }

        /**
         *
         * @return
         * The cjReporttime
         */
        public String getCjReporttime() {
            return cjReporttime;
        }

        /**
         *
         * @param cjReporttime
         * The cj_reporttime
         */
        public void setCjReporttime(String cjReporttime) {
            this.cjReporttime = cjReporttime;
        }

        /**
         *
         * @return
         * The involvepeoplename
         */
        public String getInvolvepeoplename() {
            return involvepeoplename;
        }

        /**
         *
         * @param involvepeoplename
         * The involvepeoplename
         */
        public void setInvolvepeoplename(String involvepeoplename) {
            this.involvepeoplename = involvepeoplename;
        }

        /**
         *
         * @return
         * The involvepeopleidcard
         */
        public String getInvolvepeopleidcard() {
            return involvepeopleidcard;
        }

        /**
         *
         * @param involvepeopleidcard
         * The involvepeopleidcard
         */
        public void setInvolvepeopleidcard(String involvepeopleidcard) {
            this.involvepeopleidcard = involvepeopleidcard;
        }

        /**
         *
         * @return
         * The involvepeoplephone
         */
        public String getInvolvepeoplephone() {
            return involvepeoplephone;
        }

        /**
         *
         * @param involvepeoplephone
         * The involvepeoplephone
         */
        public void setInvolvepeoplephone(String involvepeoplephone) {
            this.involvepeoplephone = involvepeoplephone;
        }

        /**
         *
         * @return
         * The involvepeoplemobilephone
         */
        public String getInvolvepeoplemobilephone() {
            return involvepeoplemobilephone;
        }

        /**
         *
         * @param involvepeoplemobilephone
         * The involvepeoplemobilephone
         */
        public void setInvolvepeoplemobilephone(String involvepeoplemobilephone) {
            this.involvepeoplemobilephone = involvepeoplemobilephone;
        }

        /**
         *
         * @return
         * The remark
         */
        public Object getRemark() {
            return remark;
        }

        /**
         *
         * @param remark
         * The remark
         */
        public void setRemark(Object remark) {
            this.remark = remark;
        }

        /**
         *
         * @return
         * The gander
         */
        public String getGander() {
            return gander;
        }

        /**
         *
         * @param gander
         * The gander
         */
        public void setGander(String gander) {
            this.gander = gander;
        }

        /**
         *
         * @return
         * The addr
         */
        public String getAddr() {
            return addr;
        }

        /**
         *
         * @param addr
         * The addr
         */
        public void setAddr(String addr) {
            this.addr = addr;
        }

        /**
         *
         * @return
         * The mac
         */
        public Object getMac() {
            return mac;
        }

        /**
         *
         * @param mac
         * The mac
         */
        public void setMac(Object mac) {
            this.mac = mac;
        }

        /**
         *
         * @return
         * The serialnumber
         */
        public String getSerialnumber() {
            return serialnumber;
        }

        /**
         *
         * @param serialnumber
         * The serialnumber
         */
        public void setSerialnumber(String serialnumber) {
            this.serialnumber = serialnumber;
        }

        /**
         *
         * @return
         * The simi
         */
        public String getSimi() {
            return simi;
        }

        /**
         *
         * @param simi
         * The simi
         */
        public void setSimi(String simi) {
            this.simi = simi;
        }

        /**
         *
         * @return
         * The imei
         */
        public Object getImei() {
            return imei;
        }

        /**
         *
         * @param imei
         * The imei
         */
        public void setImei(Object imei) {
            this.imei = imei;
        }

        /**
         *
         * @return
         * The sim
         */
        public Object getSim() {
            return sim;
        }

        /**
         *
         * @param sim
         * The sim
         */
        public void setSim(Object sim) {
            this.sim = sim;
        }

        /**
         *
         * @return
         * The wechat
         */
        public Object getWechat() {
            return wechat;
        }

        /**
         *
         * @param wechat
         * The wechat
         */
        public void setWechat(Object wechat) {
            this.wechat = wechat;
        }

    }


    public class Resphone {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("jqid")
        @Expose
        private String jqid;
        @SerializedName("cj_taskid")
        @Expose
        private String cjTaskid;
        @SerializedName("cj_policeid")
        @Expose
        private String cjPoliceid;
        @SerializedName("cj_reporttime")
        @Expose
        private String cjReporttime;
        @SerializedName("relationname")
        @Expose
        private String relationname;
        @SerializedName("relationidcard")
        @Expose
        private String relationidcard;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("mobilephone")
        @Expose
        private String mobilephone;
        @SerializedName("remark")
        @Expose
        private Object remark;
        @SerializedName("mac")
        @Expose
        private String mac;
        @SerializedName("serialnumber")
        @Expose
        private String serialnumber;
        @SerializedName("simi")
        @Expose
        private String simi;

        /**
         *
         * @return
         * The id
         */
        public String getId() {
            return id;
        }

        /**
         *
         * @param id
         * The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         *
         * @return
         * The jqid
         */
        public String getJqid() {
            return jqid;
        }

        /**
         *
         * @param jqid
         * The jqid
         */
        public void setJqid(String jqid) {
            this.jqid = jqid;
        }

        /**
         *
         * @return
         * The cjTaskid
         */
        public String getCjTaskid() {
            return cjTaskid;
        }

        /**
         *
         * @param cjTaskid
         * The cj_taskid
         */
        public void setCjTaskid(String cjTaskid) {
            this.cjTaskid = cjTaskid;
        }

        /**
         *
         * @return
         * The cjPoliceid
         */
        public String getCjPoliceid() {
            return cjPoliceid;
        }

        /**
         *
         * @param cjPoliceid
         * The cj_policeid
         */
        public void setCjPoliceid(String cjPoliceid) {
            this.cjPoliceid = cjPoliceid;
        }

        /**
         *
         * @return
         * The cjReporttime
         */
        public String getCjReporttime() {
            return cjReporttime;
        }

        /**
         *
         * @param cjReporttime
         * The cj_reporttime
         */
        public void setCjReporttime(String cjReporttime) {
            this.cjReporttime = cjReporttime;
        }

        /**
         *
         * @return
         * The relationname
         */
        public String getRelationname() {
            return relationname;
        }

        /**
         *
         * @param relationname
         * The relationname
         */
        public void setRelationname(String relationname) {
            this.relationname = relationname;
        }

        /**
         *
         * @return
         * The relationidcard
         */
        public String getRelationidcard() {
            return relationidcard;
        }

        /**
         *
         * @param relationidcard
         * The relationidcard
         */
        public void setRelationidcard(String relationidcard) {
            this.relationidcard = relationidcard;
        }

        /**
         *
         * @return
         * The phone
         */
        public String getPhone() {
            return phone;
        }

        /**
         *
         * @param phone
         * The phone
         */
        public void setPhone(String phone) {
            this.phone = phone;
        }

        /**
         *
         * @return
         * The mobilephone
         */
        public String getMobilephone() {
            return mobilephone;
        }

        /**
         *
         * @param mobilephone
         * The mobilephone
         */
        public void setMobilephone(String mobilephone) {
            this.mobilephone = mobilephone;
        }

        /**
         *
         * @return
         * The remark
         */
        public Object getRemark() {
            return remark;
        }

        /**
         *
         * @param remark
         * The remark
         */
        public void setRemark(Object remark) {
            this.remark = remark;
        }

        /**
         *
         * @return
         * The mac
         */
        public String getMac() {
            return mac;
        }

        /**
         *
         * @param mac
         * The mac
         */
        public void setMac(String mac) {
            this.mac = mac;
        }

        /**
         *
         * @return
         * The serialnumber
         */
        public String getSerialnumber() {
            return serialnumber;
        }

        /**
         *
         * @param serialnumber
         * The serialnumber
         */
        public void setSerialnumber(String serialnumber) {
            this.serialnumber = serialnumber;
        }

        /**
         *
         * @return
         * The simi
         */
        public String getSimi() {
            return simi;
        }

        /**
         *
         * @param simi
         * The simi
         */
        public void setSimi(String simi) {
            this.simi = simi;
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