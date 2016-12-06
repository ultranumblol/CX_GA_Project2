package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wgz on 2016/12/6.
 */

public class PicAndVideo {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("respic")
    @Expose
    private List<Respic> respic = null;
    @SerializedName("resvideo")
    @Expose
    private List<Resvideo> resvideo = null;

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
     * @return The respic
     */
    public List<Respic> getRespic() {
        return respic;
    }

    /**
     * @param respic The respic
     */
    public void setRespic(List<Respic> respic) {
        this.respic = respic;
    }

    /**
     * @return The resvideo
     */
    public List<Resvideo> getResvideo() {
        return resvideo;
    }

    /**
     * @param resvideo The resvideo
     */
    public void setResvideo(List<Resvideo> resvideo) {
        this.resvideo = resvideo;
    }


    public class Respic {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("pic")
        @Expose
        private String pic;
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
         * @return The pic
         */
        public String getPic() {
            return pic;
        }

        /**
         * @param pic The pic
         */
        public void setPic(String pic) {
            this.pic = pic;
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

    public class Resvideo {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("videopic")
        @Expose
        private String videopic;
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
         * @return The videopic
         */
        public String getVideopic() {
            return videopic;
        }

        /**
         * @param videopic The videopic
         */
        public void setVideopic(String videopic) {
            this.videopic = videopic;
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
