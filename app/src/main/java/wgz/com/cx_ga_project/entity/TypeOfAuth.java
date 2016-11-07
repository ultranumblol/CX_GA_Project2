package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/11/7.
 */

public class TypeOfAuth {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("res")
    @Expose
    private List<AuthRe> res = new ArrayList<AuthRe>();

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
    public List<AuthRe> getRes() {
        return res;
    }

    /**
     * @param res The res
     */
    public void setRes(List<AuthRe> res) {
        this.res = res;
    }


    public class AuthRe {

        @SerializedName("valcode")
        @Expose
        private String valcode;
        @SerializedName("valname")
        @Expose
        private String valname;

        @Override
        public String toString() {
            return "AuthRe{" +
                    "valcode='" + valcode + '\'' +
                    ", valname='" + valname + '\'' +
                    '}';
        }

        /**
         * @return The valcode
         */
        public String getValcode() {
            return valcode;
        }

        /**
         * @param valcode The valcode
         */
        public void setValcode(String valcode) {
            this.valcode = valcode;
        }

        /**
         * @return The valname
         */
        public String getValname() {
            return valname;
        }

        /**
         * @param valname The valname
         */
        public void setValname(String valname) {
            this.valname = valname;
        }


    }
}
