package com.sy.appletree.bean;

import java.util.List;

/**
 * Created by 郁智良
 * on 2016/11/17 0017.
 * des
 */

public class SchoolClassListBean {

    /**
     * status : y
     * data : [{"classId":252,"className":"一年级一班"},{"classId":253,"className":"一年级二班"},{"classId":254,"className":"一年级三班"},{"classId":255,"className":"二年级五班"},{"classId":259,"className":"三年级四班"}]
     * info : 操作成功
     */

    private String status;
    private String info;
    /**
     * classId : 252
     * className : 一年级一班
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
        private int classId;
        private String className;

        public int getClassId() {
            return classId;
        }

        public void setClassId(int classId) {
            this.classId = classId;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }
    }
}
