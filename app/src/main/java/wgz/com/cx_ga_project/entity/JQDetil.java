package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/9/20.
 */

public class JQDetil {

    @SerializedName("code")

    private Integer code;
    @SerializedName("result")

    private List<JQResult> result = new ArrayList<JQResult>();

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
     * @return The result
     */
    public List<JQResult> getResult() {
        return result;
    }

    /**
     * @param result The result
     */
    public void setResult(List<JQResult> result) {
        this.result = result;
    }


    public class JQResult {

        @SerializedName("briefcase")

        private String briefcase;
        @SerializedName("jqgps")

        private Object jqgps;
        @SerializedName("jqlaiyuan")

        private Object jqlaiyuan;
        @SerializedName("gps_e")

        private Object gpsE;
        @SerializedName("gps_n")

        private Object gpsN;
        @SerializedName("jqid")

        private String jqid;
        @SerializedName("callpolicetime")

        private String callpolicetime;
        @SerializedName("jqaddr")

        private String jqaddr;
        @SerializedName("callingnumber")

        private String callingnumber;
        @SerializedName("callednumber")

        private String callednumber;
        @SerializedName("jqtype")

        private String jqtype;
        @SerializedName("jqnature")

        private String jqnature;
        @SerializedName("property_classification")

        private Object propertyClassification;
        @SerializedName("alarmperson")

        private String alarmperson;
        @SerializedName("attendant")

        private String attendant;
        @SerializedName("ringingduration")

        private String ringingduration;
        @SerializedName("talktime")

        private String talktime;

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
        public Object getGpsE() {
            return gpsE;
        }

        /**
         * @param gpsE The gps_e
         */
        public void setGpsE(Object gpsE) {
            this.gpsE = gpsE;
        }

        /**
         * @return The gpsN
         */
        public Object getGpsN() {
            return gpsN;
        }

        /**
         * @param gpsN The gps_n
         */
        public void setGpsN(Object gpsN) {
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

        @Override
        public String toString() {
            return "JQResult{" +
                    "briefcase='" + briefcase + '\'' +
                    ", jqgps=" + jqgps +
                    ", jqlaiyuan=" + jqlaiyuan +
                    ", gpsE=" + gpsE +
                    ", gpsN=" + gpsN +
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
                    '}';
        }
    }
}