package wgz.com.cx_ga_project.entity;


import com.google.gson.annotations.SerializedName;

/**
 * Created by wgz on 2016/9/26.
 */

public class DatrixCreat {
    @SerializedName("code")

    private Integer code;
    @SerializedName("result")

    private DatrixCreateResult result;

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
     * @return The result
     */
    public DatrixCreateResult getResult() {
        return result;
    }

    /**
     * @param result The result
     */
    public void setResult(DatrixCreateResult result) {
        this.result = result;
    }


    public class DatrixCreateResult {

        @SerializedName("fileid")

        private String fileid;
        @SerializedName("uid")

        private String uid;
        @SerializedName("name")

        private String name;
        @SerializedName("filesize")

        private String filesize;
        @SerializedName("createtime")

        private String createtime;
        @SerializedName("lastmodifytime")

        private String lastmodifytime;
        @SerializedName("lastaccesstime")

        private String lastaccesstime;
        @SerializedName("lock")

        private Boolean lock;
        @SerializedName("archived")

        private Boolean archived;
        @SerializedName("type")

        private Integer type;
        @SerializedName("dirid")

        private String dirid;
        @SerializedName("datasetid")

        private String datasetid;
        @SerializedName("parentid")

        private String parentid;
        @SerializedName("publicdirid")

        private String publicdirid;

        /**
         * @return The fileid
         */
        public String getFileid() {
            return fileid;
        }

        /**
         * @param fileid The fileid
         */
        public void setFileid(String fileid) {
            this.fileid = fileid;
        }

        /**
         * @return The uid
         */
        public String getUid() {
            return uid;
        }

        /**
         * @param uid The uid
         */
        public void setUid(String uid) {
            this.uid = uid;
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
         * @return The filesize
         */
        public String getFilesize() {
            return filesize;
        }

        /**
         * @param filesize The filesize
         */
        public void setFilesize(String filesize) {
            this.filesize = filesize;
        }

        /**
         * @return The createtime
         */
        public String getCreatetime() {
            return createtime;
        }

        /**
         * @param createtime The createtime
         */
        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        /**
         * @return The lastmodifytime
         */
        public String getLastmodifytime() {
            return lastmodifytime;
        }

        /**
         * @param lastmodifytime The lastmodifytime
         */
        public void setLastmodifytime(String lastmodifytime) {
            this.lastmodifytime = lastmodifytime;
        }

        /**
         * @return The lastaccesstime
         */
        public String getLastaccesstime() {
            return lastaccesstime;
        }

        /**
         * @param lastaccesstime The lastaccesstime
         */
        public void setLastaccesstime(String lastaccesstime) {
            this.lastaccesstime = lastaccesstime;
        }

        /**
         * @return The lock
         */
        public Boolean getLock() {
            return lock;
        }

        /**
         * @param lock The lock
         */
        public void setLock(Boolean lock) {
            this.lock = lock;
        }

        /**
         * @return The archived
         */
        public Boolean getArchived() {
            return archived;
        }

        /**
         * @param archived The archived
         */
        public void setArchived(Boolean archived) {
            this.archived = archived;
        }

        /**
         * @return The type
         */
        public Integer getType() {
            return type;
        }

        /**
         * @param type The type
         */
        public void setType(Integer type) {
            this.type = type;
        }

        /**
         * @return The dirid
         */
        public String getDirid() {
            return dirid;
        }

        /**
         * @param dirid The dirid
         */
        public void setDirid(String dirid) {
            this.dirid = dirid;
        }

        /**
         * @return The datasetid
         */
        public String getDatasetid() {
            return datasetid;
        }

        /**
         * @param datasetid The datasetid
         */
        public void setDatasetid(String datasetid) {
            this.datasetid = datasetid;
        }

        /**
         * @return The parentid
         */
        public String getParentid() {
            return parentid;
        }

        /**
         * @param parentid The parentid
         */
        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        /**
         * @return The publicdirid
         */
        public String getPublicdirid() {
            return publicdirid;
        }

        /**
         * @param publicdirid The publicdirid
         */
        public void setPublicdirid(String publicdirid) {
            this.publicdirid = publicdirid;
        }

        @Override
        public String toString() {
            return "DatrixCreateResult{" +
                    "fileid='" + fileid + '\'' +
                    ", uid='" + uid + '\'' +
                    ", name='" + name + '\'' +
                    ", filesize='" + filesize + '\'' +
                    ", createtime='" + createtime + '\'' +
                    ", lastmodifytime='" + lastmodifytime + '\'' +
                    ", lastaccesstime='" + lastaccesstime + '\'' +
                    ", lock=" + lock +
                    ", archived=" + archived +
                    ", type=" + type +
                    ", dirid='" + dirid + '\'' +
                    ", datasetid='" + datasetid + '\'' +
                    ", parentid='" + parentid + '\'' +
                    ", publicdirid='" + publicdirid + '\'' +
                    '}';
        }
    }
}