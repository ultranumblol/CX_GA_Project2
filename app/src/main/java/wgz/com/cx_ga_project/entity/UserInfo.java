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

    @SerializedName("userid")

    private String userid;
    @SerializedName("policename")

    private String policename;
    @SerializedName("datrixid")

    private Object datrixid;
    @SerializedName("pphonenum")

    private String pphonenum;
    @SerializedName("officecodename")

    private String officecodename;

    @Override
    public String toString() {
        return "UserRes{" +
                "userid='" + userid + '\'' +
                ", policename='" + policename + '\'' +
                ", datrixid=" + datrixid +
                ", pphonenum='" + pphonenum + '\'' +
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
     * The datrixid
     */
    public Object getDatrixid() {
        return datrixid;
    }

    /**
     *
     * @param datrixid
     * The datrixid
     */
    public void setDatrixid(Object datrixid) {
        this.datrixid = datrixid;
    }

    /**
     *
     * @return
     * The pphonenum
     */
    public String getPphonenum() {
        return pphonenum;
    }

    /**
     *
     * @param pphonenum
     * The pphonenum
     */
    public void setPphonenum(String pphonenum) {
        this.pphonenum = pphonenum;
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
