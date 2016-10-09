package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/10/8.
 */

public class SchedulingOneDay {
    @SerializedName("code")

    private Integer code;
    @SerializedName("res")

    private List<Re> res = new ArrayList<Re>();
    @SerializedName("res1")

    private List<Res1> res1 = new ArrayList<Res1>();
    @SerializedName("res2")

    private List<Res2> res2 = new ArrayList<Res2>();

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

    /**
     * @return The res1
     */
    public List<Res1> getRes1() {
        return res1;
    }

    /**
     * @param res1 The res1
     */
    public void setRes1(List<Res1> res1) {
        this.res1 = res1;
    }

    /**
     * @return The res2
     */
    public List<Res2> getRes2() {
        return res2;
    }

    /**
     * @param res2 The res2
     */
    public void setRes2(List<Res2> res2) {
        this.res2 = res2;
    }


    public class Re {

        @SerializedName("id")

        private String id;
        @SerializedName("policeid")

        private String policeid;
        @SerializedName("policename")

        private String policename;
        @SerializedName("dutytime")

        private String dutytime;
        @SerializedName("starttime")

        private Object starttime;
        @SerializedName("endtime")

        private Object endtime;
        @SerializedName("isleader")

        private String isleader;
        @SerializedName("ismoniter")

        private String ismoniter;
        @SerializedName("dutytype")

        private String dutytype;
        @SerializedName("remark")

        private Object remark;

        @Override
        public String toString() {
            return "Re{" +
                    "id='" + id + '\'' +
                    ", policeid='" + policeid + '\'' +
                    ", policename='" + policename + '\'' +
                    ", dutytime='" + dutytime + '\'' +
                    ", starttime=" + starttime +
                    ", endtime=" + endtime +
                    ", isleader='" + isleader + '\'' +
                    ", ismoniter='" + ismoniter + '\'' +
                    ", dutytype='" + dutytype + '\'' +
                    ", remark=" + remark +
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

        /**
         * @return The dutytime
         */
        public String getDutytime() {
            return dutytime;
        }

        /**
         * @param dutytime The dutytime
         */
        public void setDutytime(String dutytime) {
            this.dutytime = dutytime;
        }

        /**
         * @return The starttime
         */
        public Object getStarttime() {
            return starttime;
        }

        /**
         * @param starttime The starttime
         */
        public void setStarttime(Object starttime) {
            this.starttime = starttime;
        }

        /**
         * @return The endtime
         */
        public Object getEndtime() {
            return endtime;
        }

        /**
         * @param endtime The endtime
         */
        public void setEndtime(Object endtime) {
            this.endtime = endtime;
        }

        /**
         * @return The isleader
         */
        public String getIsleader() {
            return isleader;
        }

        /**
         * @param isleader The isleader
         */
        public void setIsleader(String isleader) {
            this.isleader = isleader;
        }

        /**
         * @return The ismoniter
         */
        public String getIsmoniter() {
            return ismoniter;
        }

        /**
         * @param ismoniter The ismoniter
         */
        public void setIsmoniter(String ismoniter) {
            this.ismoniter = ismoniter;
        }

        /**
         * @return The dutytype
         */
        public String getDutytype() {
            return dutytype;
        }

        /**
         * @param dutytype The dutytype
         */
        public void setDutytype(String dutytype) {
            this.dutytype = dutytype;
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

    public class Res1 {
        @Override
        public String toString() {
            return "Res1{" +
                    "id='" + id + '\'' +
                    ", policeid='" + policeid + '\'' +
                    ", policename='" + policename + '\'' +
                    ", dutytime='" + dutytime + '\'' +
                    ", starttime=" + starttime +
                    ", endtime=" + endtime +
                    ", isleader='" + isleader + '\'' +
                    ", ismoniter='" + ismoniter + '\'' +
                    ", dutytype='" + dutytype + '\'' +
                    ", remark=" + remark +
                    '}';
        }

        @SerializedName("id")

        private String id;
        @SerializedName("policeid")

        private String policeid;
        @SerializedName("policename")

        private String policename;
        @SerializedName("dutytime")

        private String dutytime;
        @SerializedName("starttime")

        private Object starttime;
        @SerializedName("endtime")

        private Object endtime;
        @SerializedName("isleader")

        private String isleader;
        @SerializedName("ismoniter")

        private String ismoniter;
        @SerializedName("dutytype")

        private String dutytype;
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

        /**
         * @return The dutytime
         */
        public String getDutytime() {
            return dutytime;
        }

        /**
         * @param dutytime The dutytime
         */
        public void setDutytime(String dutytime) {
            this.dutytime = dutytime;
        }

        /**
         * @return The starttime
         */
        public Object getStarttime() {
            return starttime;
        }

        /**
         * @param starttime The starttime
         */
        public void setStarttime(Object starttime) {
            this.starttime = starttime;
        }

        /**
         * @return The endtime
         */
        public Object getEndtime() {
            return endtime;
        }

        /**
         * @param endtime The endtime
         */
        public void setEndtime(Object endtime) {
            this.endtime = endtime;
        }

        /**
         * @return The isleader
         */
        public String getIsleader() {
            return isleader;
        }

        /**
         * @param isleader The isleader
         */
        public void setIsleader(String isleader) {
            this.isleader = isleader;
        }

        /**
         * @return The ismoniter
         */
        public String getIsmoniter() {
            return ismoniter;
        }

        /**
         * @param ismoniter The ismoniter
         */
        public void setIsmoniter(String ismoniter) {
            this.ismoniter = ismoniter;
        }

        /**
         * @return The dutytype
         */
        public String getDutytype() {
            return dutytype;
        }

        /**
         * @param dutytype The dutytype
         */
        public void setDutytype(String dutytype) {
            this.dutytype = dutytype;
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

    public class Res2 {
        @Override
        public String toString() {
            return "Res2{" +
                    "id='" + id + '\'' +
                    ", policeid='" + policeid + '\'' +
                    ", policename='" + policename + '\'' +
                    ", dutytime='" + dutytime + '\'' +
                    ", starttime=" + starttime +
                    ", endtime=" + endtime +
                    ", isleader='" + isleader + '\'' +
                    ", ismoniter='" + ismoniter + '\'' +
                    ", dutytype='" + dutytype + '\'' +
                    ", remark=" + remark +
                    '}';
        }

        @SerializedName("id")

        private String id;
        @SerializedName("policeid")

        private String policeid;
        @SerializedName("policename")

        private String policename;
        @SerializedName("dutytime")

        private String dutytime;
        @SerializedName("starttime")

        private Object starttime;
        @SerializedName("endtime")

        private Object endtime;
        @SerializedName("isleader")

        private String isleader;
        @SerializedName("ismoniter")

        private String ismoniter;
        @SerializedName("dutytype")

        private String dutytype;
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

        /**
         * @return The dutytime
         */
        public String getDutytime() {
            return dutytime;
        }

        /**
         * @param dutytime The dutytime
         */
        public void setDutytime(String dutytime) {
            this.dutytime = dutytime;
        }

        /**
         * @return The starttime
         */
        public Object getStarttime() {
            return starttime;
        }

        /**
         * @param starttime The starttime
         */
        public void setStarttime(Object starttime) {
            this.starttime = starttime;
        }

        /**
         * @return The endtime
         */
        public Object getEndtime() {
            return endtime;
        }

        /**
         * @param endtime The endtime
         */
        public void setEndtime(Object endtime) {
            this.endtime = endtime;
        }

        /**
         * @return The isleader
         */
        public String getIsleader() {
            return isleader;
        }

        /**
         * @param isleader The isleader
         */
        public void setIsleader(String isleader) {
            this.isleader = isleader;
        }

        /**
         * @return The ismoniter
         */
        public String getIsmoniter() {
            return ismoniter;
        }

        /**
         * @param ismoniter The ismoniter
         */
        public void setIsmoniter(String ismoniter) {
            this.ismoniter = ismoniter;
        }

        /**
         * @return The dutytype
         */
        public String getDutytype() {
            return dutytype;
        }

        /**
         * @param dutytype The dutytype
         */
        public void setDutytype(String dutytype) {
            this.dutytype = dutytype;
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
}