package com.sy.appletree.bean;

import java.util.List;

/**
 * Created by 郁智良
 * on 2016/11/17 0017.
 * des
 */

public class ClasssSummaryBean {

    /**
     * status : y
     * data : [{"classId":279,"className":"一年级十四班","classType":"A","groupNum":"1","schoolName":"深圳小学","studentNum":"70"},{"classId":257,"className":"一年级一班","classType":"A","groupNum":"0","schoolName":"深圳小学","studentNum":"6"},{"classId":261,"className":"一年级二班","classType":"B","groupNum":"1","schoolName":"深圳小学","studentNum":"6"},{"classId":283,"className":null,"classType":"A","groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":284,"className":null,"classType":"A","groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":285,"className":null,"classType":"A","groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":286,"className":null,"classType":"A","groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":287,"className":null,"classType":"A","groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":288,"className":null,"classType":"A","groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":289,"className":"初中七年级(2)班","classType":"A","groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":292,"className":"困","classType":null,"groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":293,"className":"了","classType":null,"groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":294,"className":"初中七年级(1)班","classType":"A","groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":298,"className":"家","classType":null,"groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":301,"className":"啊","classType":null,"groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":303,"className":"补","classType":null,"groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":304,"className":"不得了的","classType":null,"groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":305,"className":"啊都","classType":null,"groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":306,"className":"啊啦啦啦","classType":null,"groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":307,"className":"拒绝","classType":null,"groupNum":"0","schoolName":"深圳小学","studentNum":"0"},{"classId":309,"className":"他","classType":null,"groupNum":"0","schoolName":"深圳小学","studentNum":"0"}]
     * info : 操作成功
     */

    private String status;
    private String info;
    /**
     * classId : 279
     * className : 一年级十四班
     * classType : A
     * groupNum : 1
     * schoolName : 深圳小学
     * studentNum : 70
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
        private String classType;
        private String groupNum;
        private String schoolName;
        private String studentNum;

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

        public String getClassType() {
            return classType;
        }

        public void setClassType(String classType) {
            this.classType = classType;
        }

        public String getGroupNum() {
            return groupNum;
        }

        public void setGroupNum(String groupNum) {
            this.groupNum = groupNum;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getStudentNum() {
            return studentNum;
        }

        public void setStudentNum(String studentNum) {
            this.studentNum = studentNum;
        }
    }
}
