package com.sy.appletree.bean;

import java.util.List;

/**
 * Created by 郁智良
 * on 2016/11/20 0020.
 * des
 */

public class CollectionListBean {

    /**
     * status : y
     * data : [{"allowDownload":"Y","collectId":42,"courseId":27,"createDateStr":"2016-11-14","name":"è®ºæ\u0096\u0087","subject":"2","useGrade":"2","version":"2"},{"allowDownload":"Y","collectId":43,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":44,"courseId":116,"createDateStr":"2016-11-17","name":"a","subject":"2","useGrade":"2","version":"2"},{"allowDownload":"Y","collectId":45,"courseId":116,"createDateStr":"2016-11-17","name":"a","subject":"2","useGrade":"2","version":"2"},{"allowDownload":"Y","collectId":47,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":48,"courseId":116,"createDateStr":"2016-11-17","name":"a","subject":"2","useGrade":"2","version":"2"},{"allowDownload":"Y","collectId":49,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":50,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":51,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":52,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":53,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":54,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":55,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":56,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":57,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":58,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":59,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":60,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":61,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":62,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":63,"courseId":27,"createDateStr":"2016-11-14","name":"è®ºæ\u0096\u0087","subject":"2","useGrade":"2","version":"2"},{"allowDownload":"Y","collectId":64,"courseId":116,"createDateStr":"2016-11-17","name":"a","subject":"2","useGrade":"2","version":"2"},{"allowDownload":"Y","collectId":65,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":67,"courseId":116,"createDateStr":"2016-11-17","name":"a","subject":"2","useGrade":"2","version":"2"},{"allowDownload":"Y","collectId":68,"courseId":116,"createDateStr":"2016-11-17","name":"a","subject":"2","useGrade":"2","version":"2"},{"allowDownload":"Y","collectId":69,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"},{"allowDownload":"Y","collectId":70,"courseId":118,"createDateStr":"2016-11-17","name":"3","subject":"2","useGrade":"2","version":"3"}]
     * info : 操作成功
     */

    private String status;
    private String info;
    /**
     * allowDownload : Y
     * collectId : 42
     * courseId : 27
     * createDateStr : 2016-11-14
     * name : è®ºæ
     * subject : 2
     * useGrade : 2
     * version : 2
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
        private int collectId;
        private int courseId;
        private String createDateStr;
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

        public int getCollectId() {
            return collectId;
        }

        public void setCollectId(int collectId) {
            this.collectId = collectId;
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
