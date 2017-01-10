package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/11/3.
 */

public class CloudTag {

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

        @SerializedName("key")
        @Expose
        private String key;
        @SerializedName("doc_count")
        @Expose
        private Integer docCount;

        /**
         * @return The key
         */
        public String getKey() {
            return key;
        }

        /**
         * @param key The key
         */
        public void setKey(String key) {
            this.key = key;
        }

        /**
         * @return The docCount
         */
        public Integer getDocCount() {
            return docCount;
        }

        /**
         * @param docCount The doc_count
         */
        public void setDocCount(Integer docCount) {
            this.docCount = docCount;
        }

    }
}
