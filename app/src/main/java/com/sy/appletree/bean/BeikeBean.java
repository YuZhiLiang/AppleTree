package com.sy.appletree.bean;

/**
 * Created by Administrator on 2016/11/1.
 */
public class BeikeBean {
    private String name;
    private String muBiao;
    private String mID;

    public BeikeBean() {
    }

    public BeikeBean(String name, String muBiao, String ID) {
        this.name = name;
        this.muBiao = muBiao;
        mID = ID;
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuBiao() {
        return muBiao;
    }

    public void setMuBiao(String muBiao) {
        this.muBiao = muBiao;
    }
}
