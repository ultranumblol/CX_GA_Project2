package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/10/8.
 */

public class Scheduling {
    @SerializedName("code")

    private Integer code;
    @SerializedName("res")

    private List<ScheduilingRe> res = new ArrayList<ScheduilingRe>();

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
    public List<ScheduilingRe> getRes() {
        return res;
    }

    /**
     *
     * @param res
     * The res
     */
    public void setRes(List<ScheduilingRe> res) {
        this.res = res;
    }


public class Id {

    @SerializedName("policenum")

    private String policenum;
    @SerializedName("dataid")

    private String dataid;

    /**
     *
     * @return
     * The policenum
     */
    public String getPolicenum() {
        return policenum;
    }

    /**
     *
     * @param policenum
     * The policenum
     */
    public void setPolicenum(String policenum) {
        this.policenum = policenum;
    }

    /**
     *
     * @return
     * The dataid
     */
    public String getDataid() {
        return dataid;
    }

    /**
     *
     * @param dataid
     * The dataid
     */
    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

}

public class ScheduilingRe {
    @Override
    public String toString() {
        return "ScheduilingRe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }

    @SerializedName("id")

    private Id id;
    @SerializedName("title")

    private String title;
    @SerializedName("start")

    private String start;
    @SerializedName("end")

    private String end;
    @SerializedName("mark")

    private String mark;

    /**
     *
     * @return
     * The id
     */
    public Id getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Id id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The start
     */
    public String getStart() {
        return start;
    }

    /**
     *
     * @param start
     * The start
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     *
     * @return
     * The end
     */
    public String getEnd() {
        return end;
    }

    /**
     *
     * @param end
     * The end
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     *
     * @return
     * The mark
     */
    public String getMark() {
        return mark;
    }

    /**
     *
     * @param mark
     * The mark
     */
    public void setMark(String mark) {
        this.mark = mark;
    }
}
}