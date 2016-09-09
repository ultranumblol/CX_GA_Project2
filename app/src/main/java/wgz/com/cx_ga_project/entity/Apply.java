package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/8/11.
 */

public class Apply {
    @SerializedName("code")
    private Integer code;
    @SerializedName("result")
    private List<Result> result = new ArrayList<Result>();

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
     * The result
     */
    public List<Result> getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(List<Result> result) {
        this.result = result;
    }

        public class Result {

        @SerializedName("id")
        private String id;
        @SerializedName("policeid")
        private String policeid;
        @SerializedName("start")
        private String start;
        @SerializedName("end")
        private String end;
        @SerializedName("content")
        private String content;
        @SerializedName("applytime")
        private String applytime;
        @SerializedName("status")
        private String status;
        @SerializedName("upperid")
        private String upperid;
        @SerializedName("type")
        private String type;
        @SerializedName("days")
        private Object days;
        @SerializedName("reasontype")
        private String reasontype;

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
         * The policeid
         */
        public String getPoliceid() {
            return policeid;
        }

        /**
         *
         * @param policeid
         * The policeid
         */
        public void setPoliceid(String policeid) {
            this.policeid = policeid;
        }

        /**
         *
         * @return
         * The start
         */
        public String getStart() {
            return start;
        }

        /**
         *
         * @param start
         * The start
         */
        public void setStart(String start) {
            this.start = start;
        }

        /**
         *
         * @return
         * The end
         */
        public String getEnd() {
            return end;
        }

        /**
         *
         * @param end
         * The end
         */
        public void setEnd(String end) {
            this.end = end;
        }

        /**
         *
         * @return
         * The content
         */
        public String getContent() {
            return content;
        }

        /**
         *
         * @param content
         * The content
         */
        public void setContent(String content) {
            this.content = content;
        }

        /**
         *
         * @return
         * The applytime
         */
        public String getApplytime() {
            return applytime;
        }

        /**
         *
         * @param applytime
         * The applytime
         */
        public void setApplytime(String applytime) {
            this.applytime = applytime;
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
         * The upperid
         */
        public String getUpperid() {
            return upperid;
        }

        /**
         *
         * @param upperid
         * The upperid
         */
        public void setUpperid(String upperid) {
            this.upperid = upperid;
        }

        /**
         *
         * @return
         * The type
         */
        public String getType() {
            return type;
        }

        /**
         *
         * @param type
         * The type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         *
         * @return
         * The days
         */
        public Object getDays() {
            return days;
        }

        /**
         *
         * @param days
         * The days
         */
        public void setDays(Object days) {
            this.days = days;
        }

        /**
         *
         * @return
         * The reasontype
         */
        public String getReasontype() {
            return reasontype;
        }

        /**
         *
         * @param reasontype
         * The reasontype
         */
        public void setReasontype(String reasontype) {
            this.reasontype = reasontype;
        }

    }

    @Override
    public String toString() {
        return "Apply{" +
                "code=" + code +
                ", result=" + result +
                '}';
    }
}
