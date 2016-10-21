package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/10/11.
 */

public class ChuJingRen {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("res")
    @Expose
    private List<CJRRe> res = new ArrayList<CJRRe>();

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
    public List<CJRRe> getRes() {
        return res;
    }

    /**
     * @param res The res
     */
    public void setRes(List<CJRRe> res) {
        this.res = res;
    }


    public class CJRRe {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("taskid")
        @Expose
        private String taskid;
        @SerializedName("policeid")
        @Expose
        private String policeid;
        @SerializedName("remark")
        @Expose
        private Object remark;
        @SerializedName("policename")
        @Expose
        private String policename;

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
