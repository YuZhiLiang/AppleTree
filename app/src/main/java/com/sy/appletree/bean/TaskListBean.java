package com.sy.appletree.bean;

import java.util.List;

/**
 * Created by 郁智良
 * on 2016/11/12 0012.
 * des
 */

public class TaskListBean {

    /**
     * status : y
     * data : [{"taskId":48,"taskName":"@"},{"taskId":49,"taskName":"你牛皮"},{"taskId":50,"taskName":"LV"}]
     * info : 操作成功
     */

    private String status;
    private String info;
    /**
     * taskId : 48
     * taskName : @
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
        private int taskId;
        private String taskName;

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
    }
}
