package com.wemedia.model;


import com.wemedia.vo.base.BaseVo;

public class BizDislike extends BaseVo {

    private Integer disId;
    private Integer disType;
    private String userId;
    private String userIp;
    private Integer status;

    public Integer getDisId() {
        return disId;
    }

    public void setDisId(Integer disId) {
        this.disId = disId;
    }

    public Integer getDisType() {
        return disType;
    }

    public void setDisType(Integer disType) {
        this.disType = disType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}