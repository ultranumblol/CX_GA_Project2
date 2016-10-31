package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/10/31.
 */

public class NearJQ {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("res")
    @Expose
    private List<Re> res = new ArrayList<Re>();

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
     * @return The res
     */
    public List<Re> getRes() {
        return res;
    }

    /**
     * @param res The res
     */
    public void setRes(List<Re> res) {
        this.res = res;
    }

    public class Re {

        @SerializedName("briefcase")
        @Expose
        private String briefcase;
        @SerializedName("jqgps")
        @Expose
        private Object jqgps;
        @SerializedName("jqlaiyuan")
        @Expose
        private Object jqlaiyuan;
        @SerializedName("gps_e")
        @Expose
        private String gpsE;
        @SerializedName("gps_n")
        @Expose
        private String gpsN;
        @SerializedName("jqid")
        @Expose
        private String jqid;
        @SerializedName("callpolicetime")
        @Expose
        private String callpolicetime;
        @SerializedName("jqaddr")
        @Expose
        private String jqaddr;
        @SerializedName("callingnumber")
        @Expose
        private String callingnumber;
        @SerializedName("callednumber")
        @Expose
        private String callednumber;
        @SerializedName("jqtype")
        @Expose
        private String jqtype;
        @SerializedName("jqnature")
        @Expose
        private String jqnature;
        @SerializedName("property_classification")
        @Expose
        private Object propertyClassification;
        @SerializedName("alarmperson")
        @Expose
        private String alarmperson;
        @SerializedName("attendant")
        @Expose
        private String attendant;
        @SerializedName("ringingduration")
        @Expose
        private String ringingduration;
        @SerializedName("talktime")
        @Expose
        private String talktime;
        @SerializedName("jqddsj")
        @Expose
        private Object jqddsj;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("belongarea")
        @Expose
        private Object belongarea;
        @SerializedName("belongareabw")
        @Expose
        private Object belongareabw;

        @Override
        public String toString() {
            return "Re{" +
                    "briefcase='" + briefcase + '\'' +
                    ", jqgps=" + jqgps +
                    ", jqlaiyuan=" + jqlaiyuan +
                    ", gpsE='" + gpsE + '\'' +
                    ", gpsN='" + gpsN + '\'' +
                    ", jqid='" + jqid + '\'' +
                    ", callpolicetime='" + callpolicetime + '\'' +
                    ", jqaddr='" + jqaddr + '\'' +
                    ", callingnumber='" + callingnumber + '\'' +
                    ", callednumber='" + callednumber + '\'' +
                    ", jqtype='" + jqtype + '\'' +
                    ", jqnature='" + jqnature + '\'' +
                    ", propertyClassification=" + propertyClassification +
                    ", alarmperson='" + alarmperson + '\'' +
                    ", attendant='" + attendant + '\'' +
                    ", ringingduration='" + ringingduration + '\'' +
                    ", talktime='" + talktime + '\'' +
                    ", jqddsj=" + jqddsj +
                    ", id='" + id + '\'' +
                    ", belongarea=" + belongarea +
                    ", belongareabw=" + belongareabw +
                    '}';
        }

        /**
         * @return The briefcase
         */
        public String getBriefcase() {
            return briefcase;
        }

        /**
         * @param briefcase The briefcase
         */
        public void setBriefcase(String briefcase) {
            this.briefcase = briefcase;
        }

        /**
         * @return The jqgps
         */
        public Object getJqgps() {
            return jqgps;
        }

        /**
         * @param jqgps The jqgps
         */
        public void setJqgps(Object jqgps) {
            this.jqgps = jqgps;
        }

        /**
         * @return The jqlaiyuan
         */
        public Object getJqlaiyuan() {
            return jqlaiyuan;
        }

        /**
         * @param jqlaiyuan The jqlaiyuan
         */
        public void setJqlaiyuan(Object jqlaiyuan) {
            this.jqlaiyuan = jqlaiyuan;
        }

        /**
         * @return The gpsE
         */
        public String getGpsE() {
            return gpsE;
        }

        /**
         * @param gpsE The gps_e
         */
        public void setGpsE(String gpsE) {
            this.gpsE = gpsE;
        }

        /**
         * @return The gpsN
         */
        public String getGpsN() {
            return gpsN;
        }

        /**
         * @param gpsN The gps_n
         */
        public void setGpsN(String gpsN) {
            this.gpsN = gpsN;
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
         * @return The callpolicetime
         */
        public String getCallpolicetime() {
            return callpolicetime;
        }

        /**
         * @param callpolicetime The callpolicetime
         */
        public void setCallpolicetime(String callpolicetime) {
            this.callpolicetime = callpolicetime;
        }

        /**
         * @return The jqaddr
         */
        public String getJqaddr() {
            return jqaddr;
        }

        /**
         * @param jqaddr The jqaddr
         */
        public void setJqaddr(String jqaddr) {
            this.jqaddr = jqaddr;
        }

        /**
         * @return The callingnumber
         */
        public String getCallingnumber() {
            return callingnumber;
        }

        /**
         * @param callingnumber The callingnumber
         */
        public void setCallingnumber(String callingnumber) {
            this.callingnumber = callingnumber;
        }

        /**
         * @return The callednumber
         */
        public String getCallednumber() {
            return callednumber;
        }

        /**
         * @param callednumber The callednumber
         */
        public void setCallednumber(String callednumber) {
            this.callednumber = callednumber;
        }

        /**
         * @return The jqtype
         */
        public String getJqtype() {
            return jqtype;
        }

        /**
         * @param jqtype The jqtype
         */
        public void setJqtype(String jqtype) {
            this.jqtype = jqtype;
        }

        /**
         * @return The jqnature
         */
        public String getJqnature() {
            return jqnature;
        }

        /**
         * @param jqnature The jqnature
         */
        public void setJqnature(String jqnature) {
            this.jqnature = jqnature;
        }

        /**
         * @return The propertyClassification
         */
        public Object getPropertyClassification() {
            return propertyClassification;
        }

        /**
         * @param propertyClassification The property_classification
         */
        public void setPropertyClassification(Object propertyClassification) {
            this.propertyClassification = propertyClassification;
        }

        /**
         * @return The alarmperson
         */
        public String getAlarmperson() {
            return alarmperson;
        }

        /**
         * @param alarmperson The alarmperson
         */
        public void setAlarmperson(String alarmperson) {
            this.alarmperson = alarmperson;
        }

        /**
         * @return The attendant
         */
        public String getAttendant() {
            return attendant;
        }

        /**
         * @param attendant The attendant
         */
        public void setAttendant(String attendant) {
            this.attendant = attendant;
        }

        /**
         * @return The ringingduration
         */
        public String getRingingduration() {
            return ringingduration;
        }

        /**
         * @param ringingduration The ringingduration
         */
        public void setRingingduration(String ringingduration) {
            this.ringingduration = ringingduration;
        }

        /**
         * @return The talktime
         */
        public String getTalktime() {
            return talktime;
        }

        /**
         * @param talktime The talktime
         */
        public void setTalktime(String talktime) {
            this.talktime = talktime;
        }

        /**
         * @return The jqddsj
         */
        public Object getJqddsj() {
            return jqddsj;
        }

        /**
         * @param jqddsj The jqddsj
         */
        public void setJqddsj(Object jqddsj) {
            this.jqddsj = jqddsj;
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
         * @return The belongarea
         */
        public Object getBelongarea() {
            return belongarea;
        }

        /**
         * @param belongarea The belongarea
         */
        public void setBelongarea(Object belongarea) {
            this.belongarea = belongarea;
        }

        /**
         * @return The belongareabw
         */
        public Object getBelongareabw() {
            return belongareabw;
        }

        /**
         * @param belongareabw The belongareabw
         */
        public void setBelongareabw(Object belongareabw) {
            this.belongareabw = belongareabw;
        }

    }
}
