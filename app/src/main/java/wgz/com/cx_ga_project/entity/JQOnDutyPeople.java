package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/10/27.
 */

public class JQOnDutyPeople {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("res")
    @Expose
    private List<peoRe> res = new ArrayList<peoRe>();

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
    public List<peoRe> getRes() {
        return res;
    }

    /**
     * @param res The res
     */
    public void setRes(List<peoRe> res) {
        this.res = res;
    }


    public class peoRe {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("policeid")
        @Expose
        private String policeid;
        @SerializedName("policename")
        @Expose
        private String policename;
        @SerializedName("dutytime")
        @Expose
        private String dutytime;
        @SerializedName("starttime")
        @Expose
        private Object starttime;
        @SerializedName("endtime")
        @Expose
        private Object endtime;
        @SerializedName("isleader")
        @Expose
        private String isleader;
        @SerializedName("ismoniter")
        @Expose
        private String ismoniter;
        @SerializedName("dutytype")
        @Expose
        private String dutytype;
        @SerializedName("remark")
        @Expose
        private Object remark;

        @Override
        public String toString() {
            return "peoRe{" +
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
}
