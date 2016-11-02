package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/11/1.
 */

public class SICType {
    @SerializedName("code")

    private Integer code;
    @SerializedName("res")

    private List<Re> res = new ArrayList<>();

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
    public List<Re> getRes() {
        return res;
    }

    /**
     *
     * @param res
     * The res
     */
    public void setRes(List<Re> res) {
        this.res = res;
    }

public class Re {

    @SerializedName("moudleclasscode")

    private String moudleclasscode;
    @SerializedName("moudleclass")

    private String moudleclass;
    @SerializedName("ordernum")

    private String ordernum;

    /**
     *
     * @return
     * The moudleclasscode
     */
    public String getMoudleclasscode() {
        return moudleclasscode;
    }

    /**
     *
     * @param moudleclasscode
     * The moudleclasscode
     */
    public void setMoudleclasscode(String moudleclasscode) {
        this.moudleclasscode = moudleclasscode;
    }

    /**
     *
     * @return
     * The moudleclass
     */
    public String getMoudleclass() {
        return moudleclass;
    }

    /**
     *
     * @param moudleclass
     * The moudleclass
     */
    public void setMoudleclass(String moudleclass) {
        this.moudleclass = moudleclass;
    }

    /**
     *
     * @return
     * The ordernum
     */
    public String getOrdernum() {
        return ordernum;
    }

    /**
     *
     * @param ordernum
     * The ordernum
     */
    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    @Override
    public String toString() {
        return "Re{" +
                "moudleclasscode='" + moudleclasscode + '\'' +
                ", moudleclass='" + moudleclass + '\'' +
                ", ordernum='" + ordernum + '\'' +
                '}';
    }
}
}
