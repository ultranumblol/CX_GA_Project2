package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/10/11.
 */

public class JqOrbit {

    @SerializedName("code")

    private Integer code;
    @SerializedName("res")

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

        @SerializedName("taskid")

        private String taskid;
        @SerializedName("jqid")

        private String jqid;
        @SerializedName("treatmentdep")

        private String treatmentdep;
        @SerializedName("sendtime")

        private String sendtime;
        @SerializedName("arrivetime")

        private String arrivetime;
        @SerializedName("gps_e")

        private String gpsE;
        @SerializedName("status")

        private String status;
        @SerializedName("remark")

        private Object remark;
        @SerializedName("gps_n")

        private Object gpsN;
        @SerializedName("treatmentdepcode")

        private Object treatmentdepcode;

        @Override
        public String toString() {
            return "Re{" +
                    "taskid='" + taskid + '\'' +
                    ", jqid='" + jqid + '\'' +
                    ", treatmentdep='" + treatmentdep + '\'' +
                    ", sendtime='" + sendtime + '\'' +
                    ", arrivetime='" + arrivetime + '\'' +
                    ", gpsE='" + gpsE + '\'' +
                    ", status='" + status + '\'' +
                    ", remark=" + remark +
                    ", gpsN=" + gpsN +
                    ", treatmentdepcode=" + treatmentdepcode +
                    '}';
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
         * @return The treatmentdep
         */
        public String getTreatmentdep() {
            return treatmentdep;
        }

        /**
         * @param treatmentdep The treatmentdep
         */
        public void setTreatmentdep(String treatmentdep) {
            this.treatmentdep = treatmentdep;
        }

        /**
         * @return The sendtime
         */
        public String getSendtime() {
            return sendtime;
        }

        /**
         * @param sendtime The sendtime
         */
        public void setSendtime(String sendtime) {
            this.sendtime = sendtime;
        }

        /**
         * @return The arrivetime
         */
        public String getArrivetime() {
            return arrivetime;
        }

        /**
         * @param arrivetime The arrivetime
         */
        public void setArrivetime(String arrivetime) {
            this.arrivetime = arrivetime;
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
         * @return The status
         */
        public String getStatus() {
            return status;
        }

        /**
         * @param status The status
         */
        public void setStatus(String status) {
            this.status = status;
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
         * @return The treatmentdepcode
         */
        public Object getTreatmentdepcode() {
            return treatmentdepcode;
        }

        /**
         * @param treatmentdepcode The treatmentdepcode
         */
        public void setTreatmentdepcode(Object treatmentdepcode) {
            this.treatmentdepcode = treatmentdepcode;
        }

    }
}


