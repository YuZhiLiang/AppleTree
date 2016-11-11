package com.sy.appletree.bean;

import java.util.List;

/**
 * Created by 郁智良
 * on 2016/11/9 0009.
 * des
 */

public class WisomLibBean {

    /**
     * status : y
     * data : [{"allowDownload":"Y","courseId":5,"createDateStr":"2016-11-08","havaCollect":"N","name":"ENGSH","subject":"3","useGrade":"6","version":"5"}]
     * info : 操作成功
     */

    private String status;
    private String info;
    /**
     * allowDownload : Y
     * courseId : 5
     * createDateStr : 2016-11-08
     * havaCollect : N
     * name : ENGSH
     * subject : 3
     * useGrade : 6
     * version : 5
     */

    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String allowDownload;
        private int courseId;
        private String createDateStr;
        private String havaCollect;
        private String name;
        private String subject;
        private String useGrade;
        private String version;

        public String getAllowDownload() {
            return allowDownload;
        }

        public void setAllowDownload(String allowDownload) {
            this.allowDownload = allowDownload;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getCreateDateStr() {
            return createDateStr;
        }

        public void setCreateDateStr(String createDateStr) {
            this.createDateStr = createDateStr;
        }

        public String getHavaCollect() {
            return havaCollect;
        }

        public void setHavaCollect(String havaCollect) {
            this.havaCollect = havaCollect;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getUseGrade() {
            return useGrade;
        }

        public void setUseGrade(String useGrade) {
            this.useGrade = useGrade;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
