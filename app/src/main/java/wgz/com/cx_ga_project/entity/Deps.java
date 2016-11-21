package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/11/21.
 */

public class Deps {
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

        @SerializedName("departmentid")
        @Expose
        private String departmentid;
        @SerializedName("depart_simplename")
        @Expose
        private String departSimplename;
        @SerializedName("departmentname")
        @Expose
        private String departmentname;

        @Override
        public String toString() {
            return "Re{" +
                    "departmentid='" + departmentid + '\'' +
                    ", departSimplename='" + departSimplename + '\'' +
                    ", departmentname='" + departmentname + '\'' +
                    '}';
        }

        /**
         * @return The departmentid
         */
        public String getDepartmentid() {
            return departmentid;
        }

        /**
         * @param departmentid The departmentid
         */
        public void setDepartmentid(String departmentid) {
            this.departmentid = departmentid;
        }

        /**
         * @return The departSimplename
         */
        public String getDepartSimplename() {
            return departSimplename;
        }

        /**
         * @param departSimplename The depart_simplename
         */
        public void setDepartSimplename(String departSimplename) {
            this.departSimplename = departSimplename;
        }

        /**
         * @return The departmentname
         */
        public String getDepartmentname() {
            return departmentname;
        }

        /**
         * @param departmentname The departmentname
         */
        public void setDepartmentname(String departmentname) {
            this.departmentname = departmentname;
        }
    }
}
