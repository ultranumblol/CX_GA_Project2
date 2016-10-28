package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/10/28.
 */

public class NewJQ {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("res")
    @Expose
    private List<NewjqRe> res = new ArrayList<NewjqRe>();

    /**
     *
     * @return
     * The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     *
     * @return
     * The res
     */
    public List<NewjqRe> getRes() {
        return res;
    }

    /**
     *
     * @param res
     * The res
     */
    public void setRes(List<NewjqRe> res) {
        this.res = res;
    }


public class NewjqRe {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("jqid")
    @Expose
    private String jqid;
    @SerializedName("treatmentdep")
    @Expose
    private String treatmentdep;
    @SerializedName("treatmentdepcode")
    @Expose
    private String treatmentdepcode;
    @SerializedName("sendtime")
    @Expose
    private String sendtime;
    @SerializedName("arrivetime")
    @Expose
    private String arrivetime;
    @SerializedName("gps_e")
    @Expose
    private String gpsE;
    @SerializedName("gps_n")
    @Expose
    private String gpsN;
    @SerializedName("gpsstatus")
    @Expose
    private String gpsstatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("remark")
    @Expose
    private Object remark;
    @SerializedName("taskid")
    @Expose
    private String taskid;
    @SerializedName("pushpolid")
    @Expose
    private String pushpolid;

    @Override
    public String toString() {
        return "NewjqRe{" +
                "id='" + id + '\'' +
                ", jqid='" + jqid + '\'' +
                ", treatmentdep='" + treatmentdep + '\'' +
                ", treatmentdepcode='" + treatmentdepcode + '\'' +
                ", sendtime='" + sendtime + '\'' +
                ", arrivetime='" + arrivetime + '\'' +
                ", gpsE='" + gpsE + '\'' +
                ", gpsN='" + gpsN + '\'' +
                ", gpsstatus='" + gpsstatus + '\'' +
                ", status='" + status + '\'' +
                ", remark=" + remark +
                ", taskid='" + taskid + '\'' +
                ", pushpolid='" + pushpolid + '\'' +
                '}';
    }

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
     * The treatmentdep
     */
    public String getTreatmentdep() {
        return treatmentdep;
    }

    /**
     *
     * @param treatmentdep
     * The treatmentdep
     */
    public void setTreatmentdep(String treatmentdep) {
        this.treatmentdep = treatmentdep;
    }

    /**
     *
     * @return
     * The treatmentdepcode
     */
    public String getTreatmentdepcode() {
        return treatmentdepcode;
    }

    /**
     *
     * @param treatmentdepcode
     * The treatmentdepcode
     */
    public void setTreatmentdepcode(String treatmentdepcode) {
        this.treatmentdepcode = treatmentdepcode;
    }

    /**
     *
     * @return
     * The sendtime
     */
    public String getSendtime() {
        return sendtime;
    }

    /**
     *
     * @param sendtime
     * The sendtime
     */
    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    /**
     *
     * @return
     * The arrivetime
     */
    public String getArrivetime() {
        return arrivetime;
    }

    /**
     *
     * @param arrivetime
     * The arrivetime
     */
    public void setArrivetime(String arrivetime) {
        this.arrivetime = arrivetime;
    }

    /**
     *
     * @return
     * The gpsE
     */
    public String getGpsE() {
        return gpsE;
    }

    /**
     *
     * @param gpsE
     * The gps_e
     */
    public void setGpsE(String gpsE) {
        this.gpsE = gpsE;
    }

    /**
     *
     * @return
     * The gpsN
     */
    public String getGpsN() {
        return gpsN;
    }

    /**
     *
     * @param gpsN
     * The gps_n
     */
    public void setGpsN(String gpsN) {
        this.gpsN = gpsN;
    }

    /**
     *
     * @return
     * The gpsstatus
     */
    public String getGpsstatus() {
        return gpsstatus;
    }

    /**
     *
     * @param gpsstatus
     * The gpsstatus
     */
    public void setGpsstatus(String gpsstatus) {
        this.gpsstatus = gpsstatus;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
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
     * The taskid
     */
    public String getTaskid() {
        return taskid;
    }

    /**
     *
     * @param taskid
     * The taskid
     */
    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    /**
     *
     * @return
     * The pushpolid
     */
    public String getPushpolid() {
        return pushpolid;
    }

    /**
     *
     * @param pushpolid
     * The pushpolid
     */
    public void setPushpolid(String pushpolid) {
        this.pushpolid = pushpolid;
    }

}
}