package com.lj.socket.entity;

import java.io.Serializable;
import java.util.Arrays;

public class SysUpgrade implements Serializable{
    private Integer sysId;
    private String version;
    private String content;
    private Integer[] areaId;

    public Integer getSysId() {
        return sysId;
    }

    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer[] getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer[] areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "SysUpgrade{" +
                "sysId=" + sysId +
                ", version='" + version + '\'' +
                ", content='" + content + '\'' +
                ", areaId=" + Arrays.toString(areaId) +
                '}';
    }
}
