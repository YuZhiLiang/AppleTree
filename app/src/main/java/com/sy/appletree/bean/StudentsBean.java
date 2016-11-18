package com.sy.appletree.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郁智良
 * on 2016/11/17 0017.
 * des
 */

public class StudentsBean {

    /**
     * status : y
     * data : [{"startChar":"#","studentId":245,"studentName":"45"},{"startChar":"#","studentId":244,"studentName":"5"},{"startChar":"B","studentId":243,"studentName":"不得了"},{"startChar":"B","studentId":246,"studentName":"不了"}]
     * info : 操作成功
     */

    private String status;
    private String info;
    /**
     * startChar : #
     * studentId : 245
     * studentName : 45
     */

    private ArrayList<DataBean> data;

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

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String startChar;
        private int studentId;
        private String studentName;

        public String getStartChar() {
            return startChar;
        }

        public void setStartChar(String startChar) {
            this.startChar = startChar;
        }

        public int getStudentId() {
            return studentId;
        }

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

    }
}
