package wgz.com.cx_ga_project.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/11/3.
 */

public class CloudTag {

    private List<cTag> list = new ArrayList<>();

    public List<cTag> getList() {
        return list;
    }

    public void setList(List<cTag> list) {
        this.list = list;
    }

    public class cTag {
        private String TagText;
        private String TagId;

        public String getTagText() {
            return TagText;
        }

        public void setTagText(String tagText) {
            TagText = tagText;
        }

        public String getTagId() {
            return TagId;
        }

        public void setTagId(String tagId) {
            TagId = tagId;
        }
    }

}
