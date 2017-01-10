package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/11/8.
 */

public class SICList {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("res")
    @Expose
    private List<SICListRe> res = new ArrayList<SICListRe>();

    /**
     * @return The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return The res
     */
    public List<SICListRe> getRes() {
        return res;
    }

    /**
     * @param res The res
     */
    public void setRes(List<SICListRe> res) {
        this.res = res;
    }


    public class SICListRe {

        @SerializedName("defendtelphone")
        @Expose
        private String defendtelphone;
        @SerializedName("addtime")
        @Expose
        private String addtime;
        @SerializedName("defendname")
        @Expose
        private String defendname;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("bedsnumber")
        @Expose
        private String bedsnumber;
        @SerializedName("roomsnumber")
        @Expose
        private String roomsnumber;
        @SerializedName("principalperson")
        @Expose
        private String principalperson;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("hotelrank")
        @Expose
        private String hotelrank;
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
        @SerializedName("docid")
        @Expose
        private String docid;
        @SerializedName("type")
        @Expose
        private String type;

        @Override
        public String toString() {
            return "Re{" +
                    "defendtelphone='" + defendtelphone + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", defendname='" + defendname + '\'' +
                    ", address='" + address + '\'' +
                    ", bedsnumber='" + bedsnumber + '\'' +
                    ", roomsnumber='" + roomsnumber + '\'' +
                    ", principalperson='" + principalperson + '\'' +
                    ", name='" + name + '\'' +
                    ", hotelrank='" + hotelrank + '\'' +
                    ", principalpersonphone='" + principalpersonphone + '\'' +
                    ", ondutytelephone='" + ondutytelephone + '\'' +
                    ", policeid='" + policeid + '\'' +
                    ", authid='" + authid + '\'' +
                    ", docid='" + docid + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }

        /**
         * @return The defendtelphone
         */
        public String getDefendtelphone() {
            return defendtelphone;
        }

        /**
         * @param defendtelphone The defendtelphone
         */
        public void setDefendtelphone(String defendtelphone) {
            this.defendtelphone = defendtelphone;
        }

        /**
         * @return The addtime
         */
        public String getAddtime() {
            return addtime;
        }

        /**
         * @param addtime The addtime
         */
        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        /**
         * @return The defendname
         */
        public String getDefendname() {
            return defendname;
        }

        /**
         * @param defendname The defendname
         */
        public void setDefendname(String defendname) {
            this.defendname = defendname;
        }

        /**
         * @return The address
         */
        public String getAddress() {
            return address;
        }

        /**
         * @param address The address
         */
        public void setAddress(String address) {
            this.address = address;
        }

        /**
         * @return The bedsnumber
         */
        public String getBedsnumber() {
            return bedsnumber;
        }

        /**
         * @param bedsnumber The bedsnumber
         */
        public void setBedsnumber(String bedsnumber) {
            this.bedsnumber = bedsnumber;
        }

        /**
         * @return The roomsnumber
         */
        public String getRoomsnumber() {
            return roomsnumber;
        }

        /**
         * @param roomsnumber The roomsnumber
         */
        public void setRoomsnumber(String roomsnumber) {
            this.roomsnumber = roomsnumber;
        }

        /**
         * @return The principalperson
         */
        public String getPrincipalperson() {
            return principalperson;
        }

        /**
         * @param principalperson The principalperson
         */
        public void setPrincipalperson(String principalperson) {
            this.principalperson = principalperson;
        }

        /**
         * @return The name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return The hotelrank
         */
        public String getHotelrank() {
            return hotelrank;
        }

        /**
         * @param hotelrank The hotelrank
         */
        public void setHotelrank(String hotelrank) {
            this.hotelrank = hotelrank;
        }

        /**
         * @return The principalpersonphone
         */
        public String getPrincipalpersonphone() {
            return principalpersonphone;
        }

        /**
         * @param principalpersonphone The principalpersonphone
         */
        public void setPrincipalpersonphone(String principalpersonphone) {
            this.principalpersonphone = principalpersonphone;
        }

        /**
         * @return The ondutytelephone
         */
        public String getOndutytelephone() {
            return ondutytelephone;
        }

        /**
         * @param ondutytelephone The ondutytelephone
         */
        public void setOndutytelephone(String ondutytelephone) {
            this.ondutytelephone = ondutytelephone;
        }

        /**
         * @return The policeid
         */
        public String getPoliceid() {
            return policeid;
        }

        /**
         * @param policeid The policeid
         */
        public void setPoliceid(String policeid) {
            this.policeid = policeid;
        }

        /**
         * @return The authid
         */
        public String getAuthid() {
            return authid;
        }

        /**
         * @param authid The authid
         */
        public void setAuthid(String authid) {
            this.authid = authid;
        }

        /**
         * @return The docid
         */
        public String getDocid() {
            return docid;
        }

        /**
         * @param docid The docid
         */
        public void setDocid(String docid) {
            this.docid = docid;
        }

        /**
         * @return The type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type The type
         */
        public void setType(String type) {
            this.type = type;
        }

    }
}
