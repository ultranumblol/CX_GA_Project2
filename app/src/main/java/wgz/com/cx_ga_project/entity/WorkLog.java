package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/8/12.
 */

public class WorkLog {
    @SerializedName("code")

    private Integer code;
    @SerializedName("result")

    private List<Mylog> log = new ArrayList<Mylog>();

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
     * @return The result
     */
    public List<Mylog> getLogs() {
        return log;
    }

    /**
     * @param result The result
     */
    public void setLogs(List<Mylog> result) {
        this.log = result;
    }

    public class Mylog {

        @SerializedName("id")

        private String id;
        @SerializedName("loginid")

        private String loginid;
        @SerializedName("time")

        private String time;
        @SerializedName("summary")

        private String summary;

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
         * @return The time
         */
        public String getTime() {
            return time;
        }

        /**
         * @param time The time
         */
        public void setTime(String time) {
            this.time = time;
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

    }
}
