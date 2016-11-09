package com.sy.appletree.bean;

/**
 * Created by 郁智良
 * on 2016/11/9 0009.
 * des
 */
public class EvaluateStudentBean {
    String studentId;//学id
    String taskPointId; //评价指标ID
    String point; //得分
    String filename;//录音文件名

    public EvaluateStudentBean(String studentId, String taskPointId, String point, String filename) {
        this.studentId = studentId;
        this.taskPointId = taskPointId;
        this.point = point;
        this.filename = filename;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTaskPointId() {
        return taskPointId;
    }

    public void setTaskPointId(String taskPointId) {
        this.taskPointId = taskPointId;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
