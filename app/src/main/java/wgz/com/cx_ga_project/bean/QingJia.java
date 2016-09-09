package wgz.com.cx_ga_project.bean;

/**
 * Created by wgz on 2016/8/9.
 */

public class QingJia {
    private String policeid;
    private String applytime;
    private String status;
    private String end;
    private String start;
    private String upper;
    private String content;
    private String days;
    private String reasontype;

    public String getPoliceid() {
        return policeid;
    }

    public void setPoliceid(String policeid) {
        this.policeid = policeid;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getReasontype() {
        return reasontype;
    }

    public void setReasontype(String reasontype) {
        this.reasontype = reasontype;
    }

    @Override
    public String toString() {
        return "QingJia{" +
                "policeid='" + policeid + '\'' +
                ", applytime='" + applytime + '\'' +
                ", status='" + status + '\'' +
                ", end='" + end + '\'' +
                ", start='" + start + '\'' +
                ", upper='" + upper + '\'' +
                ", content='" + content + '\'' +
                ", days='" + days + '\'' +
                ", reasontype='" + reasontype + '\'' +
                '}';
    }
}
