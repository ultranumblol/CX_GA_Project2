package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/10/10.
 */

public class AppVersion {
    @SerializedName("code")

    private Integer code;
    @SerializedName("res")
    private List<VersionRe> res = new ArrayList<VersionRe>();

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
    public List<VersionRe> getRes() {
        return res;
    }

    /**
     * @param res The res
     */
    public void setRes(List<VersionRe> res) {
        this.res = res;
    }


    public class VersionRe {

        @SerializedName("id")

        private String id;
        @SerializedName("apk_name")

        private String apkName;
        @SerializedName("apk_updatelog")

        private String apkUpdatelog;
        @SerializedName("apk_versioncode")

        private String apkVersioncode;
        @SerializedName("apk_url")

        private String apkUrl;
        @SerializedName("uploadperson")

        private String uploadperson;
        @SerializedName("uploadtime")

        private String uploadtime;

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
         * @return The apkName
         */
        public String getApkName() {
            return apkName;
        }

        /**
         * @param apkName The apk_name
         */
        public void setApkName(String apkName) {
            this.apkName = apkName;
        }

        /**
         * @return The apkUpdatelog
         */
        public String getApkUpdatelog() {
            return apkUpdatelog;
        }

        /**
         * @param apkUpdatelog The apk_updatelog
         */
        public void setApkUpdatelog(String apkUpdatelog) {
            this.apkUpdatelog = apkUpdatelog;
        }

        /**
         * @return The apkVersioncode
         */
        public String getApkVersioncode() {
            return apkVersioncode;
        }

        /**
         * @param apkVersioncode The apk_versioncode
         */
        public void setApkVersioncode(String apkVersioncode) {
            this.apkVersioncode = apkVersioncode;
        }

        /**
         * @return The apkUrl
         */
        public String getApkUrl() {
            return apkUrl;
        }

        /**
         * @param apkUrl The apk_url
         */
        public void setApkUrl(String apkUrl) {
            this.apkUrl = apkUrl;
        }

        /**
         * @return The uploadperson
         */
        public String getUploadperson() {
            return uploadperson;
        }

        /**
         * @param uploadperson The uploadperson
         */
        public void setUploadperson(String uploadperson) {
            this.uploadperson = uploadperson;
        }

        /**
         * @return The uploadtime
         */
        public String getUploadtime() {
            return uploadtime;
        }

        /**
         * @param uploadtime The uploadtime
         */
        public void setUploadtime(String uploadtime) {
            this.uploadtime = uploadtime;
        }

    }
}
