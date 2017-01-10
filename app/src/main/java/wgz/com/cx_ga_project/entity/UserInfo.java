package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/10/9.
 */

public class UserInfo {

    @SerializedName("code")

    private Integer code;
    @SerializedName("res")

    private List<UserRes> res = new ArrayList<UserRes>();

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
     * The res
     */
    public List<UserRes> getRes() {
        return res;
    }

    /**
     *
     * @param res
     * The res
     */
    public void setRes(List<UserRes> res) {
        this.res = res;
    }


public class UserRes {

    @SerializedName("polid")

    private String userid;
    @SerializedName("policename")

    private String policename;
    @SerializedName("officecode")

    private Object officecode;
    @SerializedName("ppolid")

    private String ppolid;


    @SerializedName("officecodename")

    private String officecodename;

    public Object getOfficecode() {
        return officecode;
    }

    public void setOfficecode(Object officecode) {
        this.officecode = officecode;
    }

    public String getPpolid() {
        return ppolid;
    }

    public void setPpolid(String ppolid) {
        this.ppolid = ppolid;
    }

    @Override
    public String toString() {
        return "UserRes{" +
                "userid='" + userid + '\'' +
                ", policename='" + policename + '\'' +
                ", officecode=" + officecode +
                ", ppolid='" + ppolid + '\'' +
                ", officecodename='" + officecodename + '\'' +
                '}';
    }

    /**
     *
     * @return
     * The userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     *
     * @param userid
     * The userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
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
     * The officecodename
     */
    public String getOfficecodename() {
        return officecodename;
    }

    /**
     *
     * @param officecodename
     * The officecodename
     */
    public void setOfficecodename(String officecodename) {
        this.officecodename = officecodename;
    }

}
}
