package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wgz on 2016/9/26.
 */

public class DatrixFinish {
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @SerializedName("code")

    private Integer code;
}
