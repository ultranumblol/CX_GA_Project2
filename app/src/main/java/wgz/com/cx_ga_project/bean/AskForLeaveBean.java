package wgz.com.cx_ga_project.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by wgz on 2016/8/5.
 */

public class AskForLeaveBean implements IPickerViewData {

    private long ID;
    private String type;

    public AskForLeaveBean(long ID, String type) {
        this.ID = ID;

        this.type = type;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getPickerViewText() {
        return type;
    }
}
