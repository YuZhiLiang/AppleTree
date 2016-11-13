package com.sy.appletree.base;

import java.util.List;

/**
 * Created by 郁智良
 * on 2016/11/12 0012.
 * des
 */

public class WisomLibListBean {

    /**
     * status : y
     * data : [{"allowDownload":"Y","courseId":5,"createDateStr":"2016-11-08","havaCollect":"N","name":"ENGSH","subject":"3","useGrade":"6","version":"5"},{"allowDownload":"Y","courseId":14,"createDateStr":"2016-11-09","havaCollect":"N","name":"测试课程","subject":"8","useGrade":"8","version":"8"},{"allowDownload":"Y","courseId":15,"createDateStr":"2016-11-09","havaCollect":"N","name":"CESHI","subject":"8","useGrade":"12","version":"8"},{"allowDownload":"Y","courseId":16,"createDateStr":"2016-11-09","havaCollect":"N","name":"CESHI","subject":"8","useGrade":"12","version":"8"},{"allowDownload":"Y","courseId":17,"createDateStr":"2016-11-09","havaCollect":"N","name":"CESHI","subject":"8","useGrade":"12","version":"8"},{"allowDownload":"Y","courseId":22,"createDateStr":"2016-11-10","havaCollect":"N","name":"CESHI","subject":"8","useGrade":"12","version":"8"},{"allowDownload":"Y","courseId":52,"createDateStr":"2016-11-11","havaCollect":"N","name":"sfasfsf","subject":"3","useGrade":"3","version":"2"},{"allowDownload":"Y","courseId":53,"createDateStr":"2016-11-11","havaCollect":"N","name":"ferf","subject":"3","useGrade":"4","version":"4"},{"allowDownload":"Y","courseId":76,"createDateStr":"2016-11-11","havaCollect":"N","name":"啊","subject":"1","useGrade":"7","version":"1"},{"allowDownload":"Y","courseId":78,"createDateStr":"2016-11-11","havaCollect":"N","name":"她","subject":"1","useGrade":"1","version":"7"},{"allowDownload":"Y","courseId":80,"createDateStr":"2016-11-11","havaCollect":"N","name":"哈","subject":"7","useGrade":"7","version":"7"},{"allowDownload":"Y","courseId":81,"createDateStr":"2016-11-11","havaCollect":"N","name":"啊","subject":"2","useGrade":"2","version":"5"},{"allowDownload":"Y","courseId":82,"createDateStr":"2016-11-11","havaCollect":"N","name":"哈哈","subject":"2","useGrade":"2","version":"2"},{"allowDownload":"Y","courseId":83,"createDateStr":"2016-11-11","havaCollect":"N","name":"啊","subject":"2","useGrade":"2","version":"8"},{"allowDownload":"Y","courseId":88,"createDateStr":"2016-11-11","havaCollect":"N","name":"了","subject":"7","useGrade":"3","version":"8"},{"allowDownload":"Y","courseId":109,"createDateStr":"2016-11-12","havaCollect":"N","name":"我","subject":"1","useGrade":"1","version":"1"},{"allowDownload":"Y","courseId":119,"createDateStr":"2016-11-12","havaCollect":"N","name":"提交","subject":"6","useGrade":"2","version":"5"},{"allowDownload":"Y","courseId":128,"createDateStr":"2016-11-12","havaCollect":"N","name":"啊","subject":"2","useGrade":"2","version":"8"}]
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
