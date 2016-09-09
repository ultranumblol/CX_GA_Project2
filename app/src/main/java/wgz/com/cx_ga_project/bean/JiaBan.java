package wgz.com.cx_ga_project.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wgz on 2016/8/9.
 */

public class JiaBan {
  private String id;
    private String applytime;
    private String status;
    private String end;
    private String start;
    private String upper;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplytime() {
        return applytime;
    }

    public void setApplytime(String applytime) {
        this.applytime = applytime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getUpper() {
        return upper;
    }

    public void setUpper(String upper) {
        this.upper = upper;
    }

    @Override
    public String toString() {
        return "JiaBan{" +
                "id='" + id + '\'' +
                ", applytime='" + applytime + '\'' +
                ", status='" + status + '\'' +
                ", end='" + end + '\'' +
                ", start='" + start + '\'' +
                ", upper='" + upper + '\'' +
                '}';
    }
}
