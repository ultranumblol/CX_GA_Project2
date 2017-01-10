package wgz.com.cx_ga_project.entity;

import java.util.List;

/**
 * Created by wgz on 2016/11/16.
 */

public class ChatPic {
    private List<String> paths ;

    public ChatPic(List<String> paths) {
        this.paths = paths;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }
}
