package wgz.com.cx_ga_project.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgz on 2016/11/21.
 */

public class DepPeople {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("res")
    @Expose
    private List<Re> res = new ArrayList<Re>();

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
    public List<Re> getRes() {
        return res;
    }

    /**
     * @param res The res
     */
    public void setRes(List<Re> res) {
        this.res = res;
    }

    public class Re {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("policeid")
        @Expose
        private String policeid;
        @SerializedName("polid")
        @Expose
        private String polid;
        @SerializedName("personidcard")
        @Expose
        private String personidcard;
        @SerializedName("policename")
        @Expose
        private String policename;
        @SerializedName("pnamespell")
        @Expose
        private Object pnamespell;
        @SerializedName("policesex")
        @Expose
        private String policesex;
        @SerializedName("policebirthdate")
        @Expose
        private String policebirthdate;
        @SerializedName("policenative")
        @Expose
        private String policenative;
        @SerializedName("pphonenum")
        @Expose
        private String pphonenum;
        @SerializedName("cjgzrq")
        @Expose
        private String cjgzrq;
        @SerializedName("cjgagzrq")
        @Expose
        private String cjgagzrq;
        @SerializedName("officecode")
        @Expose
        private String officecode;
        @SerializedName("officecodename")
        @Expose
        private String officecodename;
        @SerializedName("peducationlevel")
        @Expose
        private Object peducationlevel;
        @SerializedName("policerank")
        @Expose
        private Object policerank;
        @SerializedName("bloodtype")
        @Expose
        private Object bloodtype;
        @SerializedName("politicalstatus")
        @Expose
        private Object politicalstatus;
        @SerializedName("maritalstatus")
        @Expose
        private Object maritalstatus;
        @SerializedName("healthcondition")
        @Expose
        private Object healthcondition;
        @SerializedName("police_posit")
        @Expose
        private Object policePosit;
        @SerializedName("native_place")
        @Expose
        private String nativePlace;
        @SerializedName("ppolid")
        @Expose
        private Object ppolid;

        @Override
        public String toString() {
            return "Re{" +
                    "id='" + id + '\'' +
                    ", policeid='" + policeid + '\'' +
                    ", polid='" + polid + '\'' +
                    ", personidcard='" + personidcard + '\'' +
                    ", policename='" + policename + '\'' +
                    ", pnamespell=" + pnamespell +
                    ", policesex='" + policesex + '\'' +
                    ", policebirthdate='" + policebirthdate + '\'' +
                    ", policenative='" + policenative + '\'' +
                    ", pphonenum='" + pphonenum + '\'' +
                    ", cjgzrq='" + cjgzrq + '\'' +
                    ", cjgagzrq='" + cjgagzrq + '\'' +
                    ", officecode='" + officecode + '\'' +
                    ", officecodename='" + officecodename + '\'' +
                    ", peducationlevel=" + peducationlevel +
                    ", policerank=" + policerank +
                    ", bloodtype=" + bloodtype +
                    ", politicalstatus=" + politicalstatus +
                    ", maritalstatus=" + maritalstatus +
                    ", healthcondition=" + healthcondition +
                    ", policePosit=" + policePosit +
                    ", nativePlace='" + nativePlace + '\'' +
                    ", ppolid=" + ppolid +
                    '}';
        }

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
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
         * @return The polid
         */
        public String getPolid() {
            return polid;
        }

        /**
         * @param polid The polid
         */
        public void setPolid(String polid) {
            this.polid = polid;
        }

        /**
         * @return The personidcard
         */
        public String getPersonidcard() {
            return personidcard;
        }

        /**
         * @param personidcard The personidcard
         */
        public void setPersonidcard(String personidcard) {
            this.personidcard = personidcard;
        }

        /**
         * @return The policename
         */
        public String getPolicename() {
            return policename;
        }

        /**
         * @param policename The policename
         */
        public void setPolicename(String policename) {
            this.policename = policename;
        }

        /**
         * @return The pnamespell
         */
        public Object getPnamespell() {
            return pnamespell;
        }

        /**
         * @param pnamespell The pnamespell
         */
        public void setPnamespell(Object pnamespell) {
            this.pnamespell = pnamespell;
        }

        /**
         * @return The policesex
         */
        public String getPolicesex() {
            return policesex;
        }

        /**
         * @param policesex The policesex
         */
        public void setPolicesex(String policesex) {
            this.policesex = policesex;
        }

        /**
         * @return The policebirthdate
         */
        public String getPolicebirthdate() {
            return policebirthdate;
        }

        /**
         * @param policebirthdate The policebirthdate
         */
        public void setPolicebirthdate(String policebirthdate) {
            this.policebirthdate = policebirthdate;
        }

        /**
         * @return The policenative
         */
        public String getPolicenative() {
            return policenative;
        }

        /**
         * @param policenative The policenative
         */
        public void setPolicenative(String policenative) {
            this.policenative = policenative;
        }

        /**
         * @return The pphonenum
         */
        public String getPphonenum() {
            return pphonenum;
        }

        /**
         * @param pphonenum The pphonenum
         */
        public void setPphonenum(String pphonenum) {
            this.pphonenum = pphonenum;
        }

        /**
         * @return The cjgzrq
         */
        public String getCjgzrq() {
            return cjgzrq;
        }

        /**
         * @param cjgzrq The cjgzrq
         */
        public void setCjgzrq(String cjgzrq) {
            this.cjgzrq = cjgzrq;
        }

        /**
         * @return The cjgagzrq
         */
        public String getCjgagzrq() {
            return cjgagzrq;
        }

        /**
         * @param cjgagzrq The cjgagzrq
         */
        public void setCjgagzrq(String cjgagzrq) {
            this.cjgagzrq = cjgagzrq;
        }

        /**
         * @return The officecode
         */
        public String getOfficecode() {
            return officecode;
        }

        /**
         * @param officecode The officecode
         */
        public void setOfficecode(String officecode) {
            this.officecode = officecode;
        }

        /**
         * @return The officecodename
         */
        public String getOfficecodename() {
            return officecodename;
        }

        /**
         * @param officecodename The officecodename
         */
        public void setOfficecodename(String officecodename) {
            this.officecodename = officecodename;
        }

        /**
         * @return The peducationlevel
         */
        public Object getPeducationlevel() {
            return peducationlevel;
        }

        /**
         * @param peducationlevel The peducationlevel
         */
        public void setPeducationlevel(Object peducationlevel) {
            this.peducationlevel = peducationlevel;
        }

        /**
         * @return The policerank
         */
        public Object getPolicerank() {
            return policerank;
        }

        /**
         * @param policerank The policerank
         */
        public void setPolicerank(Object policerank) {
            this.policerank = policerank;
        }

        /**
         * @return The bloodtype
         */
        public Object getBloodtype() {
            return bloodtype;
        }

        /**
         * @param bloodtype The bloodtype
         */
        public void setBloodtype(Object bloodtype) {
            this.bloodtype = bloodtype;
        }

        /**
         * @return The politicalstatus
         */
        public Object getPoliticalstatus() {
            return politicalstatus;
        }

        /**
         * @param politicalstatus The politicalstatus
         */
        public void setPoliticalstatus(Object politicalstatus) {
            this.politicalstatus = politicalstatus;
        }

        /**
         * @return The maritalstatus
         */
        public Object getMaritalstatus() {
            return maritalstatus;
        }

        /**
         * @param maritalstatus The maritalstatus
         */
        public void setMaritalstatus(Object maritalstatus) {
            this.maritalstatus = maritalstatus;
        }

        /**
         * @return The healthcondition
         */
        public Object getHealthcondition() {
            return healthcondition;
        }

        /**
         * @param healthcondition The healthcondition
         */
        public void setHealthcondition(Object healthcondition) {
            this.healthcondition = healthcondition;
        }

        /**
         * @return The policePosit
         */
        public Object getPolicePosit() {
            return policePosit;
        }

        /**
         * @param policePosit The police_posit
         */
        public void setPolicePosit(Object policePosit) {
            this.policePosit = policePosit;
        }

        /**
         * @return The nativePlace
         */
        public String getNativePlace() {
            return nativePlace;
        }

        /**
         * @param nativePlace The native_place
         */
        public void setNativePlace(String nativePlace) {
            this.nativePlace = nativePlace;
        }

        /**
         * @return The ppolid
         */
        public Object getPpolid() {
            return ppolid;
        }

        /**
         * @param ppolid The ppolid
         */
        public void setPpolid(Object ppolid) {
            this.ppolid = ppolid;
        }


    }
}
