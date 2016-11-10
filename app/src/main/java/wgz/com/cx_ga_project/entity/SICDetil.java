package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/11/8.
 */

public class SICDetil {
    @SerializedName("defendtelphone")
    @Expose
    private String defendtelphone;
    @SerializedName("addtime")
    @Expose
    private String addtime;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("bedsnumber")
    @Expose
    private String bedsnumber;
    @SerializedName("roomsnumber")
    @Expose
    private String roomsnumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("principalperson")
    @Expose
    private String principalperson;
    @SerializedName("principalpersonphone")
    @Expose
    private String principalpersonphone;
    @SerializedName("ondutytelephone")
    @Expose
    private String ondutytelephone;
    @SerializedName("policeid")
    @Expose
    private String policeid;
    @SerializedName("authid")
    @Expose
    private String authid;
    @SerializedName("datrixvideo")
    @Expose
    private String datrixvideo;
    @SerializedName("videourl")
    @Expose
    private List<String> videourl = new ArrayList<String>();
    @SerializedName("videodocid")
    @Expose
    private String videodocid;
    @SerializedName("datrixpic")
    @Expose
    private String datrixpic;
    @SerializedName("picurl")
    @Expose
    private List<String> picurl = new ArrayList<String>();
    @SerializedName("picdocid")
    @Expose
    private String picdocid;

    @Override
    public String toString() {
        return "SICDetil{" +
                "defendtelphone='" + defendtelphone + '\'' +
                ", addtime='" + addtime + '\'' +
                ", address='" + address + '\'' +
                ", bedsnumber='" + bedsnumber + '\'' +
                ", roomsnumber='" + roomsnumber + '\'' +
                ", name='" + name + '\'' +
                ", principalperson='" + principalperson + '\'' +
                ", principalpersonphone='" + principalpersonphone + '\'' +
                ", ondutytelephone='" + ondutytelephone + '\'' +
                ", policeid='" + policeid + '\'' +
                ", authid='" + authid + '\'' +
                ", datrixvideo='" + datrixvideo + '\'' +
                ", videourl=" + videourl +
                ", videodocid='" + videodocid + '\'' +
                ", datrixpic='" + datrixpic + '\'' +
                ", picurl=" + picurl +
                ", picdocid='" + picdocid + '\'' +
                '}';
    }

    /**
     *
     * @return
     * The defendtelphone
     */
    public String getDefendtelphone() {
        return defendtelphone;
    }

    /**
     *
     * @param defendtelphone
     * The defendtelphone
     */
    public void setDefendtelphone(String defendtelphone) {
        this.defendtelphone = defendtelphone;
    }

    /**
     *
     * @return
     * The addtime
     */
    public String getAddtime() {
        return addtime;
    }

    /**
     *
     * @param addtime
     * The addtime
     */
    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The bedsnumber
     */
    public String getBedsnumber() {
        return bedsnumber;
    }

    /**
     *
     * @param bedsnumber
     * The bedsnumber
     */
    public void setBedsnumber(String bedsnumber) {
        this.bedsnumber = bedsnumber;
    }

    /**
     *
     * @return
     * The roomsnumber
     */
    public String getRoomsnumber() {
        return roomsnumber;
    }

    /**
     *
     * @param roomsnumber
     * The roomsnumber
     */
    public void setRoomsnumber(String roomsnumber) {
        this.roomsnumber = roomsnumber;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The principalperson
     */
    public String getPrincipalperson() {
        return principalperson;
    }

    /**
     *
     * @param principalperson
     * The principalperson
     */
    public void setPrincipalperson(String principalperson) {
        this.principalperson = principalperson;
    }

    /**
     *
     * @return
     * The principalpersonphone
     */
    public String getPrincipalpersonphone() {
        return principalpersonphone;
    }

    /**
     *
     * @param principalpersonphone
     * The principalpersonphone
     */
    public void setPrincipalpersonphone(String principalpersonphone) {
        this.principalpersonphone = principalpersonphone;
    }

    /**
     *
     * @return
     * The ondutytelephone
     */
    public String getOndutytelephone() {
        return ondutytelephone;
    }

    /**
     *
     * @param ondutytelephone
     * The ondutytelephone
     */
    public void setOndutytelephone(String ondutytelephone) {
        this.ondutytelephone = ondutytelephone;
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
     * The authid
     */
    public String getAuthid() {
        return authid;
    }

    /**
     *
     * @param authid
     * The authid
     */
    public void setAuthid(String authid) {
        this.authid = authid;
    }

    /**
     *
     * @return
     * The datrixvideo
     */
    public String getDatrixvideo() {
        return datrixvideo;
    }

    /**
     *
     * @param datrixvideo
     * The datrixvideo
     */
    public void setDatrixvideo(String datrixvideo) {
        this.datrixvideo = datrixvideo;
    }

    /**
     *
     * @return
     * The videourl
     */
    public List<String> getVideourl() {
        return videourl;
    }

    /**
     *
     * @param videourl
     * The videourl
     */
    public void setVideourl(List<String> videourl) {
        this.videourl = videourl;
    }

    /**
     *
     * @return
     * The videodocid
     */
    public String getVideodocid() {
        return videodocid;
    }

    /**
     *
     * @param videodocid
     * The videodocid
     */
    public void setVideodocid(String videodocid) {
        this.videodocid = videodocid;
    }

    /**
     *
     * @return
     * The datrixpic
     */
    public String getDatrixpic() {
        return datrixpic;
    }

    /**
     *
     * @param datrixpic
     * The datrixpic
     */
    public void setDatrixpic(String datrixpic) {
        this.datrixpic = datrixpic;
    }

    /**
     *
     * @return
     * The picurl
     */
    public List<String> getPicurl() {
        return picurl;
    }

    /**
     *
     * @param picurl
     * The picurl
     */
    public void setPicurl(List<String> picurl) {
        this.picurl = picurl;
    }

    /**
     *
     * @return
     * The picdocid
     */
    public String getPicdocid() {
        return picdocid;
    }

    /**
     *
     * @param picdocid
     * The picdocid
     */
    public void setPicdocid(String picdocid) {
        this.picdocid = picdocid;
    }
}
