package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/10/25.
 */

public class Subordinate {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("resup")
    @Expose
    private List<Resup> resup = new ArrayList<Resup>();
    @SerializedName("resdown")
    @Expose
    private List<Resdown> resdown = new ArrayList<Resdown>();

    @Override
    public String toString() {
        return "Subordinate{" +
                "code=" + code +
                ", resup=" + resup +
                ", resdown=" + resdown +
                '}';
    }

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
     * The resup
     */
    public List<Resup> getResup() {
        return resup;
    }

    /**
     *
     * @param resup
     * The resup
     */
    public void setResup(List<Resup> resup) {
        this.resup = resup;
    }

    /**
     *
     * @return
     * The resdown
     */
    public List<Resdown> getResdown() {
        return resdown;
    }

    /**
     *
     * @param resdown
     * The resdown
     */
    public void setResdown(List<Resdown> resdown) {
        this.resdown = resdown;
    }

    public class Resup {

        @SerializedName("policename")
        @Expose
        private String policename;
        @SerializedName("polid")
        @Expose
        private String polid;
        @SerializedName("face")
        @Expose
        private String face;

        @Override
        public String toString() {
            return "Resup{" +
                    "policename='" + policename + '\'' +
                    ", polid='" + polid + '\'' +
                    ", face='" + face + '\'' +
                    '}';
        }

        /**
         *
         * @return
         * The policename
         */
        public String getPolicename() {
            return policename;
        }

        /**
         *
         * @param policename
         * The policename
         */
        public void setPolicename(String policename) {
            this.policename = policename;
        }

        /**
         *
         * @return
         * The polid
         */
        public String getPolid() {
            return polid;
        }

        /**
         *
         * @param polid
         * The polid
         */
        public void setPolid(String polid) {
            this.polid = polid;
        }

        /**
         *
         * @return
         * The face
         */
        public String getFace() {
            return face;
        }

        /**
         *
         * @param face
         * The face
         */
        public void setFace(String face) {
            this.face = face;
        }

    }
    public class Resdown {

        @SerializedName("policename")
        @Expose
        private String policename;
        @SerializedName("polid")
        @Expose
        private String polid;
        @SerializedName("face")
        @Expose
        private String face;

        @Override
        public String toString() {
            return "Resdown{" +
                    "policename='" + policename + '\'' +
                    ", polid='" + polid + '\'' +
                    ", face='" + face + '\'' +
                    '}';
        }

        /**
         *
         * @return
         * The policename
         */
        public String getPolicename() {
            return policename;
        }

        /**
         *
         * @param policename
         * The policename
         */
        public void setPolicename(String policename) {
            this.policename = policename;
        }

        /**
         *
         * @return
         * The polid
         */
        public String getPolid() {
            return polid;
        }

        /**
         *
         * @param polid
         * The polid
         */
        public void setPolid(String polid) {
            this.polid = polid;
        }

        /**
         *
         * @return
         * The face
         */
        public String getFace() {
            return face;
        }

        /**
         *
         * @param face
         * The face
         */
        public void setFace(String face) {
            this.face = face;
        }

    }

}
