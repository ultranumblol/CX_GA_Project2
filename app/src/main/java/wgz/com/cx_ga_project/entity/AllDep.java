package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/10/29.
 */

public class AllDep {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("res")
    @Expose
    private List<AlldepRe> res = new ArrayList<AlldepRe>();

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
    public List<AlldepRe> getRes() {
        return res;
    }

    /**
     * @param res The res
     */
    public void setRes(List<AlldepRe> res) {
        this.res = res;
    }

    public class AlldepRe {

        @SerializedName("depart_sqlid")
        @Expose
        private String departSqlid;
        @SerializedName("departmentid")
        @Expose
        private String departmentid;
        @SerializedName("departmentname")
        @Expose
        private String departmentname;
        @SerializedName("depart_simplename")
        @Expose
        private String departSimplename;
        @SerializedName("depart_leader")
        @Expose
        private String departLeader;
        @SerializedName("depart_level")
        @Expose
        private String departLevel;
        @SerializedName("depart_parentid")
        @Expose
        private String departParentid;
        @SerializedName("depart_virtual")
        @Expose
        private String departVirtual;
        @SerializedName("depart_sort")
        @Expose
        private String departSort;
        @SerializedName("depart_datrix_groupid")
        @Expose
        private Object departDatrixGroupid;
        @SerializedName("isparent")
        @Expose
        private String isparent;

        /**
         * @return The departSqlid
         */
        public String getDepartSqlid() {
            return departSqlid;
        }

        /**
         * @param departSqlid The depart_sqlid
         */
        public void setDepartSqlid(String departSqlid) {
            this.departSqlid = departSqlid;
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
         * @return The departLeader
         */
        public String getDepartLeader() {
            return departLeader;
        }

        /**
         * @param departLeader The depart_leader
         */
        public void setDepartLeader(String departLeader) {
            this.departLeader = departLeader;
        }

        /**
         * @return The departLevel
         */
        public String getDepartLevel() {
            return departLevel;
        }

        /**
         * @param departLevel The depart_level
         */
        public void setDepartLevel(String departLevel) {
            this.departLevel = departLevel;
        }

        /**
         * @return The departParentid
         */
        public String getDepartParentid() {
            return departParentid;
        }

        /**
         * @param departParentid The depart_parentid
         */
        public void setDepartParentid(String departParentid) {
            this.departParentid = departParentid;
        }

        /**
         * @return The departVirtual
         */
        public String getDepartVirtual() {
            return departVirtual;
        }

        /**
         * @param departVirtual The depart_virtual
         */
        public void setDepartVirtual(String departVirtual) {
            this.departVirtual = departVirtual;
        }

        /**
         * @return The departSort
         */
        public String getDepartSort() {
            return departSort;
        }

        /**
         * @param departSort The depart_sort
         */
        public void setDepartSort(String departSort) {
            this.departSort = departSort;
        }

        /**
         * @return The departDatrixGroupid
         */
        public Object getDepartDatrixGroupid() {
            return departDatrixGroupid;
        }

        /**
         * @param departDatrixGroupid The depart_datrix_groupid
         */
        public void setDepartDatrixGroupid(Object departDatrixGroupid) {
            this.departDatrixGroupid = departDatrixGroupid;
        }

        /**
         * @return The isparent
         */
        public String getIsparent() {
            return isparent;
        }

        /**
         * @param isparent The isparent
         */
        public void setIsparent(String isparent) {
            this.isparent = isparent;
        }


    }
}
