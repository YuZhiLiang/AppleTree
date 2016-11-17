package com.sy.appletree.bean;

import java.util.List;

/**
 * Created by 郁智良
 * on 2016/11/17 0017.
 * des
 */

public class TaskDetailInfo {

    /**
     * status : y
     * data : {"attachmentList":[],"content":"a","evaluatePointList":[],"taskId":76,"taskLinkVoList":[],"taskName":"a"}
     * info : 操作成功
     */

    private String status;
    /**
     * attachmentList : []
     * content : a
     * evaluatePointList : []
     * taskId : 76
     * taskLinkVoList : []
     * taskName : a
     */

    private DataBean data;
    private String info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static class DataBean {
        private String content;
        private int taskId;
        private String taskName;
        private List<?> attachmentList;
        private List<?> evaluatePointList;
        private List<?> taskLinkVoList;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public List<?> getAttachmentList() {
            return attachmentList;
        }

        public void setAttachmentList(List<?> attachmentList) {
            this.attachmentList = attachmentList;
        }

        public List<?> getEvaluatePointList() {
            return evaluatePointList;
        }

        public void setEvaluatePointList(List<?> evaluatePointList) {
            this.evaluatePointList = evaluatePointList;
        }

        public List<?> getTaskLinkVoList() {
            return taskLinkVoList;
        }

        public void setTaskLinkVoList(List<?> taskLinkVoList) {
            this.taskLinkVoList = taskLinkVoList;
        }
    }
}
