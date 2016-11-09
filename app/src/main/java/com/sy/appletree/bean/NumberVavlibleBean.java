package com.sy.appletree.bean;

/**
 * Created by 郁智良
 * on 2016/11/8 0008.
 * des 验证手机号码是否可用的Bean类
 */

public class NumberVavlibleBean {

    /**
     * status : y
     * data : null
     * info : 可以注册
     */

    private String status;
    private Object data;
    private String info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
