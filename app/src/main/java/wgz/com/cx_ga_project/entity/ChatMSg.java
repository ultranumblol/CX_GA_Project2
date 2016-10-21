package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/9/11.
 */

public class ChatMsg {

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

        @SerializedName("id")

        private String id;
        @SerializedName("jqid")

        private String jqid;
        @SerializedName("taskid")

        private String taskid;
        @SerializedName("from_policeid")

        private String fromPoliceid;
        @SerializedName("to_policeid")

        private String toPoliceid;
        @SerializedName("txt")

        private String txt;

        public String getIssend() {
            return issend;
        }

        public void setIssend(String issend) {
            this.issend = issend;
        }

        @SerializedName("issend")
        private String issend;
        @SerializedName("pic")

        private String pic;
        @SerializedName("video")

        private String video;
        @SerializedName("videopic")

        private String videopic;

        public String getVideopic() {
            return videopic;
        }

        public void setVideopic(String videopic) {
            this.videopic = videopic;
        }

        @SerializedName("sendtime")


        private String sendtime;
        @SerializedName("frompolicename")

        private String frompolicename;
        @SerializedName("mark")

        private Integer mark;
        @SerializedName("policeid")

        private String policeid;
        @SerializedName("remark")

        private Object remark;
        @SerializedName("policename")

        private String policename;

        @Override
        public String toString() {
            return "Re{" +
                    "id='" + id + '\'' +
                    ", jqid='" + jqid + '\'' +
                    ", taskid='" + taskid + '\'' +
                    ", fromPoliceid='" + fromPoliceid + '\'' +
                    ", toPoliceid='" + toPoliceid + '\'' +
                    ", txt='" + txt + '\'' +
                    ", issend='" + issend + '\'' +
                    ", pic='" + pic + '\'' +
                    ", video='" + video + '\'' +
                    ", videopic='" + videopic + '\'' +
                    ", sendtime='" + sendtime + '\'' +
                    ", frompolicename='" + frompolicename + '\'' +
                    ", mark=" + mark +
                    ", policeid='" + policeid + '\'' +
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
         * @return The fromPoliceid
         */
        public String getFromPoliceid() {
            return fromPoliceid;
        }

        /**
         * @param fromPoliceid The from_policeid
         */
        public void setFromPoliceid(String fromPoliceid) {
            this.fromPoliceid = fromPoliceid;
        }

        /**
         * @return The toPoliceid
         */
        public String getToPoliceid() {
            return toPoliceid;
        }

        /**
         * @param toPoliceid The to_policeid
         */
        public void setToPoliceid(String toPoliceid) {
            this.toPoliceid = toPoliceid;
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
         * @return The frompolicename
         */
        public String getFrompolicename() {
            return frompolicename;
        }

        /**
         * @param frompolicename The frompolicename
         */
        public void setFrompolicename(String frompolicename) {
            this.frompolicename = frompolicename;
        }

        /**
         * @return The mark
         */
        public Integer getMark() {
            return mark;
        }

        /**
         * @param mark The mark
         */
        public void setMark(Integer mark) {
            this.mark = mark;
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