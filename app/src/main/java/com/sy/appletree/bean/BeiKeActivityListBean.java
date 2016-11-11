package com.sy.appletree.bean;

import java.util.List;

/**
 * Created by 郁智良
 * on 2016/11/11 0011.
 * des
 */

public class BeiKeActivityListBean {

    /**
     * status : y
     * data : [{"courseId":24,"courseName":"这会又用了吧"},{"courseId":25,"courseName":"爱"},{"courseId":26,"courseName":"哎呀"},{"courseId":27,"courseName":"兔兔"},{"courseId":28,"courseName":"哈哈啊哈"},{"courseId":29,"courseName":"兔兔我吃吃"}]
     * info : 操作成功
     */

    private String status;
    private String info;
    /**
     * courseId : 24
     * courseName : 这会又用了吧
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
        public int courseId;
        public String courseName;

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }
    }
}
