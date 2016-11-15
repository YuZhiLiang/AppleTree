package com.sy.appletree.bean;

import java.util.List;

/**
 * Created by 郁智良
 * on 2016/11/14 0014.
 * des
 */

public class SchoolListBean {

    /**
     * status : y
     * data : [{"schoolId":1,"schoolName":"深圳小学"},{"schoolId":2,"schoolName":"罗湖小学"},{"schoolId":3,"schoolName":"西丽中学"}]
     * info : 操作成功
     */

    private String status;
    private String info;
    /**
     * schoolId : 1
     * schoolName : 深圳小学
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
        private int schoolId;
        private String schoolName;

        public int getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(int schoolId) {
            this.schoolId = schoolId;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }
    }
}
