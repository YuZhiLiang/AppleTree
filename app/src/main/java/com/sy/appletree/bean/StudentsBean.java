package com.sy.appletree.bean;

import java.util.List;

/**
 * Created by 郁智良
 * on 2016/11/17 0017.
 * des
 */

public class StudentsBean {

    /**
     * status : y
     * data : [{"startChar":null,"studentId":186,"studentName":"cbl10"},{"startChar":null,"studentId":152,"studentName":"cbl10"},{"startChar":null,"studentId":153,"studentName":"cbl11"},{"startChar":null,"studentId":187,"studentName":"cbl11"},{"startChar":null,"studentId":154,"studentName":"cbl12"},{"startChar":null,"studentId":188,"studentName":"cbl12"},{"startChar":null,"studentId":155,"studentName":"cbl13"},{"startChar":null,"studentId":189,"studentName":"cbl13"},{"startChar":null,"studentId":156,"studentName":"cbl14"},{"startChar":null,"studentId":190,"studentName":"cbl14"},{"startChar":null,"studentId":191,"studentName":"cbl15"},{"startChar":null,"studentId":157,"studentName":"cbl15"},{"startChar":null,"studentId":158,"studentName":"cbl16"},{"startChar":null,"studentId":192,"studentName":"cbl16"},{"startChar":null,"studentId":159,"studentName":"cbl17"},{"startChar":null,"studentId":193,"studentName":"cbl17"},{"startChar":null,"studentId":194,"studentName":"cbl18"},{"startChar":null,"studentId":160,"studentName":"cbl18"},{"startChar":null,"studentId":161,"studentName":"cbl19"},{"startChar":null,"studentId":195,"studentName":"cbl19"},{"startChar":null,"studentId":162,"studentName":"cbl20"},{"startChar":null,"studentId":196,"studentName":"cbl20"},{"startChar":null,"studentId":197,"studentName":"cbl21"},{"startChar":null,"studentId":163,"studentName":"cbl21"},{"startChar":null,"studentId":164,"studentName":"cbl22"},{"startChar":null,"studentId":198,"studentName":"cbl22"},{"startChar":null,"studentId":199,"studentName":"cbl23"},{"startChar":null,"studentId":165,"studentName":"cbl23"},{"startChar":null,"studentId":200,"studentName":"cbl24"},{"startChar":null,"studentId":166,"studentName":"cbl24"},{"startChar":null,"studentId":201,"studentName":"cbl25"},{"startChar":null,"studentId":167,"studentName":"cbl25"},{"startChar":null,"studentId":168,"studentName":"cbl26"},{"startChar":null,"studentId":202,"studentName":"cbl26"},{"startChar":null,"studentId":203,"studentName":"cbl27"},{"startChar":null,"studentId":169,"studentName":"cbl27"},{"startChar":null,"studentId":204,"studentName":"cbl28"},{"startChar":null,"studentId":170,"studentName":"cbl28"},{"startChar":null,"studentId":171,"studentName":"cbl29"},{"startChar":null,"studentId":205,"studentName":"cbl29"},{"startChar":null,"studentId":172,"studentName":"cbl30"},{"startChar":null,"studentId":206,"studentName":"cbl30"},{"startChar":null,"studentId":207,"studentName":"cbl31"},{"startChar":null,"studentId":173,"studentName":"cbl31"},{"startChar":null,"studentId":208,"studentName":"cbl32"},{"startChar":null,"studentId":174,"studentName":"cbl32"},{"startChar":null,"studentId":209,"studentName":"cbl33"},{"startChar":null,"studentId":175,"studentName":"cbl33"},{"startChar":null,"studentId":176,"studentName":"cbl34"},{"startChar":null,"studentId":210,"studentName":"cbl34"},{"startChar":null,"studentId":211,"studentName":"cbl35"},{"startChar":null,"studentId":177,"studentName":"cbl35"},{"startChar":null,"studentId":178,"studentName":"cbl36"},{"startChar":null,"studentId":212,"studentName":"cbl36"},{"startChar":null,"studentId":179,"studentName":"cbl37"},{"startChar":null,"studentId":213,"studentName":"cbl37"},{"startChar":null,"studentId":180,"studentName":"cbl38"},{"startChar":null,"studentId":214,"studentName":"cbl38"},{"startChar":null,"studentId":215,"studentName":"cbl39"},{"startChar":null,"studentId":181,"studentName":"cbl39"},{"startChar":null,"studentId":182,"studentName":"cbl40"},{"startChar":null,"studentId":216,"studentName":"cbl40"},{"startChar":null,"studentId":217,"studentName":"cbl41"},{"startChar":null,"studentId":183,"studentName":"cbl41"},{"startChar":null,"studentId":218,"studentName":"cbl42"},{"startChar":null,"studentId":184,"studentName":"cbl42"},{"startChar":null,"studentId":150,"studentName":"cbltest1"},{"startChar":null,"studentId":151,"studentName":"cbltest2"},{"startChar":null,"studentId":185,"studentName":"CESHI"},{"startChar":null,"studentId":231,"studentName":"English"}]
     * info : 操作成功
     */

    private String status;
    private String info;
    /**
     * startChar : null
     * studentId : 186
     * studentName : cbl10
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
        private Object startChar;
        private int studentId;
        private String studentName;

        public Object getStartChar() {
            return startChar;
        }

        public void setStartChar(Object startChar) {
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
