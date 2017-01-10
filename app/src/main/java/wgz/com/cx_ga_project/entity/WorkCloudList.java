package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/11/12.
 */

public class WorkCloudList {

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

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("summaryid")
        @Expose
        private Object summaryid;
        @SerializedName("loginid")
        @Expose
        private String loginid;
        @SerializedName("title")
        @Expose
        private Object title;
        @SerializedName("summary")
        @Expose
        private String summary;
        @SerializedName("inserttime")
        @Expose
        private String inserttime;
        @SerializedName("remark")
        @Expose
        private Object remark;
        @SerializedName("summary_pic")
        @Expose
        private String summaryPic;
        @SerializedName("eagles_task_id")
        @Expose
        private String eaglesTaskId;

        @Override
        public String toString() {
            return "Re{" +
                    "id=" + id +
                    ", summaryid=" + summaryid +
                    ", loginid='" + loginid + '\'' +
                    ", title=" + title +
                    ", summary='" + summary + '\'' +
                    ", inserttime='" + inserttime + '\'' +
                    ", remark=" + remark +
                    ", summaryPic='" + summaryPic + '\'' +
                    ", eaglesTaskId='" + eaglesTaskId + '\'' +
                    '}';
        }

        /**
         * @return The id
         */
        public Integer getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         * @return The summaryid
         */
        public Object getSummaryid() {
            return summaryid;
        }

        /**
         * @param summaryid The summaryid
         */
        public void setSummaryid(Object summaryid) {
            this.summaryid = summaryid;
        }

        /**
         * @return The loginid
         */
        public String getLoginid() {
            return loginid;
        }

        /**
         * @param loginid The loginid
         */
        public void setLoginid(String loginid) {
            this.loginid = loginid;
        }

        /**
         * @return The title
         */
        public Object getTitle() {
            return title;
        }

        /**
         * @param title The title
         */
        public void setTitle(Object title) {
            this.title = title;
        }

        /**
         * @return The summary
         */
        public String getSummary() {
            return summary;
        }

        /**
         * @param summary The summary
         */
        public void setSummary(String summary) {
            this.summary = summary;
        }

        /**
         * @return The inserttime
         */
        public String getInserttime() {
            return inserttime;
        }

        /**
         * @param inserttime The inserttime
         */
        public void setInserttime(String inserttime) {
            this.inserttime = inserttime;
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
         * @return The summaryPic
         */
        public String getSummaryPic() {
            return summaryPic;
        }

        /**
         * @param summaryPic The summary_pic
         */
        public void setSummaryPic(String summaryPic) {
            this.summaryPic = summaryPic;
        }

        /**
         * @return The eaglesTaskId
         */
        public String getEaglesTaskId() {
            return eaglesTaskId;
        }

        /**
         * @param eaglesTaskId The eagles_task_id
         */
        public void setEaglesTaskId(String eaglesTaskId) {
            this.eaglesTaskId = eaglesTaskId;
        }

    }
}
