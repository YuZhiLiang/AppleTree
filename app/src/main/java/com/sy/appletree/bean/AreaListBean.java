package com.sy.appletree.bean;

import java.util.List;

/**
 * Created by 郁智良
 * on 2016/11/14 0014.
 * des
 */

public class AreaListBean {

    /**
     * status : y
     * data : [{"areaName":"广东省","id":1},{"areaName":"湖南省","id":2},{"areaName":"北京","id":3}]
     * info : 操作成功
     */

    private String status;
    private String info;
    /**
     * areaName : 广东省
     * id : 1
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
        private String areaName;
        private int id;

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
